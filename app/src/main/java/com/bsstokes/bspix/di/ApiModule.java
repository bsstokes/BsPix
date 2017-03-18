package com.bsstokes.bspix.di;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.BuildConfig;
import com.bsstokes.bspix.R;
import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.UserAgentInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
class ApiModule {
    static final String API_CLIENT = "apiClient";
    static final String BASE_API_CLIENT = "baseApiClient";
    private static final String BASE_CLIENT = "baseClient";
    private static final String USER_AGENT = "userAgent";

    @Provides
    @Singleton
    InstagramApi provideInstagramApi(@Named(API_CLIENT) OkHttpClient okHttpClient) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InstagramApi.BASE_API_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        return retrofit.create(InstagramApi.class);
    }

    @Provides
    @Singleton
    @Named(USER_AGENT)
    String provideUserAgent(BsPixApplication application) {
        final String appName = application.getString(R.string.app_name);
        return appName + "/" + BuildConfig.VERSION_NAME;
    }

    @Provides
    @Singleton
    @Named(BASE_CLIENT)
    OkHttpClient provideBaseOkHttpClient(@Named(USER_AGENT) String userAgent) {
        return new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor(userAgent))
                .build();
    }

    @Provides
    @Singleton
    @Named(ApiModule.BASE_API_CLIENT)
    OkHttpClient provideBaseApiOkHttpClient(@Named(BASE_CLIENT) OkHttpClient baseOkHttpClient) {
        return baseOkHttpClient.newBuilder()
                .build();
    }
}
