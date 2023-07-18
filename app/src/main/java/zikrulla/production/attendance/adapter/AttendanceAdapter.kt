package zikrulla.production.attendance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import zikrulla.production.attendance.R
import zikrulla.production.attendance.database.entity.AttendanceEntity
import zikrulla.production.attendance.databinding.ItemAttendanceBinding
import java.text.SimpleDateFormat

class AttendanceAdapter : ListAdapter<AttendanceEntity, AttendanceAdapter.Vh>(MyDiffUtil()) {

    lateinit var itemClick: (AttendanceEntity) -> Unit
    lateinit var context: Context

    class MyDiffUtil : DiffUtil.ItemCallback<AttendanceEntity>() {
        override fun areItemsTheSame(
            oldItem: AttendanceEntity,
            newItem: AttendanceEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AttendanceEntity,
            newItem: AttendanceEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class Vh(private val binding: ItemAttendanceBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(attendanceEntity: AttendanceEntity) {
            binding.apply {
                date.text = dateFormat(attendanceEntity.date)
                description.text =
                    if (attendanceEntity.description != null) attendanceEntity.description
                    else context.getString(R.string.no_description)
                itemView.setOnClickListener {
                    itemClick.invoke(attendanceEntity)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateFormat(date: Long): String? {
        return SimpleDateFormat("dd.MM.yyyy HH:mm").format(date)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(getItem(position))
    }
}