package com.hebaelsaid.android.athletesapp.ui.di

import com.hebaelsaid.android.athletesapp.data.remote.AthletesApiInterface
import com.hebaelsaid.android.athletesapp.data.repository.AthletesApiRepoImpl
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesApiRepo
import com.hebaelsaid.android.athletesapp.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoffeeApiInterface(): AthletesApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AthletesApiInterface::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataPort{
    @Binds
    @Singleton
    abstract fun bindCoffeeMenuRepo( impl: AthletesApiRepoImpl):AthletesApiRepo
}