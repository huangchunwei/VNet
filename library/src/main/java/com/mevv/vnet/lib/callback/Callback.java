package com.mevv.vnet.lib.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 10:27.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public interface Callback<T> {
    /**
     * 开始请求 UI线程
     *
     * @param id      请求id
     * @param request {@link Request}
     */
    void onStart(int id, Request request);

    /**
     * 请求响应 工作线程
     *
     * @param id       请求id
     * @param response {@link Response}
     */
    void onResponse(int id, Response response);

    /**
     * 解析响应内容 工作线程
     *
     * @param id       请求id
     * @param response {@link Response}
     * @return {@link T}
     * @throws Exception
     */
    T parseResponse(int id, Response response) throws Exception;

    /**
     * 响应解析成功 UI线程
     *
     * @param id     请求id
     * @param result {@link T}
     */
    void onSuccess(int id, T result);

    /**
     * 请求失败(网络,解析,请求等异常) UI线程
     *
     * @param id   请求di
     * @param call {@link Call}
     * @param t    {@link Throwable}
     */
    void onFailed(int id, Call call, Throwable t);

    /**
     * 进度回调 UI线程
     *
     * @param id       请求id
     * @param progress 当前进度
     * @param total    总大小
     */
    void inProgress(int id, float progress, long total);

    /**
     * 请求结束 UI线程
     *
     * @param id 请求id
     */
    void onEnd(int id);
}

