package com.vascome.fogtail.ui.dev_settings.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;
import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.databinding.FragmentDeveloperSettingsBinding;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;
import com.vascome.fogtail.ui.dev_settings.adapters.DeveloperSettingsSpinnerAdapter;
import com.vascome.fogtail.ui.dev_settings.presenters.DeveloperSettingsPresenter;
import com.vascome.fogtail.ui.dev_settings.views.DeveloperSettingsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class DeveloperSettingsFragment extends BaseFragment implements DeveloperSettingsView {

    @Inject
    DeveloperSettingsPresenter presenter;

    @Inject
    LynxConfig lynxConfig;

    private FragmentDeveloperSettingsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FogtailApplication.get(getContext()).appComponent().plusDeveloperSettingsComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_developer_settings, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.developerSettingsHttpLoggingLevelSpinner
                .setAdapter(new DeveloperSettingsSpinnerAdapter<>(getActivity().getLayoutInflater())
                .setSelectionOptions(HttpLoggingLevel.allValues()));

        binding.developerSettingsStethoSwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> presenter.changeStethoState(checked));

        binding.developerSettingsTinyDancerSwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> presenter.changeTinyDancerState(checked));



        binding.developerSettingsHttpLoggingLevelSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        presenter.changeHttpLoggingLevel(
                                ((HttpLoggingLevel) binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        binding.developerSettingsLeakCanarySwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> presenter.changeLeakCanaryState(checked));


        binding.bShowLog.setOnClickListener(view1 -> {
            Context context = getActivity();
            context.startActivity(LynxActivity.getIntent(context, lynxConfig));
        });
        presenter.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.syncDeveloperSettings();
    }


    @Override
    @AnyThread
    public void changeGitSha(@NonNull String gitSha) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsGitShaTextView != null;
            binding.developerSettingsGitShaTextView.setText(gitSha);
        });
    }

    @Override
    @AnyThread
    public void changeBuildDate(@NonNull String date) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsBuildDateTextView != null;
            binding.developerSettingsBuildDateTextView.setText(date);
        });
    }

    @Override
    @AnyThread
    public void changeBuildVersionCode(@NonNull String versionCode) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsBuildVersionCodeTextView != null;
            binding.developerSettingsBuildVersionCodeTextView.setText(versionCode);
        });
    }

    @Override
    @AnyThread
    public void changeBuildVersionName(@NonNull String versionName) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsBuildVersionNameTextView != null;
            binding.developerSettingsBuildVersionNameTextView.setText(versionName);
        });
    }

    @Override
    @AnyThread
    public void changeStethoState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsStethoSwitch != null;
            binding.developerSettingsStethoSwitch.setChecked(enabled);
        });
    }

    @Override
    @AnyThread
    public void changeLeakCanaryState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsLeakCanarySwitch != null;
            binding.developerSettingsLeakCanarySwitch.setChecked(enabled);
        });
    }

    @Override
    @AnyThread
    public void changeTinyDancerState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsTinyDancerSwitch != null;
            binding.developerSettingsTinyDancerSwitch.setChecked(enabled);
        });
    }

    @Override
    @AnyThread
    public void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert binding.developerSettingsHttpLoggingLevelSpinner != null;

            for (int position = 0, count = binding.developerSettingsHttpLoggingLevelSpinner.getCount(); position < count; position++) {
                if (loggingLevel == ((HttpLoggingLevel) binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel) {
                    binding.developerSettingsHttpLoggingLevelSpinner.setSelection(position);
                    return;
                }
            }

            throw new IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel);
        });
    }

    @Override
    @AnyThread
    public void showMessage(@NonNull String message) {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    @AnyThread
    public void showAppNeedsToBeRestarted() {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show());
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }

    private static class HttpLoggingLevel implements DeveloperSettingsSpinnerAdapter.SelectionOption {

        @NonNull
        final HttpLoggingInterceptor.Level loggingLevel;

        HttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        @NonNull
        @Override
        public String title() {
            return loggingLevel.toString();
        }

        @NonNull
        static List<HttpLoggingLevel> allValues() {
            final HttpLoggingInterceptor.Level[] loggingLevels = HttpLoggingInterceptor.Level.values();
            final List<HttpLoggingLevel> values = new ArrayList<>(loggingLevels.length);
            for (HttpLoggingInterceptor.Level loggingLevel : loggingLevels) {
                values.add(new HttpLoggingLevel(loggingLevel));
            }
            return values;
        }
    }
}
