package com.vascome.fogtail.presentation.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem

import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.ActivityMainBinding
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.Companion.MAIN_ACTIVITY_VIEW_MODIFIER
import com.vascome.fogtail.presentation.base.other.ViewModifier
import com.vascome.fogtail.presentation.base.views.BaseActivity
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment
import com.vascome.fogtail.utils.replaceFragment

import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @field:Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    lateinit var viewModifier: ViewModifier

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(viewModifier.modify(binding.root))

        val toggle = ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout)

        if (fragment == null) {
            replaceFragment(this,R.id.main_frame_layout, ListAppFragment())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout)

        when (id) {
            R.id.nav_list -> {
                if (fragment !is ListAppFragment) {
                    replaceFragment(this, R.id.main_frame_layout, ListAppFragment())
                }
            }
            R.id.nav_grid -> {
                if (fragment !is GridAppFragment) {
                    replaceFragment(this, R.id.main_frame_layout, GridAppFragment())
                }
            }
            R.id.nav_gallery -> {
                if (fragment !is GalleryAppFragment) {
                    replaceFragment(this, R.id.main_frame_layout, GalleryAppFragment())
                }
            }
            R.id.nav_stack -> {
                if (fragment !is StackAppFragment) {
                    replaceFragment(this, R.id.main_frame_layout, StackAppFragment())
                }
            }
            R.id.nav_carousel -> {
                if (fragment !is CarouselAppFragment) {
                    replaceFragment(this, R.id.main_frame_layout, CarouselAppFragment())
                }
            }
            else -> {
            }
        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

