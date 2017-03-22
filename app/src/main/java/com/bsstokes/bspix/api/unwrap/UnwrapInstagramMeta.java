package com.bsstokes.bspix.api.unwrap;

import com.bsstokes.bspix.api.InstagramApi;

import rx.functions.Func1;

public class UnwrapInstagramMeta<T> implements Func1<InstagramApi.InstagramResponse<T>, InstagramApi.Meta> {
    @Override public InstagramApi.Meta call(InstagramApi.InstagramResponse<T> response) {
        return response.meta;
    }
}
