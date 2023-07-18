package zikrulla.production.attendance.model

import zikrulla.production.attendance.database.entity.StudentEntity
import java.io.Serializable

data class Students(
    val students: List<StudentEntity>
) : Serializable
