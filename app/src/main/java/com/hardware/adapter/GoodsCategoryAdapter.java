package com.hardware.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.bean.GoodsFirstCategoryBean.MessageBean;
import com.hardware.ui.fragment.GoodsScrollGridFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hover on 2016/4/9.
 */
public class GoodsCategoryAdapter extends RecyclerView.Adapter<GoodsCategoryAdapter.ViewHolder> {
    private Activity context;
    private LayoutInflater inflater;
    private String[] titles = {"五金机电", "电工电气", "五金零件", "电子器件", "仪器仪表", "机械设备", "行业设备", "橡胶化工", "安防劳保", "行政办公", "厨房用具", "安装建材"};
    private int[] imgId = {R.drawable.goods_categroy_wjjd, R.drawable.goods_categroy_dgdq, R.drawable.goods_categroy_wjlj, R.drawable.goods_categroy_dzqj, R.drawable.goods_categroy_yqyb, R.drawable.goods_categroy_jxsb, R.drawable.goods_categroy_hysb, R.drawable.goods_categroy_xjhg, R.drawable.goods_categroy_afnb, R.drawable.goods_categroy_xzbg, R.drawable.goods_categroy_qfyj, R.drawable.goods_categroy_azjc};
    private List<MessageBean> datas;

    public GoodsCategoryAdapter(Activity context, List<MessageBean> datas) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.goods_recycler_grid_item, null);
        return new ViewHolder(itemView, new IMyViewHolderClicks() {
            @Override
            public void onItemClick(int pos) {
//                Toast.makeText(context, "" + pos, Toast.LENGTH_SHORT).show();
//                switch (pos) {
//                    case 0:
//                        id = datas.get(pos).getId();
//                        title = datas.get(pos).getName();
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                    case 5:
//                        break;
//                    case 6:
//                        break;
//                    case 7:
//                        break;
//                    case 8:
//                        break;
//                    case 9:
//                        break;
//                    case 10:
//                        break;
//                    case 11:
//                        break;
//                }
                String id = String.valueOf(datas.get(pos).getId());
                String name = datas.get(pos).getName();
                GoodsScrollGridFragment.launch(context, name, id);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(datas.get(i).getName());
        viewHolder.imageView.setImageResource(imgId[i]);
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.textView)
        TextView textView;
        @Bind(R.id.imageView)
        ImageView imageView;
        private IMyViewHolderClicks mListener;
        private View mItemView;

        public ViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            mItemView = itemView;
            itemView.setOnClickListener(this);
        }

        private void bind(int pos) {
            mItemView.setTag(pos);
            mItemView.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            if (itemView.equals(v)) {
                mListener.onItemClick((Integer) mItemView.getTag());
            }
        }
    }
}
