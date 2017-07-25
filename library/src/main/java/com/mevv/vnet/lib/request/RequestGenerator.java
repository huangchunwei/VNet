package com.mevv.vnet.lib.request;


import com.mevv.vnet.lib.callback.Callback;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 10:55.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class RequestGenerator {
    protected int mId;
    protected String mUrl;
    protected Map<String, String> mParams;
    protected Map<String, String> mHeaders;
    protected Request.Builder mBuilder = new Request.Builder();

    public RequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers) {
        this.mId = id;
        this.mUrl = url;
        this.mParams = params;
        this.mHeaders = headers;
        if (mUrl == null) {
            throw new IllegalArgumentException("URL can not be null.");
        }
        init();
    }

    private void init() {
        mBuilder.url(mUrl);
        appendHeaders();
    }

    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (mHeaders == null || mHeaders.isEmpty()) return;
        for (String key : mHeaders.keySet()) {
            headerBuilder.add(key, mHeaders.get(key));
        }
        mBuilder.headers(headerBuilder.build());
    }

    /**
     * 构建RequestBody
     *
     * @return {@link RequestBody}
     */
    protected abstract RequestBody buildRequestBody();

    /**
     * 改造RequestBody
     *
     * @param requestBody {@link RequestBody}
     * @param callback    {@link Callback}
     * @return {@link RequestBody}
     */
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    /**
     * 构建Request
     *
     * @param requestBody {@link RequestBody}
     * @return {@link Request}
     */
    protected abstract Request buildRequest(RequestBody requestBody);

    public Request generateRequest(final Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrapRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrapRequestBody);
        return request;
    }

    /**
     * 开始构建
     *
     * @return
     */
    public RequestCall build() {
        return new RequestCall(this);
    }

    public int getId() {
        return mId;
    }
}
