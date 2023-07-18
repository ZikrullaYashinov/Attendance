package zikrulla.production.attendance.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "group_entity")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String? = null,
    var description: String? = null,
    var size: Int? = null
) : Serializable