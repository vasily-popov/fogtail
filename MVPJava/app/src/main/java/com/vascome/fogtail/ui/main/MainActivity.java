package com.vascome.fogtail.ui.main;

import android.os.Bundle;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.ui.base.other.ViewModifier;
import com.vascome.fogtail.ui.base.views.BaseActivity;

import javax.inject.Inject;
import javax.inject.Named;

import static com.vascome.fogtail.developer_settings.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

public class MainActivity extends BaseActivity {

    @Inject
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FogtailApplication.get(this).applicationComponent().inject(this);
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        /*
        if (savedInstanceState == null) {

            // TODO switch to ScreenValley or Flow & Mortar
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ItemsFragment())
                    .commit();
        }*/
    }
}

