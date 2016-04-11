package com.hardware.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hover on 2016/4/10.
 */
public class GoodsListAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private Context mContext;

    public GoodsListAdapter(Context context) {
        super();
        mContext = context;
        inflater = LayoutInflater.from(context);
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
        final GoodsListViewHolder articleViewHolder =
                (GoodsListViewHolder) holder;
//        articleViewHolder.setData(datas.get(position));
//        articleViewHolder.bind(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class GoodsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.goods_img)
        ImageView goodsImg;
        @Bind(R.id.goods_title)
        TextView goodsTitle;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        private IMyViewHolderClicks mListener;
        private View mItemView;

        public GoodsListViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            mItemView = itemView;
            itemView.setOnClickListener(this);
        }
//
//        public void setData(@NonNull final CompanyInfo companyInfo) {
//            tvCoName.setText(companyInfo.getCompanyName());
//            tvCoLocation.setText(companyInfo.getDetailAddress());
//            tvCoTel.setText(companyInfo.getPhoneNo());
//        }

//        private void bind(CompanyInfo companyInfo) {
//            mItemView.setTag(companyInfo);
//            mItemView.setClickable(true);
//        }

        @Override
        public void onClick(View v) {//String.valueOf(((CompanyInfo) mItemView.getTag()).getCity())
            if (itemView.equals(v)) {
//                int city = ((CompanyInfo) mItemView.getTag()).getCity();
//                String cityStr = queryCity(city);
//                mListener.onItemClick(cityStr, ((CompanyInfo) mItemView.getTag()).getDetailAddress(), ((CompanyInfo) mItemView.getTag()).getCompanyName(), ((CompanyInfo) mItemView.getTag()).getPhoneNo());
            }
        }

    }
}
