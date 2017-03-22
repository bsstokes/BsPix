package com.bsstokes.bspix.api.unwrap;

import com.bsstokes.bspix.api.InstagramApi;

import rx.functions.Func1;

public class UnwrapInstagramResponse<T> implements Func1<InstagramApi.InstagramResponse<T>, T> {
    @Override public T call(InstagramApi.InstagramResponse<T> response) {
        return response.data;
    }
}
