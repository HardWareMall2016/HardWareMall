package com.hardware.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.DefResponseBean;
import com.hardware.bean.DefResponseBean2;
import com.hardware.bean.MyCartOrderCarResponse;
import com.hardware.bean.ProductContent;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.cart.CartOrderFragment;
import com.hardware.ui.products.ProductDetailFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends ABaseFragment {
    @ViewInject(id = R.id.my_order_list)
    private PullToRefreshExpandableListView mMyOrderListView;
    
    private ExpandableListView mExpandableListView;

    @ViewInject(id = R.id.sel_all_order)
    private CheckBox mCkSelelctAll;

    @ViewInject(id = R.id.all_product_total_price)
    private TextView mTvTotalPrice;

    @ViewInject(id = R.id.all_product_total_count)
    private TextView mTvTotalCount;

    @ViewInject(id = R.id.right_menu, click = "OnClick")
    private TextView mTvRightMenu;

    @ViewInject(id = R.id.to_pay, click = "OnClick")
    private TextView mTvToPay;

    @ViewInject(id = R.id.delete, click = "OnClick")
    private TextView mTvDelete;

    @ViewInject(id = R.id.move_to_fav, click = "OnClick")
    private TextView mTvMoveToFac;

    @ViewInject(id = R.id.go_home_page, click = "OnClick")
    private Button mBtnGoHomePage;

    private ExpandableAdapter mAdapter;

    private LayoutInflater mInflater;

    private DisplayImageOptions options;

    private List<ShopOrderInfo> mOrderList=new LinkedList<>();

    private boolean mIsEditMode=false;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mInflater=inflater;
        mIsEditMode=false;
        options= ToolsHelper.buldDefDisplayImageOptions();
        mOrderList.clear();
        refreshViewsByEditMode();
        mMyOrderListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mMyOrderListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>(){
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                requestData();
            }
        });
        mExpandableListView=mMyOrderListView.getRefreshableView();
        mExpandableListView.setGroupIndicator(null);
        mAdapter=new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        //不能点击收缩
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mCkSelelctAll.setChecked(false);
        mCkSelelctAll.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (ShopOrderInfo shopOrderInfo : mOrderList) {
                    shopOrderInfo.isSelectedAll = isChecked;
                    for (ProductOrderInfo orderInfo : shopOrderInfo.productOrderList) {
                        orderInfo.isSelelcted = isChecked;
                    }
                }
                Log.i("wuyue", "setOnCheckedChangeListener isChecked = " + isChecked);
                refreshAllView();
            }
        }));
    }

    @Override
    protected void taskStateChanged(ABaseTaskState state, Serializable tag) {
        super.taskStateChanged(state, tag);
        if (state == ABaseTaskState.success){
            if (isContentEmpty()){
                mTvRightMenu.setVisibility(View.GONE);
            }else{
                mTvRightMenu.setVisibility(View.VISIBLE);
            }
        }
    }

    void OnClick(View v) {
        switch (v.getId()){
            case R.id.right_menu:
                mIsEditMode=!mIsEditMode;
                refreshViewsByEditMode();
                break;
            case R.id.go_home_page:
                Intent homePageIntent = new Intent(getActivity(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homePageIntent);
                //AddNewAddressFragment.launch(getActivity());
                break;
            case R.id.delete:
                String skuId=null;
                for (ShopOrderInfo shopOrderInfo:mOrderList){
                    for(ProductOrderInfo orderInfo:shopOrderInfo.productOrderList){
                        if(orderInfo.isSelelcted){
                            if(TextUtils.isEmpty(skuId)){
                                skuId=orderInfo.skuId;
                            }else{
                                skuId+=","+orderInfo.skuId;
                            }
                        }
                    }
                }
                if(TextUtils.isEmpty(skuId)){
                    ToastUtils.toast("请选择产品!");
                }else {
                    deleteOrderCar(skuId);
                }
                break;
            case R.id.to_pay:
                String selectedSkuIds=null;
                for (ShopOrderInfo shopOrderInfo:mOrderList){
                    for(ProductOrderInfo orderInfo:shopOrderInfo.productOrderList){
                        if(orderInfo.isSelelcted){
                            if(TextUtils.isEmpty(selectedSkuIds)){
                                selectedSkuIds=orderInfo.skuId;
                            }else{
                                selectedSkuIds+=","+orderInfo.skuId;
                            }
                        }
                    }
                }
                if(TextUtils.isEmpty(selectedSkuIds)){
                    ToastUtils.toast("请选择产品!");
                }else {
                    CartOrderFragment.launch(getActivity(),selectedSkuIds);
                }
                break;
            case R.id.move_to_fav:
                String goodsId=null;
                String skuIds=null;
                for (ShopOrderInfo shopOrderInfo:mOrderList){
                    for(ProductOrderInfo orderInfo:shopOrderInfo.productOrderList){
                        if(orderInfo.isSelelcted){
                            if(TextUtils.isEmpty(goodsId)){
                                goodsId=String.valueOf(orderInfo.id);
                                skuIds=orderInfo.skuId;
                            }else{
                                goodsId+=","+orderInfo.id;
                                skuIds+=","+orderInfo.skuId;
                            }
                        }
                    }
                }

                if(TextUtils.isEmpty(goodsId)){
                    ToastUtils.toast("请选择产品!");
                }else {
                    HashMap<String,String> requestParams=new HashMap<>();
                    requestParams.put("Token",App.sToken);
                    requestParams.put("goodsids",goodsId);//,分割

                    final String finalSkuIds = skuIds;
                    startRequest(Constants.BASE_URL_2,ApiConstants.SHOPPING_REMOVE_COLLECTION, requestParams, new HttpRequestHandler() {
                        @Override
                        public void onRequestFinished(ResultCode resultCode, String result) {
                            switch (resultCode) {
                                case success:
                                    DefResponseBean2 responseBean = ToolsHelper.parseJson(result, DefResponseBean2.class);
                                    if (responseBean != null && responseBean.getStatus() == 0) {
                                        deleteOrderCar(finalSkuIds);
                                    } else {
                                        ToastUtils.toast("移至收藏夹失败!");
                                    }
                                    break;
                                case canceled:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, HttpRequestUtils.RequestType.POST);
                }
                break;
        }
    }

    private void deleteOrderCar(String skuIds) {
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("Token", App.sToken);
        requestParams.put("skuId",skuIds);//,分割
        startRequest(ApiConstants.DELETE_ORDER_CAR, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        DefResponseBean responseBean = ToolsHelper.parseJson(result, DefResponseBean.class);
                        if (responseBean != null && responseBean.getFlag() == 1) {
                            mIsEditMode = false;
                            refreshViewsByEditMode();
                            requestData();
                        } else {
                            ToastUtils.toast("删除失败!");
                        }
                        break;
                    case canceled:
                        break;
                    default:
                        break;
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isRequestProcessing(ApiConstants.MY_CART_ORDER_CAR)&&mOrderList.size()==0){
            requestData();
        }
    }

    @Override
    public void requestData() {
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("Token", App.sToken);
        startRequest(ApiConstants.MY_CART_ORDER_CAR, requestParams, new BaseHttpRequestTask<MyCartOrderCarResponse>() {
            @Override
            public MyCartOrderCarResponse parseResponseToResult(String content) {
                DefResponseBean defResponseBean=ToolsHelper.parseJson(content, DefResponseBean.class);
                if(defResponseBean!=null&&defResponseBean.getFlag()==0){
                    //无数据
                    MyCartOrderCarResponse bean=new MyCartOrderCarResponse();
                    bean.setMessage(new ArrayList<MyCartOrderCarResponse.MessageBean>());
                    return bean;
                }else if(defResponseBean!=null&&defResponseBean.getFlag()==-1){
                    //Token无效!
                    LoginFragment.launch(getActivity());
                    return null;
                }
                return ToolsHelper.parseJson(content, MyCartOrderCarResponse.class);
            }

            @Override
            public String verifyResponseResult(MyCartOrderCarResponse result) {
                if (result == null) {
                    return "无法获取数据";
                }
                return null;
            }

            @Override
            protected boolean resultIsEmpty(MyCartOrderCarResponse result) {
                return result.getMessage() == null || result.getMessage().size() == 0;
            }

            @Override
            protected void onSuccess(MyCartOrderCarResponse result) {
                super.onSuccess(result);
                mOrderList.clear();
                for (MyCartOrderCarResponse.MessageBean shopInfo : result.getMessage()) {
                    ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
                    shopOrderInfo.productOrderList.clear();
                    shopOrderInfo.shopName = shopInfo.getShopName();
                    double totalPrice = 0;
                    int totalNumber = 0;
                    for (MyCartOrderCarResponse.MessageBean.CartItemModelsBean cart : shopInfo.getCartItemModels()) {
                        ProductOrderInfo orderInfo = new ProductOrderInfo();
                        orderInfo.id = cart.getId();
                        orderInfo.skuId=cart.getSkuId();
                        orderInfo.ProductName = cart.getProductName();
                        orderInfo.imgUrl = cart.getImgUrl();
                        orderInfo.isSelelcted = false;
                        orderInfo.count = cart.getCount();
                        orderInfo.price = cart.getPrice();
                        orderInfo.size = cart.getSize();
                        orderInfo.amount = orderInfo.price * orderInfo.count;
                        totalPrice += orderInfo.amount;
                        totalNumber += orderInfo.count;

                        shopOrderInfo.productOrderList.add(orderInfo);
                    }
                    shopOrderInfo.totalPrice = totalPrice;
                    shopOrderInfo.productsNum = totalNumber;

                    mOrderList.add(shopOrderInfo);
                }
                refreshAllView();
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                mMyOrderListView.onRefreshComplete();
                mCkSelelctAll.setChecked(false);
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    private void refreshAllView(){
        mAdapter.notifyDataSetChanged();
        //默认展开
        for (int i=0;i<mOrderList.size();i++){
            mExpandableListView.expandGroup(i);
        }

        int totalCount=0;
        double totalPrice=0;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("总计:￥##0.00");
        for (ShopOrderInfo shopOrderInfo:mOrderList){
            for(ProductOrderInfo orderInfo:shopOrderInfo.productOrderList){
                if(orderInfo.isSelelcted){
                    totalCount+=orderInfo.count;
                    totalPrice+=(orderInfo.count*orderInfo.price);
                }
            }
        }

        mTvTotalCount.setText(String.format("%d件不含运费", totalCount));
        mTvTotalPrice.setText(df.format(totalPrice));

        if(mOrderList.size()==0){
            mIsEditMode=false;
            refreshViewsByEditMode();
        }
    }

    private void refreshViewsByEditMode(){
        if(mIsEditMode){
            mTvRightMenu.setText("完成");
            mTvDelete.setVisibility(View.VISIBLE);
            mTvMoveToFac.setVisibility(View.VISIBLE);
        }else{
            mTvRightMenu.setText("编辑");
            mTvDelete.setVisibility(View.GONE);
            mTvMoveToFac.setVisibility(View.GONE);
        }
    }

    public  class  ExpandableAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return mOrderList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mOrderList.get(groupPosition).productOrderList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mOrderList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mOrderList.get(groupPosition).productOrderList.get(childPosition);
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
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            if(convertView==null){
                convertView= mInflater.inflate(R.layout.my_card_order_group_header_layout,null);
                holder=new GroupHolder();
                holder.ckShopName =(CheckBox)convertView.findViewById(R.id.shop_name);
                convertView.setTag(holder);
            }else{
                holder=(GroupHolder)convertView.getTag();
            }

            holder.ckShopName.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mOrderList.get(groupPosition).isSelectedAll=isChecked;
                    for(ProductOrderInfo item:mOrderList.get(groupPosition).productOrderList){
                        item.isSelelcted=isChecked;
                    }
                    refreshAllView();
                }
            }));

            holder.ckShopName.setChecked(mOrderList.get(groupPosition).isSelectedAll);

            holder.ckShopName.setText(mOrderList.get(groupPosition).shopName);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if(convertView==null) {
                convertView = mInflater.inflate(R.layout.my_card_order_item_layout, null);
                holder=new ChildHolder();
                convertView.setTag(holder);
                holder.checkBox=(CheckBox)convertView.findViewById(R.id.select);
                holder.imageView=(ImageView)convertView.findViewById(R.id.image);
                holder.productName=(TextView)convertView.findViewById(R.id.product_name);
                holder.totalPrice=(TextView)convertView.findViewById(R.id.total_price);
                holder.minus=(TextView)convertView.findViewById(R.id.minus);
                holder.number=(TextView)convertView.findViewById(R.id.number);
                holder.plus=(TextView)convertView.findViewById(R.id.plus);
                holder.shopTotalPprice=(TextView)convertView.findViewById(R.id.shop_total_price);
                holder.shopTotalCount=(TextView)convertView.findViewById(R.id.shop_total_count);
                holder.size=(TextView)convertView.findViewById(R.id.size);
                holder.unitPrice=(TextView)convertView.findViewById(R.id.unit_price);
            } else {
                holder=(ChildHolder)convertView.getTag();
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductOrderInfo productOrderInfo=mOrderList.get(groupPosition).productOrderList.get(childPosition);
                    ProductContent content = new ProductContent();
                    content.setId(productOrderInfo.id);
                    content.setDistrict(Constants.REGION_NAME);
                    ProductDetailFragment.launch(getActivity(), content);
                }
            });

            holder.productName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductOrderInfo productOrderInfo=mOrderList.get(groupPosition).productOrderList.get(childPosition);
                    ProductContent content = new ProductContent();
                    content.setId(productOrderInfo.id);
                    content.setDistrict(Constants.REGION_NAME);
                    ProductDetailFragment.launch(getActivity(), content);
                }
            });

            holder.checkBox.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ProductOrderInfo productOrderInfo=mOrderList.get(groupPosition).productOrderList.get(childPosition);
                    productOrderInfo.isSelelcted=isChecked;
                    refreshAllView();
                }
            }));

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProductOrderInfo productOrderInfo=mOrderList.get(groupPosition).productOrderList.get(childPosition);
                    if(productOrderInfo.count>1){
                        HashMap<String,String> requestParams=new HashMap<>();
                        requestParams.put("Quantity", String.valueOf(productOrderInfo.count-1));
                        requestParams.put("Token",App.sToken);
                        requestParams.put("skuId", productOrderInfo.skuId);
                        startRequest(ApiConstants.UPDATE_CART_ORDER_CAR, requestParams, new HttpRequestHandler() {
                            @Override
                            public void onRequestFinished(ResultCode resultCode, String result) {
                                switch (resultCode) {
                                    case success:
                                        DefResponseBean responseBean = ToolsHelper.parseJson(result, DefResponseBean.class);
                                        if (responseBean != null && responseBean.getFlag() == 1) {
                                            productOrderInfo.count--;
                                            //小计
                                            productOrderInfo.amount = productOrderInfo.price * productOrderInfo.count;
                                            mOrderList.get(groupPosition).productsNum--;
                                            mOrderList.get(groupPosition).totalPrice -= productOrderInfo.price;
                                            refreshAllView();
                                        }
                                        break;
                                    case canceled:
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }, HttpRequestUtils.RequestType.POST);
                    }
                }
            });

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProductOrderInfo productOrderInfo = mOrderList.get(groupPosition).productOrderList.get(childPosition);

                    HashMap<String,String> requestParams=new HashMap<>();
                    requestParams.put("Quantity", String.valueOf(productOrderInfo.count+1));
                    requestParams.put("Token",App.sToken);
                    requestParams.put("skuId", productOrderInfo.skuId);
                    startRequest(ApiConstants.UPDATE_CART_ORDER_CAR, requestParams, new HttpRequestHandler() {
                        @Override
                        public void onRequestFinished(ResultCode resultCode, String result) {
                            switch (resultCode) {
                                case success:
                                    DefResponseBean responseBean = ToolsHelper.parseJson(result, DefResponseBean.class);
                                    if (responseBean != null && responseBean.getFlag() == 1) {
                                        productOrderInfo.count++;

                                        //小计
                                        productOrderInfo.amount = productOrderInfo.price * productOrderInfo.count;
                                        mOrderList.get(groupPosition).productsNum++;
                                        mOrderList.get(groupPosition).totalPrice += productOrderInfo.price;

                                        refreshAllView();
                                    }
                                    break;
                                case canceled:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, HttpRequestUtils.RequestType.POST);

                }
            });

            ProductOrderInfo productOrderInfo=mOrderList.get(groupPosition).productOrderList.get(childPosition);

            holder.checkBox.setChecked(productOrderInfo.isSelelcted);

            holder.number.setText(String.valueOf(productOrderInfo.count));

            String imgUrl= ApiConstants.IMG_BASE_URL+productOrderInfo.imgUrl;
            ImageLoader.getInstance().displayImage(imgUrl, holder.imageView, options);

            holder.productName.setText(productOrderInfo.ProductName);
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("￥##0.00");
            holder.unitPrice.setText("单价:" + df.format(productOrderInfo.price));
            holder.totalPrice.setText(df.format(productOrderInfo.price * productOrderInfo.count));

            holder.size.setText("规格:"+productOrderInfo.size );


            if(isLastChild){
                holder.shopTotalPprice.setVisibility(View.VISIBLE);
                holder.shopTotalCount.setVisibility(View.VISIBLE);

                double totalPrice=0;
                int totalCount=0;
                ShopOrderInfo shopOrderInfo=mOrderList.get(groupPosition);
                for(ProductOrderInfo orderInfo:shopOrderInfo.productOrderList){
                    if(orderInfo.isSelelcted){
                        totalPrice+=(orderInfo.price * orderInfo.count);
                        totalCount+=orderInfo.count;
                    }
                }

                holder.shopTotalPprice.setText("小计:" + df.format(totalPrice));
                holder.shopTotalCount.setText(String.format("%d件",totalCount));
            }else {
                holder.shopTotalPprice.setVisibility(View.GONE);
                holder.shopTotalCount.setVisibility(View.GONE);
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    private class GroupHolder{
        CheckBox ckShopName;
    }

    private class ChildHolder{
        CheckBox checkBox;
        ImageView imageView;
        TextView productName;
        TextView totalPrice;
        TextView minus;
        TextView number;
        TextView plus;

        TextView shopTotalPprice;
        TextView shopTotalCount;

        TextView size;
        TextView unitPrice;
    }

    private class ShopOrderInfo{
        List<ProductOrderInfo> productOrderList =new ArrayList<>();
        String shopName;
        boolean isSelectedAll=false;
        int productsNum=0;
        double totalPrice=0;
        private int shopId;
    }

    private class ProductOrderInfo{
        int count;
        int id;
        String imgUrl;
        String ProductName;
        double price;
        String productCode;
        int shopId;
        String size;
        String skuId;
        double amount;
        int carId;
        boolean isSelelcted=false;
    }
}
