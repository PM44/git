package com.example.github.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.github.model.CommentsModel
import com.example.github.model.GitApiModel
import com.example.github.model.GitHubApiService
import com.example.github.database.GitHubDataBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    private val disposable = CompositeDisposable()
    val comments = MutableLiveData<List<CommentsModel>>()
    private val gitHubApiService = GitHubApiService()
    val selectedIssue = MutableLiveData<GitApiModel>()

    fun fetchDataFromRemote(number: Int?) {
        disposable.add(
            gitHubApiService.getComments(number.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CommentsModel>>() {
                    override fun onSuccess(commentsList: List<CommentsModel>) {
                        Toast.makeText(getApplication(), "Recieved", Toast.LENGTH_LONG)
                        comments.value = commentsList
                    }

                    override fun onError(e: Throwable) {

                    }

                })
        )
    }

    fun fetchDataFromDatabase(number: Int?) {
        launch {
            val dao = GitHubDataBase(getApplication()).issueDao()
            selectedIssue.value = dao.getIssue(number!!)

        }

    }
}