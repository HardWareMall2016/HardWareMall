package com.hardware.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.GoodsThirdCategoryBean.MessageBean;
import com.hardware.bean.Type;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



public class GridViewAdapter extends BaseAdapter {

    private List<MessageBean> list;
    private Type type;
    private Context context;
    private Holder view;
    private DisplayImageOptions options;

    public GridViewAdapter(Context context, List<MessageBean> list) {
        this.list = list;
        this.context = context;
        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview, null);
            view = new Holder(convertView);
            convertView.setTag(view);
        } else {
            view = (Holder) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            ImageLoader.getInstance().displayImage(ApiConstants.IMG_BASE_URL + list.get(position).getIcon(), view.icon, options);
            view.name.setText(list.get(position).getName());
        }
        return convertView;
    }

    private class Holder {
        private ImageView icon;
        private TextView name;

        public Holder(View view) {
            icon = (ImageView) view.findViewById(R.id.typeicon);
            name = (TextView) view.findViewById(R.id.typename);
        }
    }

}
