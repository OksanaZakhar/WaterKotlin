package com.ksusha.vel.waterkotlin.localdata.repository

import androidx.lifecycle.LiveData
import com.ksusha.vel.waterkotlin.localdata.WaterDAO
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class WaterRepositoryLocalData(private val waterDAO: WaterDAO) {

    fun getWaterList(): LiveData<List<WaterEntity>> {
        return waterDAO.getAllWaterList()
    }


    fun getWaterEntity(id: Long): WaterEntity {
        return waterDAO.getWaterEntity(id)
    }

    fun insertWaterEntity(waterEntity: WaterEntity) {
        Observable.create<Any> {
            waterDAO.insertWaterEntity(
                waterEntity
            )
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun updateWaterEntity(waterEntity: WaterEntity) {
        Observable.create<Any> {
            waterDAO.updateWaterEntity(
                waterEntity
            )
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteWaterEntity(waterEntity: WaterEntity) {
        Observable.create<Any> {
            waterDAO.deleteWaterEntity(
                waterEntity
            )
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getFilterList(idWater: String?): LiveData<List<WaterEntity>> {
        return waterDAO.getFilterList(idWater)
    }

    fun addWaterEntityDB(waterEntity: WaterEntity) {
        waterDAO.insertWaterEntity(waterEntity)
    }

}
