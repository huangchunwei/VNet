package com.mevv.vnet.lib.processor;


import com.mevv.vnet.lib.Api;
import com.mevv.vnet.lib.callback.Callback;
import com.mevv.vnet.lib.request.RealRequestCall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 11:31.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class AsyncHttpProcessor {

    private RealRequestCall mRequestCall;
    private Callback mVCallBack;
    private int mId;
    private Call mCall;

    public AsyncHttpProcessor(RealRequestCall requestCall) {
        this.mRequestCall = requestCall;
        if (mRequestCall != null) {
            this.mId = mRequestCall.getRequestGenerator().getId();
            this.mCall = mRequestCall.getCall();
        }
    }

    public void process(Callback callback) {
        this.mVCallBack = callback;
        start();
        mCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailedResult(mId, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //任务为是否被取消
                    if (call.isCanceled()) {
                        //UI线程
                        sendFailedResult(mId, call, new IOException("this request canceled!"));
                        return;
                    }
                    //请求是否是成功的
                    if (!response.isSuccessful()) {
                        //UI线程
                        sendFailedResult(mId, call, new IOException("request failed, response's code is:" + response.code()));
                        return;
                    }
                    //工作线程
                    mVCallBack.onResponse(mId, response);
                    //工作线程
                    Object o = mVCallBack.parseResponse(mId, response);
                    //UI线程
                    sendSuccessResult(mId, o);
                } catch (Exception e) {
                    //UI线程
                    sendFailedResult(mId, call, e);
                } finally {
                    if (response.body() != null) {
                        response.body().close();
                    }
                }
            }
        });
    }

    private void start() {
        //开始网络请求
        Api.getInstance().runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mVCallBack != null) {
                    mVCallBack.onStart(mRequestCall.getRequestGenerator().getId(), mRequestCall.getRequest());
                }
            }
        });
    }

    /**
     * 发送网络请求成功回调
     *
     * @param id
     * @param o
     */
    private void sendSuccessResult(final int id, final Object o) {
        Api.getInstance().runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mVCallBack.onSuccess(id, o);
                mVCallBack.onEnd(id);
            }
        });
    }

    /**
     * 发送网络请求失败回调
     *
     * @param id
     * @param call
     * @param e
     */
    private void sendFailedResult(final int id, final Call call, final Exception e) {
        //网络请求失败
        Api.getInstance().runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mVCallBack != null) {
                    mVCallBack.onFailed(id, call, e);
                }
            }
        });
    }
}
