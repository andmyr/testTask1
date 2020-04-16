package and.od.ua.atttest1.presenters

import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.repository.Repository

class ViewPagerPresenter(private val viewPagerInterface: ViewPagerInterface):
    Repository.RequestListInterface {
    private var itemList = listOf<Item>()

    init {
        Repository.getItems(this)
    }

    fun setContent(startItemId: String= "") {
        viewPagerInterface.initItemList(itemList, itemIdToPosition(startItemId))
    }

    fun setCurrentItem(itemId:String){
        viewPagerInterface.setCurrentItem(itemIdToPosition(itemId))
    }

    override fun getList(list: List<Item>) {
        itemList = list
    }

    private fun itemIdToPosition(itemId: String): Int {
        var index = itemList.indexOfFirst { it.itemId == itemId }
        if (index < 0) {
            index = 0
        }
        return index
    }

    interface ViewPagerInterface {
        fun initItemList(itemList: List<Item>, startItemId: Int)
        fun setCurrentItem(position: Int)
    }
}