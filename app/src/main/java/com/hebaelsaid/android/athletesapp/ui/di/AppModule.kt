package com.hebaelsaid.android.athletesapp.ui.di

import android.content.Context
import androidx.room.Room
import com.hebaelsaid.android.athletesapp.data.local.database.AthletesDatabase
import com.hebaelsaid.android.athletesapp.data.remote.AthletesApiInterface
import com.hebaelsaid.android.athletesapp.data.repository.AthletesApiRepoImpl
import com.hebaelsaid.android.athletesapp.data.repository.AthletesRepoImpl
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesApiRepo
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesRepo
import com.hebaelsaid.android.athletesapp.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAthletesApiInterface(): AthletesApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AthletesApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun provideAthletesDatabase(@ApplicationContext appContext: Context): AthletesDatabase {
        return Room.databaseBuilder(
            appContext, AthletesDatabase::class.java,
            "athletes.db"
        ).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataPort{
    @Binds
    @Singleton
    abstract fun bindAthletesApiRepo( impl: AthletesApiRepoImpl):AthletesApiRepo
    @Binds
    @Singleton
    abstract fun bindAthletesRepo( impl: AthletesRepoImpl):AthletesRepo
}