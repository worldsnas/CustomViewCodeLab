package mosleh.saeed.customview.ViewModels


import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import io.reactivex.disposables.CompositeDisposable
import mosleh.saeed.states.MainActivityState


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val data: MutableLiveData<MainActivityState> = MutableLiveData()
    var mCompositeDisposable: CompositeDisposable? = null
    var state: MainActivityState? = null
    val dataObservable: LiveData<MainActivityState>
        get() = data
    init {
        mCompositeDisposable = CompositeDisposable()
    }
    override fun onCleared() {

        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable!!.clear()

        }
        super.onCleared()
    }
}
