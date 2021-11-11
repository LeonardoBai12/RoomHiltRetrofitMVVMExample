package io.lb.roomexample.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.roomexample.db.AppDao
import io.lb.roomexample.db.AppDataBase
import io.lb.roomexample.network.RetrofitServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val baseUrl = "https://api.github.com/search/"

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInterface {
        return retrofit.create(RetrofitServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun getAppDataBase(context: Application): AppDataBase {
        return AppDataBase.getAppDataBaseInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDataBase: AppDataBase): AppDao {
        return appDataBase.getAppDao()
    }
}