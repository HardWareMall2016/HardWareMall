package com.hardware.ui.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.hardware.R;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by Administrator on 2016/4/9.
 */
public class HomeListFragment extends ABaseFragment{

    private final static String ARG_KEY = "homelist";

    private String mTitle;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_home_list_layout;
    }

    public static void launch(FragmentActivity activity, String mTitle) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, mTitle);
        FragmentContainerActivity.launch(activity, HomeListFragment.class, args);
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
    }


}
