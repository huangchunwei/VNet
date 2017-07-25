package com.mevv.vnet.lib.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 14:04.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class BitmapCallback extends SimpleCallback<Bitmap> {


    @Override
    public Bitmap parseResponse(int id, Response response) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
