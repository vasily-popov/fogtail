package com.vascome.fogtail.presentation.main.fragment.stack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import java.util.Collections.emptyList
import java.util.Collections.unmodifiableList

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class SwipeStackAdapter(private val layoutInflater: LayoutInflater,
                        private val imageLoader: AppImageLoader,
                        private val collectionAreaItemListener: CollectionAreaItemListener) : BaseAdapter() {

    private var items = emptyList<RecAreaItem>()

    fun setData(items: List<RecAreaItem>) {
        this.items = unmodifiableList(items) // Prevent possible side-effects.
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): RecAreaItem {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.gallery_item, parent, false)
            viewHolder = ViewHolder(view)
            view!!.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val item = getItem(position)
        setupData(viewHolder, item)
        view.setOnClickListener{ _ -> collectionAreaItemListener.onItemClick(item) }
        return view
    }

    private fun setupData(viewHolder: ViewHolder, item: RecAreaItem) {

        viewHolder.titleTextView.text = item.name
        viewHolder.shortDescriptionTextView.text = item.shortDescription

        item.imageUrl?.run {
            imageLoader.downloadInto(item.imageUrl, viewHolder.imageView)
        }
    }

    private inner class ViewHolder(convertView: View) {
        var imageView: ImageView = convertView.findViewById(R.id.list_item_image_view) as ImageView
        var titleTextView: TextView = convertView.findViewById(R.id.list_item_title) as TextView
        var shortDescriptionTextView: TextView = convertView.findViewById(R.id.list_item_description) as TextView

    }
}
