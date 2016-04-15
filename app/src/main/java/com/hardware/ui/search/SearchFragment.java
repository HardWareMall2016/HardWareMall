package com.hardware.ui.search;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hardware.R;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SearchFragment extends ABaseFragment {
    @ViewInject(id = R.id.search_content)
    EditText mEditText;
    @ViewInject(id = R.id.search_product_shop)
    TextView mSearchPs;
    @ViewInject(id = R.id.search_down, click = "OnClick")
    ImageView mDown;
    @ViewInject(id = R.id.right_menu, click = "OnClick")
    TextView mRightMenu;

    private PopupWindow mPopupWindow;
    private WindowManager windowManager ;
    private ListView mPopupListView;
    private List<String> groups = new ArrayList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_search_layout;
    }

    public static void launch(FragmentActivity activity) {
        FragmentArgs args = new FragmentArgs();
        FragmentContainerActivity.launch(activity, SearchFragment.class, null, false);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        mEditText.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mEditText.getText().toString().length() > 0){
                mRightMenu.setText("搜索");
            }else{
                mRightMenu.setText("取消");
            }
        }
    };

    private void showPopupWindow(View parent) {
        if (mPopupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.group_list, null);
            mPopupListView = (ListView) view.findViewById(R.id.popup_list);
            groups.add("商品");
            groups.add("供应商");
            GroupAdapter groupAdapter = new GroupAdapter(groups);
            mPopupListView.setAdapter(groupAdapter);
            mPopupWindow = new PopupWindow(view, 200, 220);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int xPos = - mPopupWindow.getWidth() / 2;
        mPopupWindow.showAsDropDown(parent, xPos, 4);

        mPopupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                mSearchPs.setText(groups.get((int)id));
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
            }
        });
    }

    class GroupAdapter extends BaseAdapter {
        private List<String> groups = new ArrayList<>();

        public GroupAdapter(List<String> groups) {
            this.groups = groups;
        }

        @Override
        public int getCount() {
            return groups.size();
        }

        @Override
        public Object getItem(int position) {
            return groups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PopViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new PopViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_list_item, null);
                viewHolder.popupTitle = (TextView) convertView.findViewById(R.id.tv_popup_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PopViewHolder) convertView.getTag();
            }

            viewHolder.popupTitle.setText(groups.get(position));

            return convertView;
        }
    }

    private class PopViewHolder {
        TextView popupTitle;
    }


    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.search_down:
                showPopupWindow(view);
                break;
            case R.id.right_menu:
                chechView();
                break;
        }
    }

    private void chechView() {
        if(mEditText.getText().toString().length() > 0){
            ToastUtils.toast("搜索");
        }else{
            getActivity().finish();
        }
    }
}
