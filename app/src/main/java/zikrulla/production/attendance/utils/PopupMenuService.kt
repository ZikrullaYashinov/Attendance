package zikrulla.production.attendance.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import zikrulla.production.attendance.model.PopupMenuItem

class PopupMenuService {
    fun showPopupMenu(
        context: Context,
        view: View?,
        layoutId: Int,
        list: List<PopupMenuItem>
    ) {
        val service =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = service.inflate(layoutId, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        list.forEach { popupMenuItem ->
            popupWindow.contentView?.findViewById<LinearLayout>(popupMenuItem.resource)
                ?.setOnClickListener {
                    popupMenuItem.listener.invoke()
                    popupWindow.dismiss()
                }
        }
        popupWindow.showAsDropDown(view)
    }
}