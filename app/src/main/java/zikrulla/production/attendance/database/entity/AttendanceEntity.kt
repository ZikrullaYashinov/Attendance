package zikrulla.production.attendance.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var groupId: Long,
    var description: String?,
    var date: Long
) : Serializable
