package com.hardware.ui.main;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.LoginResponseBean;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by Administrator on 2016/4/16.
 */
public class LoginFragment extends ABaseFragment {

    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, LoginFragment.class, null);
    }

    @ViewInject(id = R.id.account)
    EditText mAccount;

    @ViewInject(id = R.id.password)
    EditText mPassword;

    @ViewInject(id = R.id.login, click = "OnClick")
    Button mBtnLogin;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_login;
    }

    void OnClick(View v) {
        String account = mAccount.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("password", password);
            requestParams.put("phone", account);
            startRequest(Constants.BASE_URL_2, ApiConstants.LOGIN, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestSucceeded(String content) {
                    super.onRequestSucceeded(content);
                    LoginResponseBean responseBean = ToolsHelper.parseJson(content, LoginResponseBean.class);
                    if (responseBean != null&&responseBean.isSuccess()) {
                        App.sToken=responseBean.getMsg().getToken();
                        getActivity().finish();
                    }
                }
            }, HttpRequestUtils.RequestType.POST);
        }
    }
}
