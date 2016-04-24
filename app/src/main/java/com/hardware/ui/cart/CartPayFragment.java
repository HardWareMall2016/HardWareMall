package com.hardware.ui.cart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hardware.R;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CartPayFragment extends ABaseFragment{
    private final static String ARG_KEY = "arg_key";
    private double amount ;

    @ViewInject(id = R.id.tv_cost_price)
    TextView mCostPrice ;

    @ViewInject(id = R.id.tv_pay_price)
    TextView mPayPrice ;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_cart_pay_layout;
    }

    public static void lauch(FragmentActivity activity, double amount) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, amount);
        FragmentContainerActivity.launch(activity, CartPayFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount = savedInstanceState == null ? (double) getArguments().getSerializable(ARG_KEY)
                : (double) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, amount);
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("支付方式");

        DecimalFormat df = new DecimalFormat();
        df.applyPattern("￥##0.00");

        mCostPrice.setText(df.format(amount));
        mPayPrice.setText(df.format(amount));
    }
}
