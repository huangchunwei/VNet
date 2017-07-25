package com.mevv.vnet.lib.request;


import com.mevv.vnet.lib.Api;
import com.mevv.vnet.lib.callback.BitmapCallback;
import com.mevv.vnet.lib.callback.Callback;
import com.mevv.vnet.lib.callback.FileCallback;
import com.mevv.vnet.lib.callback.JsonCallback;
import com.mevv.vnet.lib.callback.StringCallback;
import com.mevv.vnet.lib.processor.AsyncHttpProcessor;
import com.mevv.vnet.lib.processor.SyncHttpProcessor;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 13:47.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class RealRequestCall {

    private RequestGenerator mRequestGenerator;
    private Request mRequest;
    private Call mCall;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    //默认为异步请求
    private boolean mAsync = true;
    //同步请求
    private boolean mSync = false;

    private OkHttpClient mNewClient;
    private OkHttpClient mDefaultClient;

    public RealRequestCall(RequestGenerator requestGenerator) {
        this.mRequestGenerator = requestGenerator;
    }

    /**
     * 设置读超时时间,单位ms
     *
     * @param readTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RealRequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    /**
     * 设置写超时时间,单位ms
     *
     * @param writeTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RealRequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    /**
     * 设置连接超时时间,单位ms
     *
     * @param connTimeOut 超时时间
     * @return {@link RequestCall}
     */
    public RealRequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    /**
     * 设置新的OkHttpClient
     *
     * @param okHttpClient 超时时间
     * @return {@link RequestCall}
     */
    public RealRequestCall client(OkHttpClient okHttpClient) {
        this.mNewClient = okHttpClient;
        return this;
    }

    public RealRequestCall sync() {
        this.mSync = true;
        this.mAsync = false;
        return this;
    }

    /**
     * 生成Request对象
     *
     * @param callback {@link Callback}
     * @return {@link Request}
     */
    private Request generateRequest(Callback callback) {
        return mRequestGenerator.generateRequest(callback);
    }

    private Call buildCall(Callback callback) {
        mRequest = generateRequest(callback);
        if (mNewClient == null) {//是否设置新的OkHttpClient对象
            if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {//是否设置超时时间
                readTimeOut = readTimeOut > 0 ? readTimeOut : Api.DEFAULT_TIMEOUT_MILLISECONDS;
                writeTimeOut = writeTimeOut > 0 ? writeTimeOut : Api.DEFAULT_TIMEOUT_MILLISECONDS;
                connTimeOut = connTimeOut > 0 ? connTimeOut : Api.DEFAULT_TIMEOUT_MILLISECONDS;
                mDefaultClient = Api.getInstance().getClient().newBuilder()
                        .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                        .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                        .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                        .build();
                mCall = mDefaultClient.newCall(mRequest);
            } else {//默认的OkHttpClient
                mCall = Api.getInstance().getClient().newCall(mRequest);
            }
        } else {//新的OkHttpClient
            mCall = mNewClient.newCall(mRequest);
        }
        return mCall;
    }

    public void asString(StringCallback callback) {
        startCall(callback);
    }

    public <T> void asJson(JsonCallback<T> callback) {
        startCall(callback);
    }

    public void asFile(FileCallback callback) {
        startCall(callback);
    }

    public void asBitmap(BitmapCallback callback) {
        startCall(callback);
    }

    private void startCall(Callback callback) {
        buildCall(callback);
        if (mAsync) {//异步请求
            new AsyncHttpProcessor(this).process(callback);
        } else {//同步请求
            new SyncHttpProcessor(this).process(callback);
        }
    }

    /**
     * 获取Request对象
     *
     * @return {@link Request}
     */
    public Request getRequest() {
        return mRequest;
    }

    /**
     * 获取Call
     *
     * @return {@link Call}
     */
    public Call getCall() {
        return mCall;
    }

    /**
     * 获取OKRequest
     *
     * @return {@link RequestGenerator}
     */
    public RequestGenerator getRequestGenerator() {
        return mRequestGenerator;
    }


}
