package com.hardware.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.hardware.R;
import com.hardware.adapter.GoodsCategoryAdapter;
import com.hardware.adapter.GridSpacingItemDecoration;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends ABaseFragment {
    @ViewInject(id = R.id.recyclerView)
    RecyclerView recylcer;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        int spanCount = 3;
        int spacing = 50;
        boolean includeEdge = true;
        recylcer.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 3);
        recylcer.setLayoutManager(mgr);
        recylcer.setAdapter(new GoodsCategoryAdapter(getActivity()));
    }
}
