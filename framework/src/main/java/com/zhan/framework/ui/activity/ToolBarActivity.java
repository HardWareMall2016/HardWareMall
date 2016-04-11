package com.zhan.framework.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zhan.framework.utils.ToolBarHelper;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ToolBarActivity extends AppCompatActivity {

    /*base view*/
    private FrameLayout mContentView;
    private View mUserView;

    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;
    public TextView mTitle ;

    public static boolean mShowToolBar = true;

    public View getUserView() {
        return mUserView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        //显示，隐藏toolbar
        if(mShowToolBar){
            initView(layoutResID);
        }
    }

    private void initView(int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        /*自定义toolbar标题*/
        mTitle = mToolBarHelper.getTitle();
        setContentView(mToolBarHelper.getContentView());
         /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
         /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
         /*toolbar中点击事件操作*/
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "点击了标题事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

}
