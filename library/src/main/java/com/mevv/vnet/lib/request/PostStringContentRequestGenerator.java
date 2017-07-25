package com.mevv.vnet.lib.request;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 14:54.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostStringContentRequestGenerator extends RequestGenerator {

    /**
     * 默认的内容类型
     */
    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    /**
     * 内容
     */
    private String mContent;
    /**
     * 内容的类型
     */
    private MediaType mMediaType;


    public PostStringContentRequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers, String content, MediaType mediaType) {
        super(id, url, params, headers);
        this.mContent = content;
        this.mMediaType = mediaType;
        if (this.mContent == null) {
            throw new IllegalArgumentException("the content cannot be null !");
        }
        if (this.mMediaType == null) {
            this.mMediaType = MEDIA_TYPE_PLAIN;
        }

    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mContent);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}
