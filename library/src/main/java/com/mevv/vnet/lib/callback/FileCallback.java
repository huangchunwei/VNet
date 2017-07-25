package com.mevv.vnet.lib.callback;


import com.mevv.vnet.lib.VNet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/7/25 14:04.
 * Copyright (c) 2017, Vv All Rights Reserved.
 * Description: TODO
 */


public abstract class FileCallback extends SimpleCallback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public abstract void progress(int id, float progress, long total);

    @Override
    public void inProgress(int id, float progress, long total) {
        progress(id, progress, total);
    }

    @Override
    public File parseResponse(int id, Response response) throws Exception {
        return saveFile(id, response);
    }

    private File saveFile(final int id, Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                VNet.getInstance().runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(id, finalSum * 100.0f / total, total);
                    }
                });
            }
            fos.flush();
            return file;
        } finally {
            try {
                response.body().close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
