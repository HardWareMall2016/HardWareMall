package com.hardware.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.App;
import com.hardware.base.Constants;
import com.hardware.bean.CartOrderAddressResponse;
import com.hardware.bean.DefResponseBean2;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.address.AddNewAddressFragment;
import com.hardware.ui.base.APullToRefreshListFragment;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ARefreshFragment;
import com.zhan.framework.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderAddressFragment extends APullToRefreshListFragment<CartOrderAddressResponse.AddressInfo> {

    private final static int REQUEST_CODE=100;

    @ViewInject(id = R.id.submit,click = "OnClick")
    private Button mBtnSubmit;

    private PullToRefreshListView mRefreshListView;

    private boolean mEditMode=false;
    private TextView mActionBarMenu;

    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, CartOrderAddressFragment.class, null);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        menu.setText("编辑");
        mActionBarMenu=menu;
        mActionBarMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEditMode=!mEditMode;
                refreshViewByEditMode();
            }
        });
    }

    private void refreshViewByEditMode(){
        if(mEditMode){
            mActionBarMenu.setText("完成");
            mBtnSubmit.setText("确认删除");
        }else{
            mActionBarMenu.setText("编辑");
            mBtnSubmit.setText("新增收货地址");
        }
        notifyDataSetChanged();
    }


    @Override
    protected int inflateContentView() {
        return R.layout.frag_layout_cartorder_address;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("收货地址");
        mEditMode=false;
        refreshViewByEditMode();
    }


    @Override
    protected void setInitPullToRefresh(ListView listView, PullToRefreshListView pullToRefreshListView, Bundle savedInstanceState) {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRefreshListView=pullToRefreshListView;
    }

    @Override
    protected ABaseAdapter.AbstractItemView<CartOrderAddressResponse.AddressInfo> newItemView() {
        return new AddressInfoItemView();
    }

    @Override
    protected void requestData(RefreshMode mode) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("token", App.sToken);
        requestParams.put("Page", getNextPage(mode));
        startRequest(Constants.BASE_URL_2, ApiConstants.GET_MYADDRESS, requestParams, new PagingTask<CartOrderAddressResponse>(mode) {
            @Override
            public CartOrderAddressResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, CartOrderAddressResponse.class);
            }

            @Override
            protected List<CartOrderAddressResponse.AddressInfo> parseResult(CartOrderAddressResponse cartOrderAddressResponse) {
                return cartOrderAddressResponse.getMsg();
            }
        }, HttpRequestUtils.RequestType.POST);

    }

    @Override
    protected void configRefresh(ARefreshFragment.RefreshConfig config) {
        config.minResultSize=10;
    }

    @Override
    public int getFirstPageIndex() {
        return 1;
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if(mEditMode){
                    CartOrderAddressResponse.AddressInfo data = null;
                    for(CartOrderAddressResponse.AddressInfo addressInfo:getAdapterItems()){
                        if(addressInfo.isSelected()){
                            data=addressInfo;
                            break;
                        }
                    }
                    if(data!=null){
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("token", App.sToken);
                        requestParams.put("addressid", data.getAddressId());
                        startRequest(Constants.BASE_URL_2, ApiConstants.DELETE_MY_ADDRESS, requestParams,new HttpRequestHandler(){
                            @Override
                            public void onRequestFinished(ResultCode resultCode, String result) {
                                switch (resultCode){
                                    case success:
                                        DefResponseBean2 responseBean=ToolsHelper.parseJson(result,DefResponseBean2.class);
                                        if(responseBean!=null&&responseBean.getStatus()==0){
                                            mEditMode=false;
                                            refreshViewByEditMode();
                                            mRefreshListView.setRefreshing();
                                        }else{
                                            ToastUtils.toast("删除失败!");
                                        }
                                        break;
                                    case canceled:
                                        break;
                                    default:
                                        ToastUtils.toast(result);
                                }
                            }
                        }, HttpRequestUtils.RequestType.POST);
                    }else{
                        ToastUtils.toast("请选择地址!");
                    }
                }else{
                    AddNewAddressFragment.launch(CartOrderAddressFragment.this,REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mEditMode){
            int selPos=(int) id;
            for(int i=0;i<getAdapterItems().size();i++){
                if(selPos==i){
                    getAdapterItems().get(i).setIsSelected(true);
                }else{
                    getAdapterItems().get(i).setIsSelected(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            mEditMode=false;
            refreshViewByEditMode();
            mRefreshListView.setRefreshing();
        }
    }

    class AddressInfoItemView extends ABaseAdapter.AbstractItemView<CartOrderAddressResponse.AddressInfo> {
        @ViewInject(id = R.id.name)
        private TextView mTvName;

        @ViewInject(id = R.id.phone)
        private TextView mTvPhone;

        @ViewInject(id = R.id.address)
        private TextView mTvAddress;

        @ViewInject(id = R.id.arrow_right)
        private ImageView mImgArrowRight;

        @ViewInject(id = R.id.select)
        private CheckBox mCkSelected;

        @Override
        public int inflateViewId() {
            return R.layout.address_list_item;
        }

        @Override
        public void bindingData(View convertView, final CartOrderAddressResponse.AddressInfo data) {
            mTvName.setText(data.getReceiverPerson());
            mTvPhone.setText(data.getReceiverPhone());
            String address=data.getAddress();
            if(data.isIsDefault()){
                address="[默认]"+address;
            }
            mTvAddress.setText(address);

            if(mEditMode){
                mImgArrowRight.setVisibility(View.GONE);
                mCkSelected.setVisibility(View.VISIBLE);
                mCkSelected.setChecked(data.isSelected());
            }else{
                mImgArrowRight.setVisibility(View.VISIBLE);
                mCkSelected.setVisibility(View.GONE);
            }
        }
    }
}
