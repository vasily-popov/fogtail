package com.vascome.fogtail.di.presentation.main

import com.vascome.fogtail.di.ActivityScope
import dagger.Subcomponent
import com.vascome.fogtail.presentation.MainActivity
import com.vascome.fogtail.di.ControllerScope
import com.vascome.fogtail.presentation.detail.DetailController
import com.vascome.fogtail.presentation.main.controllers.carousel.CarouselController
import com.vascome.fogtail.presentation.main.controllers.gallery.GalleryController
import com.vascome.fogtail.presentation.main.controllers.list.ListViewController
import com.vascome.fogtail.presentation.main.controllers.stack.StackController
import com.vascome.fogtail.presentation.main.controllers.table.GridController


/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Subcomponent(modules = [(CollectionModule::class)])
@ActivityScope
interface CollectionComponent {

    fun inject(mainActivity: MainActivity)

    fun listComponent(): ListComponent
    fun gridComponent(): GridComponent
    fun carouselComponent(): CarouselComponent
    fun galleryComponent(): GalleryComponent
    fun stackComponent(): StackComponent
    fun detailComponent(): DetailComponent


}

@Subcomponent
@ControllerScope
interface CarouselComponent {
    fun inject(items: CarouselController)
}

@Subcomponent
@ControllerScope
interface GridComponent {
    fun inject(items: GridController)
}

@Subcomponent
@ControllerScope
interface ListComponent {
    fun inject(items: ListViewController)
}

@Subcomponent
@ControllerScope
interface GalleryComponent {
    fun inject(items: GalleryController)
}

@Subcomponent
@ControllerScope
interface StackComponent {
    fun inject(items: StackController)
}

@Subcomponent
@ControllerScope
interface DetailComponent {
    fun inject(items: DetailController)
}