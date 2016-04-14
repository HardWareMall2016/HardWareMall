package com.hardware.ui.products;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.AppraiseContent;
import com.hardware.bean.AppraiseResponseBean;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.net.IDN;
import java.util.List;


/**
 * Created by Administrator on 2016/4/13.
 */
public class AppraiseFragment extends ABaseFragment {
    private final static String ARG_KEY = "key";

    @ViewInject(id = R.id.apprise_numn)
    TextView mAppriseNum ;

    @ViewInject(id = R.id.apprise_packmark)
    TextView mAppriseMark ;
    @ViewInject(id = R.id.tv_apprise_packmark)
    TextView mTvAppriseMark ;

    @ViewInject(id = R.id.apprise_servicemark)
    TextView mAppriseServeMark ;
    @ViewInject(id = R.id.tv_apprise_servicemark)
    TextView mTvAppriseServeMark ;

    @ViewInject(id = R.id.apprise_derverymark)
    TextView mAppriseDerMark ;
    @ViewInject(id = R.id.tv_apprise_derverymark)
    TextView mTvAppriseDerMark;

    private AppraiseContent content;
    private int id;


    public static void launch(FragmentActivity activity, AppraiseContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, AppraiseFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (AppraiseContent) getArguments().getSerializable(ARG_KEY)
                : (AppraiseContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_products_appraise;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("产品评价");
        id = content.getId();
        mAppriseNum.setText(content.getCommentSum()+"");
        refershPack(content.getPackMark());
        refershServe(content.getServiceMark());
        refershDer(content.getDeliveryMark());
    }

    private void refershDer(int i) {
        mAppriseDerMark.setText(i+"");
        if(i>0 && i<3){
            mAppriseDerMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseDerMark.setText("低");
            mTvAppriseDerMark.setBackgroundResource(R.drawable.bg_green_square);
        }else if(i>3 && i< 5){
            mAppriseDerMark.setTextColor(getResources().getColor(R.color.blue));
            mTvAppriseDerMark.setText("中");
            mTvAppriseDerMark.setBackgroundResource(R.drawable.bg_blue_square);
        }else if(i == 5){
            mAppriseDerMark.setTextColor(getResources().getColor(R.color.yellow));
            mTvAppriseDerMark.setText("高");
            mTvAppriseDerMark.setBackgroundResource(R.drawable.bg_yellow_square);
        }else{
            mAppriseDerMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseDerMark.setText("低");
            mTvAppriseDerMark.setBackgroundResource(R.drawable.bg_green_square);
        }
    }

    private void refershServe(int i) {
        mAppriseServeMark.setText(i+"");
        if(i>0 && i<3){
            mAppriseServeMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseServeMark.setText("低");
            mTvAppriseServeMark.setBackgroundResource(R.drawable.bg_green_square);
        }else if(i>3 && i< 5){
            mAppriseServeMark.setTextColor(getResources().getColor(R.color.blue));
            mTvAppriseServeMark.setText("中");
            mTvAppriseServeMark.setBackgroundResource(R.drawable.bg_blue_square);
        }else if(i == 5){
            mAppriseServeMark.setTextColor(getResources().getColor(R.color.yellow));
            mTvAppriseServeMark.setText("高");
            mTvAppriseServeMark.setBackgroundResource(R.drawable.bg_yellow_square);
        }else{
            mAppriseServeMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseServeMark.setText("低");
            mTvAppriseServeMark.setBackgroundResource(R.drawable.bg_green_square);
        }
    }

    private void refershPack(int i) {
        mAppriseMark.setText(i+"");
        if(i>0 && i<3){
            mAppriseMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseMark.setText("低");
            mTvAppriseMark.setBackgroundResource(R.drawable.bg_green_square);
        }else if(i>3 && i< 5){
            mAppriseMark.setTextColor(getResources().getColor(R.color.blue));
            mTvAppriseMark.setText("中");
            mTvAppriseMark.setBackgroundResource(R.drawable.bg_blue_square);
        }else if(i == 5){
            mAppriseMark.setTextColor(getResources().getColor(R.color.yellow));
            mTvAppriseMark.setText("高");
            mTvAppriseMark.setBackgroundResource(R.drawable.bg_yellow_square);
        }else{
            mAppriseMark.setTextColor(getResources().getColor(R.color.green));
            mTvAppriseMark.setText("低");
            mTvAppriseMark.setBackgroundResource(R.drawable.bg_green_square);
        }

    }
/*
    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize = 10;
    }

    @Override
    public int getFirstPageIndex() {
        return 1;
    }

    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", id);
        requestParams.put("Page", getNextPage(mode));
        requestParams.put("ReviewMark", 0);

        startRequest(ApiConstants.PRODUCTS_EVALUATE, requestParams, new PagingTask<AppraiseResponseBean>(mode) {
            @Override
            public AppraiseResponseBean parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, AppraiseResponseBean.class);
            }

            @Override
            protected List<AppraiseInfo> parseResult(AppraiseResponseBean appraiseResponseBean) {
                return null;
            }
        }, HttpRequestUtils.RequestType.GET);
    }


    @Override
    protected ABaseAdapter.AbstractItemView<AppraiseInfo> newItemView() {
        return null;
    }


    class AppraiseInfo {

    }*/

}
