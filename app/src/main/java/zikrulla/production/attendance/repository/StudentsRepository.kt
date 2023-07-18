package zikrulla.production.attendance.repository

import androidx.lifecycle.LiveData
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.dao.StudentDao
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.StudentEntity

class StudentsRepository(
    appDatabase: AppDatabase
) {
    var studentDao: StudentDao = appDatabase.studentDao()
    private var attendanceDao = appDatabase.attendanceDao()
    private var dayStudentDao = appDatabase.dayStudentDao()

    fun insert(studentEntity: StudentEntity): Long = studentDao.insert(studentEntity)
    fun insert(students: List<StudentEntity>) = studentDao.insertAll(students)
    fun insertAttendance(attendance: AttendanceEntity) = attendanceDao.insert(attendance)
    fun insertDayStudents(dayStudents: List<DayStudentEntity>) =
        dayStudentDao.insertAll(dayStudents)

    fun update(studentEntity: StudentEntity) = studentDao.update(studentEntity)
    fun updateDayStudents(dayStudents: List<DayStudentEntity>) = dayStudentDao.updateAll(dayStudents)

    fun delete(studentEntity: StudentEntity) = studentDao.delete(studentEntity)

    fun getStudents(groupId: Long): LiveData<List<StudentEntity>> =
        studentDao.getStudent(groupId)

    fun getAttendance(groupId: Long) = attendanceDao.getAttendance(groupId)
    fun getDayStudent(attendanceId: Long) = dayStudentDao.getDayStudents(attendanceId)
}