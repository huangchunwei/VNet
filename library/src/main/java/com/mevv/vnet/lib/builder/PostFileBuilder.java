package com.mevv.vnet.lib.builder;

import android.support.v4.util.ArrayMap;

import com.mevv.vnet.lib.request.PostFileRequestGenerator;
import com.mevv.vnet.lib.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Project name:WalkingNote
 * Author:VV
 * Created on 2017/7/25 15:07.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public class PostFileBuilder extends AbsBuilder<PostFileBuilder> {

    private List<FileInput> mFileInputs = new ArrayList<>();

    public PostFileBuilder(String url) {
        super(url);
    }

    public PostFileBuilder addParam(String key, String value) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.put(key, value);
        return this;
    }

    public PostFileBuilder addParams(Map<String, String> params) {
        if (mParams == null) {
            mParams = new ArrayMap<String, String>();
        }
        mParams.putAll(params);
        return this;
    }

    public PostFileBuilder addFile(String name, String filename, File file) {
        mFileInputs.add(new FileInput(name, filename, file));
        return this;
    }

    public PostFileBuilder addFiles(String name, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.mFileInputs.add(new FileInput(name, filename, files.get(filename)));
        }
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequestGenerator(mId, mUrl, mParams, mHeaders, mFileInputs).build();
    }

    public static class FileInput {
        private String mName;
        private String mFilename;
        private File mFile;

        public FileInput(String name, String filename, File file) {
            this.mName = name;
            this.mFilename = filename;
            this.mFile = file;
        }

        public String getName() {
            return mName;
        }

        public String getFilename() {
            return mFilename;
        }

        public File getFile() {
            return mFile;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "mName='" + mName + '\'' +
                    ", mFilename='" + mFilename + '\'' +
                    ", mFile=" + mFile +
                    '}';
        }
    }
}
