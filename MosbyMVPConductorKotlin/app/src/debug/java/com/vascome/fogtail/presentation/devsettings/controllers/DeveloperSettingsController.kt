package com.vascome.fogtail.presentation.devsettings.controllers

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.annotation.AnyThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.github.pedrovgs.lynx.LynxActivity

import com.github.pedrovgs.lynx.LynxConfig
import com.vascome.fogtail.FogtailApplication
import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.FragmentDeveloperSettingsBinding
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.devsettings.adapters.DeveloperSettingsSpinnerAdapter
import com.vascome.fogtail.presentation.devsettings.presenters.DeveloperSettingsPresenter
import com.vascome.fogtail.presentation.devsettings.views.DevViewState
import com.vascome.fogtail.presentation.devsettings.views.DeveloperSettingsContract

import javax.inject.Inject

import okhttp3.logging.HttpLoggingInterceptor

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsController :
        BaseViewController<DeveloperSettingsContract.View, DeveloperSettingsContract.Presenter, DevViewState>(),
        DeveloperSettingsContract.View {

    private lateinit var binding: FragmentDeveloperSettingsBinding

    override fun createViewState()= DevViewState()

    override fun onNewViewStateInstance() {
    }

    override fun createPresenter(): DeveloperSettingsContract.Presenter = devPresenter

    @Inject
    lateinit var devPresenter: DeveloperSettingsPresenter

    @Inject
    lateinit var lynxConfig: LynxConfig


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if(!isInjected) {
            FogtailApplication.get(activity!!.applicationContext).appComponent().devSettingsComponent().inject(this)
            isInjected = true
        }
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_developer_settings, container, false)
        initRecyclerView(binding.root.context)
        return binding.root
    }


    fun initRecyclerView(context: Context) {

        binding.developerSettingsHttpLoggingLevelSpinner.adapter = DeveloperSettingsSpinnerAdapter<DeveloperSettingsSpinnerAdapter.SelectionOption>(activity!!.layoutInflater)
                .setSelectionOptions(HttpLoggingLevel.allValues())

        binding.developerSettingsStethoSwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeStethoState(checked) }

        binding.developerSettingsTinyDancerSwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeTinyDancerState(checked) }

        binding.developerSettingsHttpLoggingLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, l: Long) {
                presenter.changeHttpLoggingLevel(
                        (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position) as HttpLoggingLevel).loggingLevel)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        binding.developerSettingsLeakCanarySwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeLeakCanaryState(checked) }


        binding.bShowLog.setOnClickListener {
            activity!!.startActivity(LynxActivity.getIntent(context, lynxConfig))
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.syncDeveloperSettings()
    }

    @AnyThread
    override fun changeGitSha(gitSha: String) {
        runOnUI(Runnable{ binding.developerSettingsGitShaTextView.text = gitSha })
    }

    @AnyThread
    override fun changeBuildDate(date: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildDateTextView.text = date })
    }

    @AnyThread
    override fun changeBuildVersionCode(versionCode: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildVersionCodeTextView.text = versionCode })

    }

    @AnyThread
    override fun changeBuildVersionName(versionName: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildVersionNameTextView.text = versionName })
    }

    @AnyThread
    override fun changeStethoState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsStethoSwitch.isChecked = enabled })
    }

    @AnyThread
    override fun changeLeakCanaryState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsLeakCanarySwitch.isChecked = enabled })
    }

    @AnyThread
    override fun changeTinyDancerState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsTinyDancerSwitch.isChecked = enabled })

    }

    @AnyThread
    override fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        runOnUI(Runnable{
            var position = 0
            val count = binding.developerSettingsHttpLoggingLevelSpinner.count
            while (position < count) {
                if (loggingLevel == (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position) as? HttpLoggingLevel)?.loggingLevel) {
                    binding.developerSettingsHttpLoggingLevelSpinner.setSelection(position)
                    return@Runnable
                }
                position++
            }

            throw IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel)
        })
    }

    @AnyThread
    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    @AnyThread
    override fun showAppNeedsToBeRestarted() {
        Toast.makeText(activity, "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show()
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
