package com.stormagain.easycache;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 37X21=777 on 15/9/24.
 */
public final class EasyCacheManager {

    private static volatile EasyCacheManager manager;
    private Context mContext;
    private CacheProxy cacheProxy;
    private Interceptor interceptor;

    public static EasyCacheManager getInstance() {
        if (manager == null) {
            synchronized (EasyCacheManager.class) {
                if (manager == null) {
                    manager = new EasyCacheManager();
                }
            }
        }
        return manager;
    }

    private EasyCacheManager() {
        super();
        cacheProxy = new CacheProxy();
        interceptor = new DefaultInterceptor();
    }

    public void setup(Context context) {
        if (mContext == null && context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public Context getContext() {
        if (mContext == null) {
            try {
                Class<?> clazz = Class.forName("android.app.ActivityThread");
                Method method = clazz.getDeclaredMethod("currentApplication", new Class[0]);
                mContext = (Context) method.invoke(null, new Object[0]);
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mContext;
    }

    public <T> T create(Class<T> clazz) {
        return cacheProxy.create(clazz);
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }
}
