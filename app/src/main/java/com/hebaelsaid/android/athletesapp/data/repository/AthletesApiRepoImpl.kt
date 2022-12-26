package com.hebaelsaid.android.athletesapp.data.repository

import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel
import com.hebaelsaid.android.athletesapp.data.remote.AthletesApiInterface
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesApiRepo
import javax.inject.Inject

class AthletesApiRepoImpl @Inject constructor( private val apiInterface: AthletesApiInterface) : AthletesApiRepo {
    override suspend fun getAthletesList(): AthletesListResponseModel {
        return apiInterface.getAthletesList()
    }
}