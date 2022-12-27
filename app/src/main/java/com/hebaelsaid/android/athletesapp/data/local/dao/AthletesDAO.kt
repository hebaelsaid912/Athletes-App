package com.hebaelsaid.android.athletesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel

@Dao
interface AthletesDAO {
    @get:Query("SELECT * FROM athletes ORDER BY _id DESC")
    val getAllAthletesList:List<AthleteItemModel>
    @Query("SELECT * FROM athletes WHERE _id =:id")
    suspend fun getAthleteDetailsById(id:Int):AthleteItemModel
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAthleteItem(athleteItem: AthleteItemModel)
    @Query("DELETE  FROM athletes")
    suspend fun clearAthletesDatabase()
}