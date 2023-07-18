package zikrulla.production.attendance.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zikrulla.production.attendance.database.entity.DayStudentEntity

@Dao
interface DayStudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dayStudents: List<DayStudentEntity>)

    @Query("SELECT * FROM DayStudentEntity WHERE attendanceId = :attendanceId")
    fun getDayStudents(attendanceId: Long): LiveData<List<DayStudentEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(dayStudents: List<DayStudentEntity>)
}