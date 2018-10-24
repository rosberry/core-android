package io.reactivex;

import io.reactivex.disposables.Disposable;

public class Single<T> {

    private Single() {
        throw new IllegalStateException("You can't create a instance of a stub");
    }

    public Single<T> doOnSuccess(CallListener<T> listener) {
        throw new RuntimeException("You can't invoke this method, because this class is stub.");
    }

    public Disposable subscribe(CallListener<T> successListener, CallListener<Throwable> errorListener) {
        throw new RuntimeException("You can't invoke this method, because this class is stub.");
    }

    public interface CallListener<T> {
        void invoke(T obj);
    }
}