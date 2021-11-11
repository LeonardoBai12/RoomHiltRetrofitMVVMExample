package io.lb.roomexample.network

import androidx.lifecycle.LiveData
import io.lb.roomexample.db.AppDao
import io.lb.roomexample.model.RepositoriesList
import io.lb.roomexample.model.RepositoryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val retrofitServiceInterface: RetrofitServiceInterface,
    private val appDao: AppDao
){
    fun getAllRecords(): LiveData<List<RepositoryData>> {
        return appDao.getAllRecords()
    }

    fun insertRecords(repositoryData: RepositoryData) {
        return appDao.insertRecords(repositoryData)
    }

    fun deleteRecords() {
        appDao.deleteAllRecords()
    }

    fun makeApiCall(query: String) {
        val call = retrofitServiceInterface.getDataFromApi(query)
        call.enqueue(object : Callback<RepositoriesList>{
            override fun onResponse(
                call: Call<RepositoriesList>,
                response: Response<RepositoriesList>
            ) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        deleteRecords()
                        response.body()?.items?.forEach {
                            insertRecords(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RepositoriesList>, t: Throwable) {
                Timber.e(call.toString())
            }
        })
    }
}