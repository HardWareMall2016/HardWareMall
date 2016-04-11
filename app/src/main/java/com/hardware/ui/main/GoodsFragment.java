package com.hardware.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardware.R;
import com.hardware.adapter.GoodsCategoryAdapter;
import com.hardware.adapter.GridSpacingItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recylcer;

    public GoodsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int spanCount = 3;
        int spacing = 50;
        boolean includeEdge = true;
        recylcer.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 3);
        recylcer.setLayoutManager(mgr);
        recylcer.setAdapter(new GoodsCategoryAdapter(getActivity()));
    }
}
