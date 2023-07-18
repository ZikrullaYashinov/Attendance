package zikrulla.production.attendance.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zikrulla.production.attendance.database.entity.StudentEntity

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(studentEntity: StudentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students: List<StudentEntity>)

    @Update
    fun update(studentEntity: StudentEntity)

    @Delete
    fun delete(studentEntity: StudentEntity)

    @Query("SELECT * FROM StudentEntity WHERE groupId = :groupId")
    fun getStudent(groupId: Long) : LiveData<List<StudentEntity>>

}