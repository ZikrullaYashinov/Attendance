package zikrulla.production.attendance.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var groupId: Long? = null,
    var number: Int? = null,
    var name: String? = null
) : Serializable