package com.mevv.vnet.lib.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 14:07.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class SimpleCallback<T> implements Callback<T> {

    private Gson mGson = new Gson();

    public abstract void start(int id);

    public abstract void success(int id, T data);

    public abstract void failed(int id, Call call, Throwable t);

    public abstract void end(int id);


    @Override
    public void onStart(int id, Request request) {
        start(id);
    }

    @Override
    public void onResponse(int id, Response response) {

    }

    @Override
    public T parseResponse(int id, Response response) throws Exception {
        String result = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) result;
        }
        T t = mGson.fromJson(result, entityClass);
        return t;
    }

    @Override
    public void onSuccess(int id, T result) {
        success(id, result);
    }

    @Override
    public void onFailed(int id, Call call, Throwable t) {
        failed(id, call, t);
    }

    @Override
    public void inProgress(int id, float progress, long total) {

    }

    @Override
    public void onEnd(int id) {
        end(id);
    }
}
