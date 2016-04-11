package com.zhan.framework.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;


import com.zhan.framework.R;
import com.zhan.framework.support.adapter.FragmentPagerAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.widget.SlidingTabLayout;
import com.zhan.framework.utils.ActivityHelper;
import com.zhan.framework.utils.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangdan on 15-1-20.
 */
public abstract class AStripTabsFragment<T extends AStripTabsFragment.StripTabItem> extends ABaseFragment
                                implements ViewPager.OnPageChangeListener {

    static final String TAG = AStripTabsFragment.class.getSimpleName();

    public static final String SET_INDEX = "org.aisen.android.ui.SET_INDEX";// 默认选择第几个

    @ViewInject(idStr = "slidingTabs")
    SlidingTabLayout slidingTabs;
    @ViewInject(idStr = "pager")
    ViewPager viewPager;
    MyViewPagerAdapter mViewPagerAdapter;

    ArrayList<T> mItems;
    Map<String, Fragment> fragments;
    int mCurrentPosition = 0;

    @Override
    protected int inflateContentView() {
        return R.layout.comm_ui_tabs;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mCurrentPosition = viewPager.getCurrentItem();
        outState.putSerializable("items", mItems);
        outState.putInt("current", mCurrentPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = savedInstanceState == null ? generateTabs()
                                            : (ArrayList<T>) savedInstanceState.getSerializable("items");

        mCurrentPosition = savedInstanceState == null ? 0
                                                      : savedInstanceState.getInt("current");
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, final Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        setHasOptionsMenu(true);

        if (delayGenerateTabs() == 0) {
            setTab(savedInstanceSate);
        }
        else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    setTab(savedInstanceSate);
                }

            }, delayGenerateTabs());
        }
    }

    protected void setTab(final Bundle savedInstanceSate) {
        if (getActivity() == null)
            return;

        if (savedInstanceSate == null) {
            if (getArguments() != null && getArguments().containsKey(SET_INDEX)) {
                mCurrentPosition = Integer.parseInt(getArguments().getSerializable(SET_INDEX).toString());
            }
            else {
                if (configLastPositionKey() != null) {
                    // 记录了最后阅读的标签
                    String type = ActivityHelper.getShareData("PagerLastPosition" + configLastPositionKey(), "");
                    if (!TextUtils.isEmpty(type)) {
                        for (int i = 0; i < mItems.size(); i++) {
                            StripTabItem item = mItems.get(i);
                            if (item.getType().equals(type)) {
                                mCurrentPosition = i;
                                break;
                            }
                        }
                    }
                }
            }
        }

        Logger.w("strip-current-" + mCurrentPosition);

        fragments = new HashMap<String, Fragment>();

        if (mItems == null)
            return;

        for (int i = 0; i < mItems.size(); i++) {
            //Fragment fragment = getActivity().getFragmentManager().findFragmentByTag(makeFragmentName(i));
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(makeFragmentName(i));
            if (fragment != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment).commit();
            }
//                fragments.put(makeFragmentName(i), fragment);
        }

        mViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
//					viewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount());
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(mViewPagerAdapter);
        if (mCurrentPosition >= mViewPagerAdapter.getCount())
            mCurrentPosition = 0;
        viewPager.setCurrentItem(mCurrentPosition);
        slidingTabs.setCustomTabView(R.layout.comm_lay_tab_indicator, android.R.id.text1);
        Resources res = getResources();
        slidingTabs.setSelectedIndicatorColors(res.getColor(R.color.comm_tab_selected_strip));
        slidingTabs.setDistributeEvenly(isDistributeEvenly());
        slidingTabs.setViewPager(viewPager);
        slidingTabs.setOnPageChangeListener(this);
        slidingTabs.setCurrent(mCurrentPosition);
    }

    protected boolean isDistributeEvenly() {
        return mItems.size() <= 5;
    }

    protected void destoryFragments() {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                BaseActivity mainActivity = (BaseActivity) getActivity();
                if (mainActivity.mIsDestoryed())
                    return;
            }

            try {
                FragmentTransaction trs =  getFragmentManager().beginTransaction();
                Set<String> keySet = fragments.keySet();
                for (String key : keySet) {
                    if (fragments.get(key) != null) {
                        trs.remove(fragments.get(key));

                        Logger.e("remove fragment , key = " + key);
                    }
                }
                trs.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;

        if (configLastPositionKey() != null) {
            ActivityHelper.putShareData("PagerLastPosition" + configLastPositionKey(), mItems.get(position).getType());
        }

        // 查看是否需要拉取数据
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof IStripTabInitData) {
            ((IStripTabInitData) fragment).onStripTabRequestData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    protected String makeFragmentName(int position) {
        return mItems.get(position).getTitle();
    }

    // 是否保留最后阅读的标签
    protected String configLastPositionKey() {
        return null;
    }

    abstract protected ArrayList<T> generateTabs();

    abstract protected Fragment newFragment(T bean);

    // 延迟一点初始化tabs，用于在首页切换菜单的时候，太多的tab页导致有点点卡顿
    protected int delayGenerateTabs() {
        return 0;
    }

    @Override
    public void onDestroy() {
        try {
            destoryFragments();

            viewPager.setAdapter(null);
            mViewPagerAdapter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    public Fragment getCurrentFragment() {
        if (mViewPagerAdapter == null || mViewPagerAdapter.getCount() < mCurrentPosition)
            return null;

        return fragments.get(makeFragmentName(mCurrentPosition));
    }

    public Fragment getFragment(String tabTitle) {
        if (fragments == null || TextUtils.isEmpty(tabTitle))
            return null;

        for (int i = 0; i < mItems.size(); i++) {
            if (tabTitle.equals(mItems.get(i).getTitle())) {
                return fragments.get(makeFragmentName(i));
            }
        }

        return null;
    }

    public Map<String, Fragment> getFragments() {
        return fragments;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public SlidingTabLayout getSlidingTabLayout() {
        return slidingTabs;
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(makeFragmentName(position));
            if (fragment == null) {
                fragment = newFragment(mItems.get(position));

                fragments.put(makeFragmentName(position), fragment);
            }

            return fragment;
        }

        @Override
        protected void freshUI(Fragment fragment) {
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItems.get(position).getTitle();
        }

        @Override
        protected String makeFragmentName(int position) {
            return AStripTabsFragment.this.makeFragmentName(position);
        }

    }

    public static class StripTabItem implements Serializable {

        private static final long serialVersionUID = 3680682035685685311L;

        private String type;

        private String title;

        private Serializable tag;

        public StripTabItem() {

        }

        public StripTabItem(String type, String title) {
            this.type = type;
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Serializable getTag() {
            return tag;
        }

        public void setTag(Serializable tag) {
            this.tag = tag;
        }
    }

    // 这个接口用于多页面时，只有当前的页面才加载数据，其他不显示的页面暂缓加载
    // 当每次onPagerSelected的时候，再调用这个接口初始化数据
    public interface IStripTabInitData {

        public void onStripTabRequestData();

    }

}
