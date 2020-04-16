package and.od.ua.atttest1.model

import java.io.Serializable

data class Item(
    var itemId: String = "",
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var time: String = ""
) : Serializable