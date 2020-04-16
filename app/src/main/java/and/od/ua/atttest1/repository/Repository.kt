package and.od.ua.atttest1.repository

import and.od.ua.atttest1.Constants
import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.utils.Utils
import android.util.Log

object Repository {
    private var list =
        //listOf<Item>()
        listOf(
            Item(
                itemId = "10056",
                name = "IronMan",
                image = "http://s8.hostingkartinok.com/uploads/images/2016/03/b70762d52599ffc44dc7539bf57baa1c.jpg",
                description = "heavy armor",
                time = "1457018867393"
            ),
            Item(
                itemId = "10054",
                name = "Deadpool",
                image = "http://s8.hostingkartinok.com/uploads/images/2016/03/3d23f908f56428ac97840acae92c1f50.jpg",
                description = "regeneration",
                time = "1457018837658"
            ),
            Item(
                itemId = "10028",
                name = "Thor",
                image = "http://s8.hostingkartinok.com/uploads/images/2016/03/44819c5cf496a059797d43ffcab07508.jpg",
                description = "immortal",
                time = "1457018877305"
            ),
            Item(
                itemId = "10048",
                name = "Hulk",
                image = "http://s8.hostingkartinok.com/uploads/images/2016/03/dd54db9d3ea626ece12f4123d3b63306.jpg",
                description = "angry",
                time = "1457018788101"
            )
        )

    private var requestInterface: RequestListInterface? = null

    fun getItems(requestListInterface: RequestListInterface) {
        if (list.isEmpty()) {
            RequestTask().execute(Constants.URL)
        } else {
            requestListInterface.getList(list)
        }
        requestInterface = requestListInterface
    }

    fun onSuccess(response: String) {
        list = Utils.parse(response)
        requestInterface?.getList(list)
    }

    fun onError() {
        Log.e("TAG", "Request error")
    }

    interface RequestListInterface {
        fun getList(list: List<Item>)
    }
}