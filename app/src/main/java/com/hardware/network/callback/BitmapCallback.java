package com.hardware.network.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Response;

import java.io.IOException;


public abstract class BitmapCallback extends Callback<Bitmap>
{
    @Override
    public Bitmap parseNetworkResponse(Response response) throws IOException
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
