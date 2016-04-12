package com.zhan.framework.ui.activity;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhan.framework.R;

/**
 * Created by WuYue on 2015/12/2.
 */
public abstract class ActionBarActivity extends AppCompatActivity{

    /*base view*/
    private FrameLayout mContentView;
    private View mUserView;

    /*action_bar_layout*/
    private View mActionbarLayout;
    private View mDefActionbarContent;
    private ImageView mbtnBack;
    private TextView mTvTtile;
    private LinearLayout mActionBarRightContent;
    private TextView mTvMenu;
    private FrameLayout mActionBarCustomerContent;
    private View mActionbarUnderline;

    /*用户定义的view*/
    //private View mUserView;

    /*视图构造器*/
    private LayoutInflater mInflater;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    //开关
    boolean mShowActionbarUnderline=true;
    boolean mShowActionbar = true;
    boolean mShowBackIcon= true;//show back icon
    boolean mOverlay =false;//Overlay statusBar and actionbar
    boolean mOverlayActionbar=false;//Overlay actionbar

    private int mStatusBarHeight =0;
    private int mActionbarHeight=0;
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(this);

        TypedArray typedArray = getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        mOverlay = typedArray.getBoolean(0, false);
        /*获取主题中定义的actionlbar的高度*/
        mActionbarHeight = (int) typedArray.getDimension(1, (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // create our manager instance after the content view is set
            mTintManager = new SystemBarTintManager(this);
            // enable status bar tint
            mTintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            mTintManager.setNavigationBarTintEnabled(true);
            // set a custom tint color for all system bars
            if(!mOverlay){
                mTintManager.setTintColor(getResources().getColor(R.color.status_bar_bg_color));
            }else{
                mTintManager.setTintColor(getResources().getColor(R.color.transparent));
            }

            SystemBarTintManager.SystemBarConfig config = mTintManager.getConfig();

            mStatusBarHeight =config.getStatusBarHeight();
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mTvTtile != null) {
            mTvTtile.setText(title);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        mUserView = mInflater.inflate(layoutResID, null);
        pupulateContentView(mUserView);
    }

    @Override
    public void setContentView(View view) {
        mUserView=view;
        pupulateContentView(view);
    }

    private void pupulateContentView(View userView) {
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(userView);
        if (mShowActionbar) {
            /*初始化actionbar*/
            initActionBar();
            /*自定义actionbar*/
            onCreateCustomActionbarBar(mActionBarCustomerContent);
            /*准备menu*/
            onPrepareActionbarMenu(mTvMenu);
            /*自定义menu*/
            onCreateCustomActionMenu(mActionBarRightContent);
        }
        super.setContentView(mContentView);
    }


    public void showActionbar(boolean showActionbar) {
        mShowActionbar = showActionbar;
    }

    public void showBackIcon(boolean showBack) {
        mShowBackIcon=showBack;
    }

    public void overlayActionbar(boolean overlay) {
        mOverlayActionbar=overlay;
    }

    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }

    public View getActionbarLayout(){
        return mActionbarLayout;
    }

    public int getActionbarHeight() {
        return mActionbarHeight;
    }

    public void showActionbarUnderline(boolean show){
        mShowActionbarUnderline=show;
    }

    public View getUserView() {
        return mUserView;
    }

    public void setActionbarBackgroundColor(int color){
        mActionbarLayout.setBackgroundColor(color);
    }

    public void setStatusbarBackgroundColor(int color){
        if(mTintManager!=null){
            mTintManager.setTintColor(color);
        }
    }

    /**
     * 自定义Actionbar
     */
    public void onCreateCustomActionbarBar(FrameLayout customerContent) {
        if(customerContent.getChildCount()>0){
            mDefActionbarContent.setVisibility(View.GONE);
            mActionBarCustomerContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 自定义Menu
     */
    public void onCreateCustomActionMenu(LinearLayout menuContent) {
        if(menuContent.getChildCount()>0){
            mTvMenu.setVisibility(View.GONE);
            mActionBarRightContent.setVisibility(View.VISIBLE);
        }
    }

    /*准备Menu */
    public void onPrepareActionbarMenu(TextView menu){}

    /*点击Menu */
    public void onActionBarMenuClick(){}

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    private void initActionBar() {
        /*通过inflater获取toolbar的布局文件*/
        mActionbarLayout = mInflater.inflate(R.layout.action_bar_layout, null);
        if(mOverlay){
            mActionbarLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        //mActionbarLayout = mInflater.inflate(R.layout.toolbar_bar_layout, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionbarHeight);
        /*如果是悬浮状态，则不需要设置间距*/
        //params.topMargin = mOverlay ? 0 : mStatusBarHeight;
        params.topMargin = mStatusBarHeight;//不管是否悬浮，Actionbar都在Status Bar 下面
        mContentView.addView(mActionbarLayout, params);

        mDefActionbarContent=mActionbarLayout.findViewById(R.id.def_content);

        mbtnBack =(ImageView)mActionbarLayout.findViewById(R.id.go_back);
        mbtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //mbtnBack.setText(R.string.go_back);
        mbtnBack.setVisibility(mShowBackIcon ? View.VISIBLE : View.GONE);

        mTvTtile=(TextView)mActionbarLayout.findViewById(R.id.title);
        setTitle(getTitle());
        mActionBarRightContent=(LinearLayout)mActionbarLayout.findViewById(R.id.right_content);
        mTvMenu=(TextView)mActionbarLayout.findViewById(R.id.right_menu);
        mTvMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onActionBarMenuClick();
            }
        });
        mActionBarCustomerContent=(FrameLayout)mActionbarLayout.findViewById(R.id.customer_content);
        mActionbarUnderline=mActionbarLayout.findViewById(R.id.underline);
        mActionbarUnderline.setVisibility(mShowActionbarUnderline?View.VISIBLE:View.GONE);
    }

    private void initUserView(View userView) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*获取主题中定义的toolbar的高度*/
        int margTop=mStatusBarHeight;
        /*只在actionbar显示并不悬浮时才加上mActionbarHeight*/
        if(mShowActionbar&&!mOverlayActionbar){
            margTop+=mActionbarHeight;
        }
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = mOverlay ? 0 : margTop;
        mContentView.addView(userView, params);
    }
}
