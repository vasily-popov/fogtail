package com.vascome.fogtail.presentation.main.fragment.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class ListAreaAdapter extends RecyclerView.Adapter<ListAreaAdapter.ViewHolder> {


    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final AppImageLoader imageLoader;

    private final CollectionAreaItemListener listAreaItemListener;

    @NonNull
    private List<RecAreaItem> items = emptyList();

    public ListAreaAdapter(@NonNull LayoutInflater layoutInflater,
                           @NonNull AppImageLoader imageLoader,
                           CollectionAreaItemListener listAreaItemListener) {
        this.layoutInflater = layoutInflater;
        this.imageLoader = imageLoader;
        this.listAreaItemListener = listAreaItemListener;
    }

    public void setData(@NonNull List<RecAreaItem> items) {
        this.items = unmodifiableList(items); // Prevent possible side-effects.
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false), imageLoader);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(items.get(position));
        viewHolder.itemView.setOnClickListener(v-> listAreaItemListener.onItemClick(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final AppImageLoader imageLoader;

        private final ImageView imageView;

        private final TextView titleTextView;

        private final TextView shortDescriptionTextView;

        ViewHolder(@NonNull View itemView, @NonNull AppImageLoader imageLoader) {
            super(itemView);
            this.imageLoader = imageLoader;
            imageView = itemView.findViewById(R.id.list_item_image_view);
            titleTextView = itemView.findViewById(R.id.list_item_title);
            shortDescriptionTextView = itemView.findViewById(R.id.list_item_description);
        }

        void bind(@NonNull RecAreaItem item) {
            imageLoader.downloadInto(item.imageUrl(), imageView);
            titleTextView.setText(item.name());
            shortDescriptionTextView.setText(item.shortDescription());
        }

    }
}
