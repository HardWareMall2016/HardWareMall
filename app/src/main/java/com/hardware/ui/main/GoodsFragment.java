package com.hardware.ui.main;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.hardware.R;
import com.hardware.adapter.GoodsCategoryAdapter;
import com.hardware.adapter.GridSpacingItemDecoration;
import com.hardware.bean.GoodsFirstCategoryBean;
import com.hardware.bean.GoodsFirstCategoryBean.MessageBean;
import com.hardware.common.HardWareApi;
import com.hardware.network.callback.StringCallback;
import com.hardware.utils.ACache;
import com.hardware.utils.JsonCache;
import com.hardware.utils.JsonHelper;
import com.hardware.utils.StringTools;
import com.squareup.okhttp.Request;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends ABaseFragment {
    @ViewInject(id = R.id.recyclerView)
    RecyclerView recylcer;
    private List<MessageBean> datas;
    private JsonCache cache;
    private ACache mCache;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    recylcer.setAdapter(new GoodsCategoryAdapter(getActivity(), datas));
                    break;
                case 0x002:
                    break;
            }
        }
    };

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
        cache = new JsonCache(getContext());
        mCache = ACache.get(getContext());
    }

    @Override
    public void requestData() {
        super.requestData();
        String cacheString = cache.readString("firstcategory");
        if (StringTools.isNotEmpty(cacheString)) {
            GoodsFirstCategoryBean goodsFirstCategoryBean = JsonHelper.getInstance().getObject(cacheString, GoodsFirstCategoryBean.class);
            Log.i("tag", "cacheString = " + goodsFirstCategoryBean.toString());
            datas = goodsFirstCategoryBean.getMessage();
            mHandler.sendEmptyMessage(0x001);
        } else {
            HardWareApi.getInstance().getGoodsFirstCategory(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.i("tag", "onError = " + e.toString());
                }

                @Override
                public void onResponse(String response) {
                    Log.i("tag", "onResponse = " + response.toString());
                    GoodsFirstCategoryBean goodsFirstCategoryBean = JsonHelper.getInstance().getObject(response, GoodsFirstCategoryBean.class);
                    Log.i("tag", "onResponse = " + goodsFirstCategoryBean.toString());
                    datas = goodsFirstCategoryBean.getMessage();
                    if (null != response && datas.size() > 0) {
                        cache.cacheString("firstcategory", response);
                        mHandler.sendEmptyMessage(0x001);
                    }
                }
            });
        }
    }
}
