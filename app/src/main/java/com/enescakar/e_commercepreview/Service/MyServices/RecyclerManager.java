package com.enescakar.e_commercepreview.Service.MyServices;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerManager {
    public static void bind(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
