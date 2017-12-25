package com.vascome.fogtail.presentation.main.fragment.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

internal class ListAreaAdapter(private val layoutInflater: LayoutInflater,
                      private val imageLoader: AppImageLoader,
                      private val listAreaItemListener: CollectionAreaItemListener) : RecyclerView.Adapter<ListAreaAdapter.ViewHolder>() {

    private var items = emptyList<RecAreaItem>()

    fun setData(items: List<RecAreaItem>) {
        this.items = unmodifiableList(items) // Prevent possible side-effects.
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false), imageLoader)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
        viewHolder.itemView.setOnClickListener { _ -> listAreaItemListener.onItemClick(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal inner class ViewHolder(itemView: View, private val imageLoader: AppImageLoader) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.list_item_image_view)

        private val titleTextView: TextView = itemView.findViewById(R.id.list_item_title)

        private val shortDescriptionTextView: TextView = itemView.findViewById(R.id.list_item_description)

        fun bind(item: RecAreaItem) {
            item.imageUrl?.let { url -> imageLoader.downloadInto(url,imageView) }
            titleTextView.text = item.name
            shortDescriptionTextView.text = item.shortDescription
        }

    }
}
