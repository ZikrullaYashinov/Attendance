package zikrulla.production.attendance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zikrulla.production.attendance.database.dao.AttendanceDao
import zikrulla.production.attendance.database.dao.DayStudentDao
import zikrulla.production.attendance.database.dao.GroupDao
import zikrulla.production.attendance.database.dao.StudentDao
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.database.entity.StudentEntity

@Database(
    entities = [GroupEntity::class, StudentEntity::class, AttendanceEntity::class, DayStudentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun studentDao(): StudentDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun dayStudentDao(): DayStudentDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            return INSTANCE!!
        }
    }
}