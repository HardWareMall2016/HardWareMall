package com.zhan.framework.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by WuYue on 2016/3/30.
 * 用户注册回调BaseFragmentHelper的生命周期及相关的方法，自行添加
 */
public class BaseFragmentHelper {
    private ABaseFragment mFragment;

    protected void bindFragment(ABaseFragment fragment) {
        this.mFragment = fragment;
    }

    protected ABaseFragment getFragment() {
        return mFragment;
    }

    public void onAttach(Activity activity){

    }

    public void onCreate(Bundle savedInstanceState){

    }

    public void onCreateView(View rootView, Bundle savedInstanceState) {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onDestroyView() {

    }

    public View findViewById(int id) {
        return mFragment.findViewById(id);
    }

}
