package and.od.ua.atttest1

import android.Manifest
import android.os.Build

object Constants {
    const val URL = "http://XXX.com/test.json"

    const val KEY_MSG = "KEY_MSG"
    const val TAG_1 = "ViewPagerFragmentTag"
    const val TAG_2 = "HomeFragmentTag"
    const val CACHE_FOLDER_NAME = "Images_cache"
    const val RC_PERMISSION = 888
    const val KEY = "key"
    const val CURRENT_VP_ITEM_ID = "CURRENT_VP_ITEM_ID"
    const val SHOW_VP = "SHOW_VP"

    val PERMISSIONS_STORAGE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else {
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}