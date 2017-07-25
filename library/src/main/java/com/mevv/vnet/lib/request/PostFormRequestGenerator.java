package com.mevv.vnet.lib.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 14:17.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostFormRequestGenerator extends RequestGenerator {


    public PostFormRequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers) {
        super(id, url, params, headers);
    }

    @Override
    protected RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        addParams(builder);
        return builder.build();
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }

    /**
     * 添加参数
     *
     * @param builder {@link FormBody.Builder}
     */
    private void addParams(FormBody.Builder builder) {
        if (mParams != null) {
            for (String key : mParams.keySet()) {
                builder.add(key, mParams.get(key));
            }
        }
    }
}
