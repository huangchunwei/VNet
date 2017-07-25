package com.mevv.vnet.lib.builder;

import android.support.v4.util.ArrayMap;

import com.mevv.vnet.lib.request.PostFormRequestGenerator;
import com.mevv.vnet.lib.request.RequestCall;

import java.util.Map;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 14:15.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostFormBuilder extends AbsBuilder<PostFormBuilder> {


    public PostFormBuilder(String url) {
        super(url);
    }

    public PostFormBuilder addParam(String key, String value) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.put(key, value);
        return this;
    }

    public PostFormBuilder addParams(Map<String, String> params) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.putAll(params);
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFormRequestGenerator(mId, mUrl, mParams, mHeaders).build();
    }
}
