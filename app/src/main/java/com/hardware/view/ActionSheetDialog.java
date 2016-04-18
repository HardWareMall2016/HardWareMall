package com.hardware.view;


import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestHandle;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ActionSheetDialog {

    private Context context;
    private Display display;
    private Dialog dialog;
    private int id;

    private DisplayImageOptions options;

    private ImageView mCancel;
    private ImageView productUrl;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductStock;

    private String imgUrl;
    private String productName;
    private String productPrice;
    private String mDialog_skuId;
    private int mDialog_Stock;

    private ImageView mSubduction;
    private ImageView mPlus;
    private TextView mNumber;

    private RelativeLayout mConfirm;

    private int count = 1;

    private RequestHandle mUpgradeHandle;

    private View.OnClickListener mOnConformClickListener;


    public ActionSheetDialog(Context content, String mDialog_skuId, String imgUrl, String productName, String productPrice, int mDialog_Stock) {
        this.context = content;
        this.imgUrl = imgUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.mDialog_skuId = mDialog_skuId;
        this.mDialog_Stock = mDialog_Stock;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        options = ToolsHelper.buldDefDisplayImageOptions();
    }


    public ActionSheetDialog builder() {

        View view = LayoutInflater.from(context).inflate(
                R.layout.view_actionsheet, null);
        mCancel = (ImageView) view.findViewById(R.id.view_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        productUrl = (ImageView) view.findViewById(R.id.products_detail_shopurl);
        ImageLoader.getInstance().displayImage(imgUrl, productUrl, options);

        mProductName = (TextView) view.findViewById(R.id.dialog_products_detail_shopname);
        mProductName.setText(productName);
        mProductPrice = (TextView) view.findViewById(R.id.dialog_products_detail_price);
        mProductPrice.setText("￥" + productPrice);

        mSubduction = (ImageView) view.findViewById(R.id.view_imageview_subduction);
        mPlus = (ImageView) view.findViewById(R.id.view_imageview_plus);
        mNumber = (TextView) view.findViewById(R.id.view_textview_num);
        mProductStock = (TextView) view.findViewById(R.id.view_stock);
        mProductStock.setText("剩余" + mDialog_Stock + "件");

        mConfirm = (RelativeLayout) view.findViewById(R.id.product_confirm);
        mConfirm.setOnClickListener(mOnConformClickListener);

        mSubduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 1) {
                    count = 1;
                    mNumber.setText(count + "");
                } else {
                    --count;
                    mNumber.setText(count + "");
                }
            }
        });

        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = ++count;
                mNumber.setText(count + "");
            }
        });


        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.x = 0;
        lp.y = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        lp.height = height / 2;
        lp.width = width;
        dialogWindow.setAttributes(lp);

        return this;
    }

  /*  private void Data() {
        RequestParams requestParams = new RequestParams();
        if(mNumber.getText().toString().equals("0")){
            requestParams.put("Quantity",1);
        }else{
            requestParams.put("Quantity", Integer.parseInt(mNumber.getText().toString()));
        }
        requestParams.put("Token", App.sToken);
        requestParams.put("skuId",mDialog_skuId);
        startRequest(ApiConstants.ADD_ORDERCAR, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        break;
                    case canceled:
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        }, HttpRequestUtils.RequestType.POST);
    }*/

    public void setOnConformClickListener(View.OnClickListener onClickListener) {
        mOnConformClickListener = onClickListener;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

}
