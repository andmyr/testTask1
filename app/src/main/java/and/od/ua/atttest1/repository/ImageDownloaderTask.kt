package and.od.ua.atttest1.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloaderTask(imageView: ImageView, private val url: String) :
    AsyncTask<String?, Void?, Bitmap?>() {

    private val imageViewReference: WeakReference<ImageView>?

    override fun doInBackground(vararg params: String?): Bitmap? {
        return downloadBitmap(url)
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        var bitmap = bitmap
        if (isCancelled) {
            bitmap = null
        }
        if (imageViewReference != null) {
            val imageView = imageViewReference.get()
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                    ImageController.saveBitmapToCache(url, bitmap, imageView.context)
                }
            }
        }
    }

    private fun downloadBitmap(url: String): Bitmap? {
        var urlConnection: HttpURLConnection? = null
        if (url.isBlank()) return null
        try {
            val uri = URL(url)
            urlConnection = uri.openConnection() as HttpURLConnection
            val inputStream = urlConnection.inputStream
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.d("URLCONNECTIONERROR", e.toString())
            urlConnection?.disconnect()
            Log.w("ImageDownloader", "Error downloading image from $url")
        } finally {
            urlConnection?.disconnect()
        }
        return null
    }

    init {
        imageViewReference = WeakReference(imageView)
    }
}