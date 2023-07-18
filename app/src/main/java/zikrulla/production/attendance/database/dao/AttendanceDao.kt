package zikrulla.production.attendance.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zikrulla.production.attendance.database.entity.AttendanceEntity

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(attendanceEntity: AttendanceEntity): Long

    @Query("SELECT * FROM AttendanceEntity WHERE groupId = :groupId")
    fun getAttendance(groupId: Long): LiveData<List<AttendanceEntity>>
}