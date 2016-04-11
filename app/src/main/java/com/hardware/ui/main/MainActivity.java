package com.hardware.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.home_iv)
    ImageView homeIv;
    @Bind(R.id.home_tv)
    TextView homeTv;
    @Bind(R.id.home_rl)
    RelativeLayout homeRl;
    @Bind(R.id.goods_iv)
    ImageView goodsIv;
    @Bind(R.id.goods_tv)
    TextView goodsTv;
    @Bind(R.id.goods_rl)
    RelativeLayout goodsRl;
    @Bind(R.id.shop_iv)
    ImageView shopIv;
    @Bind(R.id.shop_tv)
    TextView shopTv;
    @Bind(R.id.shop_rl)
    RelativeLayout shopRl;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.cart_tv)
    TextView cartTv;
    @Bind(R.id.cart_rl)
    RelativeLayout cartRl;
    @Bind(R.id.me_iv)
    ImageView meIv;
    @Bind(R.id.me_tv)
    TextView meTv;
    @Bind(R.id.me_rl)
    RelativeLayout meRl;
    @Bind(R.id.fragment_container_rl)
    RelativeLayout fragmentContainerRl;

    private Fragment[] fragments;
    private HomeFragment homefragment;
    private GoodsFragment goodsFragment;
    private ShopFragment shopFragment;
    private CartFragment cartFragment;
    private MeFragment meFragment;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        imageViews[0].setSelected(true);
    }

    private void initView() {
        homefragment = new HomeFragment();
        goodsFragment = new GoodsFragment();
        shopFragment = new ShopFragment();
        cartFragment = new CartFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{homefragment, goodsFragment, shopFragment, cartFragment, meFragment};
        imageViews = new ImageView[]{homeIv, goodsIv, shopIv, cartIv, meIv};
        textViews = new TextView[]{homeTv, goodsTv, shopTv, cartTv, meTv};
        textViews[0].setTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_rl, homefragment)
                .add(R.id.fragment_container_rl, goodsFragment)
                .add(R.id.fragment_container_rl, shopFragment)
                .add(R.id.fragment_container_rl, cartFragment)
                .add(R.id.fragment_container_rl, meFragment)
                .hide(goodsFragment).hide(shopFragment).hide(cartFragment)
                .hide(meFragment).show(homefragment).commit();
    }

    @OnClick({R.id.home_rl, R.id.goods_rl, R.id.shop_rl, R.id.cart_rl, R.id.me_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_rl:
                index = 0;
                break;
            case R.id.goods_rl:
                index = 1;
                break;
            case R.id.shop_rl:
                index = 2;
                break;
            case R.id.cart_rl:
                index = 3;
                break;
            case R.id.me_rl:
                index = 4;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container_rl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imageViews[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imageViews[index].setSelected(true);
        textViews[currentTabIndex].setTextColor(getResources().getColor(R.color.colorTabGray));
        textViews[index].setTextColor(getResources().getColor(R.color.colorPrimary));
        currentTabIndex = index;
    }
}
