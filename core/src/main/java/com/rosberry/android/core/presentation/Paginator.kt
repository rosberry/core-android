package com.rosberry.android.core.presentation

import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * @author Alexei Korshun on 28/09/2018.
 */
class Paginator<T>(
        private val requestFactory: (Int) -> Single<List<T>>,
        private val viewController: ViewController<T>
) {

    interface ViewController<T> {
        fun showEmptyProgress(show: Boolean)
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyView(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList())
        fun showErrorMessage(error: Throwable)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
    }

    private val startOffset = 0

    private var currentState: State<T> = Empty()
    private var currentOffset = 0
    private val currentData = mutableListOf<T>()
    private var disposable: Disposable? = null

    fun restart() {
        currentState.restart()
    }

    fun refresh() {
        currentState.refresh()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState.release()
    }

    private fun loadData(offset: Int) {
        disposable?.dispose()
        disposable = requestFactory(offset)
            .doOnSuccess { currentOffset += it.size }
            .subscribe(
                    { currentState.newData(it) },
                    { currentState.fail(it) }
            )
    }

    private interface State<T> {
        fun restart() {}
        fun refresh() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private inner class Empty : State<T> {

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class EmptyProgress : State<T> {

        override fun restart() {
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.clear()
                currentData.addAll(data)
                viewController.showData(true, currentData)
                viewController.showEmptyProgress(false)
            } else {
                currentState = EmptyData()
                viewController.showEmptyProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = EmptyError()
            viewController.showEmptyProgress(false)
            viewController.showEmptyError(true, error)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class EmptyError : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class EmptyData : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class Data : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun loadNewPage() {
            currentState = PageProgress()
            viewController.showPageProgress(true)
            loadData(currentOffset)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class Refresh : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.clear()
                currentData.addAll(data)
                viewController.showRefreshProgress(false)
                viewController.showData(true, currentData)
            } else {
                currentState = EmptyData()
                currentData.clear()
                viewController.showData(false)
                viewController.showRefreshProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showRefreshProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class PageProgress : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showPageProgress(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.addAll(data)
                viewController.showPageProgress(false)
                viewController.showData(true, currentData)
            } else {
                currentState = AllData()
                viewController.showPageProgress(false)
            }
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showPageProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class AllData : State<T> {

        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showEmptyProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            currentOffset = startOffset
            loadData(currentOffset)
        }

        override fun release() {
            currentState = Released()
            disposable?.dispose()
        }
    }

    private inner class Released : State<T>
}
