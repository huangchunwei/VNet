package com.mevv.vnet.lib.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 14:04.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class JsonCallback<T> extends SimpleCallback<T> {

    private Gson mGson = new Gson();

    @Override
    public T parseResponse(int id, Response response) throws Exception {
        String result = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T t = mGson.fromJson(result, entityClass);
        return t;
    }
}
