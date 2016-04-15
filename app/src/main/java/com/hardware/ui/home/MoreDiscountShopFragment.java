package com.hardware.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.MoreDiscountShopResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.shop.ShopHomePageFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MoreDiscountShopFragment extends ABaseFragment {

    private final static int PAGE_SIZE = 10;

    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;

    private boolean QueryMore = false;
    private boolean HasMoreData = true;

    private LayoutInflater mInflater;

    private List<Product> mProducts = new LinkedList<>();
    private ProductAdapter mAdpater = new ProductAdapter();


    private Handler mHandler = new Handler();

    private class Product {
        private int Id;
        private String ShopGrade;
        private String ShopName;
        private String BusinessSphere;
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

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getShopGrade() {
            return ShopGrade;
        }

        public void setShopGrade(String ShopGrade) {
            this.ShopGrade = ShopGrade;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String CompanyAddress) {
            this.CompanyAddress = CompanyAddress;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(int CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getProductNumbere() {
            return ProductNumbere;
        }

        public void setProductNumbere(int ProductNumbere) {
            this.ProductNumbere = ProductNumbere;
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


    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, MoreDiscountShopFragment.class, null);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_more_discount_shop;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle(getString(R.string.home_shop_more_title));
        mInflater = inflater;
        mListView = mPullToRefreshListView.getRefreshableView();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放开始刷新");
        intPullUpLable(true);
        mPullToRefreshListView.setAdapter(mAdpater);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    QueryMore = false;
                    intPullUpLable(true);
                    requestData();
                } else {
                    //上拉加载更多
                    if (!HasMoreData) {
                        onRefreshComplete();
                        return;
                    }
                    QueryMore = true;
                    requestData();
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopHomePageFragment.launch(getActivity(), mProducts.get((int) id).getId(), mProducts.get((int) id).getLogo());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
    }

    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Page", getNextPage());
        requestParams.put("regionName", 1);
        startRequest(ApiConstants.MORE_POPULARITY_SHOP_LIST, requestParams, new BaseHttpRequestTask<MoreDiscountShopResponse>() {
            @Override
            public MoreDiscountShopResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, MoreDiscountShopResponse.class);
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                onRefreshComplete();
            }

            @Override
            protected void onSuccess(MoreDiscountShopResponse response) {
                super.onSuccess(response);
                if (!QueryMore) {
                    mProducts.clear();
                }
                if (response != null && response.getFlag() == 1) {
                    List<Product> tempProducts = new LinkedList<>();
                    for (MoreDiscountShopResponse.MessageBean.RowsBean responseItem : response.getMessage().getRows()) {
                        Product product = new Product();
                        product.setId(responseItem.getId());
                        product.setShopGrade(responseItem.getShopGrade());
                        product.setShopName(responseItem.getShopName());
                        product.setBusinessSphere(responseItem.getBusinessSphere());
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

                    if (tempProducts.size() == PAGE_SIZE) {
                        HasMoreData = true;
                    } else {
                        HasMoreData = false;
                    }

                    intPullUpLable(HasMoreData);
                    mProducts.addAll(tempProducts);
                    mAdpater.notifyDataSetChanged();
                }
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    private int getNextPage() {
        if (!QueryMore) {
            return 1;
        }
        int pageIndex = 1 + mProducts.size() / PAGE_SIZE;
        return pageIndex;
    }

    private void onRefreshComplete() {
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable, 50);
    }

    private Runnable mRefreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            mPullToRefreshListView.onRefreshComplete();
        }
    };

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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.more_discount_shop_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.shopName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.businesssphere = (TextView) convertView.findViewById(R.id.businesssphere);
                viewHolder.createDate = (TextView) convertView.findViewById(R.id.createDate);
                viewHolder.productnumber = (TextView) convertView.findViewById(R.id.productnumber);
                viewHolder.orderproduct = (TextView) convertView.findViewById(R.id.orderproduct);
                viewHolder.companyaddress = (TextView) convertView.findViewById(R.id.companyaddress);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.shopName.setText(mProducts.get(position).getShopName());
            viewHolder.businesssphere.setText("经营模式：" + mProducts.get(position).getBusinessSphere());
            viewHolder.createDate.setText(mProducts.get(position).getCreateDate() + "年");
            viewHolder.productnumber.setText(mProducts.get(position).getProductNumbere() + "件产品 |");
            viewHolder.orderproduct.setText(mProducts.get(position).getOrderProduct() + "位买家");
            viewHolder.companyaddress.setText(mProducts.get(position).getCompanyAddress());
            return convertView;
        }
    }

    class ViewHolder {
        TextView shopName;
        TextView businesssphere;
        TextView createDate;
        TextView productnumber;
        TextView orderproduct;
        TextView companyaddress;
    }


    private void intPullUpLable(boolean canLoadMore) {
        if (canLoadMore) {
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        } else {
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("没有更多数据了");
        }
    }


}
