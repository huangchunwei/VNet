package com.mevv.vnet.lib.builder;

import android.net.Uri;
import android.support.v4.util.ArrayMap;

import com.mevv.vnet.lib.request.GetRequestGenerator;
import com.mevv.vnet.lib.request.RequestCall;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 10:29.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class GetBuilder extends AbsBuilder<GetBuilder> {

    public GetBuilder(String url) {
        super(url);
    }

    @Override
    public RequestCall build() {
        //需要把参数拼接到url上
        if (mParams != null) {
            mUrl = mergeParams(mUrl, mParams);
        }
        return new GetRequestGenerator(mId, mUrl, mParams, mHeaders).build();
    }

    public GetBuilder addParam(String key, String value) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.put(key, value);
        return this;
    }

    public GetBuilder addParams(Map<String, String> params) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.putAll(params);
        return this;
    }

    private String mergeParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
