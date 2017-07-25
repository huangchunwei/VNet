package com.mevv.vnet.lib.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 11:10.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class GetRequestGenerator extends RequestGenerator {

    public GetRequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers) {
        super(id, url, params, headers);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.get().build();
    }
}
