package com.vascome.fogtail.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.vascome.fogtail.FogtailApplication

import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.ActivityMainBinding
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.Companion.MAIN_ACTIVITY_VIEW_MODIFIER
import com.vascome.fogtail.di.presentation.main.CollectionComponent
import com.vascome.fogtail.presentation.base.other.ViewModifier
import com.vascome.fogtail.presentation.base.BaseActivity
import com.vascome.fogtail.presentation.main.controllers.carousel.CarouselController
import com.vascome.fogtail.presentation.main.controllers.gallery.GalleryController
import com.vascome.fogtail.presentation.main.controllers.list.ListViewController
import com.vascome.fogtail.presentation.main.controllers.stack.StackController
import com.vascome.fogtail.presentation.main.controllers.table.GridController

import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @field:Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    lateinit var viewModifier: ViewModifier

    lateinit private var collectionComponent: CollectionComponent


    private lateinit var router: Router
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionComponent = FogtailApplication.get(this).appComponent().collectionComponent()
        collectionComponent.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(viewModifier.modify(binding.root, this))
        //setContentView(binding.root)
        val toggle = ActionBarDrawerToggle(
                this, binding.mainDrawerLayout, toolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        router = Conductor.attachRouter(this, binding.mainFrameLayout, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(ListViewController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_list -> {
                router.popToRoot()
            }
            R.id.nav_grid -> {

                router.replaceTopController(RouterTransaction.with(GridController()))
            }
            R.id.nav_gallery -> {
                router.replaceTopController(RouterTransaction.with(GalleryController()))
            }
            R.id.nav_stack -> {
                router.replaceTopController(RouterTransaction.with(StackController()))
            }
            R.id.nav_carousel -> {
                router.replaceTopController(RouterTransaction.with(CarouselController()))
            }
        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun collectionComponent() : CollectionComponent {
        return collectionComponent
    }
}

