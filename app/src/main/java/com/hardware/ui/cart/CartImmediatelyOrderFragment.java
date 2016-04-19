package com.hardware.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.bean.CartOrderAddressResponse;
import com.hardware.bean.CartOrderImmedResponse;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CartImmediatelyOrderFragment extends ABaseFragment {
    private final static String ARG_KEY = "arg_key";

    private final static int REQUEST_CODE_SELECTED_ADDR=101;

    @ViewInject(id = R.id.tv_cartorder_writes_immed)
    TextView mWritImmed ;
    @ViewInject(id = R.id.tv_cartorder_phone_immed)
    TextView mPhoneImmed ;
    @ViewInject(id = R.id.tv_cartorder_address_immed)
    TextView mAddressImmed ;
    @ViewInject(id = R.id.rl_cartorder_immed,click = "OnClick")
    RelativeLayout mRlCartorderImmed ;

    @ViewInject(id = R.id.message_list_order_shopname_immed)
    TextView mShopName ;
    @ViewInject(id = R.id.cart_immed_url)
    ImageView mCartImmedUrl ;
    @ViewInject(id = R.id.tv_cartorder_product_name_immed)
    TextView mProductNameImmed ;

    @ViewInject(id = R.id.message_list_item_item_standard_immed)
    TextView mStandardImmed ;
    @ViewInject(id = R.id.message_list_item_item_allprice_immed)
    TextView mAllpriceImmed ;
    @ViewInject(id = R.id.message_list_item_item_oneprice_immed)
    TextView mOnePrice ;
    @ViewInject(id = R.id.message_list_item_item_number_immed)
    TextView mNumberImmed ;
    @ViewInject(id = R.id.message_list_item_item_express_immed)
    TextView mExpressImmed ;
    @ViewInject(id = R.id.message_list_order_number_immed)
    TextView mOrderNumberImmed ;
    @ViewInject(id = R.id.message_list_order_allmoney_immed)
    TextView mOrderAllMoneyImmed ;
    @ViewInject(id = R.id.cartorder_express_immed)
    TextView mAllExpressImmed ;
    @ViewInject(id = R.id.cartorder_summoney_immed)
    TextView mSummoneyImmed ;
    @ViewInject(id = R.id.cartorder_productallmoney_immed)
    TextView mProductAllmoneyImmed ;
    @ViewInject(id = R.id.cartorder_productcount_immed)
    TextView mproductCount ;

    private String mSelectedSkuIds;
    private DisplayImageOptions options;

    private List<CartOrderImmedResponse.MessageEntity.CartItemModelsEntity> mCartItemModelList = new ArrayList<>();

    public static void lunch(FragmentActivity activity, String mDialog_skuId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, mDialog_skuId);
        FragmentContainerActivity.launch(activity, CartImmediatelyOrderFragment.class, args);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mSelectedSkuIds);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedSkuIds = savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY)
                : (String) savedInstanceState.getSerializable(ARG_KEY);
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("确认订单");
        options = ToolsHelper.buldDefDisplayImageOptions();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cart_immed_order;
    }


    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Quantity", 1);
        requestParams.put("Token", App.sToken);
        requestParams.put("skuId", mSelectedSkuIds);
        startRequest(ApiConstants.PRODUCT_BYORDER, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        CartOrderImmedResponse response = ToolsHelper.parseJson(result, CartOrderImmedResponse.class);
                        if(response != null && response.getFlag() == 1){
                            mCartItemModelList = response.getMessage().getCartItemModels();
                            mWritImmed.setText(response.getAddress().getShipTo());
                            mPhoneImmed.setText(response.getAddress().getPhone());
                            mAddressImmed.setText(response.getAddress().getAddress());

                            mShopName.setText(response.getMessage().getShopName());
                            String imgUrl = ApiConstants.IMG_BASE_URL + response.getMessage().getCartItemModels().get(0).getImgUrl();
                            ImageLoader.getInstance().displayImage(imgUrl, mCartImmedUrl, options);
                            mProductNameImmed.setText(response.getMessage().getCartItemModels().get(0).getProductName());

                            mStandardImmed.setText("规格：");
                            mAllpriceImmed.setText("￥"+response.getMessage().getCarMoney()+"");
                            mOnePrice.setText("单价："+response.getMessage().getCarMoney()+"");
                            mNumberImmed.setText(response.getMessage().getNumber()+"个");
                            mExpressImmed.setText("￥"+response.getMessage().getExpress()+"");

                            mOrderNumberImmed.setText(response.getSumnumber()+"件");
                            mOrderAllMoneyImmed.setText("￥"+response.getSumMoney()+"");

                            mAllExpressImmed.setText("运费总计：¥"+response.getMessage().getExpress()+"");
                            mSummoneyImmed.setText("总计：¥"+response.getSumMoney()+"");
                            mProductAllmoneyImmed.setText("货款总计：¥"+response.getSumMoney()+"");
                            mproductCount.setText(response.getSumnumber()+"件含运费");

                        }
                        break;
                    case canceled:
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_cartorder_immed:
                CartOrderAddressFragment.launch(this,REQUEST_CODE_SELECTED_ADDR);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_SELECTED_ADDR&&resultCode== Activity.RESULT_OK){
            CartOrderAddressResponse.AddressInfo addressInfo= (CartOrderAddressResponse.AddressInfo) data.getSerializableExtra(CartOrderAddressFragment.KEY_SELECTED_ADDRESS);
            mWritImmed.setText(addressInfo.getReceiverPerson());
            mPhoneImmed.setText(addressInfo.getReceiverPhone());
            mAddressImmed.setText(addressInfo.getAddress());
        }
    }

}
