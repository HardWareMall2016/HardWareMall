package com.hardware.ui.products;

/**
 * Created by Administrator on 2016/4/11.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.AddOrderCarRespon;
import com.hardware.bean.AppraiseContent;
import com.hardware.bean.ProductContent;
import com.hardware.bean.ProductSskuRespon;
import com.hardware.bean.ProductsDetailResponse;
import com.hardware.bean.ShopRecommendListRespon;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.main.LoginFragment;
import com.hardware.ui.shop.ShopHomePageFragment;
import com.hardware.view.ActionSheetDialog;
import com.hardware.view.MyGridView;
import com.hardware.view.RatingBar;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/11.
 */
public class ProductDetailFragment extends ABaseFragment {
    private final static String ARG_KEY = "productId";

    @ViewInject(id = R.id.iv_back, click = "OnClick")
    ImageView mBack;
    //第一页
    @ViewInject(id = R.id.products_detail_head_bg)
    ImageView mProductHead;
    @ViewInject(id = R.id.products_detail_productname)
    TextView mProductName;
    @ViewInject(id = R.id.products_wholesale)
    TextView mProductsWholesale;
    @ViewInject(id = R.id.products_wholesale_price)
    TextView mProductsWholesalePrice;
    @ViewInject(id = R.id.products_detail_express)
    TextView mProductsExpress;
    @ViewInject(id = R.id.products_detail_sales_volume)
    TextView mPrductsSalesVolume;
    @ViewInject(id = R.id.products_detail_place)
    TextView mProductsPlace;
    @ViewInject(id = R.id.products_detail_comment_num)
    TextView mProductsCommentNum;
    @ViewInject(id = R.id.products_detail_appraise)
    RelativeLayout mAppraise;
    @ViewInject(id = R.id.tv_products_detail_appraise_numbers)
    TextView mProductsAppriceNum;
    @ViewInject(id = R.id.products_detail_ratingbar)
    RatingBar mProductsRatingbar;
    @ViewInject(id = R.id.products_detail_shopurl)
    ImageView mProductsDetailLogo;
    @ViewInject(id = R.id.tv_products_detail_shopname)
    TextView mProductShopName;
    @ViewInject(id = R.id.tv_products_detail_year)
    TextView mProductDetailYear;
    @ViewInject(id = R.id.products_detail_shop)
    LinearLayout mDetailShop;
    @ViewInject(id = R.id.products_detail_describe)
    TextView mDetailDescribe;
    @ViewInject(id = R.id.products_detail_describe_mark)
    TextView mDetailMark;
    @ViewInject(id = R.id.products_detail_service)
    TextView mDetailService;
    @ViewInject(id = R.id.products_detail_service_mark)
    TextView mDetailServiceMark;
    @ViewInject(id = R.id.products_detail_delivery)
    TextView mDetailDelivery;
    @ViewInject(id = R.id.products_detail_delivery_mark)
    TextView mDetailDeliveryMark;
    @ViewInject(id = R.id.products_detail_type, click = "OnClick")
    RelativeLayout mProductsDetailType;
    @ViewInject(id = R.id.products_detail_cart,click = "OnClick")
    RelativeLayout mProductCart ;
    @ViewInject(id = R.id.products_detail_order,click = "OnClick")
    RelativeLayout mProductOrder ;

    //第二页
    @ViewInject(id = R.id.detail_picture, click = "OnClick")
    TextView mDetailPicture;
    @ViewInject(id = R.id.detail_products, click = "OnClick")
    TextView mDetailProducts;
    @ViewInject(id = R.id.detail_recommend, click = "OnClick")
    TextView mDetailRecommend;
    @ViewInject(id = R.id.detail_picture_framelayout)
    FrameLayout mPictureFrameLayout;
    @ViewInject(id = R.id.detail_products_framelayout)
    FrameLayout mProductFrameLayout;
    @ViewInject(id = R.id.detail_recommend_framelayout)
    FrameLayout mRecommendFrameLayout;
    @ViewInject(id = R.id.detail_picture_framelayout_detail)
    TextView mProductDetailPrictue;
    @ViewInject(id = R.id.detail_recommend_gridview)
    MyGridView mRecommengGridView;


    private ProductContent content;
    private int id;
    private int shopid;
    private String district;
    private int RecommendFrameLayoutfalg = 0;
    private List<ShopRecommendListRespon.MessageEntity> mRecommendList = new ArrayList<>();
    private RecommendAdpater mRecommendAdpater;
    private DisplayImageOptions options;
    private int CommentSum;
    private int PackMark;
    private int ServiceMark;
    private int DeliveryMark;
    private String imgUrl ;
    private String productName ;
    private String productPrice ;

    private String mColor ;
    private String mSize ;
    private String mVersion ;

    private String mDialog_SKUId ;
    private String mDialog_Version ;
    private String mDialog_Color ;
    private String mDialog_Size ;
    private double mDialog_SalePrice ;
    private int mDialog_Stock;
    private boolean flag = true;



    public static void launch(FragmentActivity activity, ProductContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, ProductDetailFragment.class, args, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (ProductContent) getArguments().getSerializable(ARG_KEY)
                : (ProductContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_product_detail_layout;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        id = content.getId();
        district = content.getDistrict();
        options = ToolsHelper.buldDefDisplayImageOptions();
    }

    @Override
    public void requestData() {

        if (RecommendFrameLayoutfalg == 0) {
            final RequestParams requestParams = new RequestParams();
            requestParams.put("id", id);
            requestParams.put("district", district);

            startRequest(ApiConstants.PRODUCTS_DETAIL, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:
                            final ProductsDetailResponse response = ToolsHelper.parseJson(result, ProductsDetailResponse.class);
                            if (response != null && response.getFlag() == 1) {
                                imgUrl = ApiConstants.IMG_BASE_URL + response.getMessage().getImgUrl();
                                ImageLoader.getInstance().displayImage(imgUrl, mProductHead);
                                mProductName.setText(response.getMessage().getProductName());
                                productName = response.getMessage().getProductName();
                                //mProductsWholesale.setText(tempProducts.get(0).get);
                                mProductsWholesalePrice.setText("￥" + response.getMessage().getMarketPrice() + "");
                                productPrice = response.getMessage().getMarketPrice()+"";
                                mProductsExpress.setText("快递 ￥" + response.getMessage().getDeliveryMark());
                                mPrductsSalesVolume.setText("成交 " + response.getMessage().getSaleCount() + "个");
//                                mProductsPlace.setText("发货地址: "+response.getMessage().getCompanyRegionName());
                                mProductsPlace.setText("发货地址: ");
                                mProductsCommentNum.setText(response.getMessage().getCommentNumber() + "人已评价");
                                mProductsAppriceNum.setText(response.getMessage().getCommentSum() + "");
                                mProductsRatingbar.setRating((float) response.getMessage().getServiceMark());
                                String shopimgUrl = ApiConstants.IMG_BASE_URL + response.getMessage().getLogo();
                                ImageLoader.getInstance().displayImage(shopimgUrl, mProductsDetailLogo);
                                mProductShopName.setText(response.getMessage().getShopName());

                                mDetailShop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ShopHomePageFragment.launch(getActivity(), response.getMessage().getShopid(), response.getMessage().getLogo());
                                    }
                                });

                                mProductDetailPrictue.setText(Html.fromHtml(response.getMessage().getDescription()));
                                shopid = response.getMessage().getShopid();

                                CommentSum = response.getMessage().getCommentSum();
                                PackMark = response.getMessage().getPackMark();
                                ServiceMark = response.getMessage().getServiceMark();
                                DeliveryMark = response.getMessage().getDeliveryMark();
                                refreshDescribe(PackMark);
                                refreshService(ServiceMark);
                                refreshDelivery(DeliveryMark);

                                mAppraise.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AppraiseContent content = new AppraiseContent();
                                        content.setId(id);
                                        content.setCommentSum(CommentSum);
                                        content.setPackMark(PackMark);
                                        content.setServiceMark(ServiceMark);
                                        content.setDeliveryMark(DeliveryMark);
                                        AppraiseFragment.launch(getActivity(), content);
                                    }
                                });

                                mColor = response.getMessage().getColor();
                                mSize = response.getMessage().getSize();
                                mVersion = response.getMessage().getVersion();
                            }
                            break;
                        case canceled:
                            break;
                        default:
                            ToastUtils.toast(result);
                            break;
                    }
                }
            }, HttpRequestUtils.RequestType.GET);
        } else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("shopid", shopid);
            requestParams.put("page", 1);
            startRequest(ApiConstants.PRODUCTS_SHOPSPRODUCTS, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:
                            ShopRecommendListRespon response = ToolsHelper.parseJson(result, ShopRecommendListRespon.class);
                            if (response != null && response.getFlag() == 1 && response.getMessage() != null) {
                                final List<Recommend> tempProducts = new LinkedList<>();
                                for (ShopRecommendListRespon.MessageEntity messageEntity : response.getMessage()) {
                                    Recommend recommend = new Recommend();
                                    recommend.setId(messageEntity.getId());
                                    recommend.setImgUrl(messageEntity.getImgUrl());
                                    recommend.setName(messageEntity.getName());
                                    recommend.setSaleCounts(messageEntity.getSaleCounts());
                                    recommend.setMarketPrice(messageEntity.getMarketPrice());
                                    tempProducts.add(recommend);
                                }
                                mRecommengGridView.setAdapter(new RecommendAdpater(tempProducts));
                                mRecommengGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        ProductContent content = new ProductContent();
                                        content.setId(tempProducts.get(position).getId());
                                        content.setDistrict(Constants.REGION_NAME);
                                        ProductDetailFragment.launch(getActivity(), content);
                                    }
                                });
                            }
                            break;
                        case canceled:
                            break;
                        default:
                            ToastUtils.toast(result);
                            break;
                    }
                }
            }, HttpRequestUtils.RequestType.GET);
        }
    }

    private void refreshDescribe(int i) {
        if (i > 0 && i < 3) {
            mDetailDescribe.setText(i + "");
            mDetailDescribe.setBackgroundResource(R.drawable.bg_green_square);
            mDetailMark.setText("低");
            mDetailMark.setBackgroundResource(R.drawable.bg_green_square);
        } else if (i > 3 && i < 5) {
            mDetailDescribe.setText(i + "");
            mDetailDescribe.setBackgroundResource(R.drawable.bg_blue_square);
            mDetailMark.setText("中");
            mDetailMark.setBackgroundResource(R.drawable.bg_blue_square);
        } else if (i == 5) {
            mDetailDescribe.setText(i + "");
            mDetailDescribe.setBackgroundResource(R.drawable.bg_yellow_square);
            mDetailMark.setText("高");
            mDetailMark.setBackgroundResource(R.drawable.bg_yellow_square);
        } else {
            mDetailDescribe.setText(i + "");
            mDetailDescribe.setBackgroundResource(R.drawable.bg_green_square);
            mDetailMark.setText("低");
            mDetailMark.setBackgroundResource(R.drawable.bg_green_square);
        }
    }

    private void refreshService(int i) {
        if (i > 0 && i < 3) {
            mDetailService.setText(i + "");
            mDetailService.setBackgroundResource(R.drawable.bg_green_square);
            mDetailServiceMark.setText("低");
            mDetailServiceMark.setBackgroundResource(R.drawable.bg_green_square);
        } else if (i > 3 && i < 5) {
            mDetailService.setText(i + "");
            mDetailService.setBackgroundResource(R.drawable.bg_blue_square);
            mDetailServiceMark.setText("中");
            mDetailServiceMark.setBackgroundResource(R.drawable.bg_blue_square);
        } else if (i == 5) {
            mDetailService.setText(i + "");
            mDetailService.setBackgroundResource(R.drawable.bg_yellow_square);
            mDetailServiceMark.setText("高");
            mDetailServiceMark.setBackgroundResource(R.drawable.bg_yellow_square);
        } else {
            mDetailService.setText(i + "");
            mDetailService.setBackgroundResource(R.drawable.bg_green_square);
            mDetailServiceMark.setText("低");
            mDetailServiceMark.setBackgroundResource(R.drawable.bg_green_square);
        }
    }

    private void refreshDelivery(int i) {
        if (i > 0 && i < 3) {
            mDetailDelivery.setText(i + "");
            mDetailDelivery.setBackgroundResource(R.drawable.bg_green_square);
            mDetailDeliveryMark.setText("低");
            mDetailDeliveryMark.setBackgroundResource(R.drawable.bg_green_square);
        } else if (i > 3 && i < 5) {
            mDetailDelivery.setText(i + "");
            mDetailDelivery.setBackgroundResource(R.drawable.bg_blue_square);
            mDetailDeliveryMark.setText("中");
            mDetailDeliveryMark.setBackgroundResource(R.drawable.bg_blue_square);
        } else if (i == 5) {
            mDetailDelivery.setText(i + "");
            mDetailDelivery.setBackgroundResource(R.drawable.bg_yellow_square);
            mDetailDeliveryMark.setText("高");
            mDetailDeliveryMark.setBackgroundResource(R.drawable.bg_yellow_square);
        } else {
            mDetailDelivery.setText(i + "");
            mDetailDelivery.setBackgroundResource(R.drawable.bg_green_square);
            mDetailDeliveryMark.setText("低");
            mDetailDeliveryMark.setBackgroundResource(R.drawable.bg_green_square);
        }
    }

    class RecommendAdpater extends BaseAdapter {

        private List<Recommend> tempProducts = new ArrayList<>();

        public RecommendAdpater(List<Recommend> tempProducts) {
            this.tempProducts = tempProducts;
        }

        @Override
        public int getCount() {
            return tempProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return tempProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.products_detail_gridview_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_products_detail_item);
                viewHolder.productName = (TextView) convertView.findViewById(R.id.products_detail_item_productname);
                viewHolder.productPrice = (TextView) convertView.findViewById(R.id.products_detail_item_product_price);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.productName.setText("￥" + tempProducts.get(position).getMarketPrice() + "");
            viewHolder.productName.setText(tempProducts.get(position).getName());
            String imgUrl = ApiConstants.IMG_BASE_URL + tempProducts.get(position).getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, viewHolder.imageView, options);

            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView productName;
        TextView productPrice;
    }


    class Recommend {
        private int Id;
        private String imgUrl;
        private String name;
        private int SaleCounts;
        private int MarketPrice;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSaleCounts(int SaleCounts) {
            this.SaleCounts = SaleCounts;
        }

        public void setMarketPrice(int MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public int getId() {
            return Id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getName() {
            return name;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public int getMarketPrice() {
            return MarketPrice;
        }
    }


    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.detail_picture:
                mDetailPicture.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailPicture.setTextColor(getResources().getColor(R.color.blue));

                mDetailProducts.setBackgroundColor(Color.WHITE);
                mDetailProducts.setTextColor(getResources().getColor(R.color.text_color));

                mDetailRecommend.setBackgroundColor(Color.WHITE);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.text_color));

                mPictureFrameLayout.setVisibility(View.VISIBLE);
                mProductFrameLayout.setVisibility(View.GONE);
                mRecommendFrameLayout.setVisibility(View.GONE);
                break;
            case R.id.detail_products:
                mDetailProducts.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailProducts.setTextColor(getResources().getColor(R.color.blue));

                mDetailPicture.setBackgroundColor(Color.WHITE);
                mDetailPicture.setTextColor(getResources().getColor(R.color.text_color));

                mDetailRecommend.setBackgroundColor(Color.WHITE);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.text_color));

                mProductFrameLayout.setVisibility(View.VISIBLE);
                mPictureFrameLayout.setVisibility(View.GONE);
                mRecommendFrameLayout.setVisibility(View.GONE);

                break;
            case R.id.detail_recommend:
                mDetailRecommend.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.blue));

                mDetailPicture.setBackgroundColor(Color.WHITE);
                mDetailPicture.setTextColor(getResources().getColor(R.color.text_color));

                mDetailProducts.setBackgroundColor(Color.WHITE);
                mDetailProducts.setTextColor(getResources().getColor(R.color.text_color));

                mRecommendFrameLayout.setVisibility(View.VISIBLE);
                mPictureFrameLayout.setVisibility(View.GONE);
                mProductFrameLayout.setVisibility(View.GONE);

                RecommendFrameLayoutfalg = 1;
                requestData();
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.products_detail_type:
                flag = true;
                refersh();
                break;
            case R.id.products_detail_cart:
                flag = true;
                refersh();
                break;
            case R.id.products_detail_order:
                flag = false;
                refersh();
                break;
        }
    }

    private View.OnClickListener mOnConformClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(App.sToken != null){
                if(flag){
                    RequestParams requestParams = new RequestParams();
            /*if(mNumber.getText().toString().equals("0")){*/
                    requestParams.put("Quantity",1);
           /* }else{
                requestParams.put("Quantity", Integer.parseInt(mNumber.getText().toString()));
            }*/
                    requestParams.put("Token", App.sToken);
                    requestParams.put("skuId",mDialog_SKUId);
                    startRequest(ApiConstants.ADD_ORDERCAR, requestParams, new HttpRequestHandler() {
                        @Override
                        public void onRequestFinished(ResultCode resultCode, String result) {
                            switch (resultCode) {
                                case success:
                                    AddOrderCarRespon response = ToolsHelper.parseJson(result, AddOrderCarRespon.class);
                                    if(response != null && response.getFlag() == 1){
                                        ToastUtils.toast(response.getMessage());

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
                }else{
                    ToastUtils.toast("123");
                }
            }else{
                LoginFragment.launch(getActivity());
            }
        }
    };

    private void refersh() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Color",mColor);
        requestParams.put("Size",mSize);
        requestParams.put("Version",mVersion);
        requestParams.put("id", id);
        startRequest(ApiConstants.PRODUCT_SSKU, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        ProductSskuRespon response = ToolsHelper.parseJson(result, ProductSskuRespon.class);
                        if(response != null && response.getFlag() == 1){
                            mDialog_SKUId = response.getMessage().getSKUId();
                            mDialog_Version = response.getMessage().getVersion() ;
                            mDialog_Color = response.getMessage().getColor() ;
                            mDialog_Size = response.getMessage().getSize() ;
                            mDialog_SalePrice = response.getMessage().getSalePrice() ;
                            mDialog_Stock = response.getMessage().getStock() ;

                            ActionSheetDialog dialog= new ActionSheetDialog(getActivity(),mDialog_SKUId,imgUrl,productName,productPrice,mDialog_Stock);
                            dialog.setOnConformClickListener(mOnConformClickListener);// 回调（点击确定）
                            dialog.builder();
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
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
