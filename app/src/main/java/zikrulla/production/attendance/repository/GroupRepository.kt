package zikrulla.production.attendance.repository

import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.GroupEntity

class GroupRepository(appDatabase: AppDatabase) {
    private var groupDao = appDatabase.groupDao()

    fun insertGroup(groupEntity: GroupEntity) = groupDao.insert(groupEntity)

    fun update(groupEntity: GroupEntity) = groupDao.update(groupEntity)

    fun delete(groupEntity: GroupEntity) = groupDao.delete(groupEntity)

    fun getGroups(id: Long) = groupDao.getGroup(id)
    fun getGroups() = groupDao.loadAllGroups()



}