package zikrulla.production.attendance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.StudentEntity
import zikrulla.production.attendance.repository.StudentsRepository

class StudentsViewModel(appDatabase: AppDatabase) : ViewModel() {
    private val repository = StudentsRepository(appDatabase)
    private var allStudents: LiveData<List<StudentEntity>>? = null
    private var attendance: LiveData<List<AttendanceEntity>>? = null
    private var dayStudentEntities: LiveData<List<DayStudentEntity>>? = null
    var dayStudents: List<DayStudentEntity>? = null

    fun insert(studentEntity: StudentEntity): Long = repository.insert(studentEntity)
    fun insertAll(students: List<StudentEntity>) = repository.insert(students)
    fun insertAttendance(attendanceEntity: AttendanceEntity): Long =
        repository.insertAttendance(attendanceEntity)
    fun insertDayStudent(dayStudents: List<DayStudentEntity>) = repository.insertDayStudents(dayStudents)

    fun update(studentEntity: StudentEntity) = repository.update(studentEntity)
    fun updateDayStudent(dayStudents: List<DayStudentEntity>) = repository.updateDayStudents(dayStudents)

    fun delete(studentEntity: StudentEntity) = repository.delete(studentEntity)

    fun fetchStudents(groupId: Long) {
        allStudents = repository.getStudents(groupId)

    }

    fun fetchAttendance(groupId: Long) {
        attendance = repository.getAttendance(groupId)
    }

    fun fetchDayStudents(attendanceId: Long) {
        dayStudentEntities = repository.getDayStudent(attendanceId)
    }

    fun getStudents() = allStudents
    fun getAttendance() = attendance
    fun getDayStudents() = dayStudentEntities
}