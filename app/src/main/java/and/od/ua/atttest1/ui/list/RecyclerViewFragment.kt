package and.od.ua.atttest1.ui.list

import and.od.ua.atttest1.MainActivity
import and.od.ua.atttest1.R
import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.presenters.RecyclerViewPresenter
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

class RecyclerViewFragment : Fragment(), ItemsAdapter.ItemsAdapterListener,
    RecyclerViewPresenter.RecyclerViewInterface {

    lateinit var presenter: RecyclerViewPresenter
    private lateinit var recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null
    private var adapter: ItemsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(root)

        presenter = RecyclerViewPresenter(this)

        (activity as MainActivity).showSearchMenu()
        return root
    }

    override fun onItemSelected(item: Item) {
        (activity as MainActivity).openDetails(item.itemId)
    }

    override fun initItemList(itemList: List<Item>) {
        context?.let {
            adapter = ItemsAdapter(
                itemList = itemList,
                itemListFiltered = itemList,
                listener = this
            )
        }
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            itemAnimator = android.support.v7.widget.DefaultItemAnimator()
        }
        recyclerView.adapter = adapter
    }

    override fun setFilter(filter: String) {
        adapter?.filter?.filter(filter)
    }

    override fun update() {
        adapter?.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    private fun initViews(root: View) {
        recyclerView = root.findViewById(R.id.recycler_view)
        initProgressBar()
    }

    private fun initProgressBar() {
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleSmall)
        progressBar?.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(
            Resources.getSystem().displayMetrics.widthPixels,
            350
        )
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        activity?.addContentView(progressBar, params)
        showProgressBar()
    }
}