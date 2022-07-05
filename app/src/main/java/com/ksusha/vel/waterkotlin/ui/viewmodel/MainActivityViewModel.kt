package com.ksusha.vel.waterkotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ksusha.vel.waterkotlin.localdata.WaterDataBase
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.localdata.repository.WaterRepositoryLocalData

class MainActivityViewModel(application: Application) :
    AndroidViewModel(application) {

    private var waterRepositoryLocalData: WaterRepositoryLocalData

    init {
        val waterDAO = WaterDataBase.getInstance(application).waterDAO()
        waterRepositoryLocalData = WaterRepositoryLocalData(waterDAO)
    }

    fun getWaterList(): LiveData<List<WaterEntity>> {
        return waterRepositoryLocalData.getWaterList()
    }

    fun getWaterEntity(id: Long): WaterEntity {
        return waterRepositoryLocalData.getWaterEntity(id)
    }

    fun insertWaterEntity(waterEntity: WaterEntity) {
        waterRepositoryLocalData.insertWaterEntity(waterEntity)
    }

    fun updateWaterEntity(waterEntity: WaterEntity) {
        waterRepositoryLocalData.updateWaterEntity(waterEntity)
    }

    fun deleteWaterEntity(waterEntity: WaterEntity) {
        waterRepositoryLocalData.deleteWaterEntity(waterEntity)
    }

    fun getFilterList(idWater: String?): LiveData<List<WaterEntity>> {
        return waterRepositoryLocalData.getFilterList(idWater)
    }

    fun addWaterEntityDB(waterEntity: WaterEntity) {
        waterRepositoryLocalData.addWaterEntityDB(waterEntity)
    }

}
