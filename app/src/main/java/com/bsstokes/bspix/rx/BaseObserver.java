package com.bsstokes.bspix.rx;

import android.util.Log;

import rx.Observer;

public abstract class BaseObserver<T> implements Observer<T> {

    @Override public void onCompleted() {
        // Do nothing
    }

    @Override public void onError(Throwable e) {
        Log.e("BaseObserver", "onError", e);
    }
}
