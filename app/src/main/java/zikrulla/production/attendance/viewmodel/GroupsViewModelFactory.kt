package zikrulla.production.attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zikrulla.production.attendance.database.AppDatabase

class GroupsViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupsViewModel::class.java))
            return GroupsViewModel(appDatabase) as T
        return throw Exception("Error")
    }

}