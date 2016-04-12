package com.hardware.ui.shop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.ShopListResponseBean;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class AllShopFragment extends APullToRefreshListFragment<AllShopFragment.ShopInfo> {
    private final static String ARG_KEY = "key";

    private DisplayImageOptions options;

    private int mShopTypeId;

    private int mSelectedTabres=R.id.tv_tab_all;

    @ViewInject(id = R.id.tv_tab_all,click = "OnClick")
    private TextView mTabAll;

    @ViewInject(id = R.id.tv_tab_distance,click = "OnClick")
    private TextView mTabDistance;

    public static void launch(Activity from ,int typeId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, typeId);
        FragmentContainerActivity.launch(from, AllShopFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopTypeId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY)
                : (int) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_KEY, mShopTypeId);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected int inflateContentView() {
        return R.layout.all_shop_lay_pull_to_freshlist;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("全部店铺");
        options= ToolsHelper.buldDefDisplayImageOptions();
        refreshTab();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=10;
    }

    @Override
    public int getFirstPageIndex() {
        return 1;
    }


    @Override
    protected ABaseAdapter.AbstractItemView<AllShopFragment.ShopInfo> newItemView() {
        return new ShopInfoItemView();
    }

    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", mShopTypeId);
        requestParams.put("page", getNextPage(mode));
        requestParams.put("regionName", Constants.REGION_NAME);

        startRequest(ApiConstants.MORE_ALL_SHOP_LIST, requestParams, new PagingTask<ShopListResponseBean>(mode) {
            @Override
            public ShopListResponseBean parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, ShopListResponseBean.class);
            }

            @Override
            protected List<ShopInfo> parseResult(ShopListResponseBean MoreDiscountShopResponse) {
                List<ShopInfo> tempProducts = new LinkedList<>();
                if (MoreDiscountShopResponse != null && MoreDiscountShopResponse.getFlag() == 1) {
                    for (ShopListResponseBean.MessageEntity.RowsEntity responseItem : MoreDiscountShopResponse.getMessage().getRows()) {
                        ShopInfo product = new ShopInfo();
                        product.setId(responseItem.getId());
                        product.setShopGrade(responseItem.getShopGrade());
                        product.setShopName(responseItem.getShopName());
                        product.setLogo(responseItem.getLogo());
                        product.setCompanyName(responseItem.getCompanyName());
                        product.setCompanyRegionId(responseItem.getCompanyRegionId());
                        product.setCompanyAddress(responseItem.getCompanyAddress());
                        product.setCreateDate(responseItem.getCreateDate());
                        product.setProductNumbere(responseItem.getProductNumbere());
                        product.setOrderProduct(responseItem.getOrderProduct());
                        product.setBuyerNumber(responseItem.getBuyerNumber());
                        product.setDistance(responseItem.getDistance());
                        tempProducts.add(product);
                    }
                }
                return tempProducts;
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    class ShopInfoItemView extends ABaseAdapter.AbstractItemView<ShopInfo> {
        @ViewInject(id = R.id.image)
        ImageView imageView;
        @ViewInject(id = R.id.name)
        TextView shopName;
        @ViewInject(id = R.id.businesssphere)
        TextView businesssphere ;
        @ViewInject(id = R.id.num_info)
        TextView numInfo;
        @ViewInject(id = R.id.companyaddress)
        TextView companyaddress ;

        @Override
        public int inflateViewId() {
            return R.layout.all_shop_list_item;
        }

        @Override
        public void bindingData(View convertView, ShopInfo data) {
            shopName.setText(data.getShopName());
            businesssphere.setText("主营产品："+data.getOrderProduct());

            numInfo.setText(data.getCreateDate() + "年  ");

            String productNumbere=String.valueOf(data.getProductNumbere());
            SpannableString ss = new SpannableString(productNumbere);
            ss.setSpan(new ForegroundColorSpan(0xff2576AD), 0, productNumbere.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            numInfo.append(ss);
            String subStr="件产品  |  "+data.getOrderProduct()+"位买家";
            ss = new SpannableString(subStr);
            ss.setSpan(new ForegroundColorSpan(0xff4c4c4c), 0, subStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            numInfo.append(ss);

            companyaddress.setText("地址 : "+data.getCompanyAddress());

            String imgUrl= ApiConstants.IMG_BASE_URL+data.getLogo();
            ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
        }
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_all:
                mSelectedTabres=R.id.tv_tab_all;
                break;
            case R.id.tv_tab_distance:
                mSelectedTabres=R.id.tv_tab_distance;
                break;
        }
        refreshTab();
    }
    private void refreshTab(){
        if(mSelectedTabres==R.id.tv_tab_all){
            mTabAll.setBackgroundResource(R.drawable.bg_tab_selected);
            mTabDistance.setBackgroundResource(R.drawable.default_backgroud);
        }else{
            mTabDistance.setBackgroundResource(R.drawable.bg_tab_selected);
            mTabAll.setBackgroundResource(R.drawable.default_backgroud);
        }
    }

    public class ShopInfo {

        private int Id;
        private String ShopGrade;
        private String ShopName;
        private String Logo;
        private String CompanyName;
        private int CompanyRegionId;
        private String CompanyAddress;
        private int CreateDate;
        private int ProductNumbere;
        private int orderProduct;
        private int buyerNumber;
        private int distance;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getShopGrade() {
            return ShopGrade;
        }

        public void setShopGrade(String shopGrade) {
            ShopGrade = shopGrade;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String logo) {
            Logo = logo;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public void setCompanyRegionId(int companyRegionId) {
            CompanyRegionId = companyRegionId;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            CompanyAddress = companyAddress;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(int createDate) {
            CreateDate = createDate;
        }

        public int getProductNumbere() {
            return ProductNumbere;
        }

        public void setProductNumbere(int productNumbere) {
            ProductNumbere = productNumbere;
        }

        public int getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(int orderProduct) {
            this.orderProduct = orderProduct;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
