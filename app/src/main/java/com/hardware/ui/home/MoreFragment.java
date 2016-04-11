package com.hardware.ui.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.SimpleAdapter;

import com.hardware.R;
import com.hardware.view.MyGridView;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/9.
 */
public class MoreFragment extends ABaseFragment {

    private final static String ARG_KEY = "more";

    @ViewInject(id = R.id.more_gridView)
    MyGridView myGridView;

    private String mTitle;
    private SimpleAdapter mSimpleAdapter;
    private List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();

    private int[] mImageIcon = {
            R.drawable.home_popularity_brand,
            R.drawable.home_user_buy,
            R.drawable.home_spread,
            R.drawable.home_consult,
            R.drawable.home_repair,
            R.drawable.home_build,
            R.drawable.home_advertise,
            R.drawable.home_shop,
            R.drawable.home_bbs
    };
    private String[] mIconName = {
            "人气品牌",
            "用户求购",
            "商家推广",
            "行业资讯",
            "便民维修",
            "便民施工",
            "专业招聘",
            "人气商铺",
            "社区论坛"};

    @Override
    protected int inflateContentView() {
        return R.layout.frag_more_layout;
    }

    public static void launch(FragmentActivity activity, String mTitle) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, mTitle);
        FragmentContainerActivity.launch(activity, MoreFragment.class, args);
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

        getData();

        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList, R.layout.home_gridview_item, from, to);
        myGridView.setAdapter(mSimpleAdapter);
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < mImageIcon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", mImageIcon[i]);
            map.put("text", mIconName[i]);
            mDataList.add(map);
        }
        return mDataList;
    }

}
