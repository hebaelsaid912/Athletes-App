package com.hebaelsaid.android.athletesapp.data.remote

import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel
import retrofit2.http.GET

interface AthletesApiInterface {
    @GET("f227855df4d197d3737c304e9377c4d4/raw/ece2a30b16a77ee58091886bf6d3445946e10a23/")
    suspend fun getAthletesList(): AthletesListResponseModel
}