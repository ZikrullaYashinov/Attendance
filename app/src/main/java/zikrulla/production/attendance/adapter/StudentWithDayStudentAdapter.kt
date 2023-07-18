package zikrulla.production.attendance.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import zikrulla.production.attendance.databinding.ItemStudentBinding
import zikrulla.production.attendance.model.StudentWithDayStudent
import zikrulla.production.attendance.utils.Util

class StudentWithDayStudentAdapter :
    ListAdapter<StudentWithDayStudent, StudentWithDayStudentAdapter.Vh>(MyDiffUtil()) {

    lateinit var itemClick: (StudentWithDayStudent) -> Unit
    lateinit var itemSelected: (position: Int, selectItem: String) -> Unit
    lateinit var itemChecked: (position: Int, isChecked: Boolean) -> Unit

    class MyDiffUtil : DiffUtil.ItemCallback<StudentWithDayStudent>() {
        override fun areItemsTheSame(
            oldItem: StudentWithDayStudent,
            newItem: StudentWithDayStudent
        ): Boolean {
            return oldItem.studentEntity.id == newItem.studentEntity.id
        }

        override fun areContentsTheSame(
            oldItem: StudentWithDayStudent,
            newItem: StudentWithDayStudent
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class Vh(private val binding: ItemStudentBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(student: StudentWithDayStudent, positionItem: Int) {
            binding.apply {
                name.text = student.studentEntity.name
                if (student.dayStudentEntity != null) {
                    val dayStudentEntity = student.dayStudentEntity
                    val pointPosition = if (dayStudentEntity.point == null) 0
                    else Util.POINT_LIST.size - 1 - dayStudentEntity.point!!
                    point.setSelection(pointPosition)
                    isHas.isChecked = dayStudentEntity.isHas!!
                }
                itemView.setOnClickListener {
                    itemClick.invoke(student)
                }
                point.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val itemAtPosition = parent?.getItemAtPosition(position)
                        itemSelected.invoke(positionItem, itemAtPosition.toString())
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
                isHas.setOnCheckedChangeListener { _, isChecked ->
                    itemChecked.invoke(positionItem, isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val adapter = SpinnerAdapter(Util.POINT_LIST)
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.point.adapter = adapter
        return Vh(binding)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(getItem(position), position)
    }
}