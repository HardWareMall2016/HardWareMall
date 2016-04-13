package com.hardware.ui.products;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.AppraiseResponseBean;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class AppraiseFragment extends ABaseFragment {
    private final static String ARG_KEY = "key";

    private int id;

    public static void launch(FragmentActivity activity, int id) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, id);
        FragmentContainerActivity.launch(activity, AppraiseFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY)
                : (int) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_KEY, id);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_products_appraise;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("产品评价");
        //refreshTab();
    }
  /*  @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=10;
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
