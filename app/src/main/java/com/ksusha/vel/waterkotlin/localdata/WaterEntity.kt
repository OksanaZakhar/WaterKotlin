package com.ksusha.vel.waterkotlin.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_table")
class WaterEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var idWater: String,
    var img: String,
    var description: String,
    var prise: Int,
    var count: Int,
    var startCount: Int,
    var maskClickable: Boolean,
    var maskVisible: Int

)
