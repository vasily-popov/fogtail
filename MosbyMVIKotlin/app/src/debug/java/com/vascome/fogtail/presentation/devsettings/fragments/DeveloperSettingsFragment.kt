package com.vascome.fogtail.presentation.devsettings.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.pedrovgs.lynx.LynxActivity
import com.github.pedrovgs.lynx.LynxConfig
import com.jakewharton.rxbinding2.widget.*
import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.FragmentDeveloperSettingsBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.devsettings.adapters.DeveloperSettingsSpinnerAdapter
import com.vascome.fogtail.presentation.devsettings.presenters.DeveloperSettingsPresenter
import com.vascome.fogtail.presentation.devsettings.views.DevViewState
import com.vascome.fogtail.presentation.devsettings.views.DeveloperSettingsView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsFragment :
        BaseFragment<DeveloperSettingsView, DeveloperSettingsPresenter>(),
        DeveloperSettingsView {

    override fun createPresenter(): DeveloperSettingsPresenter = devPresenter

    @Inject
    lateinit var devPresenter: DeveloperSettingsPresenter

    @Inject
    lateinit var lynxConfig: LynxConfig

    private var disposables = CompositeDisposable()

    private var viewAttachedFirstTime = true


    private lateinit var binding: FragmentDeveloperSettingsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_developer_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.developerSettingsHttpLoggingLevelSpinner.adapter = DeveloperSettingsSpinnerAdapter<DeveloperSettingsSpinnerAdapter.SelectionOption>(activity.layoutInflater)
                .setSelectionOptions(HttpLoggingLevel.allValues())
        changeHttpLoggingLevel(devPresenter.httpLoggingLevel)

        binding.bShowLog.setOnClickListener {
            val context = activity
            context.startActivity(LynxActivity.getIntent(context, lynxConfig))
        }
    }

    override fun onStart() {
        super.onStart()
        if(viewAttachedFirstTime) {
            disposables = CompositeDisposable()
            subscribeEvents()
        }
        viewAttachedFirstTime = false
    }

    override fun onDestroy() {
        viewAttachedFirstTime = true
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
        super.onDestroy()
    }

    private fun subscribeEvents() {

        devPresenter.tinyDancerObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value ->
                    showMessage("TinyDancer was " + if (value) "enabled" else "disabled")
                })
                .addTo(disposables)

        devPresenter.stethoObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showAppNeedsToBeRestarted()
                })
                .addTo(disposables)

        devPresenter.leakCanaryObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value ->
                    showMessage("LeakCanary was " + if (value) "enabled" else "disabled")
                    showAppNeedsToBeRestarted()
                })
                .addTo(disposables)

        devPresenter.httpLevelObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({level ->
                    showMessage("Http logging level was changed to " + level.toString())
                })
                .addTo(disposables)

        startRelay.onNext(true)
    }

    private val startRelay = PublishSubject.create<Boolean>()

    override fun loadOnStartIntent(): Observable<Boolean> {
        return startRelay.hide().doOnComplete { Timber.d("start loading completed") }
        //return Observable.just(true).doOnComplete { Timber.d("start loading completed") }
    }

    override fun stethoSwitchChecked(): Observable<Boolean> {
        return binding.developerSettingsStethoSwitch
                .checkedChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MICROSECONDS)
    }

    override fun leakCanarySwitchChecked(): Observable<Boolean> {
        return binding.developerSettingsLeakCanarySwitch
                .checkedChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MICROSECONDS)
    }

    override fun tinyDancerSwitchChecked(): Observable<Boolean> {
        return binding.developerSettingsTinyDancerSwitch
                .checkedChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MICROSECONDS)
    }

    override fun levelChanged(): Observable<HttpLoggingInterceptor.Level> {

        return RxAdapterView
                .itemSelections(binding.developerSettingsHttpLoggingLevelSpinner)
                .skipInitialValue()
                .debounce(500, TimeUnit.MICROSECONDS)
                .map { it ->
                    (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(it) as HttpLoggingLevel).loggingLevel }
    }


    override fun render(viewState: DevViewState) {
        binding.developerSettingsGitShaTextView.text = viewState.gitSha
        binding.developerSettingsBuildDateTextView.text = viewState.buildDate
        binding.developerSettingsBuildVersionCodeTextView.text = viewState.buildVersionCode
        binding.developerSettingsBuildVersionNameTextView.text = viewState.buildVersionName
        binding.developerSettingsStethoSwitch.isChecked = viewState.isStethoEnabled
        binding.developerSettingsLeakCanarySwitch.isChecked = viewState.isLeakCanaryEnabled
        binding.developerSettingsTinyDancerSwitch.isChecked = viewState.isTinyDancerEnabled

        changeHttpLoggingLevel(viewState.httpLoggingLevel)
    }

    fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        var position = 0
        val count = binding.developerSettingsHttpLoggingLevelSpinner.count
        while (position < count) {
            if (loggingLevel == (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position) as? HttpLoggingLevel)?.loggingLevel) {
                binding.developerSettingsHttpLoggingLevelSpinner.setSelection(position)
                return
            }
            position++
        }

        throw IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel)
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showAppNeedsToBeRestarted() {
        Toast.makeText(context, "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show()
    }

    private class HttpLoggingLevel internal constructor(internal val loggingLevel: HttpLoggingInterceptor.Level) : DeveloperSettingsSpinnerAdapter.SelectionOption {

        override fun title(): String {
            return loggingLevel.toString()
        }

        companion object {

            @JvmStatic fun allValues(): List<HttpLoggingLevel> {
                val loggingLevels = HttpLoggingInterceptor.Level.values()
                val values = ArrayList<HttpLoggingLevel>(loggingLevels.size)
                loggingLevels.mapTo(values) { HttpLoggingLevel(it) }
                return values
            }
        }
    }
}
