package com.hardware.ui.shop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ProductsTypeRespon;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ProductsTypeFragment extends ABaseFragment{
    private final static String ARG_KEY_SHOP_ID = "shopId";

    private int mShopId ;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_producy_type;
    }

    public static void launch(FragmentActivity activity, int mShopId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY_SHOP_ID, mShopId);
        FragmentContainerActivity.launch(activity, ProductsTypeFragment.class, args, false);
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
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", mShopId);
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



}
