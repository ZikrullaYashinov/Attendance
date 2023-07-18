package zikrulla.production.attendance.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import zikrulla.production.attendance.database.entity.GroupEntity
import zikrulla.production.attendance.databinding.ItemGroupBinding

class GroupAdapter : ListAdapter<GroupEntity, GroupAdapter.Vh>(MyDiffUtil()) {

    lateinit var itemClick: (GroupEntity) -> Unit

    class MyDiffUtil : DiffUtil.ItemCallback<GroupEntity>() {
        override fun areItemsTheSame(oldItem: GroupEntity, newItem: GroupEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroupEntity, newItem: GroupEntity): Boolean {
            return oldItem.name == newItem.name
        }

    }

    inner class Vh(private val binding: ItemGroupBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(group: GroupEntity) {
            binding.apply {
                name.text = group.name
                size.text = "${group.size} ta talaba"
                itemView.setOnClickListener {
                    itemClick.invoke(group)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }
}