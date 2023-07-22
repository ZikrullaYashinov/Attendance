package zikrulla.production.attendance.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import zikrulla.production.attendance.R

class DialogService {

    private var dialog: Dialog? = null

    //    TODO add layout: dialog_set_name.xml
//    TODO add drawable: bg_dialog.xml, bg_simple1_btn.xml, bg_simple2_btn.xml
//    TODO add strings:
//    <string name="group_name">Group name</string>
//    <string name="save">Save</string>
//    <string name="cancel">Cancel</string>
//    <string name="edit_name">Edit name</string>
    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    fun showEditDialog(
        context: Context,
        name: String = "",
        editNameHint: String = context.getString(R.string.group_name),
        editDialogName: String = context.getString(R.string.edit_name),
        saveClick: (name: String) -> Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_set_name, null, false)

        val dialogName = dialogView.findViewById<TextView>(R.id.dialog_name)
        val editName = dialogView.findViewById<EditText>(R.id.edit_name)
        val save = dialogView.findViewById<Button>(R.id.save)
        val cancel = dialogView.findViewById<Button>(R.id.cancel)

        val dialog = builder.setView(dialogView).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        editName.setText(name)
        editName.hint = editNameHint
        dialogName.text = editDialogName

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        save.setOnClickListener {
            val invoke = saveClick.invoke(editName.text.toString())
            if (invoke)
                dialog.dismiss()
        }
        dialog.show()
    }

    fun setLoadingDialog(context: Context) {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.dialog_loading)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
    }

    fun showLoadingDialog() {
        dialog?.show()
    }

    fun dismissLoadingDialog() {
        dialog?.dismiss()
    }
}