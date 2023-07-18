package zikrulla.production.attendance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.repository.GroupRepository

class GroupsViewModel(appDatabase: AppDatabase) : ViewModel() {

    private var allGroups: LiveData<List<GroupEntity>>
    private var repository = GroupRepository(appDatabase)

    init {
        allGroups = repository.getGroups()
    }

    fun insertGroup(entity: GroupEntity): Long {
        return repository.insertGroup(entity)
    }

    fun update(entity: GroupEntity) {
        repository.update(entity)
    }

    fun delete(entity: GroupEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(entity)
        }
    }

    fun getGroups() = allGroups

}