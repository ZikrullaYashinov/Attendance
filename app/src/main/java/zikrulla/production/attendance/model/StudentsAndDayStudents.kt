package zikrulla.production.attendance.model

import androidx.room.Embedded
import androidx.room.Relation
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.StudentEntity


data class StudentsAndDayStudents(
    @Embedded
    val dayStudentEntity: DayStudentEntity,
    @Relation(parentColumn = "studentId", entityColumn = "id")
    val studentEntity: StudentEntity
)