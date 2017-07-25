package com.mevv.vnet.lib.request;


import com.mevv.vnet.lib.Api;
import com.mevv.vnet.lib.builder.PostFileBuilder;
import com.mevv.vnet.lib.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 15:20.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostFileRequestGenerator extends RequestGenerator {

    private List<PostFileBuilder.FileInput> mFileInputs;


    public PostFileRequestGenerator(int id, String url, Map<String, String> params, Map<String, String> headers, List<PostFileBuilder.FileInput> fileList) {
        super(id, url, params, headers);
        this.mFileInputs = fileList;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (mFileInputs == null || mFileInputs.isEmpty()) {//没有文件,只是普通的请求
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        } else {//有文件,混合类型post请求
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder);
            for (int i = 0; i < mFileInputs.size(); i++) {
                PostFileBuilder.FileInput fileInput = mFileInputs.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.getFilename())), fileInput.getFile());
                builder.addFormDataPart(fileInput.getName(), fileInput.getFilename(), fileBody);
            }
            return builder.build();
        }
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                Api.getInstance().runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(mId, bytesWritten * 1.0f / contentLength, contentLength);
                    }
                });
            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }

    /**
     * 猜测文件的类型
     *
     * @param path 文件路径
     * @return 猜测的类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 添加参数
     *
     * @param builder {@link MultipartBody.Builder}
     */
    private void addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                builder.addFormDataPart(key, mParams.get(key));
//                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""), RequestBody.create(null, mParams.get(key)));
            }
        }
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
