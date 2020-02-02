package com.example.github.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.github.model.GitApiModel
import com.example.github.model.GitHubApiService
import com.example.github.database.GitHubDataBase
import com.example.github.util.SharedPrefrenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ListViewModel(application: Application) : BaseViewModel(application) {

    private val gitHubApiService = GitHubApiService()
    private val disposable = CompositeDisposable()
    val issues = MutableLiveData<List<GitApiModel>>()
    val loading = MutableLiveData<Boolean>()
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private var prefHelper = SharedPrefrenceHelper(getApplication())
    private var REFRESH_TIME = 24 * 60 * 60 * 1000 * 1000 * 1000L

    fun refresh() {
        var currentDate = df.parse(df.format(Calendar.getInstance().time))
        var previousDate = df.parse(prefHelper?.getUpdateTime()!!)
        if (currentDate.compareTo(previousDate) > 0) {
            fetchDataFromRemote()

        } else {
            fetchFromDatbase()
        }
    }

    private fun fetchDataFromRemote() {
        loading.value = true
        disposable.add(
            gitHubApiService.getIssues()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<GitApiModel>>() {
                    override fun onSuccess(listIssues: List<GitApiModel>) {
                        saveDataLocally(listIssues)

                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                    }

                })
        )
    }

    private fun saveDataLocally(listIssues: List<GitApiModel>) {
        val c = Calendar.getInstance().time
        val formattedDate = df.format(c)
        prefHelper.saveUpdateTime(formattedDate)
        launch {
            val dao = GitHubDataBase(getApplication()).issueDao()
            dao.deleteAll()
            val result = dao.insertAll(*listIssues.toTypedArray())
            var i = 0
            while (i < listIssues.size) {
                listIssues[i].number = result[i].toInt()
                i++
            }
            issuesRetrived(listIssues)

        }
    }

    private fun issuesRetrived(listIssues: List<GitApiModel>) {
        loading.value = false
        Collections.sort(listIssues, object : Comparator<GitApiModel> {
            override fun compare(o1: GitApiModel, o2: GitApiModel): Int {
                return o2.getDate()!!.compareTo(o1.getDate())
            }
        })
        issues.value = listIssues
    }

    private fun fetchFromDatbase() {
        loading.value = true
        launch {
            val issuesList = GitHubDataBase(getApplication()).issueDao().getAllIssues()
            issuesRetrived(issuesList)
        }
    }
}