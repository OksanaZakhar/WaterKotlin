package com.ksusha.vel.waterkotlin.localdata

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WaterDAO {

    @Query("SELECT * FROM water_table")
    fun getAllWaterList(): LiveData<List<WaterEntity>>

    @Query("SELECT * FROM water_table WHERE id==:id")
    fun getWaterEntity(id: Long): WaterEntity

    @Insert
    fun insertWaterEntity(waterEntity: WaterEntity)

    @Update
    fun updateWaterEntity(waterEntity: WaterEntity)

    @Delete
    fun deleteWaterEntity(waterEntity: WaterEntity)

    @Query("SELECT * FROM water_table WHERE idWater==:idWater")
    fun getFilterList(idWater: String?): LiveData<List<WaterEntity>>
}
