package zikrulla.production.attendance.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.database.entity.StudentEntity

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(groupEntities: List<GroupEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(groupEntity: GroupEntity): Long

    @Update
    fun update(groupEntity: GroupEntity)

    @Delete
    fun delete(groupEntity: GroupEntity)

    @Query("SELECT * FROM group_entity")
    fun loadAllGroups() : LiveData<List<GroupEntity>>

    @Query("SELECT * FROM group_entity WHERE id = :groupId")
    fun getGroup(groupId: Long) : LiveData<List<GroupEntity>>

}