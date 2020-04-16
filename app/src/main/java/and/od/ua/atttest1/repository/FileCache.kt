package and.od.ua.atttest1.repository

import and.od.ua.atttest1.Constants.CACHE_FOLDER_NAME
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class FileCache(context: Context) {
    private var cacheDir: File? = null

    fun getFile(url: String?): File {
        val filename = kotlin.math.abs(url.hashCode()).toString()
        return File(cacheDir, filename)
    }

    fun saveFile(url: String, bitmap: Bitmap) {
        val filename = kotlin.math.abs(url.hashCode()).toString()
        val fOut: OutputStream?
        val file = File(cacheDir, filename)
        file.createNewFile()
        fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut)
        fOut.flush()
        fOut.close()
    }

    init {
        cacheDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) File(
            Environment.getExternalStorageDirectory(),
            CACHE_FOLDER_NAME
        ) else context.cacheDir
        if (cacheDir?.exists() != true) {
            cacheDir?.mkdirs()
        }
    }
}