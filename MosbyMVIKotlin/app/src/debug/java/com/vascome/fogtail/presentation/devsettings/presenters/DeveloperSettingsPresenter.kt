package com.vascome.fogtail.presentation.devsettings.presenters

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.presentation.devsettings.views.DevViewState
import com.vascome.fogtail.presentation.devsettings.views.DeveloperSettingsView
import com.vascome.fogtail.utils.AnalyticsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsPresenter
@Inject constructor(private val developerSettingsModel: DeveloperSettingsModelImpl,
                    private val analyticsModel: AnalyticsModel)
    : MviBasePresenter<DeveloperSettingsView, DevViewState>(), DeveloperSettingsView.DeveloperSettingsFeedback {


    private val tinyRelay = PublishSubject.create<Boolean>()
    private val stethoRelay = PublishSubject.create<Boolean>()
    private val leakRelay = PublishSubject.create<Boolean>()
    private val levelRelay = PublishSubject.create<HttpLoggingInterceptor.Level>()


    override val tinyDancerObservable: Observable<Boolean> = tinyRelay.hide()
    override val stethoObservable: Observable<Boolean> = stethoRelay.hide()
    override val leakCanaryObservable: Observable<Boolean> = leakRelay.hide()
    override val httpLevelObservable: Observable<HttpLoggingInterceptor.Level> = levelRelay.hide()

    override val httpLoggingLevel: HttpLoggingInterceptor.Level
        get() {return developerSettingsModel.httpLoggingLevel}


    private var stethoDisposal: Disposable? = null
    private var leakDisposal: Disposable? = null
    private var tinyDisposal: Disposable? = null
    private var levelDisposal: Disposable? = null

    override fun bindIntents() {

        stethoDisposal = intent(DeveloperSettingsView::stethoSwitchChecked)
                .filter { value -> developerSettingsModel.isStethoEnabled != value }
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { value ->
                    analyticsModel.sendEvent("developer_settings_stetho_" + booleanToEnabledDisabled(value))
                    developerSettingsModel.changeStethoState(value)
                    return@switchMap Observable.just(value)
                }
                .doOnNext({ _ -> Timber.d("intent: stetho selection") })
                .subscribe({ value -> stethoRelay.onNext(value) })

        leakDisposal = intent(DeveloperSettingsView::leakCanarySwitchChecked)
                .filter { value -> developerSettingsModel.isLeakCanaryEnabled != value }
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { value ->
                    analyticsModel.sendEvent("developer_settings_leak_canary_" + booleanToEnabledDisabled(value))
                    developerSettingsModel.changeLeakCanaryState(value)
                    return@switchMap Observable.just(value)
                }
                .doOnNext({ _ -> Timber.d("intent: leakCanary selection") })
                .subscribe({ aBoolean -> leakRelay.onNext(aBoolean) })

        tinyDisposal = intent(DeveloperSettingsView::tinyDancerSwitchChecked)
                .filter { value -> developerSettingsModel.isTinyDancerEnabled != value }
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { value ->
                    analyticsModel.sendEvent("developer_settings_tiny_dancer_" + booleanToEnabledDisabled(value))
                    developerSettingsModel.changeTinyDancerState(value)
                    return@switchMap Observable.just(value)
                }
                .doOnNext({ _ -> Timber.d("intent: tiny selection") })
                .subscribe({ aBoolean -> tinyRelay.onNext(aBoolean) })


        levelDisposal = intent(DeveloperSettingsView::levelChanged)
                .filter { value -> developerSettingsModel.httpLoggingLevel != value }
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { value ->
                    analyticsModel.sendEvent("developer_settings_http_logging_level_" + value)
                    developerSettingsModel.changeHttpLoggingLevel(value)
                    return@switchMap Observable.just(value)
                }
                .doOnNext({ _ -> Timber.d("intent: Http Level selection") })
                .subscribe({ level -> levelRelay.onNext(level) })

        val initLoad = intent(DeveloperSettingsView::loadOnStartIntent)
                .doOnNext({ _ -> Timber.d("intent: initial load") })
                .observeOn(AndroidSchedulers.mainThread())
                .map { _ ->
                    DevViewState.Builder()
                            .gitSha(developerSettingsModel.gitSha)
                            .buildDate(developerSettingsModel.buildDate)
                            .buildVersionCode(developerSettingsModel.buildVersionCode)
                            .buildVersionName(developerSettingsModel.buildVersionName)
                            .stethoEnabled(developerSettingsModel.isStethoEnabled)
                            .leakCanaryEnabled(developerSettingsModel.isLeakCanaryEnabled)
                            .tinyDancerEnabled(developerSettingsModel.isTinyDancerEnabled)
                            .httpLoggingLevel(developerSettingsModel.httpLoggingLevel)
                            .build()
                }

        subscribeViewState(initLoad, DeveloperSettingsView::render)

    }

    override fun unbindIntents() {
        stethoDisposal?.dispose()
        stethoDisposal = null
        leakDisposal?.dispose()
        leakDisposal = null
        tinyDisposal?.dispose()
        tinyDisposal = null
        levelDisposal?.dispose()
        levelDisposal = null
        super.unbindIntents()
    }

    private fun booleanToEnabledDisabled(enabled: Boolean): String {
        return if (enabled) "enabled" else "disabled"
    }

}

