package com.ksusha.vel.waterkotlin.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WaterEntity::class], version = 1, exportSchema = false)
abstract class WaterDataBase : RoomDatabase() {

    abstract fun waterDAO(): WaterDAO

    companion object {
        @Volatile
        private var INSTANCE: WaterDataBase? = null

        fun getInstance(context: Context): WaterDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterDataBase::class.java, "water_basket_table"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}
