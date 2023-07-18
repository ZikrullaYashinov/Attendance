package zikrulla.production.attendance.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.database.entity.StudentEntity
import zikrulla.production.attendance.databinding.FragmentImportBinding
import zikrulla.production.attendance.viewmodel.GroupsViewModel
import zikrulla.production.attendance.viewmodel.GroupsViewModelFactory
import zikrulla.production.attendance.viewmodel.StudentsViewModel
import zikrulla.production.attendance.viewmodel.StudentsViewModelFactory
import kotlin.coroutines.CoroutineContext

class ImportFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentImportBinding
    private lateinit var viewModel: GroupsViewModel
    private lateinit var viewModelStudents: StudentsViewModel
    private val TAG = "@@@@"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImportBinding.inflate(inflater, container, false)

        load()
        click()

        return binding.root
    }

    private fun load() {
        viewModel = ViewModelProvider(
            this,
            GroupsViewModelFactory(AppDatabase.getInstance(requireContext()))
        )[GroupsViewModel::class.java]
        viewModelStudents = ViewModelProvider(
            this,
            StudentsViewModelFactory(AppDatabase.getInstance(requireContext()))
        )[StudentsViewModel::class.java]

    }

    private fun click() {
        binding.apply {
            back.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            save.setOnClickListener {
                launch {
                    val text = editor.text.toString()
                    if (text.isNotEmpty()) {
                        var isTrue = true
                        var studentList = emptyList<StudentEntity>()
                        try {
                            studentList = split(text)
                        } catch (e: Exception) {
                            isTrue = false
                        }
                        if (isTrue) {
                            val id = viewModel.insertGroup(GroupEntity(0, "", null, 0))
                            viewModel.update(GroupEntity(id, "Guruh $id", null, studentList.size))
                            saveData(studentList, id)
                            Navigation.findNavController(binding.root).popBackStack()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Iltimos kiruvchi ma'lumotlarni tekshiring",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else
                        Toast.makeText(requireContext(), "Matin bo'sh", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveData(studentList: List<StudentEntity>, id: Long) {
        // TODO: data save in firebase and room db
        for (i in studentList.indices) {
            studentList[i].groupId = id
        }
        viewModelStudents.insertAll(studentList)
    }

    private fun split(text: String): List<StudentEntity> {
        val students = ArrayList<StudentEntity>()
        val line = text.split("\n")
        line.forEach {
            val words = it.split("  ")
            val number = words[0]
            val name = words[1]
            val student = StudentEntity(0, 0, number.toInt(), name)
            students.add(student)
        }
        return students
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}