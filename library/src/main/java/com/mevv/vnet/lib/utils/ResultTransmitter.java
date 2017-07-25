package com.mevv.vnet.lib.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Project name:VNet
 * Author:VV
 * Created on 2017/3/18 22:32.
 * Copyright (c) 2017, vvismile@163.com All Rights Reserved.
 * Description: 安全的结果发送器
 */

public class ResultTransmitter {

    private static final String TAG = ResultTransmitter.class.getSimpleName();
    private static ResultTransmitter sTransmitter = searchPlatform();

    public static ResultTransmitter get(){
        Log.d(TAG, sTransmitter.getClass().toString());
        return sTransmitter;
    }

    /**
     * 搜索是否Android平台
     *
     * @return {@link ResultTransmitter}
     */
    private static ResultTransmitter searchPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new SafeTransmitter();
            }
        } catch (ClassNotFoundException ignored) {
        }
        return new ResultTransmitter();
    }

    public void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    /**
     * 默认返回一个无边界的缓存功能的线程池对象
     *
     * @return {@link Executor}
     */
    protected Executor getExecutor() {
        return Executors.newCachedThreadPool();
    }

    static class SafeTransmitter extends ResultTransmitter {

        @Override
        protected Executor getExecutor() {
            return new MainThreadExecutor();
        }

        /**
         * 自带Handler的线程执行器
         */
        static class MainThreadExecutor implements Executor {

            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }
    }

}
