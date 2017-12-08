package com.vascome.fogtail.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.databinding.ActivityMainBinding;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;
import com.vascome.fogtail.ui.base.other.ViewModifier;
import com.vascome.fogtail.ui.base.utils.ActivityUtils;
import com.vascome.fogtail.ui.base.views.BaseActivity;
import com.vascome.fogtail.ui.main.carousel.CarouselAppFragment;
import com.vascome.fogtail.ui.main.gallery.GalleryAppFragment;
import com.vascome.fogtail.ui.main.list.ListAppFragment;
import com.vascome.fogtail.ui.main.stack.StackAppFragment;
import com.vascome.fogtail.ui.main.table.GridAppFragment;

import javax.inject.Inject;
import javax.inject.Named;

import static com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FogtailApplication.get(this).collectionComponent().inject(this);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(viewModifier.modify(binding.getRoot()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (fragment == null) {

            ActivityUtils.replaceFragmentToActivity(
                    getSupportFragmentManager(), new ListAppFragment(), R.id.main_frame_layout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);

        if (id == R.id.nav_list) {

            if (!(fragment instanceof ListAppFragment)) {

                ActivityUtils.replaceFragmentToActivity(
                        getSupportFragmentManager(),  new ListAppFragment(), R.id.main_frame_layout);
            }

        } else if (id == R.id.nav_grid) {

            if (!(fragment instanceof GridAppFragment)) {

                ActivityUtils.replaceFragmentToActivity(
                        getSupportFragmentManager(), new GridAppFragment(), R.id.main_frame_layout);
            }

        } else if (id == R.id.nav_gallery) {

            if (!(fragment instanceof GalleryAppFragment)) {

                ActivityUtils.replaceFragmentToActivity(
                        getSupportFragmentManager(), new GalleryAppFragment(), R.id.main_frame_layout);
            }

        } else if (id == R.id.nav_stack) {
            if (!(fragment instanceof StackAppFragment)) {
                ActivityUtils.replaceFragmentToActivity(
                        getSupportFragmentManager(), new StackAppFragment(), R.id.main_frame_layout);
            }

        } else if (id == R.id.nav_carousel) {
            if (!(fragment instanceof CarouselAppFragment)) {

                ActivityUtils.replaceFragmentToActivity(
                        getSupportFragmentManager(), new CarouselAppFragment(), R.id.main_frame_layout);
            }

        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}

