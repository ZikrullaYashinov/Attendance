package zikrulla.production.attendance.model

import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.StudentEntity

data class StudentWithDayStudent(
    val studentEntity: StudentEntity,
    val dayStudentEntity: DayStudentEntity?
)
