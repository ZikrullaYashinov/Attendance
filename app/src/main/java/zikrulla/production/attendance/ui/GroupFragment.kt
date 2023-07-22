package zikrulla.production.attendance.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import zikrulla.production.attendance.adapter.StudentWithDayStudentAdapter
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.database.entity.DayStudentEntity
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.database.entity.StudentEntity
import zikrulla.production.attendance.databinding.FragmentGroupBinding
import zikrulla.production.attendance.model.StudentWithDayStudent
import zikrulla.production.attendance.model.Students
import zikrulla.production.attendance.utils.PopupMenuService
import zikrulla.production.attendance.viewmodel.StudentsViewModel
import zikrulla.production.attendance.viewmodel.StudentsViewModelFactory
import java.util.Date

class GroupFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            groupEntity = arguments?.getSerializable("group") as GroupEntity
            studentEntities = (arguments?.getSerializable("students") as Students).students
            if (arguments?.getSerializable("attendance") != null) {
                attendance = arguments?.getSerializable("attendance") as AttendanceEntity
                isUpdated = true
            }
        }
    }

    private lateinit var binding: FragmentGroupBinding
    private lateinit var viewModel: StudentsViewModel
    private lateinit var adapter: StudentWithDayStudentAdapter
    private var groupEntity: GroupEntity? = null
    private var attendance: AttendanceEntity? = null
    private var studentEntities: List<StudentEntity>? = null
    private var popupMenuService: PopupMenuService? = null
    private var isUpdated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(inflater, container, false)

        load()
        click()
        change()

        return binding.root
    }

    private fun load() {
        viewModel = ViewModelProvider(
            this,
            StudentsViewModelFactory(AppDatabase.getInstance(requireContext()))
        )[StudentsViewModel::class.java]

        if (isUpdated)
            viewModel.fetchDayStudents(attendance?.id!!)

        adapter = StudentWithDayStudentAdapter()
        adapter.context = requireContext()
        binding.recyclerView.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            popupMenuService?.dismiss()
            finish()
        }
    }

    private fun click() {
        adapter.itemClick = {
            Toast.makeText(requireContext(), "${it.studentEntity.name}", Toast.LENGTH_SHORT).show()
        }
        adapter.itemSelected = { point, position ->
            viewModel.dayStudents!![position].point = point
        }
        adapter.itemSelect = {
            popupMenuService = it
        }
        adapter.itemChecked = { position, isChecked ->
            viewModel.dayStudents!![position].isHas = isChecked
        }
        binding.back.setOnClickListener { finish() }
        binding.save.setOnClickListener { saveData() }
    }

    private fun saveData() {
        if (!isUpdated) {
            val attendanceEntity = AttendanceEntity(0, groupEntity?.id!!, null, Date().time)
            val attendanceId = viewModel.insertAttendance(attendanceEntity)
            for (i in viewModel.dayStudents?.indices!!)
                viewModel.dayStudents!![i].attendanceId = attendanceId
            viewModel.insertDayStudent(viewModel.dayStudents!!)
        } else
            viewModel.updateDayStudent(viewModel.dayStudents!!)
        finish()
    }

    private fun finish() = Navigation.findNavController(binding.root).popBackStack()

    private fun change() {
        if (isUpdated) {
            Log.d("@@@@", "change: attendance != null")
            viewModel.getDayStudents()?.observe(viewLifecycleOwner) { dayStudents ->
                viewModel.dayStudents = dayStudents
                val list = ArrayList<StudentWithDayStudent>()
                for (i in 0 until studentEntities!!.size) {
                    list.add(
                        StudentWithDayStudent(
                            studentEntity = studentEntities!![i],
                            dayStudentEntity = dayStudents[i]
                        )
                    )
                }
                adapter.submitList(list)
            }
        } else {
            Log.d("@@@@", "change: attendance == null")
            val list = ArrayList<StudentWithDayStudent>()
            val dayStudentEntities = ArrayList<DayStudentEntity>()
            for (i in 0 until studentEntities!!.size) {
                list.add(
                    StudentWithDayStudent(
                        studentEntity = studentEntities!![i],
                        dayStudentEntity = null
                    )
                )
                dayStudentEntities.add(
                    DayStudentEntity(
                        studentId = studentEntities!![i].id,
                        attendanceId = null,
                        point = null,
                        isHas = false
                    )
                )
            }
            adapter.submitList(list)
            viewModel.dayStudents = dayStudentEntities
        }
    }
}