package com.hardware.ui.main;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.ui.cart.CartOrderFragment;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends ABaseFragment {

    @ViewInject(id = R.id.fragment_cat_tv, click = "OnClick")
    TextView mCatTv;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_cart;
    }


    void OnClick(View view){
        switch (view.getId()){
            case R.id.fragment_cat_tv:
                CartOrderFragment.launch(getActivity());
                break;
        }
    }

}
