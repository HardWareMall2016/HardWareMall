package com.hardware.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.ui.fragment.GoodsListFragment;
import com.zhan.framework.utils.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hover on 2016/4/10.
 */
public class GoodsListActivity2 extends MBaseActivity {
    private String tabTitles[] = new String[]{"综合", "销量", "价格", "筛选"};
    @Bind(R.id.tabhost)
    FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_goods_layout);
        ButterKnife.bind(this);
        mTabHost.setup(context, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("d").setIndicator(getTabItemView(0)),
                GoodsListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("f").setIndicator(getTabItemView(1)),
                GoodsListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("w").setIndicator(LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, null)),
                GoodsListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("s").setIndicator(LayoutInflater.from(context).inflate(R.layout.custom_tab_layout2, null)),
                GoodsListFragment.class, null);
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.about_tab_bgcolor_selected));
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int currentTab = mTabHost.getCurrentTab();
                Logger.i("fragmentTabs", " currentTab==" + currentTab);
                for (int i = 0; i < 4; i++) {
                    if (i == currentTab) {
                        mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.about_tab_bgcolor_selected));
                    } else {
                        mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.comm_white));
                    }
                }
            }
        });
    }

    private View getTabItemView(int index) {
        TextView textView = new TextView(context);
        textView.setText(tabTitles[index]);
        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setTextSize(19);
        return textView;
    }

    @OnClick({R.id.img_back})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
