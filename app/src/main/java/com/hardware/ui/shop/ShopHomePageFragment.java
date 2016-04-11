package com.hardware.ui.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ShopProductsListResponse;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ShopHomePageFragment extends ABaseFragment {
    private final static String ARG_KEY_SHOP_ID = "shopId";
    private final static String ARG_KEY_SHOP_IMG = "shopImg";

    private final static int SORT_ALL = 0;//全部
    private final static int SORT_BY_SALE = 1;//销售量排序
    private final static int SORT_BY_PRICE = 2;//价格排序
    private final static int SORT_BY_NEW = 3;//最新产品

    private final static int TAB_HOME_PAGE = 0;//首页
    private final static int TAB_COMPREHENSIVE = 1;//综合
    private final static int TAB_NEW_PRODUCTS = 2;//新品
    private final static int TAB_SALE_NUM = 3;//销量
    private final static int TAB_PRICE = 4;//价格
    private final static int TAB_NEW_ARRIVAL = 5;//新品上架

    private final static int PAGE_SIZE=10;

    private boolean QueryMore=false;
    private boolean HasMoreData=true;

    private int mShopId;
    private String mShopImgUrl;
    private int mTab = TAB_HOME_PAGE;
    private int shopSort=SORT_ALL;

    @ViewInject(id = R.id.shopImg)
    private ImageView mShopImg;
    @ViewInject(id = R.id.shop_name)
    private TextView mShopName;
    @ViewInject(id = R.id.shop_age)
    private TextView mShopAge;
    @ViewInject(id = R.id.shop_grade)
    private TextView mShopGrade;
    @ViewInject(id = R.id.collect_shop, click = "OnClick")
    private TextView mCollectShop;
    @ViewInject(id = R.id.shop_fans)
    private TextView mShopFans;

    @ViewInject(id = R.id.shop_home_page, click = "OnClick")
    private TextView mShopHomePage;
    @ViewInject(id = R.id.all_products, click = "OnClick")
    private TextView mAllProducts;
    @ViewInject(id = R.id.new_products, click = "OnClick")
    private TextView mNewProducts;

    @ViewInject(id = R.id.content_all_sub)
    private View mAllSubView;

    @ViewInject(id = R.id.comprehensive, click = "OnClick")
    private TextView comprehensive;
    @ViewInject(id = R.id.new_products_sub, click = "OnClick")
    private TextView newProductsSub;
    @ViewInject(id = R.id.sale_num, click = "OnClick")
    private TextView sortBySaleNum;
    @ViewInject(id = R.id.price, click = "OnClick")
    private TextView sortByPrice;

    @ViewInject(id = R.id.products_type, click = "OnClick")
    private TextView productsType;

    @ViewInject(id = R.id.company_info, click = "OnClick")
    private TextView companyInfo;


    @ViewInject(id = R.id.productsGridview)
    private PullToRefreshGridView mPullRefreshGridView;

    private GridView mGridView;

    private List<Product> mProducts=new LinkedList<>();

    private LayoutInflater mInflater;
    private ProductAdapter mAdpater=new ProductAdapter();

    private DisplayImageOptions options;

    public static void launch(Activity from, int shopId,String shopImg) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY_SHOP_ID, shopId);
        args.add(ARG_KEY_SHOP_IMG, shopImg);
        FragmentContainerActivity.launch(from, ShopHomePageFragment.class, args);
    }

    private class Product{
        private int Id;
        private String imgUrl;
        private String name;
        private int SaleCounts;
        private float MarketPrice;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public void setSaleCounts(int SaleCounts) {
            this.SaleCounts = SaleCounts;
        }

        public float getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(float MarketPrice) {
            this.MarketPrice = MarketPrice;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY_SHOP_ID)
                : (int) savedInstanceState.getSerializable(ARG_KEY_SHOP_ID);

        mShopImgUrl= savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY_SHOP_IMG)
                : (String) savedInstanceState.getSerializable(ARG_KEY_SHOP_IMG);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_shop_home_page;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("店铺首页");
        mInflater=inflater;

        options= buldDisplayImageOptions();

        String imgUrl=ApiConstants.IMG_BASE_URL+mShopImgUrl;
        ImageLoader.getInstance().displayImage(imgUrl, mShopImg,options);

        mGridView=mPullRefreshGridView.getRefreshableView();
        refreshViews();
        mPullRefreshGridView.setAdapter(mAdpater);

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                QueryMore=false;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                if(!HasMoreData){
                    mPullRefreshGridView.onRefreshComplete();
                    return;
                }
                QueryMore=true;
                requestData();
            }

        });
    }

    @Override
    public void requestData() {
        mPullRefreshGridView.onRefreshComplete();
        RequestParams requestParams = new RequestParams();
        requestParams.put("shopId", mShopId);
        requestParams.put("shopSort", shopSort);
        requestParams.put("page", getNextPage());
        startRequest(ApiConstants.SHOP_PRODUCTS_LIST, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        if (!QueryMore) {
                            mProducts.clear();
                        }

                        ShopProductsListResponse response = ToolsHelper.parseJson(result, ShopProductsListResponse.class);
                        if (response != null && response.getFlag() == 1) {
                            mShopName.setText(response.getShops().getShopName());
                            mShopAge.setText(getTimeSpanStr(response.getShops().getCreateDate()));
                            mShopGrade.setText(response.getShops().getGradName());
                            mShopFans.setText(response.getShops().getAttention() + "\n粉丝");

                            List<Product> tempProducts = new LinkedList<>();
                            for (ShopProductsListResponse.MessageEntity messageEntity : response.getMessage()) {
                                Product product = new Product();
                                product.setId(messageEntity.getId());
                                product.setImgUrl(messageEntity.getImgUrl());
                                product.setMarketPrice(messageEntity.getMarketPrice());
                                product.setName(messageEntity.getName());
                                product.setSaleCounts(messageEntity.getSaleCounts());
                                tempProducts.add(product);
                            }

                            if (tempProducts.size() == PAGE_SIZE) {
                                HasMoreData = true;
                            } else {
                                HasMoreData = false;
                            }

                            mProducts.addAll(tempProducts);

                            mAdpater.notifyDataSetChanged();

                            if (!QueryMore && mProducts.size() > 0) {
                                mGridView.setSelection(0);
                            }
                        }

                        break;
                    case canceled:
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
                mPullRefreshGridView.onRefreshComplete();

            }
        }, HttpRequestUtils.RequestType.GET);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY_SHOP_ID, mShopId);

        outState.putSerializable(ARG_KEY_SHOP_IMG, mShopImgUrl);
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.shop_home_page:
                if(mTab!=TAB_HOME_PAGE){
                    mTab=TAB_HOME_PAGE;
                    autoRefresh();
                }
                break;
            case R.id.all_products:
                if(mTab!=TAB_COMPREHENSIVE){
                    mTab=TAB_COMPREHENSIVE;
                    autoRefresh();
                }
                break;
            case R.id.new_products:
                if(mTab!=TAB_NEW_ARRIVAL){
                    mTab=TAB_NEW_ARRIVAL;
                    autoRefresh();
                }
                break;
            case R.id.comprehensive:
                if(mTab!=TAB_COMPREHENSIVE){
                    mTab=TAB_COMPREHENSIVE;
                    autoRefresh();
                }
                break;
            case R.id.new_products_sub:
                if(mTab!=TAB_NEW_PRODUCTS){
                    mTab=TAB_NEW_PRODUCTS;
                    autoRefresh();
                }
                break;
            case R.id.sale_num:
                if(mTab!=TAB_SALE_NUM){
                    mTab=TAB_SALE_NUM;
                    autoRefresh();
                }
                break;
            case R.id.price:
                if(mTab!=TAB_PRICE){
                    mTab=TAB_PRICE;
                    autoRefresh();
                }
                break;
            case R.id.collect_shop:
                break;
            case R.id.products_type:
                break;
            case R.id.company_info:
                break;
        }
    }

    private void autoRefresh(){
        refreshViews();
        QueryMore=false;
        HasMoreData=true;
        mPullRefreshGridView.setRefreshing();
    }

    private void refreshViews(){
        switch (mTab){
            case TAB_HOME_PAGE :
                setTabSelected(mShopHomePage,true,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,false,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);
                mAllSubView.setVisibility(View.GONE);
                shopSort=SORT_ALL;
                break;
            case TAB_COMPREHENSIVE :
                mAllSubView.setVisibility(View.VISIBLE);
                setTabSelected(mShopHomePage, false, R.drawable.shop_home_sellected, R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);

                setTabSelected(comprehensive,true,0,0);
                setTabSelected(newProductsSub,false,0,0);
                setTabSelected(sortBySaleNum,false,0,0);
                setTabSelected(sortByPrice,false,0,0);
                shopSort=SORT_ALL;
                break;
            case TAB_NEW_PRODUCTS :
                mAllSubView.setVisibility(View.VISIBLE);
                setTabSelected(mShopHomePage, false, R.drawable.shop_home_sellected, R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);

                setTabSelected(comprehensive, false, 0, 0);
                setTabSelected(newProductsSub,true,0,0);
                setTabSelected(sortBySaleNum,false,0,0);
                setTabSelected(sortByPrice,false,0,0);
                shopSort=SORT_BY_NEW;
                break;
            case TAB_SALE_NUM :
                mAllSubView.setVisibility(View.VISIBLE);
                setTabSelected(mShopHomePage, false, R.drawable.shop_home_sellected, R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);

                setTabSelected(comprehensive, false, 0, 0);
                setTabSelected(newProductsSub,false,0,0);
                setTabSelected(sortBySaleNum,true,0,0);
                setTabSelected(sortByPrice,false,0,0);
                shopSort=SORT_BY_SALE;
                break;
            case TAB_PRICE  :
                mAllSubView.setVisibility(View.VISIBLE);
                setTabSelected(mShopHomePage, false, R.drawable.shop_home_sellected, R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);

                setTabSelected(comprehensive, false, 0, 0);
                setTabSelected(newProductsSub,false,0,0);
                setTabSelected(sortBySaleNum,false,0,0);
                setTabSelected(sortByPrice,true,0,0);

                shopSort=SORT_BY_PRICE;
                break;
            case TAB_NEW_ARRIVAL  :
                setTabSelected(mShopHomePage,false,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,false,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,true,R.drawable.new_products_selected,R.drawable.new_products_normal);
                mAllSubView.setVisibility(View.GONE);

                shopSort=SORT_BY_NEW;
                break;
        }
    }


    private void setTabSelected(TextView tab,boolean isSelelcted,int selDrawableRes,int unSelDrawableRes){
        int selColor=getResources().getColor(R.color.blue);
        int unSelColor=getResources().getColor(R.color.text_color);
        if(isSelelcted){
            tab.setTextColor(selColor);
            tab.setBackgroundResource(R.drawable.bg_dark_blue_underline);
            tab.setCompoundDrawablesWithIntrinsicBounds(0,selDrawableRes,0,0);
        }else{
            tab.setTextColor(unSelColor);
            tab.setBackgroundResource(R.drawable.bg_white_small);
            tab.setCompoundDrawablesWithIntrinsicBounds(0,unSelDrawableRes,0,0);
        }
    }

    public class ProductAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return mProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoler holer;
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.shop_gridview_item,null);
                holer=new ViewHoler();
                holer.image=(ImageView)convertView.findViewById(R.id.image);
                holer.image.setMinimumWidth(mGridView.getColumnWidth());
                holer.image.setMinimumHeight(mGridView.getColumnWidth());
                holer.name=(TextView)convertView.findViewById(R.id.name);
                holer.price=(TextView)convertView.findViewById(R.id.price);
                holer.salseNum=(TextView)convertView.findViewById(R.id.sale_count);
                convertView.setTag(holer);
            }else{
                holer=(ViewHoler)convertView.getTag();
            }

            Product product=mProducts.get(position);
            holer.name.setText(product.getName());
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("￥ ###.00");
            holer.price.setText(df.format(product.getMarketPrice()));
            holer.salseNum.setText(String.format("成交%d笔", product.getSaleCounts()));
            String imgUrl=ApiConstants.IMG_BASE_URL+product.getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, holer.image,options);

            return convertView;
        }
    }

    class ViewHoler{
        ImageView image;
        TextView name;
        TextView price;
        TextView salseNum;
    }


    private String getTimeSpanStr(String startTime) {
        String diff = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = df.parse(startTime);
            Calendar calendarNow = Calendar.getInstance();
            Calendar calendarCreateTime = Calendar.getInstance();
            calendarCreateTime.setTime(date);

            long between = (calendarNow.getTimeInMillis() - calendarCreateTime.getTimeInMillis()) / 1000;//除以1000是为了转换成秒
            long days = between / (24 * 3600);
            diff = days + "天";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    private int getNextPage(){
        if(!QueryMore){
            return 1;
        }
        int pageIndex=1+mProducts.size()/PAGE_SIZE;
        return pageIndex;
    }

    public DisplayImageOptions buldDisplayImageOptions(){
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                /*.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少*/
                .build();
    }

}
