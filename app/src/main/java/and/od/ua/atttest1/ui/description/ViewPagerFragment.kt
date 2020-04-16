package and.od.ua.atttest1.ui.description

import and.od.ua.atttest1.Constants.KEY_MSG
import and.od.ua.atttest1.R
import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.presenters.ViewPagerPresenter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ViewPagerFragment : Fragment(), ViewPagerPresenter.ViewPagerInterface {
    lateinit var presenter: ViewPagerPresenter
    private var pagerAdapter: PagerAdapter? = null
    private var pager: ViewPager? = null
    private var itemId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        if (bundle != null) {
            val msg = bundle.getString(KEY_MSG)
            if (msg != null) {
                itemId = msg
            }
        }

        presenter = ViewPagerPresenter(this)

        val root = inflater.inflate(R.layout.fragment_viewpager, container, false)
        pager = root.findViewById(R.id.pager) as ViewPager

        pager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        presenter.setContent(itemId)

        return root
    }

    override fun initItemList(
        itemList: List<Item>,
        startPosition: Int
    ) {
        pagerAdapter = ViewPagerAdapter(childFragmentManager, itemList)
        if (pager?.adapter == null) {
            pager?.adapter = pagerAdapter
        }
        pager?.currentItem = startPosition
    }

    override fun setCurrentItem(position: Int) {
        pager?.postDelayed({ pager?.currentItem = position }, 100)
    }

    private class ViewPagerAdapter(
        fm: FragmentManager?,
        val itemList: List<Item>
    ) :
        FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PageFragment.newInstance(itemList[position])
        }

        override fun getCount(): Int {
            return itemList.size
        }
    }
}