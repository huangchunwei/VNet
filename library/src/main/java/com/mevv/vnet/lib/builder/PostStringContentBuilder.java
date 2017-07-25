package com.mevv.vnet.lib.builder;


import com.mevv.vnet.lib.request.PostStringContentRequestGenerator;
import com.mevv.vnet.lib.request.RequestCall;

import okhttp3.MediaType;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 14:48.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostStringContentBuilder extends AbsBuilder<PostStringContentBuilder> {

    private String mContent;
    private MediaType mMediaType;

    public PostStringContentBuilder(String url) {
        super(url);
    }

    public PostStringContentBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public PostStringContentBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringContentRequestGenerator(mId, mUrl, mParams, mHeaders, mContent, mMediaType).build();
    }
}
