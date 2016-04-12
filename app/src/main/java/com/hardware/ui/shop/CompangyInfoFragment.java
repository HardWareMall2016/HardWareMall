package com.hardware.ui.shop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.CompangyAuthInfoRespon;
import com.hardware.bean.CompangyInfoRespon;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CompangyInfoFragment extends ABaseFragment {
    private final static String ARG_KEY_SHOP_ID = "shopId";

    @ViewInject(id = R.id.company_info, click = "OnClick")
    TextView mCompangInfo;
    @ViewInject(id = R.id.authentication_info, click = "OnClick")
    TextView mAuthInfo;
    @ViewInject(id = R.id.fl_company_info)
    FrameLayout mFlCompanyInfoFrameLayout;
    @ViewInject(id = R.id.fl_authentication_info)
    FrameLayout mAutnInfoFrameLayout;

    //company view
    @ViewInject(id = R.id.company_info_company_name)
    TextView mCompanyName;
    @ViewInject(id = R.id.company_info_company_add)
    TextView mCompanyAdd;
    @ViewInject(id = R.id.company_info_products)
    TextView mCompanyProducts;
    @ViewInject(id = R.id.company_info_product_num)
    TextView mCompanyProductNum;
    @ViewInject(id = R.id.company_info_vip)
    TextView mCompanyInfoVip;
    @ViewInject(id = R.id.company_info_describe)
    TextView mCompanyInfoDescirbe;
    @ViewInject(id = R.id.company_info_service)
    TextView mCompanyInfoService;
    @ViewInject(id = R.id.company_info_arrival)
    TextView mCompanyInfoArrival;
    @ViewInject(id = R.id.company_info_contacts)
    TextView mCompanyInfoConts;
    @ViewInject(id = R.id.company_info_contact_add)
    TextView mCompanyInfoContactAdd;

    //auth view
    @ViewInject(id = R.id.auth_info_company_name)
    TextView mAuthCompanyName;
    @ViewInject(id = R.id.auth_info_company_add)
    TextView mAuthCompanyAdd;
    @ViewInject(id = R.id.auth_info_company_data)
    TextView mAuthCompanyData;
    @ViewInject(id = R.id.auth_info_company_operate)
    TextView mAuthOperate;
    @ViewInject(id = R.id.auth_info_company_registration_mark)
    TextView mAuthMark;
    @ViewInject(id = R.id.auth_info_company_corporation)
    TextView mAuthCoporation;

    private int mShopId;
    private int flag = 0;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_company_info;
    }

    public static void launch(FragmentActivity activity, int mShopId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY_SHOP_ID, mShopId);
        FragmentContainerActivity.launch(activity, CompangyInfoFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY_SHOP_ID)
                : (int) savedInstanceState.getSerializable(ARG_KEY_SHOP_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY_SHOP_ID, mShopId);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("公司信息");
    }

    @Override
    public void requestData() {
        if (flag == 0) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("id", mShopId);
            startRequest(ApiConstants.SHOP_BASICALLY, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:
                            CompangyInfoRespon response = ToolsHelper.parseJson(result, CompangyInfoRespon.class);
                            if (response != null && response.getFlag() == 1 && response.getMessage() != null) {
                                mCompanyName.setText("公司名称：" + response.getMessage().getShopName());
                                mCompanyAdd.setText("所在地区：" + response.getMessage().getCompanyRegionId() + "");
                                mCompanyProducts.setText("主营产品：" + response.getMessage().getBusinessSphere());
                                mCompanyProductNum.setText("产品数量：" + response.getMessage().getProductNumber() + "");
                                mCompanyInfoVip.setText("会员等级：" + response.getMessage().getGradeName());
                                mCompanyInfoDescirbe.setText("描述相符：" + response.getMessage().getPackMark() + "");
                                mCompanyInfoService.setText("服务态度：" + response.getMessage().getServiceMark() + "");
                                mCompanyInfoArrival.setText("到货速度：" + response.getMessage().getDeliveryMark() + "");
                                mCompanyInfoConts.setText("联 系 人：" + response.getMessage().getContactsName());
                                mCompanyInfoContactAdd.setText("联系地址：" + response.getMessage().getCompanyRegionName());
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
            requestParams.put("id", mShopId);
            startRequest(ApiConstants.SHOP_DETAILS, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:
                            CompangyAuthInfoRespon authresponse = ToolsHelper.parseJson(result, CompangyAuthInfoRespon.class);
                            if (authresponse != null && authresponse.getFlag() == 1 && authresponse.getMessage() != null) {
                                mAuthCompanyName.setText("公司名称："+authresponse.getMessage().getCompanyName());
                                mAuthCompanyAdd.setText("经营地址："+authresponse.getMessage().getCompanyAddress());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String time = simpleDateFormat.format(ToolsHelper.parseServerTime(authresponse.getMessage().getCreateDate()));
                                mAuthCompanyData.setText("成立日期：" + time);
                                mAuthOperate.setText("经营范围："+authresponse.getMessage().getBusinessSphere());
                                mAuthMark.setText("注册号："+authresponse.getMessage().getOrganizationCode()+"");
                                mAuthCoporation.setText("法人代表："+authresponse.getMessage().getLegalPerson());
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

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.company_info:
                mCompangInfo.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mCompangInfo.setTextColor(getResources().getColor(R.color.blue));
                mAuthInfo.setBackgroundColor(Color.WHITE);
                mAuthInfo.setTextColor(getResources().getColor(R.color.text_color));
                flag = 0;
                refresh();
                requestData();
                break;
            case R.id.authentication_info:
                mAuthInfo.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mAuthInfo.setTextColor(getResources().getColor(R.color.blue));
                mCompangInfo.setBackgroundColor(Color.WHITE);
                mCompangInfo.setTextColor(getResources().getColor(R.color.text_color));
                flag = 1;
                refresh();
                requestData();
                break;
        }
    }

    private void refresh() {
        if (flag == 0) {
            mFlCompanyInfoFrameLayout.setVisibility(View.VISIBLE);
            mAutnInfoFrameLayout.setVisibility(View.GONE);
        } else {
            mFlCompanyInfoFrameLayout.setVisibility(View.GONE);
            mAutnInfoFrameLayout.setVisibility(View.VISIBLE);
        }
    }

}
