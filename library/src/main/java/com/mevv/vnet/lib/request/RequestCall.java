package com.mevv.vnet.lib.request;

import com.mevv.vnet.lib.callback.BitmapCallback;
import com.mevv.vnet.lib.callback.FileCallback;
import com.mevv.vnet.lib.callback.JsonCallback;
import com.mevv.vnet.lib.callback.StringCallback;

import okhttp3.OkHttpClient;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 10:30.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class RequestCall {

    private RealRequestCall mRealRequestCall;

    public RequestCall(RequestGenerator requestGenerator) {
        mRealRequestCall = new RealRequestCall(requestGenerator);
    }

    /**
     * 设置读超时时间,单位ms
     *
     * @param readTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RequestCall readTimeOut(long readTimeOut) {
        mRealRequestCall.readTimeOut(readTimeOut);
        return this;
    }

    /**
     * 设置写超时时间,单位ms
     *
     * @param writeTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RequestCall writeTimeOut(long writeTimeOut) {
        mRealRequestCall.writeTimeOut(writeTimeOut);
        return this;
    }

    /**
     * 设置连接超时时间,单位ms
     *
     * @param connTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RequestCall connTimeOut(long connTimeOut) {
        mRealRequestCall.connTimeOut(connTimeOut);
        return this;
    }

    /**
     * 设置新的OkHttpClient
     *
     * @param okHttpClient 超时时间
     * @return {@link RequestCall}
     */
    public RequestCall client(OkHttpClient okHttpClient) {
        mRealRequestCall.client(okHttpClient);
        return this;
    }

    public RequestCall sync() {
        mRealRequestCall.sync();
        return this;
    }


    public void asString(StringCallback callback) {
        mRealRequestCall.asString(callback);
    }

    public <T> void asJson(JsonCallback<T> jsonCallback) {
        mRealRequestCall.asJson(jsonCallback);
    }

    public void asBitmap(BitmapCallback callback) {
        mRealRequestCall.asBitmap(callback);
    }

    public void asFile(FileCallback callback) {
        mRealRequestCall.asFile(callback);
    }

}
