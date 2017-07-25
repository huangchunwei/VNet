package com.mevv.vnet.lib.request;

import android.text.TextUtils;

import com.mevv.vnet.lib.utils.RequestMethod;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 15:39.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class OtherRequestGenerator extends RequestGenerator {

    /**
     * 默认请求类型
     */
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    /**
     * 请求体
     */
    private RequestBody mRequestBody;
    /**
     * 请求内容
     */
    private String mContent;
    /**
     * 请求方式
     */
    private String mMethod;

    public OtherRequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers, RequestBody requestBody, String content, String method) {
        super(id, url, params, headers);
        this.mRequestBody = requestBody;
        this.mContent = content;
        this.mMethod = method;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (mRequestBody == null && TextUtils.isEmpty(mContent) && HttpMethod.requiresRequestBody(mMethod)) {
            throw new IllegalArgumentException("requestBody and content cannot be null in method:" + mMethod);
        }
        if (mRequestBody == null && !TextUtils.isEmpty(mContent)) {
            mRequestBody = RequestBody.create(MEDIA_TYPE_PLAIN, mContent);
        }
        return mRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        if (mMethod.equals(RequestMethod.PUT)) {
            mBuilder.put(requestBody);
        } else if (mMethod.equals(RequestMethod.DELETE)) {
            if (requestBody == null) {
                mBuilder.delete();
            } else {
                mBuilder.delete(requestBody);
            }
        } else if (mMethod.equals(RequestMethod.HEAD)) {
            mBuilder.head();
        } else if (mMethod.equals(RequestMethod.PATCH)) {
            mBuilder.patch(requestBody);
        }
        return mBuilder.build();
    }
}
