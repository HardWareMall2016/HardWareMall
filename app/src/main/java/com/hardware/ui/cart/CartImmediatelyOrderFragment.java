package com.hardware.ui.cart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.hardware.R;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CartImmediatelyOrderFragment extends ABaseFragment{
    private final static String ARG_KEY = "arg_key";

    private String mSelectedSkuIds ;

    public static void lunch(FragmentActivity activity, String mDialog_skuId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, mDialog_skuId);
        FragmentContainerActivity.launch(activity, CartImmediatelyOrderFragment.class, args);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mSelectedSkuIds);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedSkuIds = savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY)
                : (String) savedInstanceState.getSerializable(ARG_KEY);
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("确认订单");

    }
    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cart_immed_order;
    }

}
