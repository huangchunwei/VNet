package com.mevv.vnet.lib.builder;


import com.mevv.vnet.lib.request.OtherRequestGenerator;
import com.mevv.vnet.lib.request.RequestCall;

import okhttp3.RequestBody;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 15:34.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class OtherRequestBuilder extends AbsBuilder<OtherRequestBuilder> {

    private RequestBody mRequestBody;
    private String mRequestMethod;
    private String mContent;

    public OtherRequestBuilder(String url, String method) {
        super(url);
        this.mRequestMethod = method;
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.mRequestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    @Override
    public RequestCall build() {
        return new OtherRequestGenerator(mId, mUrl, mParams, mHeaders, mRequestBody, mContent, mRequestMethod).build();
    }
}
