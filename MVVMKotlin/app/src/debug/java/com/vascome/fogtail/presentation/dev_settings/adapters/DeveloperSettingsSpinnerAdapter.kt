package com.vascome.fogtail.presentation.dev_settings.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.vascome.fogtail.R

import java.util.Collections.emptyList
import java.util.Collections.unmodifiableList

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsSpinnerAdapter<T : DeveloperSettingsSpinnerAdapter.SelectionOption>(private val layoutInflater: LayoutInflater) : BaseAdapter() {

    private var selectionOptions = emptyList<T>()

    fun setSelectionOptions(selectionOptions: List<T>): DeveloperSettingsSpinnerAdapter<T> {
        this.selectionOptions = unmodifiableList(selectionOptions)
        notifyDataSetChanged()
        return this
    }

    override fun getCount(): Int {
        return selectionOptions.size
    }

    override fun getItem(position: Int): T {
        return selectionOptions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, parent, false)
            viewHolder = ViewHolder(view!!)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.bindItem(selectionOptions[position])
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_developer_settings_spinner_drop_down_item, parent, false)
            viewHolder = ViewHolder(view!!)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.bindItem(selectionOptions[position])
        return view
    }

    internal class ViewHolder(itemView: View) {

        private val titleTextView: TextView = itemView.findViewById(R.id.list_developer_settings_spinner_item_title_text_view)

        fun bindItem(selectionOption: SelectionOption) {
            titleTextView.text = selectionOption.title()
        }
    }

    interface SelectionOption {

        fun title(): String
    }
}
