package com.hardware.ui.main;


import android.support.v4.app.Fragment;

import com.hardware.R;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends ABaseFragment {

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_cart;
    }

}
