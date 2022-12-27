package com.hebaelsaid.android.athletesapp.domain.repository

import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel

interface AthletesRepo {
    suspend fun getAllAthletesList():List<AthleteItemModel>
    suspend fun getAthleteDetailsById(id:Int):AthleteItemModel
    suspend fun insertAthleteItem(athleteItem: AthleteItemModel)
    suspend fun clearAthletesDatabase()
}