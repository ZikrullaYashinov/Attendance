package zikrulla.production.attendance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.bottomsheet.BottomSheetDialog
import zikrulla.production.attendance.databinding.DialogWhenPointBinding
import zikrulla.production.attendance.databinding.ItemStudentBinding
import zikrulla.production.attendance.model.StudentWithDayStudent
import zikrulla.production.attendance.utils.PopupMenuService


class StudentWithDayStudentAdapter :
    ListAdapter<StudentWithDayStudent, StudentWithDayStudentAdapter.Vh>(MyDiffUtil()) {

    lateinit var context: Context
    lateinit var itemClick: (StudentWithDayStudent) -> Unit
    lateinit var itemSelected: (point: Int?, position: Int) -> Unit
    lateinit var itemSelect: (popupMenuService: PopupMenuService) -> Unit
    lateinit var itemChecked: (position: Int, isChecked: Boolean) -> Unit
    private var popupMenuService: PopupMenuService? = null

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
                    point.text =
                        if (dayStudentEntity.point == null) "-" else "${dayStudentEntity.point}"
                    isHas.isChecked = dayStudentEntity.isHas!!
                }
                itemView.setOnClickListener {
                    showWhenDialog(
                        context,
                        positionItem,
                        point,
                        student.studentEntity.name!!,
                        isHas
                    )
                }
                point.setOnClickListener {
                    showWhenDialog(
                        context,
                        positionItem,
                        point,
                        student.studentEntity.name!!,
                        isHas
                    )
                }
                isHas.setOnCheckedChangeListener { _, isChecked ->
                    itemChecked.invoke(positionItem, isChecked)
                }
            }


        }

        @SuppressLint("MissingInflatedId")
        fun showWhenDialog(
            context: Context,
            position: Int,
            point: TextView,
            studentName: String,
            checkBoxIsHas: CheckBox
        ) {
            val bottomSheetDialog = BottomSheetDialog(context)
            val dialogView =
                DialogWhenPointBinding.inflate(LayoutInflater.from(context), null, false)
            bottomSheetDialog.setContentView(dialogView.root)
            var p: Int? = if (point.text == "-") null else point.text.toString().toInt()

            bottomSheetDialog.setOnDismissListener {
                setPoint(p, position)
            }
            dialogView.apply {
                isHas.isChecked = checkBoxIsHas.isChecked
                name.text = studentName
                isHas.setOnCheckedChangeListener { _, isChecked ->
                    checkBoxIsHas.isChecked = isChecked
                }
                back.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                pointN.setOnClickListener {
                    p = null
                    point.text = "-"
                    cardPointN.setCardBackgroundColor(Color.GREEN)
                    bottomSheetDialog.dismiss()
                }
                point5.setOnClickListener {
                    p = 5
                    point.text = "5"
                    cardPoint5.setCardBackgroundColor(Color.GREEN)
                    bottomSheetDialog.dismiss()
                }
                point4.setOnClickListener {
                    p = 4
                    cardPoint4.setCardBackgroundColor(Color.GREEN)
                    point.text = "4"
                    bottomSheetDialog.dismiss()
                }
                point3.setOnClickListener {
                    p = 3
                    cardPoint3.setCardBackgroundColor(Color.GREEN)
                    point.text = "3"
                    bottomSheetDialog.dismiss()
                }
                point2.setOnClickListener {
                    p = 2
                    cardPoint2.setCardBackgroundColor(Color.GREEN)
                    point.text = "2"
                    bottomSheetDialog.dismiss()
                }
                point1.setOnClickListener {
                    p = 1
                    cardPoint1.setCardBackgroundColor(Color.GREEN)
                    point.text = "1"
                    bottomSheetDialog.dismiss()
                }
                point0.setOnClickListener {
                    p = 0
                    cardPoint0.setCardBackgroundColor(Color.GREEN)
                    point.text = "0"
                    bottomSheetDialog.dismiss()
                }
            }

            bottomSheetDialog.show()
        }

        private fun setPoint(point: Int?, position: Int) {
            itemSelected.invoke(point, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(getItem(position), position)
    }
}