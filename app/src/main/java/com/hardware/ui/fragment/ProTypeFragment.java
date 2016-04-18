package com.hardware.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.hardware.R;
import com.hardware.adapter.GridViewAdapter;
import com.hardware.bean.GoodsThirdCategoryBean;
import com.hardware.bean.GoodsThirdCategoryBean.MessageBean;
import com.hardware.bean.Type;
import com.hardware.common.HardWareApi;
import com.hardware.network.callback.StringCallback;
import com.hardware.ui.activity.GoodsListActivity2;
import com.hardware.ui.activity.GoodsRecommendActivity;
import com.hardware.utils.JsonHelper;
import com.squareup.okhttp.Request;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProTypeFragment extends MBaseFragment {

    @Bind(R.id.gridview)
    GridView gridView;
    private GridViewAdapter adapter;
    private Type type;
    private String typename;
    private int icon;
    private List<MessageBean> datas;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    adapter = new GridViewAdapter(getActivity(), datas);
                    gridView.setAdapter(adapter);
                    break;
                case 0x002:
                    Toast.makeText(context, "数据加载为空", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_type, null);
        ButterKnife.bind(this, view);
        int id = getArguments().getInt("id");
        getThirdCategoryData(id + "");
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
                readyGo(GoodsListActivity2.class);
            }
        });

        return view;
    }

    private void getThirdCategoryData(String id) {
        HardWareApi.getInstance().getGoodsSecondCategory(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.i("tag", "getThirdCategoryData onError = " + e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.i("tag", "getThirdCategoryData onResponse = " + response.toString());
                GoodsThirdCategoryBean goodsThirdCategoryBean = JsonHelper.getInstance().getObject(response, GoodsThirdCategoryBean.class);
                if(goodsThirdCategoryBean!=null){
                    datas = goodsThirdCategoryBean.getMessage();
                    mHandler.sendEmptyMessage(0x001);
                }else {
//                    mHandler.sendEmptyMessage(0x002);
                }
            }
        }, id);
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
