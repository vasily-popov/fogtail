package com.vascome.fogtail.presentation.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class DetailViewModel
@Inject constructor() :ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var descriptionLiveData = MutableLiveData<String>()
    val itemDescription: LiveData<String> =  descriptionLiveData

    private var nameLiveData = MutableLiveData<String>()
    val name: LiveData<String>  =  nameLiveData

    private var imageUrlLiveData = MutableLiveData<String>()
    val imageUrl: LiveData<String>  =  imageUrlLiveData

    private val setItemCommand: PublishRelay<RecAreaItem> = PublishRelay.create<RecAreaItem>()
    fun setItem(item: RecAreaItem?) {
        setItemCommand.accept(item)
    }

    init {

        val itemObservable = setItemCommand.share()

        disposables.add(
                itemObservable.map({ it.shortDescription })
                        .subscribe(descriptionLiveData::setValue)
        )

        disposables.add(
                itemObservable.map({ it.name })
                        .subscribe(nameLiveData::setValue)
        )

        disposables.add(
                itemObservable.map({ it.imageUrl })
                        .subscribe({ it?.let { it1 -> imageUrlLiveData.value = it1 } })
        )
    }

    override fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}