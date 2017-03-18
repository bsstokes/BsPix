package com.bsstokes.bspix.api;

import com.bsstokes.bspix.auth.Account;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {

    private final Account account;

    public AccessTokenInterceptor(Account account) {
        this.account = account;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        final String accessToken = account.getAccessToken();

        final Request request = chain.request();
        final HttpUrl url = request.url().newBuilder()
                .addQueryParameter("access_token", accessToken)
                .build();
        final Request newRequest = request.newBuilder()
                .url(url)
                .build();
        return chain.proceed(newRequest);
    }
}
