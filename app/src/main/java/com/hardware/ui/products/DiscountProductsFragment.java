package com.hardware.ui.products;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.DiscountProductsResponse;
import com.hardware.bean.ProductContent;
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

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class DiscountProductsFragment extends APullToRefreshListFragment<DiscountProductsFragment.Product>{

    private final static String ARG_KEY = "TITLE";

    private DisplayImageOptions options;
    private String mTitle ;

    private List<Product> tempProducts = new LinkedList<>();

    public static void launch(Activity from) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, "热销单品");
        FragmentContainerActivity.launch(from, DiscountProductsFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY)
                : (String) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mTitle);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle(mTitle);
        options=ToolsHelper.buldDefDisplayImageOptions();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=10;
    }

    @Override
    protected ABaseAdapter.AbstractItemView newItemView() {
        return new ProductItemView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductContent content = new ProductContent();
        content.setId(tempProducts.get((int)id).getId());
        content.setDistrict(Constants.REGION_NAME);
        ProductDetailFragment.launch(getActivity(), content);
    }
    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Page", getNextPage(mode));
        requestParams.put("regionName", Constants.REGION_NAME);

        startRequest(ApiConstants.DISCOUNT_PRODUCTSLIST, requestParams, new PagingTask<DiscountProductsResponse>(mode) {
            @Override
            public DiscountProductsResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, DiscountProductsResponse.class);
            }

            @Override
            protected List<Product> parseResult(DiscountProductsResponse moreDiscountSaleResponse) {
                if (moreDiscountSaleResponse != null && moreDiscountSaleResponse.getFlag() == 1) {
                    for (DiscountProductsResponse.MessageEntity.RowsEntity responseItem : moreDiscountSaleResponse.getMessage().getRows()) {
                        Product product = new Product();
                        product.setId(responseItem.getId());
                        product.setImgUrl(responseItem.getImgUrl());
                        product.setMarketPrice(responseItem.getMarketPrice());
                        product.setMinSalePrice(responseItem.getMinSalePrice());
                        product.setProductName(responseItem.getProductName());
                        product.setSaleCounts(responseItem.getSaleCounts());
                        product.setCompanyAddress(responseItem.getCompanyAddress());
                        tempProducts.add(product);
                    }
                }
                return tempProducts;
            }
        }, HttpRequestUtils.RequestType.GET);
    }


    @Override
    public int getFirstPageIndex() {
        return 1;
    }

    class ProductItemView extends ABaseAdapter.AbstractItemView<Product> {
        @ViewInject(id = R.id.image)
        ImageView image;
        @ViewInject(id = R.id.name)
        TextView name;
        @ViewInject(id = R.id.local)
        TextView local;
        @ViewInject(id = R.id.sale)
        TextView salesNum;
        @ViewInject(id = R.id.new_price)
        TextView newPrice;
        @ViewInject(id = R.id.old_price)
        TextView oldPrice;

        @Override
        public int inflateViewId() {
            return R.layout.discount_products_list_item;
        }

        @Override
        public void bindingView(View convertView) {
            super.bindingView(convertView);
            oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void bindingData(View convertView, Product product) {
            name.setText(product.getProductName());
            local.setText(product.getCompanyAddress());
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("￥ 00.00");
            oldPrice.setText(df.format(product.getMarketPrice()));
            newPrice.setText(df.format(product.getMinSalePrice()));
            salesNum.setText(String.format("成交%d笔", product.getSaleCounts()));
            String imgUrl= ApiConstants.IMG_BASE_URL+product.getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, image,options);
        }
    }

    public static class Product {
        private int id;
        private String imgUrl;
        private String ProductName;
        private double MarketPrice;
        private double MinSalePrice;
        private String productCode;
        private String MeasureUnit;
        private int SaleCounts;
        private String CompanyAddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            MarketPrice = marketPrice;
        }

        public double getMinSalePrice() {
            return MinSalePrice;
        }

        public void setMinSalePrice(double minSalePrice) {
            MinSalePrice = minSalePrice;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getMeasureUnit() {
            return MeasureUnit;
        }

        public void setMeasureUnit(String measureUnit) {
            MeasureUnit = measureUnit;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public void setSaleCounts(int saleCounts) {
            SaleCounts = saleCounts;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            CompanyAddress = companyAddress;
        }
    }
}
