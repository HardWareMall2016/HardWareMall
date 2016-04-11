package com.zhan.framework.component.container;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhan.framework.R;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.lang.reflect.Method;

/**
 * Created by wangdan on 15-1-17.
 */
public class FragmentContainerActivity extends BaseActivity {
    private Fragment fragment = null;

    private int overrideTheme = -1;

    public static final String FRAGMENT_TAG = "FRAGMENT_CONTAINER";

    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args,boolean showActionbar) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        intent.putExtra("showActionbar", showActionbar);
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launchForResult(Fragment fragment, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        if (fragment.getActivity() == null)
            return;
        Activity activity = fragment.getActivity();

        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launchForResult(BaseActivity from, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        from.startActivityForResult(intent, requestCode);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String className = getIntent().getStringExtra("className");
        boolean showActionbar=getIntent().getBooleanExtra("showActionbar",true);
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }

        int contentId = R.layout.comm_ui_fragment_container;

        FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra("args");

        //Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[] { Bundle.class });
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (Exception e) {
                    }
                }
                // 重写Activity的主题
                try {
                    Method method = clazz.getMethod("setTheme");
                    if (method != null)
                        overrideTheme = Integer.parseInt(method.invoke(fragment).toString());
                } catch (Exception e) {
                }
                // 重写Activity的contentView
                try {
                    Method method = clazz.getMethod("setActivityContentView");
                    if (method != null)
                        contentId = Integer.parseInt(method.invoke(fragment).toString());
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
        }
        showActionbar(showActionbar);
        super.onCreate(savedInstanceState);
        setContentView(contentId);

//        BizFragment.getBizFragment(this);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
        }

        if (getSupportActionBar() != null)
        	getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected int configTheme() {
        if (overrideTheme > 0)
            return overrideTheme;

        return super.configTheme();
    }

    @Override
    public void onActionBarMenuClick() {
        if(fragment!=null&&(fragment instanceof ABaseFragment)){
            ((ABaseFragment) fragment).onActionBarMenuClick();
        }
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu) {
        if(fragment!=null&&(fragment instanceof ABaseFragment)){
            ((ABaseFragment) fragment).onPrepareActionbarMenu(menu);
        }
    }

    @Override
    public void onCreateCustomActionMenu(LinearLayout menuContent) {
        super.onCreateCustomActionMenu(menuContent);
        if(fragment!=null&&(fragment instanceof ABaseFragment)){
            ((ABaseFragment) fragment).onCreateCustomActionMenu(menuContent);
        }
    }

    @Override
    public void onCreateCustomActionbarBar(FrameLayout customerContent) {
        super.onCreateCustomActionbarBar(customerContent);
        if(fragment!=null&&(fragment instanceof ABaseFragment)){
            ((ABaseFragment) fragment).onCreateCustomActionbarBar(customerContent);
        }
    }
}
