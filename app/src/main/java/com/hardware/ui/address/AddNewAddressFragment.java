package com.hardware.ui.address;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.DefResponseBean2;
import com.hardware.bean.GetCityResponseBean;
import com.hardware.bean.GetCountyResponseBean;
import com.hardware.bean.GetProvinceReponseBean;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopListener;
import com.zhan.framework.view.pickerview.LoopView;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNewAddressFragment extends ABaseFragment  {

    public static void launch(Fragment fragment,int requestCode) {
        /*FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, shopContent);*/
        FragmentContainerActivity.launchForResult(fragment, AddNewAddressFragment.class, null, requestCode);
    }

    @ViewInject(id = R.id.name)
    private EditText mETName;

    @ViewInject(id = R.id.phone)
    private EditText mETPhone;

    @ViewInject(id = R.id.location,click = "OnClick")
    private TextView mETLocation;

    @ViewInject(id = R.id.specific_address)
    private EditText mETSpecificAddress;

    @ViewInject(id = R.id.set_def)
    private CheckBox mCkSetDef;

    @ViewInject(id = R.id.submit,click = "OnClick")
    private Button mBtnSubmit;

    private View mViewPopMenuContent;
    private LoopView mPickViewProvince;
    private LoopView mPickViewCity;
    private LoopView mPickViewCounty;

    private PopupWindow mPopupWindow;
    private LayoutInflater mLayoutInflater;

    //Datas
    private GetProvinceReponseBean mGetProvinceReponseBean;
    private GetCityResponseBean mGetCityResponseBean;
    private GetCountyResponseBean mGetCountyResponseBean;
    private int mCountryId=0;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_add_new_address;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        mLayoutInflater=inflater;
        getActivity().setTitle("新增地址");
        intiPopMenu();
    }


    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                ToolsHelper.hideInput(getActivity(),getRootView());
                showChooseCityMenu();
                break;
            case R.id.submit:
                submitAddress();
                break;
        }
    }

    private void showChooseCityMenu() {
        if(mViewPopMenuContent==null){
            mViewPopMenuContent = mLayoutInflater.inflate(R.layout.pop_memu_choose_city, null);
            mPopupWindow.setContentView(mViewPopMenuContent);
            View btnCancel = mViewPopMenuContent.findViewById(R.id.cancel);
            btnCancel.setOnClickListener(mOnCancelClickListener);
            View btnFinish = mViewPopMenuContent.findViewById(R.id.finish);
            btnFinish.setOnClickListener(mOnFinishClickListener);

            mPickViewProvince = (LoopView) mViewPopMenuContent.findViewById(R.id.picker_view_province);
            mPickViewProvince.setTextSize(18);
            mPickViewProvince.setNotLoop();

            mPickViewCity = (LoopView) mViewPopMenuContent.findViewById(R.id.picker_view_city);
            mPickViewCity.setTextSize(18);
            mPickViewCity.setNotLoop();

            mPickViewCounty = (LoopView) mViewPopMenuContent.findViewById(R.id.picker_view_county);
            mPickViewCounty.setTextSize(18);
            mPickViewCounty.setNotLoop();
        }
        mPickViewProvince.setVisibility(View.INVISIBLE);
        mPickViewCity.setVisibility(View.INVISIBLE);
        mPickViewCounty.setVisibility(View.INVISIBLE);

        mPickViewProvince.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                if (mGetProvinceReponseBean != null) {
                    queryCites(mGetProvinceReponseBean.getMsg().get(item).getProvinceId());
                }
            }
        });

        mPickViewCity.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                if (mGetProvinceReponseBean != null && mGetCityResponseBean != null) {
                    int provinceIndex = mPickViewProvince.getSelectedItem();
                    int provinceId = mGetProvinceReponseBean.getMsg().get(provinceIndex).getProvinceId();
                    int cityId = mGetCityResponseBean.getMsg().get(item).getCityId();
                    queryCounties(provinceId, cityId);
                }
            }
        });

        showPopMenu();
        if(mGetProvinceReponseBean==null){
            queryProvinces();
        }
    }

    private void queryProvinces(){
        mGetProvinceReponseBean = null;
        mGetCityResponseBean = null;
        mGetCountyResponseBean = null;
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_PROVINCE, null, new HttpRequestHandler() {
            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                if (getActivity() == null) {
                    return;
                }
                mPickViewProvince.setVisibility(View.INVISIBLE);
                GetProvinceReponseBean responseBean = ToolsHelper.parseJson(content, GetProvinceReponseBean.class);
                if (responseBean != null && responseBean.isSuccess()) {
                    mGetProvinceReponseBean = responseBean;

                    ArrayList<String> valueNames = new ArrayList<>();
                    for (GetProvinceReponseBean.MsgEntity provinceInfo : responseBean.getMsg()) {
                        valueNames.add(provinceInfo.getProvincName());
                    }

                    if (valueNames.size() > 0) {
                        mPickViewProvince.setVisibility(View.VISIBLE);
                        mPickViewProvince.setArrayList(valueNames);
                        mPickViewProvince.setInitPosition(0);
                        //默认取第0项
                        queryCites(mGetProvinceReponseBean.getMsg().get(0).getProvinceId());
                    }
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    private void queryCites(final int provinceId){
        mGetCityResponseBean = null;
        mGetCountyResponseBean = null;
        mPickViewCity.setVisibility(View.INVISIBLE);
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("provinceid",String.valueOf(provinceId));
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_CITY, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                if(getActivity()==null){
                    return;
                }
                mPickViewCity.setVisibility(View.INVISIBLE);
                GetCityResponseBean responseBean = ToolsHelper.parseJson(content, GetCityResponseBean.class);
                if (responseBean != null&&responseBean.isSuccess()) {
                    mGetCityResponseBean=responseBean;

                    ArrayList<String> valueNames=new ArrayList<>();
                    for(GetCityResponseBean.MsgEntity itemInfo:responseBean.getMsg()){
                        valueNames.add(itemInfo.getCityName());
                    }

                    if(valueNames.size()>0){
                        mPickViewCity.setVisibility(View.VISIBLE);
                        mPickViewCity.setArrayList(valueNames);
                        mPickViewCity.setInitPosition(0);
                        queryCounties(provinceId, responseBean.getMsg().get(0).getCityId());
                    }
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    private void queryCounties (int provinceId,int cityId){
        mGetCountyResponseBean=null;
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("provinceid", String.valueOf(provinceId));
        requestParams.put("cityid", String.valueOf(cityId));
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_COUNTY, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                if(getActivity()==null){
                    return;
                }
                mPickViewCounty.setVisibility(View.INVISIBLE);
                GetCountyResponseBean responseBean = ToolsHelper.parseJson(content, GetCountyResponseBean.class);
                if (responseBean != null&&responseBean.isSuccess()) {
                    mGetCountyResponseBean=responseBean;

                    ArrayList<String> valueNames=new ArrayList<>();
                    for(GetCountyResponseBean.MsgEntity itemInfo:responseBean.getMsg()){
                        valueNames.add(itemInfo.getCountyName());
                    }
                    if(valueNames.size()>0){
                        mPickViewCounty.setVisibility(View.VISIBLE);
                        mPickViewCounty.setArrayList(valueNames);
                        mPickViewCounty.setInitPosition(0);
                    }
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    private void submitAddress(){
        String name=mETName.getText().toString().trim();
        String phone=mETPhone.getText().toString().trim();
        String specificAddress=mETSpecificAddress.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            ToastUtils.toast("收货人姓名不能为空!");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            ToastUtils.toast("收货人手机号码不能为空!");
            return;
        }
        if(TextUtils.isEmpty(specificAddress)){
            ToastUtils.toast("详细地址不能为空!");
            return;
        }
        if(mCountryId<=0){
            ToastUtils.toast("请选择收货地区!");
            return;
        }
        //
        HashMap<String,String> requestParams=new HashMap<>();
        requestParams.put("Address", specificAddress);
        requestParams.put("IsDefault", String.valueOf(mCkSetDef.isChecked()));
        requestParams.put("ReceiverPerson", name);
        requestParams.put("ReceiverPhone", phone);
        requestParams.put("RegionId", String.valueOf(mCountryId));
        requestParams.put("token", App.sToken);

        startRequest(Constants.BASE_URL_2, ApiConstants.ADD_MY_ADDRESS_INFO, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                if (getActivity() == null) {
                    return;
                }

                DefResponseBean2 responseBean = ToolsHelper.parseJson(content, DefResponseBean2.class);
                if (responseBean != null && responseBean.isSuccess()) {
                    ToastUtils.toast("提交成功!");
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }else{
                    ToastUtils.toast("提交失败!");
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }

    private View.OnClickListener mOnCancelClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            closePopWin();
        }
    };

    private View.OnClickListener mOnFinishClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(mGetProvinceReponseBean!=null&&mGetCityResponseBean!=null&&mGetCountyResponseBean!=null){
                int provinceIndex = mPickViewProvince.getSelectedItem();
                int cityIndex = mPickViewCity.getSelectedItem();
                int countyIndex=mPickViewCounty.getSelectedItem();

                String provinceName=mGetProvinceReponseBean.getMsg().get(provinceIndex).getProvincName();
                String cityName=mGetCityResponseBean.getMsg().get(cityIndex).getCityName();
                String countryName=mGetCountyResponseBean.getMsg().get(countyIndex).getCountyName();

                mETLocation.setText(String.format("%s %s %s",provinceName,cityName,countryName));

                mCountryId=mGetCountyResponseBean.getMsg().get(countyIndex).getCountyId();
            }
            closePopWin();
        }
    };

    private void intiPopMenu() {
        /* 第一个参数弹出显示view 后两个是窗口大小 */
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        /* 设置背景显示 */
        int bgColor = getResources().getColor(com.zhan.framework.R.color.main_background);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
        /* 设置触摸外面时消失 */
        mPopupWindow.setOutsideTouchable(true);
        /* 设置系统动画 */
        mPopupWindow.setAnimationStyle(R.style.pop_menu_animation);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        /* 设置点击menu以外其他地方以及返回键退出 */
        mPopupWindow.setFocusable(true);
    }

    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            /* 最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标 */
            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
