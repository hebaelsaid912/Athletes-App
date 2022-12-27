package com.hebaelsaid.android.athletesapp.data.repository

import com.hebaelsaid.android.athletesapp.data.local.database.AthletesDatabase
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesRepo
import javax.inject.Inject

class AthletesRepoImpl @Inject constructor( private val database: AthletesDatabase) : AthletesRepo {
    override suspend fun getAllAthletesList(): List<AthleteItemModel> {
        return database.athletesDao().getAllAthletesList
    }

    override suspend fun getAthleteDetailsById(id: Int): AthleteItemModel {
        return database.athletesDao().getAthleteDetailsById(id)
    }

    override suspend fun insertAthleteItem(athleteItem: AthleteItemModel) {
        return database.athletesDao().insertAthleteItem(athleteItem)
    }

    override suspend fun clearAthletesDatabase() {
        return database.athletesDao().clearAthletesDatabase()
    }

}