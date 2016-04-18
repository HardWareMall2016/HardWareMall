package com.hardware.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.base.Constants;
import com.hardware.bean.GoodsListBean.GoodsInfo;
import com.hardware.bean.ProductContent;
import com.hardware.ui.products.ProductDetailFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hover on 2016/4/10.
 */
public class GoodsListAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private List<GoodsInfo> mDatas;
    private DisplayImageOptions options;

    public GoodsListAdapter(Activity context, List<GoodsInfo> datas) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.mDatas = datas;
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
    public GoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater
                .inflate(R.layout.goods_list_item, parent, false);
        return new GoodsListViewHolder(itemView, new IMyViewHolderClicks() {
            @Override
            public void onItemClick(int str) {
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GoodsListViewHolder goodsListViewHolder =
                (GoodsListViewHolder) holder;
        goodsListViewHolder.setData(mDatas.get(position));
        goodsListViewHolder.bind(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class GoodsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.goods_img)
        ImageView goodsImg;
        @Bind(R.id.goods_title)
        TextView goodsTitle;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        @Bind(R.id.goods_count)
        TextView goodsCount;
        private IMyViewHolderClicks mListener;
        private View mItemView;

        public GoodsListViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            mItemView = itemView;
            itemView.setOnClickListener(this);
        }

        public void setData(@NonNull final GoodsInfo goodsInfo) {
            if (null != goodsInfo) {
                ImageLoader.getInstance().displayImage(ApiConstants.IMG_BASE_URL + goodsInfo.getImgUrl(), goodsImg, options);
                goodsTitle.setText(goodsInfo.getProductName());
                goodsPrice.setText(String.valueOf(goodsInfo.getMarketPrice()));
                goodsCount.setText("成交" + goodsInfo.getSaleCounts() + "笔");
            }

        }

        private void bind(GoodsInfo goodsInfo) {
            mItemView.setTag(goodsInfo);
            mItemView.setClickable(true);
        }

        @Override
        public void onClick(View v) {//String.valueOf(((CompanyInfo) mItemView.getTag()).getCity())
            if (itemView.equals(v)) {
                int priductId = ((GoodsInfo) mItemView.getTag()).getId();
                ProductContent content = new ProductContent();
                content.setId(priductId);
                content.setDistrict(Constants.REGION_NAME);
                ProductDetailFragment.launch((FragmentActivity) mContext, content);

            }
        }

    }
}
