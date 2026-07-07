package com.rjnx.hyperengine.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rjnx.hyperengine.data.database.dao.GameDao
import com.rjnx.hyperengine.data.database.dao.PerformanceLogDao
import com.rjnx.hyperengine.data.database.dao.UserDao
import com.rjnx.hyperengine.data.database.entity.GameEntity
import com.rjnx.hyperengine.data.database.entity.PerformanceLogEntity
import com.rjnx.hyperengine.data.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        PerformanceLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun performanceLogDao(): PerformanceLogDao
    
    companion object {
        const val DATABASE_NAME = "rjnx_hyper_engine_db"
    }
}
