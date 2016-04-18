package com.hardware.ui.cart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.CartOrderResponse;
import com.hardware.bean.ProductContent;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.products.ProductDetailFragment;
import com.hardware.view.MyListView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderFragment extends ABaseFragment {

    private final static String ARG_KEY="arg_key";

    @ViewInject(id = R.id.tv_cartorder_writes)
    TextView mTvCartOrderWrites;

    @ViewInject(id = R.id.tv_cartorder_phone)
    TextView mTvPhone;

    @ViewInject(id = R.id.tv_cartorder_address)
    TextView mTvAddress;

    @ViewInject(id = R.id.cartorder_listview)
    MyListView mCartOrderListView;


    @ViewInject(id = R.id.ll_cartorder, click = "OnClick")
    LinearLayout mCartOrderAddress;

    @ViewInject(id = R.id.cartorder_express)
    TextView mExpress ;

    @ViewInject(id = R.id.cartorder_summoney)
    TextView mSummoney ;

    @ViewInject(id = R.id.cartorder_productallmoney)
    TextView mProductAllMoney ;

    @ViewInject(id = R.id.cartorder_productcount)
    TextView mProductCount ;

    private String mSelectedSkuIds;


    private List<CartOrderResponse.MessageBean> messageBeanList = new ArrayList<>();
    private DisplayImageOptions options;


    public static void launch(FragmentActivity activity,String selectedSkuIds) {
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

        messageBeanList.clear();

        options = ToolsHelper.buldDefDisplayImageOptions();


    }

    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
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
                    mTvCartOrderWrites.setText(response.getAddress().getShipTo());
                    mTvPhone.setText(response.getAddress().getPhone());
                    mTvAddress.setText(response.getAddress().getFullRegionName() + response.getAddress().getAddress());

                    mSummoney.setText("总计：¥"+response.getSumMoney()+"");
                    mProductAllMoney.setText("货款总计：¥"+response.getSumMoney()+"");
                    mProductCount.setText(response.getSumnumber()+"件含运费");

                    messageBeanList = response.getMessage();
                    mCartOrderListView.setAdapter(new MessageListViewAdapter(messageBeanList));

                }
            }
        }, HttpRequestUtils.RequestType.GET);
    }


    private class MessageListViewAdapter extends BaseAdapter {

        private List<CartOrderResponse.MessageBean> messageBeanList = new ArrayList<>();

        public MessageListViewAdapter(List<CartOrderResponse.MessageBean> messageBeanList) {
            this.messageBeanList = messageBeanList;
        }

        @Override
        public int getCount() {
            return messageBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return messageBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MessageViewHodler viewHodler;
            if (convertView == null) {
                viewHodler = new MessageViewHodler();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cartorder_message_list_item, null);
                viewHodler.listOrderShopname = (TextView) convertView.findViewById(R.id.message_list_order_shopname);
                viewHodler.listOrderNumber = (TextView) convertView.findViewById(R.id.message_list_order_number);
                viewHodler.listOrderAllMoney = (TextView) convertView.findViewById(R.id.message_list_order_allmoney);
                viewHodler.listOrderListView = (MyListView) convertView.findViewById(R.id.message_list_listview);
                convertView.setTag(viewHodler);
            } else {
                viewHodler = (MessageViewHodler) convertView.getTag();
            }

            viewHodler.listOrderShopname.setText(messageBeanList.get(position).getShopName());
            viewHodler.listOrderNumber.setText(messageBeanList.get(position).getNumber() + "");
            viewHodler.listOrderAllMoney.setText("小计：¥ " + messageBeanList.get(position).getCarMoney() + "");
            viewHodler.listOrderListView.setAdapter(new ListOrderAdapter(messageBeanList.get(position).getCartItemModels()));


            return convertView;
        }
    }


    private class ListOrderAdapter extends BaseAdapter {

        private List<CartOrderResponse.MessageBean.CartItemModelsBean> cartItemModels = new ArrayList<>();

        public ListOrderAdapter(List<CartOrderResponse.MessageBean.CartItemModelsBean> cartItemModels) {
            this.cartItemModels = cartItemModels;
        }

        @Override
        public int getCount() {
            return cartItemModels.size();
        }

        @Override
        public Object getItem(int position) {
            return cartItemModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MessageListViewHolder viewHolder ;
            if(convertView == null){
                viewHolder = new MessageListViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cartorder_message_list_item_item,null);
                viewHolder.productImage = (ImageView) convertView.findViewById(R.id.message_list_item_item_image);
                viewHolder.productName = (TextView) convertView.findViewById(R.id.message_list_item_item_productname);
                viewHolder.productStandard = (TextView) convertView.findViewById(R.id.message_list_item_item_standard);
                viewHolder.productAllPrice =(TextView)convertView.findViewById(R.id.message_list_item_item_allprice);
                viewHolder.productOnePrice = (TextView) convertView.findViewById(R.id.message_list_item_item_one_price);
                viewHolder.productCount = (TextView) convertView.findViewById(R.id.message_list_item_item_count);
                viewHolder.ll_cartorder_item = (LinearLayout) convertView.findViewById(R.id.ll_cartorder_item);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MessageListViewHolder) convertView.getTag();
            }

            String imgUrl = ApiConstants.IMG_BASE_URL + cartItemModels.get(position).getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.productImage, options);

            viewHolder.productName.setText(cartItemModels.get(position).getProductName());
            viewHolder.productStandard.setText("规格：");
            viewHolder.productAllPrice.setText("¥"+cartItemModels.get(position).getPrice()+"");
            viewHolder.productOnePrice.setText("单价：¥" + cartItemModels.get(position).getPrice() + "");
            viewHolder.productCount.setText(cartItemModels.get(position).getCount() + "件");

            final int priductId = cartItemModels.get(position).getId();
            viewHolder.ll_cartorder_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductContent content = new ProductContent();
                    content.setId(priductId);
                    content.setDistrict(Constants.REGION_NAME);
                    ProductDetailFragment.launch(getActivity(), content);
                }
            });


            return convertView;
        }
    }

    private class MessageListViewHolder{
        LinearLayout ll_cartorder_item;
        ImageView productImage ;
        TextView productName ;
        TextView productStandard ;
        TextView productAllPrice ;
        TextView productOnePrice ;
        TextView productCount ;

    }

    private class MessageViewHodler {
        TextView listOrderShopname;
        TextView listOrderNumber;
        TextView listOrderAllMoney;
        MyListView listOrderListView;
    }


    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cartorder:
                CartOrderAddressFragment.launch(getActivity());
                break;
        }
    }
}
