package com.enescakar.e_commercepreview.Service.MyServices;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerManager {
    private final RecyclerView recyclerView;

    public RecyclerManager(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void bind(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
