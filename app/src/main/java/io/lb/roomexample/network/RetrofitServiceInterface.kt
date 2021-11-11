package io.lb.roomexample.network

import io.lb.roomexample.model.RepositoriesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceInterface {
    @GET("repositories")
    fun getDataFromApi(@Query("q")query: String): Call<RepositoriesList>
}