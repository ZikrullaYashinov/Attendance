package zikrulla.production.attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.GroupEntity

class StudentsViewModelFactory(
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentsViewModel::class.java))
            return StudentsViewModel(appDatabase) as T
        return throw Exception("Error")
    }

}