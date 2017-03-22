package com.bsstokes.bspix.api.unwrap;

import retrofit2.Response;
import rx.functions.Func1;

public class UnwrapResponse<T> implements Func1<retrofit2.Response<T>, T> {
    @Override public T call(Response<T> response) {
        return response.body();
    }
}
