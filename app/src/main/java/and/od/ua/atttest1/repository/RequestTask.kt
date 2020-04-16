package and.od.ua.atttest1.repository

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RequestTask : AsyncTask<String?, String?, String?>() {

    override fun doInBackground(vararg params: String?): String? {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        try {
            val url = URL(params[0])
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val stream = connection.inputStream
            reader = BufferedReader(InputStreamReader(stream))
            val buffer = StringBuffer()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(line + "\n")
                //Log.d("Response: ", "> $line")
                line = reader.readLine()
            }
            return buffer.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("TAG", "Result: $result")
        /*if (result != null) {
            Repository.onSuccess(result)
        } else {*/
            Repository.onError()
        //}
    }
}