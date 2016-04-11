package com.hardware.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hardware.R;
import com.hardware.adapter.GridViewAdapter;
import com.hardware.bean.Type;
import com.hardware.common.Model;
import com.hardware.ui.activity.GoodsListActivity;
import com.hardware.ui.activity.GoodsRecommendActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProTypeFragment extends MBaseFragment {

    private ArrayList<Type> list;
    @Bind(R.id.gridview)
    GridView gridView;
    private GridViewAdapter adapter;
    private Type type;
    private String typename;
    private int icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_type, null);
        ButterKnife.bind(this, view);
        int index = getArguments().getInt("index");

        typename = Model.wjjdList[index];
        icon = Model.iconList[index];

        GetTypeList();
        adapter = new GridViewAdapter(getActivity(), list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
                readyGo(GoodsListActivity.class);
            }
        });

        return view;
    }

    private void GetTypeList() {
        list = new ArrayList<Type>();
        for (int i = 1; i < 10; i++) {
            type = new Type(i, typename + i, icon);
            list.add(type);
        }
    }

    @OnClick({R.id.goods_recommend_rl})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.goods_recommend_rl:
                readyGo(GoodsRecommendActivity.class);
                break;
        }
    }
}
