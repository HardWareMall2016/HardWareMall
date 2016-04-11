package com.hardware.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hardware.R;
import com.hardware.base.App;
import com.hardware.ui.fragment.GoodsListFragment;
import com.hardware.view.slidingtablayout.SlidingTabLayout;
import com.hardware.view.slidingtablayout.ViewFindUtils;
import com.zhan.framework.common.context.GlobalContext;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hover on 2016/4/10.
 */
public class GoodsListActivity extends MBaseActivity {

    @Bind(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    private String tabTitles[] = new String[]{"综合", "销量", "价格", "筛选"};
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private boolean isChangeTab;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_all_list);
        ButterKnife.bind(this);
        context = this;
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.mviewpager);
        final GoodsRecommendCoAdapter pagerAdapter =
                new GoodsRecommendCoAdapter(getSupportFragmentManager(), context);
        fragments.add(new GoodsListFragment());
        fragments.add(new GoodsListFragment());
        fragments.add(new GoodsListFragment());
        fragments.add(new GoodsListFragment());
        if (isChangeTab) {
            for (int i = 0; i < fragments.size(); i++) {
                pagerAdapter.fragmentsUpdateFlag[i] = true;
            }
            pagerAdapter.setDatas(fragments);
            pagerAdapter.notifyDataSetChanged();
        } else {
            pagerAdapter.setDatas(fragments);
            isChangeTab = true;
            App.ctx.setIsChangeTab(isChangeTab);
        }
        pagerAdapter.setFragments(fragments);
        vp.setAdapter(pagerAdapter);
        tabLayout.setViewPager(vp);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        fragments.clear();
    }

    class GoodsRecommendCoAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<Fragment> datas;
        public boolean[] fragmentsUpdateFlag = {false, false};
        private FragmentManager fm;

        public void setDatas(List<Fragment> datas) {
            this.datas = datas;
        }

        public void setFragments(List<Fragment> fragments) {
            this.datas = fragments;
        }

        public GoodsRecommendCoAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
                //如果这个fragment需要更新
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = datas.get(position);
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();
                //复位更新标志
                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
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
