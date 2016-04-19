package com.hardware.ui.shop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ProductsTypeRespon;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ProductsTypeFragment extends ABaseFragment{
    private final static String ARG_KEY_SHOP_ID = "shopId";

    @ViewInject(id = R.id.iv_back,click = "OnClick")
    ImageView mBack ;

    private int mShopId ;

    @Override
    public void onCreateCustomActionbarBar(FrameLayout customerContent,Activity activity) {
        LayoutInflater inflater=LayoutInflater.from(activity);
        View header=inflater.inflate(R.layout.comm_search_header_layout,null);
        customerContent.addView(header);
        header.findViewById(R.id.go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_producy_type;
    }

    public static void launch(FragmentActivity activity, int mShopId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY_SHOP_ID, mShopId);
        FragmentContainerActivity.launch(activity, ProductsTypeFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY_SHOP_ID)
                : (int) savedInstanceState.getSerializable(ARG_KEY_SHOP_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY_SHOP_ID, mShopId);
    }

    @Override
    public void requestData() {
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("shopId", String.valueOf(mShopId));
        startRequest(ApiConstants.SHOP_CATEGORIES, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        ProductsTypeRespon authresponse = ToolsHelper.parseJson(result, ProductsTypeRespon.class);
                        break;
                    case canceled:
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        }, HttpRequestUtils.RequestType.GET);

    }

    void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }

}
