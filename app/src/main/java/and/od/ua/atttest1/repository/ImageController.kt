package and.od.ua.atttest1.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView

object ImageController {

    private var fileCache: FileCache? = null

    fun downloadImage(url: String, imageView: ImageView) {
        if (fileCache == null) {
            fileCache = FileCache(imageView.context)
        }
        val file = fileCache?.getFile(url)
        file?.run {
            if (exists() && isFile) {
                val b: Bitmap? = BitmapFactory.decodeFile(this.absolutePath)
                if (b != null) {
                    imageView.setImageBitmap(b)
                    return
                }
            }
            ImageDownloaderTask(imageView, url).execute()
        }
    }

    fun saveBitmapToCache(url: String, bitmap: Bitmap, context: Context) {
        if (fileCache == null) {
            fileCache = FileCache(context)
        }
        fileCache?.saveFile(url, bitmap)
    }
}