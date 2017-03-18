package com.bsstokes.bspix.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    @NonNull private final String userAgent;

    public UserAgentInterceptor(@NonNull String userAgent) {
        this.userAgent = userAgent;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
                .header("User-Agent", userAgent)
                .build();
        return chain.proceed(request);
    }
}
