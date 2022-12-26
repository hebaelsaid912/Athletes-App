package com.hebaelsaid.android.athletesapp.domain.repository

import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel

interface AthletesApiRepo {
    suspend fun getAthletesList(): AthletesListResponseModel
}