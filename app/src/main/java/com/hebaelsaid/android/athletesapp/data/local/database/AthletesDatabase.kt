package com.hebaelsaid.android.athletesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hebaelsaid.android.athletesapp.data.local.dao.AthletesDAO
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel


@Database(entities = [AthleteItemModel::class],
    version = 1 , exportSchema = false)
abstract class AthletesDatabase  : RoomDatabase() {
    abstract fun athletesDao(): AthletesDAO
}