package zikrulla.production.attendance.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("studentId")
        ),
        ForeignKey(
            entity = AttendanceEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("attendanceId")
        )
    ]
)
data class DayStudentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var studentId: Long?,
    var attendanceId: Long?,
    var point: Int?,
    var isHas: Boolean?
)
