package com.mevv.vnet.lib.builder;

import android.support.v4.util.ArrayMap;

import com.mevv.vnet.lib.request.RequestCall;

import java.util.Map;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 10:29.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class AbsBuilder<B extends AbsBuilder> {

    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mParams;
    protected Map<String, String> mHeaders;
    protected int mId;

    public AbsBuilder(String url) {
        this.mUrl = url;
    }

    public B id(int id) {
        this.mId = id;
        return (B) this;
    }

    public B addHeader(String key, String value) {
        if (this.mHeaders == null) {
            mHeaders = new ArrayMap<>();
        }
        mHeaders.put(key, value);
        return (B) this;
    }

    public B addHeaders(Map<String, String> headers) {
        if (this.mHeaders == null) {
            mHeaders = new ArrayMap<>();
        }
        mHeaders.putAll(headers);
        return (B) this;
    }

    public abstract RequestCall build();

}
