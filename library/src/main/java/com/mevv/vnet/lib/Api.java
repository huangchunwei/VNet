package com.mevv.vnet.lib;


import com.mevv.vnet.lib.builder.GetBuilder;
import com.mevv.vnet.lib.builder.OtherRequestBuilder;
import com.mevv.vnet.lib.builder.PostFileBuilder;
import com.mevv.vnet.lib.builder.PostFormBuilder;
import com.mevv.vnet.lib.builder.PostStringContentBuilder;
import com.mevv.vnet.lib.utils.RequestMethod;
import com.mevv.vnet.lib.utils.ResultTransmitter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 10:14.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: 单例
 */


public class Api {

    private static Api sApi;
    public static final long DEFAULT_TIMEOUT_MILLISECONDS = 15 * 1000;


    private ResultTransmitter mResultTransmitter;
    private OkHttpClient mOkHttpClient;


    private Api(OkHttpClient client) {
        if (client == null) {
            initDefaultClient();
        } else {
            mOkHttpClient = client;
        }
        initResultTransmitter();
    }

    private void initDefaultClient() {
        if (mOkHttpClient == null) {
            //初始化默认的
            mOkHttpClient = new OkHttpClient.Builder()
                    .readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(false)
                    .build();
        }
    }

    private void initResultTransmitter() {
        if (mResultTransmitter == null) {
            mResultTransmitter = ResultTransmitter.get();
        }
    }

    public static void initClient(OkHttpClient okHttpClient) {
        if (sApi == null) {
            synchronized (Api.class) {
                if (sApi == null) {
                    sApi = new Api(okHttpClient);
                }
            }
        }
    }

    public static Api getInstance() {
        if (sApi == null) {
            synchronized (Api.class) {
                if (sApi == null) {
                    sApi = new Api(null);
                }
            }
        }
        return sApi;
    }

    public final void runOnUIThread(Runnable runnable) {
        if (runnable != null) {
            mResultTransmitter.execute(runnable);
        }
    }

    public final OkHttpClient getClient() {
        return mOkHttpClient;
    }


    public static GetBuilder get(String url) {
        return new GetBuilder(url);
    }

    public static PostFormBuilder post(String url) {
        return new PostFormBuilder(url);
    }

    public static PostStringContentBuilder postStringContent(String url) {
        return new PostStringContentBuilder(url);
    }

    public static PostFileBuilder uploadFile(String url) {
        return new PostFileBuilder(url);
    }

    public static OtherRequestBuilder put(String url) {
        return new OtherRequestBuilder(url, RequestMethod.PUT);
    }

    public static OtherRequestBuilder head(String url) {
        return new OtherRequestBuilder(url, RequestMethod.HEAD);
    }

    public static OtherRequestBuilder delete(String url) {
        return new OtherRequestBuilder(url, RequestMethod.DELETE);
    }

    public static OtherRequestBuilder patch(String url) {
        return new OtherRequestBuilder(url, RequestMethod.PATCH);
    }

    public void cancelAll(){
        mOkHttpClient.dispatcher().cancelAll();
    }


}
