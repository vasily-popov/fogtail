package com.vascome.fogtail.presentation.main

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem

import com.jakewharton.rxbinding2.support.design.widget.RxNavigationView
import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.ActivityMainBinding
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.Companion.MAIN_ACTIVITY_VIEW_MODIFIER
import com.vascome.fogtail.presentation.base.activity.BaseActivity
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.base.other.ViewModifier
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment

import javax.inject.Inject
import javax.inject.Named

import io.reactivex.disposables.CompositeDisposable

class MainActivity : BaseActivity() {

    @Inject
    @field:Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    lateinit var viewModifier: ViewModifier

    @Inject
    lateinit var router: CollectionRouter

    private lateinit var binding: ActivityMainBinding
    private var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(viewModifier.modify(binding.root))

        val toggle = ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        disposables = CompositeDisposable()
        @SuppressLint("RxSubscribeOnError")
        val disposable = RxNavigationView
                .itemSelections(binding.navView)
                .subscribe({ this.handleMenu(it) })
        disposables?.add(disposable)


        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout) as? BaseFragment
        if (fragment == null) {
            router.replaceFragment(R.id.main_frame_layout, ListAppFragment())
        }
    }

    private fun handleMenu(menuItem: MenuItem) {
        val id = menuItem.itemId
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout) as BaseFragment

        if (id == R.id.nav_list) {

            if (fragment !is ListAppFragment) {
                router.replaceFragment(R.id.main_frame_layout, ListAppFragment())
            }

        } else if (id == R.id.nav_grid) {

            if (fragment !is GridAppFragment) {
                router.replaceFragment(R.id.main_frame_layout, GridAppFragment())
            }

        } else if (id == R.id.nav_gallery) {

            if (fragment !is GalleryAppFragment) {
                router.replaceFragment(R.id.main_frame_layout, GalleryAppFragment())
            }

        } else if (id == R.id.nav_stack) {
            if (fragment !is StackAppFragment) {
                router.replaceFragment(R.id.main_frame_layout, StackAppFragment())
            }

        } else if (id == R.id.nav_carousel) {
            if (fragment !is CarouselAppFragment) {
                router.replaceFragment(R.id.main_frame_layout, CarouselAppFragment())
            }

        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        binding.unbind()
        (disposables?.isDisposed?.run {
            disposables?.dispose()
        })
        super.onDestroy()
    }
}

