package com.hardware.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hardware.R;
import com.hardware.adapter.GoodsListAdapter;
import com.hardware.bean.GoodsListBean;
import com.hardware.bean.GoodsListBean.GoodsInfo;
import com.hardware.common.HardWareApi;
import com.hardware.network.callback.StringCallback;
import com.hardware.utils.JsonHelper;
import com.squareup.okhttp.Request;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hover on 2016/4/10.
 */
public class GoodsListFragment extends MBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.common_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GoodsListAdapter goodsListAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 0x002:
                    Toast.makeText(context, "网络连接异常,请稍候再试!", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goods_list_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadData();
    }

    private void loadData() {
        HardWareApi.getInstance().getGoodsList(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.i("tag", "getGoodsList onError = " + e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.i("tag", "getGoodsList onResponse = " + response.toString());
                GoodsListBean goodsListBean = JsonHelper.getInstance().getObject(response, GoodsListBean.class);
                List<GoodsInfo> datas = goodsListBean.getMessage();
                goodsListAdapter = new GoodsListAdapter(getActivity(), datas);
                recyclerView.setAdapter(goodsListAdapter);
            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(0x002, 3000);
    }
}
