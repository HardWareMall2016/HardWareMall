package com.hardware.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.AddByOrderRespon;
import com.hardware.bean.AddressInfoResponseBean;
import com.hardware.bean.CartOrderAddressResponse;
import com.hardware.bean.CartOrderResponse;
import com.hardware.bean.ProductContent;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.products.ProductDetailFragment;
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
import java.util.HashMap;


/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderFragment extends ABaseFragment {
    private final static int REQUEST_CODE_SELECTED_ADDR = 102;
    private final static String ARG_KEY = "arg_key";

    @ViewInject(id = R.id.cartorder_listview)
    ExpandableListView mExpandableListView;

    private TextView mTvCartOrderWrites;
    private TextView mTvPhone;
    private TextView mTvAddress;
    private TextView mSummoney;
    private TextView mProductAllMoney;
    private TextView mProductCount;
    private TextView mExpress;
    private LinearLayout mCartOrderAddress;
    private RelativeLayout mCommit;

    private String mSelectedSkuIds;
    private DisplayImageOptions options;
    private ExpandableAdapter mAdapter;
    private CartOrderResponse mResponseBean;

    private int mAddressId;
    private String cardIds = "";


    public static void launch(FragmentActivity activity, String selectedSkuIds) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, selectedSkuIds);
        FragmentContainerActivity.launch(activity, CartOrderFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedSkuIds = savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY)
                : (String) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mSelectedSkuIds);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cartorder;
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("确认订单");
        options = ToolsHelper.buldDefDisplayImageOptions();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.expandablelistview_head, null);
        View bottomView = LayoutInflater.from(getActivity()).inflate(R.layout.expandablelistview_bottom, null);
        mExpandableListView.addHeaderView(headerView);
        mExpandableListView.addFooterView(bottomView);

        mExpandableListView.setGroupIndicator(null);
        mAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        //不能点击收缩
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        //headview
        mTvCartOrderWrites = (TextView) headerView.findViewById(R.id.tv_cartorder_writes);
        mTvPhone = (TextView) headerView.findViewById(R.id.tv_cartorder_phone);
        mTvAddress = (TextView) headerView.findViewById(R.id.tv_cartorder_address);
        mCartOrderAddress = (LinearLayout) headerView.findViewById(R.id.ll_cartorder);

        //footview
        mSummoney = (TextView) bottomView.findViewById(R.id.cartorder_summoney);
        mProductAllMoney = (TextView) bottomView.findViewById(R.id.cartorder_productallmoney);
        mProductCount = (TextView) bottomView.findViewById(R.id.cartorder_productcount);
        mExpress = (TextView) bottomView.findViewById(R.id.cartorder_express);
        mCommit = (RelativeLayout) bottomView.findViewById(R.id.cartorder_commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseBean.getAddress().getAddress() == null) {
                    ToastUtils.toast("请添加地址");
                } else {
                    for (CartOrderResponse.MessageBean messageBean : mResponseBean.getMessage()) {
                        for (CartOrderResponse.MessageBean.CartItemModelsBean card : messageBean.getCartItemModels()) {
                            if (TextUtils.isEmpty(cardIds)) {
                                cardIds = String.valueOf(card.getCarId());
                            } else {
                                cardIds += "," + card.getCarId();
                            }
                        }
                    }
                    final HashMap<String, String> requestParams = new HashMap<>();
                    requestParams.put("Token", App.sToken);
                    requestParams.put("cartItemIds", cardIds);
                    requestParams.put("recieveAddressId", String.valueOf(mAddressId));
                    startRequest(ApiConstants.ADD_BY_ORDER, requestParams, new HttpRequestHandler() {
                        @Override
                        public void onRequestFinished(ResultCode resultCode, String result) {
                            switch (resultCode) {
                                case success:
                                    AddByOrderRespon response = ToolsHelper.parseJson(result, AddByOrderRespon.class);
                                    if (response != null && response.getFlag() == 1) {
                                        ToastUtils.toast("提交订单成功");
                                        /*Long orderId = null;
                                        for(AddByOrderRespon.OrderPayEntity orderPayEntity :response.getOrderPay()){
                                            Log.e("----------",orderPayEntity.getOrderId()+"");
                                            orderId = orderPayEntity.getOrderId() ;
                                        }*/
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
            }
        });
    }

    private class ExpandableAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return mResponseBean == null ? 0 : mResponseBean.getMessage().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mResponseBean == null ? 0 : mResponseBean.getMessage().get(groupPosition).getCartItemModels().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mResponseBean.getMessage().get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            MessageViewHodler viewHodler;
            if (convertView == null) {
                viewHodler = new MessageViewHodler();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cartorder_message_list_item, null);
                viewHodler.listOrderShopname = (TextView) convertView.findViewById(R.id.message_list_order_shopname);
                convertView.setTag(viewHodler);
            } else {
                viewHodler = (MessageViewHodler) convertView.getTag();
            }
            viewHodler.listOrderShopname.setText(mResponseBean.getMessage().get(groupPosition).getShopName());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            MessageListViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new MessageListViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cartorder_message_list_item_item, null);
                viewHolder.productImage = (ImageView) convertView.findViewById(R.id.message_list_item_item_image);
                viewHolder.productName = (TextView) convertView.findViewById(R.id.message_list_item_item_productname);
                viewHolder.productStandard = (TextView) convertView.findViewById(R.id.message_list_item_item_standard);
                viewHolder.productAllPrice = (TextView) convertView.findViewById(R.id.message_list_item_item_allprice);
                viewHolder.productOnePrice = (TextView) convertView.findViewById(R.id.message_list_item_item_one_price);
                viewHolder.productCount = (TextView) convertView.findViewById(R.id.message_list_item_item_count);
                viewHolder.ll_cartorder_item = (LinearLayout) convertView.findViewById(R.id.ll_cartorder_item);

                viewHolder.list_item_liuyan = (LinearLayout) convertView.findViewById(R.id.list_item_liuyan);
                viewHolder.listOrderNumber = (TextView) convertView.findViewById(R.id.message_list_order_number);
                viewHolder.listOrderAllMoney = (TextView) convertView.findViewById(R.id.message_list_order_allmoney);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MessageListViewHolder) convertView.getTag();
            }


            String imgUrl = ApiConstants.IMG_BASE_URL + mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.productImage, options);


            viewHolder.productName.setText(mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getProductName());
            viewHolder.productStandard.setText("规格：");

            DecimalFormat df = new DecimalFormat();
            df.applyPattern("￥##0.00");
            viewHolder.productAllPrice.setText(df.format(mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getPrice()));

            DecimalFormat df1 = new DecimalFormat();
            df1.applyPattern("单价：￥##0.00");
            viewHolder.productOnePrice.setText(df1.format(mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getPrice()));
            viewHolder.productCount.setText(mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getCount() + "件");

            final int priductId = mResponseBean.getMessage().get(groupPosition).getCartItemModels().get(childPosition).getId();
            viewHolder.ll_cartorder_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductContent content = new ProductContent();
                    content.setId(priductId);
                    content.setDistrict(Constants.REGION_NAME);
                    ProductDetailFragment.launch(getActivity(), content);
                }
            });

            //最后一项显示留言
            if (isLastChild) {
                viewHolder.list_item_liuyan.setVisibility(View.VISIBLE);
                viewHolder.listOrderNumber.setText(mResponseBean.getMessage().get(groupPosition).getNumber() + "");

                DecimalFormat df2 = new DecimalFormat();
                df2.applyPattern("小计：￥##0.00");

                viewHolder.listOrderAllMoney.setText(df2.format(mResponseBean.getMessage().get(groupPosition).getCarMoney()));
            } else {
                viewHolder.list_item_liuyan.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    @Override
    public void requestData() {
        HashMap<String, String> requestParams = new HashMap<>();
        requestParams.put("Token", App.sToken);
        requestParams.put("skuId", mSelectedSkuIds);
        startRequest(ApiConstants.CAR_BYORDER, requestParams, new BaseHttpRequestTask<CartOrderResponse>() {
            @Override
            public CartOrderResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, CartOrderResponse.class);
            }


            @Override
            protected void onSuccess(CartOrderResponse response) {
                super.onSuccess(response);
                if (response != null && response.getFlag() == 1) {
                    mResponseBean = response;

                    mAdapter.notifyDataSetChanged();

                    mAddressId = response.getAddress().getId();
                    if(response.getAddress().getShipTo() !=  null){
                        mTvCartOrderWrites.setText(response.getAddress().getShipTo() + "");
                    }
                    if(response.getAddress().getPhone() != null){
                        mTvPhone.setText(response.getAddress().getPhone() + "");
                    }

                    if(response.getAddress().getFullRegionName() != null){
                        mTvAddress.setText(response.getAddress().getFullRegionName() + mResponseBean.getAddress().getAddress() + "");
                    }
                    mCartOrderAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CartOrderAddressFragment.launch(CartOrderFragment.this, REQUEST_CODE_SELECTED_ADDR);
                        }
                    });

                    DecimalFormat df3 = new DecimalFormat();
                    df3.applyPattern("总计：￥##0.00");
                    mSummoney.setText(df3.format(response.getSumMoney()));

                    DecimalFormat df4 = new DecimalFormat();
                    df4.applyPattern("货款总计：￥##0.00");
                    mProductAllMoney.setText(df4.format(response.getSumMoney()));
                    mProductCount.setText(response.getSumnumber() + "件含运费");

                    int express = 0;
                    for (CartOrderResponse.MessageBean cartOrderResponse : mResponseBean.getMessage()) {
                        express += cartOrderResponse.getExpress();
                        DecimalFormat df5 = new DecimalFormat();
                        df5.applyPattern("运费总计：￥##0.00");
                        mExpress.setText(df5.format(express));
                    }

                    //默认展开
                    for (int i = 0; i < mResponseBean.getMessage().size(); i++) {
                        mExpandableListView.expandGroup(i);
                    }
                }
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_SELECTED_ADDR && resultCode == Activity.RESULT_OK) {
            //通过返回键返回
            if (data.getBooleanExtra(CartOrderAddressFragment.KEY_IS_BACK_FINISH, false)) {
                //重新请求
                queryAddress(mAddressId, true);
            } else {
                //选地址返回
                CartOrderAddressResponse.AddressInfo addressInfo = (CartOrderAddressResponse.AddressInfo) data.getSerializableExtra(CartOrderAddressFragment.KEY_SELECTED_ADDRESS);
                queryAddress(addressInfo.getAddressId(), false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String name =sharedPreferences.getString("flag", "");
        if (!TextUtils.isEmpty(name)&&name.equals("1")) {
            requestData();
        }
    }

    private void queryAddress(int addressId, final boolean isBack) {
        HashMap<String, String> requestParams = new HashMap<>();
        requestParams.put("addressid", String.valueOf(addressId));
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_MYADDRESS_INFO, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        AddressInfoResponseBean response = ToolsHelper.parseJson(result, AddressInfoResponseBean.class);
                        if (response != null && response.getMsg() != null && response.getMsg().getAddressId() != 0) {
                            mTvCartOrderWrites.setText(response.getMsg().getReceiverPerson());
                            mTvPhone.setText(response.getMsg().getReceiverPhone());
                            mTvAddress.setText(response.getMsg().getRegionIdPath() + " " + response.getMsg().getAddress());
                            mAddressId = response.getMsg().getAddressId();
                        } else {
                            if (isBack) {
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

    private class MessageListViewHolder {
        LinearLayout ll_cartorder_item;
        ImageView productImage;
        TextView productName;
        TextView productStandard;
        TextView productAllPrice;
        TextView productOnePrice;
        TextView productCount;

        LinearLayout list_item_liuyan;
        TextView listOrderNumber;
        TextView listOrderAllMoney;
    }

    private class MessageViewHodler {
        TextView listOrderShopname;
    }

}
