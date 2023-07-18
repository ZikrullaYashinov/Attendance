package zikrulla.production.attendance.model

data class PopupMenuItem(
    val resource: Int,
    val listener: () -> Unit
)