package com.mevv.vnet.lib.callback;

import okhttp3.Response;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 11:24.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class StringCallback extends SimpleCallback<String> {
    @Override
    public String parseResponse(int id, Response response) throws Exception {
        return response.body().string();
    }
}
