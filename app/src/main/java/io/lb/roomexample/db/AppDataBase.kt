package io.lb.roomexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.lb.roomexample.model.RepositoryData
import io.lb.roomexample.model.TypeConverterOwner

@Database(entities = [RepositoryData::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverterOwner::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getAppDao(): AppDao

    companion object {
        private var dbInstance: AppDataBase? = null

        fun getAppDataBaseInstance(context: Context): AppDataBase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "APP_DB"
                ).build()
            }
            return dbInstance!!
        }
    }
}