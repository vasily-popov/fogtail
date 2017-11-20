package com.vascome.fogtail.ui;

import android.os.Bundle;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FogtailApplication.get(this).applicationComponent().inject(this);
        setContentView(R.layout.activity_main);

        //setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
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

