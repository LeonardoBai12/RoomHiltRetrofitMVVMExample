package io.lb.roomexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.roomexample.model.RepositoryData
import io.lb.roomexample.network.RetrofitRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RetrofitRepository
): ViewModel() {
    fun loadRepositories(): LiveData<List<RepositoryData>> {
        return repository.getAllRecords()
    }

    fun makeApiCall() {
        repository.makeApiCall("ny")
    }
}