package com.vascome.fogtail.presentation.main;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.support.design.widget.RxNavigationView;
import com.vascome.fogtail.R;
import com.vascome.fogtail.databinding.ActivityMainBinding;
import com.vascome.fogtail.presentation.base.activity.BaseActivity;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.base.other.ViewModifier;
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment;
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment;
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment;
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment;
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

public class MainActivity extends BaseActivity {

    @Inject
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @Inject
    CollectionRouter router;

    private ActivityMainBinding binding;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(viewModifier.modify(binding.getRoot()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        disposables = new CompositeDisposable();
        @SuppressLint("RxSubscribeOnError") Disposable disposable = RxNavigationView
                .itemSelections(binding.navView)
                .subscribe(this::handleMenu);
        disposables.add(disposable);


        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (fragment == null) {
            router.replaceFragment(R.id.main_frame_layout, new ListAppFragment());
        }
    }

    private void handleMenu(MenuItem menuItem) {
        int id = menuItem.getItemId();
        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);

        if (id == R.id.nav_list) {

            if (!(fragment instanceof ListAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new ListAppFragment());
            }

        } else if (id == R.id.nav_grid) {

            if (!(fragment instanceof GridAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new GridAppFragment());
            }

        } else if (id == R.id.nav_gallery) {

            if (!(fragment instanceof GalleryAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new GalleryAppFragment());
            }

        } else if (id == R.id.nav_stack) {
            if (!(fragment instanceof StackAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new StackAppFragment());
            }

        } else if (id == R.id.nav_carousel) {
            if (!(fragment instanceof CarouselAppFragment)) {
                router.replaceFragment(R.id.main_frame_layout, new CarouselAppFragment());
            }

        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDestroy();
    }
}

