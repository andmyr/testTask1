package and.od.ua.atttest1.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.widget.ImageView

object ImageController {

    private var fileCache: FileCache? = null

    fun init(context: Context) {
        fileCache = FileCache(context)
    }

    fun downloadImage(url: String, imageView: ImageView) {
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

    fun saveBitmapToCache(url: String, bitmap: Bitmap) {
        fileCache?.saveFile(url, bitmap)
    }
}