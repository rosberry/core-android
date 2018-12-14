package io.reactivex.disposables;

/**
 * @author Alexei Korshun on 30/11/2018.
 */
public class CompositeDisposable {

    private static final String EXCEPTION = "This is STUB";

    public void add(Disposable disposable) {
        throw new IllegalStateException(EXCEPTION);
    }

    public void dispose() {
        throw new IllegalStateException(EXCEPTION);
    }
}
