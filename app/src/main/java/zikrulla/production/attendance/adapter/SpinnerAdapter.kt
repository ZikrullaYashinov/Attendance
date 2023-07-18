package zikrulla.production.attendance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import zikrulla.production.attendance.databinding.ItemSpinnerBinding

class SpinnerAdapter(val list: List<String>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null)
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        else
            ItemSpinnerBinding.bind(convertView)
        binding.point.text = list[position]
        return binding.root
    }
}