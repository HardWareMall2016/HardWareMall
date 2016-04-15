package com.hardware.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.GridViewWithHeaderAndFooter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridViewWithHeaderAndFooter;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.ProductContent;
import com.hardware.bean.ShopProductsListResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.activity.GoodsListActivity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ShopHomePageFragment extends ABaseFragment implements AdapterView.OnItemClickListener {
    private final static String ARG_KEY_SHOP_ID = "shopId";
    private final static String ARG_KEY_SHOP_IMG = "shopImg";

    private final static int SORT_ALL = 0;//全部
    private final static int SORT_BY_SALE = 1;//销售量排序
    private final static int SORT_BY_PRICE = 2;//价格排序
    private final static int SORT_BY_NEW = 3;//最新产品

    private final static int PAGE_SIZE=10;

    private boolean QueryMore=false;
    private boolean HasMoreData=true;

    private int mShopId;
    private String mShopImgUrl;
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
    private PullToRefreshGridViewWithHeaderAndFooter mPullRefreshGridView;

    private GridViewWithHeaderAndFooter mGridView;

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
        return R.layout.fragment_shop_home_page;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("店铺首页");
        mInflater=inflater;

        options= buldDisplayImageOptions();

        String imgUrl=ApiConstants.IMG_BASE_URL+mShopImgUrl;
        ImageLoader.getInstance().displayImage(imgUrl, mShopImg, options);

        mGridView=mPullRefreshGridView.getRefreshableView();
        /*View header=inflater.inflate(R.layout.shop_gridview_header,null);
        header.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GoodsListActivity.class);
                startActivity(intent);
            }
        });
        mGridView.addHeaderView(header);*/
        mGridView.setOnItemClickListener(this);

        refreshViews();
        mPullRefreshGridView.setAdapter(mAdpater);

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridViewWithHeaderAndFooter>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridViewWithHeaderAndFooter> refreshView) {
                QueryMore=false;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridViewWithHeaderAndFooter> refreshView) {
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
        requestParams.put("Categoryid","");
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
            case R.id.comprehensive:
                if(shopSort!=SORT_ALL ){
                    shopSort=SORT_ALL ;
                    autoRefresh();
                }
                break;
            case R.id.new_products_sub:
                if(shopSort!=SORT_BY_NEW ){
                    shopSort=SORT_BY_NEW;
                    autoRefresh();
                }
                break;
            case R.id.sale_num:
                if(shopSort!=SORT_BY_SALE ){
                    shopSort=SORT_BY_SALE ;
                    autoRefresh();
                }
                break;
            case R.id.price:
                if(shopSort!=SORT_BY_PRICE ){
                    shopSort=SORT_BY_PRICE;
                    autoRefresh();
                }
                break;
            case R.id.collect_shop:
                break;
            case R.id.products_type:
                ProductsTypeFragment.launch(getActivity(), mShopId);
                break;
            case R.id.company_info:
                CompangyInfoFragment.launch(getActivity(), mShopId);
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
        switch (shopSort){
            case SORT_ALL  :
                setTabSelected(comprehensive,true);
                setTabSelected(newProductsSub,false);
                setTabSelected(sortBySaleNum,false);
                setTabSelected(sortByPrice,false);
                shopSort=SORT_ALL;
                break;
            case SORT_BY_NEW  :
                setTabSelected(comprehensive, false);
                setTabSelected(newProductsSub,true);
                setTabSelected(sortBySaleNum,false);
                setTabSelected(sortByPrice,false);
                shopSort=SORT_BY_NEW;
                break;
            case SORT_BY_SALE  :
                setTabSelected(comprehensive, false);
                setTabSelected(newProductsSub,false);
                setTabSelected(sortBySaleNum,true);
                setTabSelected(sortByPrice,false);
                shopSort=SORT_BY_SALE;
                break;
            case SORT_BY_PRICE   :
                setTabSelected(comprehensive, false);
                setTabSelected(newProductsSub,false);
                setTabSelected(sortBySaleNum,false);
                setTabSelected(sortByPrice, true);
                shopSort=SORT_BY_PRICE;
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductContent content = new ProductContent();
        content.setId(mProducts.get((int) id).getId());
        content.setDistrict(Constants.REGION_NAME);
        ProductDetailFragment.launch(getActivity(), content);
    }

    private void setTabSelected(TextView tab,boolean isSelelcted){
        int selColor=getResources().getColor(R.color.blue);
        int unSelColor=getResources().getColor(R.color.text_color);
        if(isSelelcted){
            tab.setTextColor(selColor);
            tab.setBackgroundResource(R.drawable.bg_dark_blue_underline);
        }else{
            tab.setTextColor(unSelColor);
            tab.setBackgroundResource(R.drawable.bg_white_small);
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
            Calendar calendarCreateTime = Calendar.getInstance();
            calendarCreateTime.setTime(date);
            int months=getMonthToNow(calendarCreateTime);
            if(months>0){
                if(months>=12){
                    diff = (months/12) + "年";
                }else{
                    diff = months + "月";
                }
            }else{
                Calendar calendarNow = Calendar.getInstance();
                long between = (calendarNow.getTimeInMillis() - calendarCreateTime.getTimeInMillis()) / 1000;//除以1000是为了转换成秒
                long days = between / (24 * 3600);
                diff = days + "天";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static int getMonthToNow(Calendar starCal) {
        int sYear = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);
        int sDay = starCal.get(Calendar.DATE);

        Calendar endCal = Calendar.getInstance();
        int eYear = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);
        int eDay = endCal.get(Calendar.DATE);

        int monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

        //开始日期大于结束日期，月份再减1
        if (sDay > eDay) {
            monthday = monthday - 1;
        }
        return monthday;
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
                .build();
    }

}
