package com.hardware.ui.main;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.HomeProductsBean;
import com.hardware.bean.ProductContent;
import com.hardware.ui.home.HomeListFragment;
import com.hardware.ui.home.MoreDiscountShopFragment;
import com.hardware.ui.home.MoreFragment;
import com.hardware.ui.products.DiscountProductsFragment;
import com.hardware.ui.products.MoreDiscountSaleFragment;
import com.hardware.ui.products.ProductDetailFragment;
import com.hardware.ui.shop.ShopHomePageFragment;
import com.hardware.tools.ToolsHelper;
import com.hardware.view.HorizontalListView;
import com.hardware.view.MyGridView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends ABaseFragment{

    @ViewInject(id = R.id.viewpager)
    ViewPager mViewPager;
    @ViewInject(id = R.id.home_gridView)
    MyGridView mGridView ;
    @ViewInject(id = R.id.home_special_offer_gridView)
    MyGridView mSaleGridView ;
    @ViewInject(id = R.id.home_protype_gridview)
    MyGridView mProTypeGridView ;
    @ViewInject(id = R.id.home_horizon_listview)
    HorizontalListView mShopListView ;
    @ViewInject(idStr = "sale_more", click = "OnClick")
    View viewSaleMore;//更多折扣
    @ViewInject(idStr = "home_protype_more", click = "OnClick")
    View viewProtypeMore ;
    @ViewInject(idStr = "home_shop_more", click = "OnClick")
    View viewShopMore ;

    private ArrayList<ImageView> mImageSource;
    private int[] mImages = {R.drawable.home_view_anim_banner1, R.drawable.home_view_anim_banner2, R.drawable.home_view_anim_banner3};
    private int[] mImageIcon = { R.drawable.home_popularity_brand, R.drawable.home_user_buy,
            R.drawable.home_spread, R.drawable.home_consult,R.drawable.home_repair,
            R.drawable.home_build, R.drawable.home_advertise,R.drawable.home_more
           };
    private String[] mIconName = { "人气品牌", "用户求购", "商家推广", "行业资讯", "便民维修", "便民施工", "专业招聘", "更多"};

    private ArrayList<View> mAdList = new ArrayList<>();
    private List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
    private List<HomeProductsBean.MessageEntity.RowsEntity> mSaleList = new ArrayList<>();//折扣特卖
    private List<HomeProductsBean.ProTypeEntity.RowsEntity> mProTypeList = new ArrayList<>();//热销单品
    private List<HomeProductsBean.ShopsEntity.RowsEntity> mShopList = new ArrayList<>();//人气店铺

    private HomePagerAdapter mHomeAdapter;
    private SimpleAdapter mSimpleAdapter;
    private HomeSaleAdapter mSaleAdapter ;
    private HorizontalListViewAdapter mShopAdapter ;
    private int currPage = 0;
    private int oldPage = 0;
    private String [] from ={"image","text"};
    private int [] to = {R.id.image,R.id.text};


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_home;
    }


    @Override
    public boolean isCacheRootView() {
        return true;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        mAdList.clear();
        mDataList.clear();
        mSaleList.clear();
        mProTypeList.clear();
        mShopList.clear();

        initViewPager();
        getData();

        mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList, R.layout.home_gridview_item, from, to);
        mGridView.setAdapter(mSimpleAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 7){
                    MoreFragment.launch(getActivity(), mIconName[position]);
                }else{
                    HomeListFragment.launch(getActivity(), mIconName[position]);
                }
            }
        });

        mSaleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int priductId = mSaleList.get(position).getId() ;
                ProductContent content = new ProductContent();
                content.setId(priductId);
                content.setDistrict(Constants.REGION_NAME);
                ProductDetailFragment.launch(getActivity(), content);
            }
        });
        mProTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int priductId = mProTypeList.get(position).getId() ;
                ProductContent content = new ProductContent();
                content.setId(priductId);
                content.setDistrict(Constants.REGION_NAME);
                ProductDetailFragment.launch(getActivity(), content);

            }
        });
        mShopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopHomePageFragment.launch(getActivity(), mShopList.get(position).getId(),mShopList.get(position).getLogo());
            }
        });
    }

    @Override
    public void requestData() {
        RequestParams requestParams=new RequestParams();
        requestParams.put("regionName", Constants.REGION_NAME);
        startRequest(ApiConstants.MOBILE_HOME_PRODUCTS_LIST, requestParams, new BaseHttpRequestTask<HomeProductsBean>() {
            @Override
            public HomeProductsBean parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, HomeProductsBean.class);
            }

            @Override
            protected void onSuccess(HomeProductsBean responseBean) {
                super.onSuccess(responseBean);
                if (responseBean != null && responseBean.getFlag() == 1) {
                    mSaleList = responseBean.getMessage().getRows();
                    mProTypeList = responseBean.getProType().getRows();
                    mShopList = responseBean.getShops().getRows();
                    if(mShopList != null){
                        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        mShopListView.setMinimumHeight(width/3+30);
                    }
                    mShopListView.setAdapter(new HorizontalListViewAdapter(mShopList));
                    mSaleGridView.setAdapter(new HomeSaleAdapter(mSaleList));
                    mProTypeGridView.setAdapter(new HomeProTypeAdapter(mProTypeList));
                }
            }

        }, HttpRequestUtils.RequestType.GET);
    }

    private List<Map<String, Object>> getData(){
        for(int i=0;i<mImageIcon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", mImageIcon[i]);
            map.put("text", mIconName[i]);
            mDataList.add(map);
        }
        return mDataList;
    }


    private void initViewPager() {
        mImageSource = new ArrayList<ImageView>();
        for (int i = 0; i < mImages.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setBackgroundResource(mImages[i]);
            mImageSource.add(image);
        }

        mAdList.add(findViewById(R.id.dot1));
        mAdList.add(findViewById(R.id.dot2));
        mAdList.add(findViewById(R.id.dot3));

        mHomeAdapter = new HomePagerAdapter();
        mViewPager.setAdapter(mHomeAdapter);
        MyPageChangeListener listener = new MyPageChangeListener();
        mViewPager.setOnPageChangeListener(listener);

        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        ViewPagerTask pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
    }


    private class HomePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageSource.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageSource.get(position));
            return mImageSource.get(position);
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mAdList.get(position).setBackgroundResource(R.drawable.dot_focused);
            mAdList.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
            oldPage = position;
            currPage = position;
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currPage = (currPage + 1) % mImages.length;
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currPage);
        }
    };

    private class HomeSaleAdapter extends BaseAdapter{

        private List<HomeProductsBean.MessageEntity.RowsEntity> saleList = new ArrayList<>();
        public HomeSaleAdapter(List<HomeProductsBean.MessageEntity.RowsEntity> saleList) {
            this.saleList = saleList ;
        }

        @Override
        public int getCount() {
            if(saleList.size() > 6){
                return 6;
            }else{
                return saleList.size();
            }

        }

        @Override
        public Object getItem(int position) {
            return saleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_sale_item,null);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_home_sale_shop);
                viewHolder.saleShopName = (TextView)convertView.findViewById(R.id.home_sale_shopname);
                viewHolder.saleDiscountPrice = (TextView)convertView.findViewById(R.id.home_sale_shop_discount_price);
                viewHolder.saleOriginalPrice = (TextView)convertView.findViewById(R.id.home_sale_shop_original_price);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.saleShopName.setText(saleList.get(position).getProductName());
            viewHolder.saleDiscountPrice.setText("￥"+saleList.get(position).getMinSalePrice()+"元");
            viewHolder.saleOriginalPrice.setText("￥"+saleList.get(position).getMarketPrice() + "元");
            viewHolder.saleOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            String imgUrl=ApiConstants.IMG_BASE_URL  + saleList.get(position).getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.imageView);
            return convertView;
        }
    }


    private class HomeProTypeAdapter extends BaseAdapter {
        private List<HomeProductsBean.ProTypeEntity.RowsEntity> mProTypeList = new ArrayList<>();
        public HomeProTypeAdapter(List<HomeProductsBean.ProTypeEntity.RowsEntity> mProTypeList) {
            this.mProTypeList = mProTypeList ;
        }

        @Override
        public int getCount() {
            if(mProTypeList.size() > 6){
                return 6;
            }else{
                return mProTypeList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return mProTypeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProTypeViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ProTypeViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_protype_item,null);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_home_protype_shop);
                viewHolder.protypeName = (TextView)convertView.findViewById(R.id.home_protype_shopname);
                viewHolder.protypePrice = (TextView)convertView.findViewById(R.id.home_protype_shop_price);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ProTypeViewHolder)convertView.getTag();
            }

            viewHolder.protypeName.setText(mProTypeList.get(position).getProductName());
            viewHolder.protypePrice.setText("￥"+mProTypeList.get(position).getMarketPrice()+"元");
            String imgUrl=ApiConstants.IMG_BASE_URL  + mProTypeList.get(position).getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.imageView);
            return convertView;
        }
    }

    private class HorizontalListViewAdapter extends BaseAdapter{

        private List<HomeProductsBean.ShopsEntity.RowsEntity> mShopList = new ArrayList<>();
        public HorizontalListViewAdapter(List<HomeProductsBean.ShopsEntity.RowsEntity> mShopList) {
            this.mShopList = mShopList ;
        }

        @Override
        public int getCount() {
            return mShopList.size();
        }

        @Override
        public Object getItem(int position) {
            return mShopList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShopViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ShopViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_shop_item,null);
                viewHolder.shopName = (TextView)convertView.findViewById(R.id.home_shop_shopname);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_home_shop_shop);
                /*viewHolder.shopBusiness = (TextView)convertView.findViewById(R.id.home_shop_business);*/
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ShopViewHolder)convertView.getTag();
            }
            viewHolder.shopName.setText(mShopList.get(position).getShopName());
            String imgUrl=ApiConstants.IMG_BASE_URL  + mShopList.get(position).getLogo();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.imageView);
            return convertView;
        }
    }

    private class ShopViewHolder{
        TextView shopName ;
//        TextView shopBusiness ;
        ImageView imageView ;
    }
    private class ProTypeViewHolder {
        TextView protypeName ;
        TextView protypePrice ;
        ImageView imageView ;
    }

    private class ViewHolder{
        TextView saleShopName ;
        TextView saleDiscountPrice ;
        TextView saleOriginalPrice ;
        ImageView imageView ;
    }


    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.sale_more:
                MoreDiscountSaleFragment.launch(getActivity(), getString(R.string.sale_more));
                break;
            case R.id.home_protype_more:
                DiscountProductsFragment.launch(getActivity());
                break;
            case R.id.home_shop_more:
                MoreDiscountShopFragment.launch(getActivity());
                break;
        }
    }
}
