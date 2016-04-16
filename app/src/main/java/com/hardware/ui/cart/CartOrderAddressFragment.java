package com.hardware.ui.cart;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hardware.R;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderAddressFragment extends ABaseFragment {

    private final static int PAGE_SIZE = 10;

    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;

    private boolean QueryMore = false;
    private boolean HasMoreData = true;

    private LayoutInflater mInflater;

    private List<Order> mProducts = new LinkedList<>();
    //private ProductAdapter mAdpater = new ProductAdapter();


    private Handler mHandler = new Handler();

    private class Order{
        private String Address;
        private int AddressId ;

    }



    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cartorder_address;
    }

    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, CartOrderAddressFragment.class, null);

    }
    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("收货地址");

    }
}
