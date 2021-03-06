package com.hardware.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.bean.AddByOrderRespon;
import com.hardware.bean.AddressInfoResponseBean;
import com.hardware.bean.CartImmedOrderRespon;
import com.hardware.bean.CartOrderAddressResponse;
import com.hardware.bean.CartOrderImmedResponse;
import com.hardware.bean.CartOrderResponse;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CartImmediatelyOrderFragment extends ABaseFragment {
    private final static String ARG_KEY = "arg_key";

    private final static int REQUEST_CODE_SELECTED_ADDR = 101;

    @ViewInject(id = R.id.tv_cartorder_writes_immed)
    TextView mWritImmed;
    @ViewInject(id = R.id.tv_cartorder_phone_immed)
    TextView mPhoneImmed;
    @ViewInject(id = R.id.tv_cartorder_address_immed)
    TextView mAddressImmed;
    @ViewInject(id = R.id.rl_cartorder_immed, click = "OnClick")
    RelativeLayout mRlCartorderImmed;

    @ViewInject(id = R.id.message_list_order_shopname_immed)
    TextView mShopName;
    @ViewInject(id = R.id.cart_immed_url)
    ImageView mCartImmedUrl;
    @ViewInject(id = R.id.tv_cartorder_product_name_immed)
    TextView mProductNameImmed;

    @ViewInject(id = R.id.message_list_item_item_standard_immed)
    TextView mStandardImmed;
    @ViewInject(id = R.id.message_list_item_item_allprice_immed)
    TextView mAllpriceImmed;
    @ViewInject(id = R.id.message_list_item_item_oneprice_immed)
    TextView mOnePrice;
    @ViewInject(id = R.id.message_list_item_item_number_immed)
    TextView mNumberImmed;
    @ViewInject(id = R.id.message_list_item_item_express_immed)
    TextView mExpressImmed;
    @ViewInject(id = R.id.message_list_order_number_immed)
    TextView mOrderNumberImmed;
    @ViewInject(id = R.id.message_list_order_allmoney_immed)
    TextView mOrderAllMoneyImmed;
    @ViewInject(id = R.id.cartorder_express_immed)
    TextView mAllExpressImmed;
    @ViewInject(id = R.id.cartorder_summoney_immed)
    TextView mSummoneyImmed;
    @ViewInject(id = R.id.cartorder_productallmoney_immed)
    TextView mProductAllmoneyImmed;
    @ViewInject(id = R.id.cartorder_productcount_immed)
    TextView mproductCount;
    @ViewInject(id = R.id.cart_immed_order, click = "OnClick")
    RelativeLayout mImmedOrder;

    private String mSelectedSkuIds;
    private DisplayImageOptions options;
    private CartOrderImmedResponse response;

    private int mAddressId;
    private String skuId = "";

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
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("Quantity", String.valueOf(1));
        requestParams.put("Token", App.sToken);
        requestParams.put("skuId", mSelectedSkuIds);
        startRequest(ApiConstants.PRODUCT_BYORDER, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        response = ToolsHelper.parseJson(result, CartOrderImmedResponse.class);
                        if (response != null && response.getFlag() == 1) {
                            mCartItemModelList = response.getMessage().getCartItemModels();
                            mWritImmed.setText(response.getAddress().getShipTo());
                            mPhoneImmed.setText(response.getAddress().getPhone());
                            mAddressImmed.setText(response.getAddress().getAddress());

                            mShopName.setText(response.getMessage().getShopName());
                            String imgUrl = ApiConstants.IMG_BASE_URL + response.getMessage().getCartItemModels().get(0).getImgUrl();
                            ImageLoader.getInstance().displayImage(imgUrl, mCartImmedUrl, options);
                            mProductNameImmed.setText(response.getMessage().getCartItemModels().get(0).getProductName());

                            mStandardImmed.setText("规格：");

                            DecimalFormat df = new DecimalFormat();
                            df.applyPattern("￥##0.00");
                            mAllpriceImmed.setText(df.format(response.getMessage().getCartItemModels().get(0).getPrice()));

                            DecimalFormat df1 = new DecimalFormat();
                            df1.applyPattern("单价：￥##0.00");

                            mOnePrice.setText(df1.format(response.getMessage().getCartItemModels().get(0).getPrice()));
                            mNumberImmed.setText(response.getMessage().getCartItemModels().get(0).getCount() + "个");

                            mExpressImmed.setText(df.format(response.getMessage().getExpress()));

                            mOrderNumberImmed.setText(response.getSumnumber() + "件");
                            mOrderAllMoneyImmed.setText("小计："+df.format(response.getSumMoney()));

                            DecimalFormat df2 = new DecimalFormat();
                            df2.applyPattern("运费总计：￥##0.00");
                            mAllExpressImmed.setText(df2.format(response.getMessage().getExpress()));

                            DecimalFormat df3 = new DecimalFormat();
                            df3.applyPattern("总计：￥##0.00");
                            mSummoneyImmed.setText(df3.format(response.getSumMoney()));

                            DecimalFormat df4 = new DecimalFormat();
                            df4.applyPattern("货款总计：￥##0.00");
                            mProductAllmoneyImmed.setText(df4.format(response.getSumMoney()));
                            mproductCount.setText(response.getSumnumber() + "件含运费");

                            mAddressId = response.getAddress().getId();

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

    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_cartorder_immed:
                CartOrderAddressFragment.launch(this, REQUEST_CODE_SELECTED_ADDR);
                break;
            case R.id.cart_immed_order:
                if (response.getAddress() == null) {
                    ToastUtils.toast("请添加地址");
                } else {
                    skuId = response.getMessage().getCartItemModels().get(0).getSkuId();
                    HashMap<String,String> requestParams=new HashMap<>();
                    requestParams.put("Quantity", String.valueOf(1));
                    requestParams.put("Token", App.sToken);
                    requestParams.put("recieveAddressId", String.valueOf(mAddressId));
                    requestParams.put("skuIds", skuId);
                    startRequest(ApiConstants.SUBMIT_ORDERBYPRODUCTID, requestParams, new HttpRequestHandler() {
                        @Override
                        public void onRequestFinished(ResultCode resultCode, String result) {
                            switch (resultCode) {
                                case success:
                                    CartImmedOrderRespon response = ToolsHelper.parseJson(result, CartImmedOrderRespon.class);
                                    if (response != null && response.getFlag() == 1) {
                                        ToastUtils.toast("提交订单成功");
                                        CartPayFragment.lauch(getActivity(), response.getAmount());
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
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECTED_ADDR && resultCode == Activity.RESULT_OK) {
            //通过返回键返回
            if(data.getBooleanExtra(CartOrderAddressFragment.KEY_IS_BACK_FINISH,false)){
                //重新请求
                queryAddress(mAddressId,true);
            }else{
                //选地址返回
                CartOrderAddressResponse.AddressInfo addressInfo = (CartOrderAddressResponse.AddressInfo) data.getSerializableExtra(CartOrderAddressFragment.KEY_SELECTED_ADDRESS);
                queryAddress(addressInfo.getAddressId(),false);
            }
        }
    }

    private void queryAddress(int addressId, final boolean isBack){
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("addressid",String.valueOf(addressId));
        startRequest(ApiConstants.GET_MYADDRESS_INFO, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        AddressInfoResponseBean response = ToolsHelper.parseJson(result, AddressInfoResponseBean.class);
                        if (response != null && response.getMsg()!=null&& response.getMsg().getAddressId() !=0) {
                            if(TextUtils.isEmpty(response.getMsg().getReceiverPerson())){
                                mWritImmed.setText("");
                            }else{
                                mWritImmed.setText(response.getMsg().getReceiverPerson());
                            }
                            if(TextUtils.isEmpty(response.getMsg().getReceiverPhone())){
                                mPhoneImmed.setText("");
                            }else{
                                mPhoneImmed.setText(response.getMsg().getReceiverPhone());
                            }
                            if(TextUtils.isEmpty(response.getMsg().getAddress())){
                                mAddressImmed.setText("");
                            }else{
                                mAddressImmed.setText(response.getMsg().getRegionIdPath()+" "+response.getMsg().getAddress());
                            }
                            mAddressId = response.getMsg().getAddressId();
                        }else {
                            if(isBack) {
                                //找不到数据，重新请求
                                requestData();
                            }
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


}
