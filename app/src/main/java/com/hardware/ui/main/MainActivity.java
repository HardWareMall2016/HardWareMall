package com.hardware.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.hardware.R;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MainActivity extends AppCompatActivity {
    public final static String EXT_KEY_SHOW_PAGE = "show_page";

    public final static String TAG_HOME = "Home";
    public final static String TAG_GOODS = "Goods";
    public final static String TAG_SHOP = "Shop";
    public final static String TAG_CART = "Cart";
    public final static String TAG_MINE = "Mine";

    @Bind(R.id.home_tv)
    TextView mHomeTv;
    @Bind(R.id.goods_tv)
    TextView mGoodsTv;
    @Bind(R.id.shop_tv)
    TextView mShopTv;
    @Bind(R.id.cart_tv)
    TextView mCartTv;
    @Bind(R.id.me_tv)
    TextView mIneTv;

    private List<Page> mPageList = new ArrayList<Page>();
    private Page mCurPage;

    class Page {
        String TAG;
        Fragment pageFragment;
        int FocusIconResId;
        int UnFocusIconResId;
        TextView BottomTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPages();
    }

    private void initPages() {
        mPageList.clear();
        //首页
        Page page = new Page();
        page.TAG = TAG_HOME;
        page.pageFragment = new HomeFragment();
        page.FocusIconResId = R.drawable.tab_home_selected;
        page.UnFocusIconResId = R.drawable.tab_home_unselect;
        page.BottomTitle = mHomeTv;
        mPageList.add(page);
        //商品
        page = new Page();
        page.TAG = TAG_GOODS;
        page.pageFragment = new GoodsFragment();
        page.FocusIconResId = R.drawable.tab_goods_selected;
        page.UnFocusIconResId = R.drawable.tab_goods_unselect;
        page.BottomTitle = mGoodsTv;
        mPageList.add(page);
        //店铺
        page = new Page();
        page.TAG = TAG_SHOP;
        page.pageFragment = new ShopFragment();
        page.FocusIconResId = R.drawable.tab_shop_selected;
        page.UnFocusIconResId = R.drawable.tab_shop_unselect;
        page.BottomTitle = mShopTv;
        mPageList.add(page);
        //购物车
        page = new Page();
        page.TAG = TAG_CART;
        page.pageFragment = new CartFragment();
        page.FocusIconResId = R.drawable.tab_cart_selected;
        page.UnFocusIconResId = R.drawable.tab_cart_unselect;
        page.BottomTitle = mCartTv;
        mPageList.add(page);
        //我
        page = new Page();
        page.TAG = TAG_MINE;
        page.pageFragment = new MeFragment();
        page.FocusIconResId = R.drawable.tab_me_selected;
        page.UnFocusIconResId = R.drawable.tab_me_unselect;
        page.BottomTitle = mIneTv;
        mPageList.add(page);

        String showPage = getIntent().getStringExtra(EXT_KEY_SHOW_PAGE);
        if (TextUtils.isEmpty(showPage)) {
            showPage = TAG_HOME;
        }
        showPage(showPage);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            //退出跳转到主页
            showPage(TAG_HOME);
        }
    }

    private void showPage(String tag) {
        for (Page page : mPageList) {
            if (page.TAG.equals(tag)) {
                if (mCurPage != page) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, page.pageFragment);
                    transaction.commit();

                    page.BottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.FocusIconResId, 0, 0);
                    page.BottomTitle.setTextColor(getResources().getColor(R.color.blue));
                    onPageChange(mCurPage, page);
                }
                mCurPage = page;
            } else {
                page.BottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.UnFocusIconResId, 0, 0);
                page.BottomTitle.setTextColor(getResources().getColor(R.color.text_color));
            }
        }
    }

    private void onPageChange(Page mCurPage, Page page) {

    }

    @OnClick({R.id.home_tv, R.id.goods_tv, R.id.shop_tv, R.id.cart_tv, R.id.me_tv})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.home_tv:
                showPage(TAG_HOME);
                break;
            case R.id.goods_tv:
                showPage(TAG_GOODS);
                break;
            case R.id.shop_tv:
                showPage(TAG_SHOP);
                break;
            case R.id.cart_tv:
                showPage(TAG_CART);
                break;
            case R.id.me_tv:
                showPage(TAG_MINE);
                break;


        }
    }




}
