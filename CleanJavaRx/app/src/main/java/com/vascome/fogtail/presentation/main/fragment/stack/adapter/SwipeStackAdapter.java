package com.vascome.fogtail.presentation.main.fragment.stack.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class SwipeStackAdapter extends BaseAdapter {

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final AppImageLoader imageLoader;

    private final CollectionAreaItemListener collectionAreaItemListener;

    @NonNull
    private List<RecAreaItem> items = emptyList();

    public SwipeStackAdapter(@NonNull LayoutInflater layoutInflater,
                             @NonNull AppImageLoader imageLoader,
                             CollectionAreaItemListener collectionAreaItemListener) {
        this.layoutInflater = layoutInflater;
        this.imageLoader = imageLoader;
        this.collectionAreaItemListener = collectionAreaItemListener;
    }

    public void setData(@NonNull List<RecAreaItem> items) {
        this.items = unmodifiableList(items); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RecAreaItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View holder = convertView;
        if (holder == null) {
            holder = layoutInflater.inflate(R.layout.gallery_item, parent, false);
        }
        RecAreaItem item = getItem(position);
        ImageView imageView = holder.findViewById(R.id.list_item_image_view);
        TextView titleTextView = holder.findViewById(R.id.list_item_title);
        TextView shortDescriptionTextView = holder.findViewById(R.id.list_item_description);
        imageLoader.downloadInto(item.imageUrl(), imageView);
        titleTextView.setText(item.name());
        shortDescriptionTextView.setText(item.shortDescription());
        holder.setOnClickListener(view -> collectionAreaItemListener.onItemClick(item));
        return holder;
    }
}
