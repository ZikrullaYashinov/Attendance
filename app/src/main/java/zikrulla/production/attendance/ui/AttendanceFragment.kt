package zikrulla.production.attendance.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import zikrulla.production.attendance.R
import zikrulla.production.attendance.adapter.AttendanceAdapter
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.database.entity.StudentEntity
import zikrulla.production.attendance.databinding.FragmentAttendanceBinding
import zikrulla.production.attendance.model.PopupMenuItem
import zikrulla.production.attendance.model.Students
import zikrulla.production.attendance.utils.DialogService
import zikrulla.production.attendance.utils.PopupMenuService
import zikrulla.production.attendance.viewmodel.GroupsViewModel
import zikrulla.production.attendance.viewmodel.GroupsViewModelFactory
import zikrulla.production.attendance.viewmodel.StudentsViewModel
import zikrulla.production.attendance.viewmodel.StudentsViewModelFactory

class AttendanceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            groupEntity = requireArguments().getSerializable("group") as GroupEntity
        }
    }

    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var viewModel: StudentsViewModel
    private lateinit var viewModelGroup: GroupsViewModel
    private lateinit var groupEntity: GroupEntity
    private lateinit var adapter: AttendanceAdapter
    private var students: List<StudentEntity>? = null
    private var popupMenuService: PopupMenuService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAttendanceBinding.inflate(inflater, container, false)

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
        viewModelGroup = ViewModelProvider(
            this,
            GroupsViewModelFactory(AppDatabase.getInstance(requireContext()))
        )[GroupsViewModel::class.java]
        viewModel.fetchAttendance(groupEntity.id)
        viewModel.fetchStudents(groupEntity.id)

        binding.name.text = groupEntity.name

        adapter = AttendanceAdapter()
        adapter.context = requireContext()
        binding.recyclerView.adapter = adapter

    }

    @SuppressLint("ResourceType")
    private fun click() {
        binding.apply {
            moreVert.setOnClickListener { showPopupMenu(it) }
            back.setOnClickListener { Navigation.findNavController(binding.root).popBackStack() }
        }
        adapter.itemClick = {
            val bundle = Bundle()
            bundle.putSerializable("group", groupEntity)
            bundle.putSerializable("attendance", it)
            bundle.putSerializable("students", Students(students!!))
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_attendanceFragment_to_groupFragment, bundle)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            popupMenuService?.dismiss()
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    private fun change() {
        viewModel.getAttendance()?.observe(viewLifecycleOwner) {
            adapter.submitList(it.reversed())
        }
        viewModel.getStudents()?.observe(viewLifecycleOwner) {
            students = it
        }
    }

    private fun showPopupMenu(view: View?) {
        popupMenuService = PopupMenuService()
        popupMenuService?.showPopupMenu(requireContext(), view, R.layout.menu_attendance, listOf(
            PopupMenuItem(R.id.add_box) {
                val bundle = Bundle()
                bundle.putSerializable("group", groupEntity)
                bundle.putSerializable("students", Students(students!!))
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_attendanceFragment_to_groupFragment, bundle)
            },
            PopupMenuItem(R.id.edit_group_name) {
                DialogService().showEditDialog(
                    context = requireContext(),
                    name = groupEntity.name!!,
                    saveClick = {
                        if (isNotEmpty(it)) {
                            groupEntity.name = it
                            viewModelGroup.update(groupEntity)
                            binding.name.text = it
                            return@showEditDialog true
                        }
                        return@showEditDialog false
                    }
                )
            },
            PopupMenuItem(R.id.delete_group) {

            }
        ))
    }

    private fun isNotEmpty(text: String): Boolean {
        return if (text.trim().isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.name_is_empty), Toast.LENGTH_SHORT)
                .show()
            false
        } else true
    }

}