package and.od.ua.atttest1.utils

import and.od.ua.atttest1.Constants.PERMISSIONS_STORAGE
import and.od.ua.atttest1.Constants.RC_PERMISSION
import and.od.ua.atttest1.model.Item
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.json.JSONArray


object Utils {
    fun parse(json: String): List<Item> {
        val list = ArrayList<Item>()
        val jArray = JSONArray(json)
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                list.add(
                    Item(
                        itemId = jArray.getJSONObject(i).get("itemId").toString(),
                        name = jArray.getJSONObject(i).get("name").toString(),
                        image = jArray.getJSONObject(i).get("image").toString(),
                        description = jArray.getJSONObject(i).get("description").toString(),
                        time = jArray.getJSONObject(i).get("time").toString()
                    )
                )
            }
        }
        return list
    }

    fun verifyStoragePermissions(activity: Activity?): Boolean {
        return if (activity != null) {
            val permission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            permission == PackageManager.PERMISSION_GRANTED
        } else false
    }

    fun requestPermissions(activity: Activity?) {
        activity?.run {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, RC_PERMISSION)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}