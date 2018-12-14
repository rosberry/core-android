package com.rosberry.android.core.presentation

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Alexei Korshun on 30/11/2018.
 */
open class AppPresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    protected fun Disposable.connect(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun disposeAll() {
        compositeDisposable.dispose()
    }
}