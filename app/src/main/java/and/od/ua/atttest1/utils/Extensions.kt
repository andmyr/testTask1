package and.od.ua.atttest1.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun String.toTime(): String {
    val pattern = "dd-MMMM-yyyy HH:mm"
    val simpleDateFormat = SimpleDateFormat(pattern)
    val date = Date()
    date.time = this.toLong()
    return simpleDateFormat.format(date)
}