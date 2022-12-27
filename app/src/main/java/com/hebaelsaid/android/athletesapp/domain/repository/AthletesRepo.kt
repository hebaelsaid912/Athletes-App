package com.hebaelsaid.android.athletesapp.domain.repository

import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel

interface AthletesRepo {
    val getAllAthletesList:List<AthleteItemModel>
    suspend fun getAthleteDetailsById(id:Int):AthleteItemModel
    suspend fun insertAthleteItem(athleteItem: AthleteItemModel)
    suspend fun clearAthletesDatabase()
}