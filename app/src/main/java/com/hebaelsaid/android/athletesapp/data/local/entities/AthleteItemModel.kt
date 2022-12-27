package com.hebaelsaid.android.athletesapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "athletes")
data class AthleteItemModel(
    @PrimaryKey(autoGenerate = true)
    val _id:Int,
    @ColumnInfo(name = "athlete_brief")
    val brief: String?,
    @ColumnInfo(name = "athlete_image")
    val image: String?,
    @ColumnInfo(name = "athlete_name")
    val name: String?
)
