package and.od.ua.atttest1.presenters

import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.repository.Repository

class RecyclerViewPresenter(private val recyclerViewInterface: RecyclerViewInterface) :
    Repository.RequestListInterface {
    private var itemList = listOf<Item>()

    init {
        Repository.getItems(this)
    }

    override fun getList(list: List<Item>) {
        itemList = list
        setContent()
    }

    fun setFilter(filter: String) {
        recyclerViewInterface.setFilter(filter)
    }

    private fun setContent() {
        recyclerViewInterface.initItemList(itemList)
        recyclerViewInterface.hideProgressBar()
    }

    interface RecyclerViewInterface {
        fun initItemList(itemList: List<Item>)
        fun setFilter(filter: String)
        fun update()
        fun showProgressBar()
        fun hideProgressBar()
    }
}