package com.hardware.ui.cart;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.CartOrderAddressResponse;
import com.hardware.bean.CartOrderResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.address.AddNewAddressFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderAddressFragment extends ABaseFragment {

    private final static int PAGE_SIZE = 10;

    @ViewInject(id = R.id.submit,click = "OnClick")
    private Button mBtnSubmit;

    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;

    private boolean QueryMore = false;
    private boolean HasMoreData = true;

    private LayoutInflater mInflater;

    private List<Order> mProducts = new LinkedList<>();
    //private ProductAdapter mAdpater = new ProductAdapter();


    private Handler mHandler = new Handler();

    private class Order {
        private String Address;
        private int AddressId;

    }


    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cartorder_address;
    }

    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, CartOrderAddressFragment.class, null);

    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("收货地址");

    }


    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Token", App.sToken);
        Log.e("---token----",App.sToken);
        requestParams.put("Page", 1);
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_MYADDRESS, requestParams, new BaseHttpRequestTask<CartOrderAddressResponse>() {
            @Override
            public CartOrderAddressResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, CartOrderAddressResponse.class);
            }


            @Override
            protected void onSuccess(CartOrderAddressResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatus() == 0) {

                }
            }
        }, HttpRequestUtils.RequestType.GET);


    }


    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                AddNewAddressFragment.launch(getActivity());
                break;
        }
    }
}
