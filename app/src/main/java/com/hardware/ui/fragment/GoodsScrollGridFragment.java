package com.hardware.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.bean.GoodsSecondCategoryBean;
import com.hardware.bean.GoodsSecondCategoryBean.MessageBean;
import com.hardware.common.HardWareApi;
import com.hardware.common.Model;
import com.hardware.network.callback.StringCallback;
import com.hardware.utils.JsonHelper;
import com.squareup.okhttp.Request;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.List;

public class GoodsScrollGridFragment extends ABaseFragment {

    private String[] list;
    private TextView[] tvList;
    private View[] views;
    private LayoutInflater inflater;
    private ViewPager viewpager;
    private int currentItem = 0;
    private ShopAdapter shopAdapter;
    private final static String ARG_KEY = "TITLE";
    private String id, name;
    private List<MessageBean> datas;
    @ViewInject(id = R.id.tools_scrlllview)
    private ScrollView scrollView;
    @ViewInject(id = R.id.title)
    private TextView title;
    @ViewInject(idStr = "img_back", click = "OnClick")
    ImageView backImg;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    title.setText(name);
                    shopAdapter = new ShopAdapter(getChildFragmentManager());
                    showToolsView();
                    initPager();
                    break;
                case 0x002:
                    break;
            }
        }
    };

    public static void launch(Activity from, String name, String id) {
        FragmentArgs args = new FragmentArgs();
        args.add("name", name);
        args.add("id", id);
        FragmentContainerActivity.launch(from, GoodsScrollGridFragment.class, args, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = savedInstanceState == null ? (String) getArguments().getSerializable("name")
                : (String) savedInstanceState.getSerializable("name");
        id = savedInstanceState == null ? (String) getArguments().getSerializable("id")
                : (String) savedInstanceState.getSerializable("id");
        Log.i("tag", "name = " + name + " ,id = " + id);
        loadGoodsListData(id);
    }


    private void loadGoodsListData(String id) {
        HardWareApi.getInstance().getGoodsSecondCategory(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.i("tag", "onError = " + e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.i("tag", "onResponse = " + response.toString());
                GoodsSecondCategoryBean goodsSecondCategoryBean = JsonHelper.getInstance().getObject(response, GoodsSecondCategoryBean.class);
                datas = goodsSecondCategoryBean.getMessage();
                mHandler.sendEmptyMessage(0x001);
            }
        }, id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("id", id);
        outState.putSerializable("name", name);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_scrollgrid;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
//        getActivity().setTitle(name);
        this.inflater = inflater;
    }

    /**
     * 动态生成显示items中的textview
     */
    private void showToolsView() {
        list = Model.wjjdList;
        LinearLayout toolsLayout = (LinearLayout) findViewById(R.id.tools);
        int size = datas.size();
        tvList = new TextView[size];
        views = new View[size];

        for (int i = 0; i < size; i++) {
            View view = inflater.inflate(R.layout.item_list_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(datas.get(i).getName());
            toolsLayout.addView(view);
            tvList[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewpager.setCurrentItem(v.getId());
        }
    };

    /**
     * initPager<br/>
     * 初始化ViewPager控件相关内容
     */
    private void initPager() {
        viewpager = (ViewPager) findViewById(R.id.goods_pager);
        viewpager.setAdapter(shopAdapter);
        viewpager.setOnPageChangeListener(onPageChangeListener);
    }

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (viewpager.getCurrentItem() != arg0)
                viewpager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    };

    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment = new ProTypeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", datas.get(index).getId());
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < tvList.length; i++) {
            if (i != id) {
                tvList[i].setBackgroundColor(0x00000000);
                tvList[i].setTextColor(0xFF000000);
            }
        }
        tvList[id].setBackgroundColor(0xFFFFFFFF);
        tvList[id].setTextColor(getResources().getColor(R.color.goods_categroy_list_textcolor));
    }

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {
        int x = (views[clickPosition].getTop());
        scrollView.smoothScrollTo(0, x);
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }
}