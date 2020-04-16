package and.od.ua.atttest1.ui.description

import and.od.ua.atttest1.Constants.KEY
import and.od.ua.atttest1.R
import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.repository.ImageController.downloadImage
import and.od.ua.atttest1.utils.toTime
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class PageFragment : Fragment() {
    var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            item = arguments?.getSerializable(KEY) as Item
        } catch (e: Exception) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.vp_item, null)
        val tvName = view.findViewById<View>(R.id.tvName) as TextView
        val tvDate = view.findViewById<View>(R.id.tvDate) as TextView
        val tvDescription = view.findViewById<View>(R.id.tvDescription) as TextView
        val imageView = view.findViewById<View>(R.id.imageView) as ImageView
        item?.run {
            tvName.text = name
            tvDate.text = time.toTime()
            tvDescription.text = description
            downloadImage(image, imageView)
        }
        return view
    }

    companion object {
        fun newInstance(item: Item): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putSerializable(KEY, item)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}