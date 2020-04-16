package and.od.ua.atttest1.ui.list

import and.od.ua.atttest1.R
import and.od.ua.atttest1.model.Item
import and.od.ua.atttest1.repository.ImageController
import and.od.ua.atttest1.utils.inflate
import and.od.ua.atttest1.utils.toTime
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView

class ItemsAdapter(
    private val itemList: List<Item>,
    private var itemListFiltered: List<Item>,
    private val listener: ItemsAdapterListener
) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflatedView = parent.inflate(R.layout.rv_item, false)
        return MyViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return itemListFiltered.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Item = itemListFiltered[position]
        holder.name.text = item.name
        holder.time.text = item.time.toTime()
        ImageController.downloadImage(item.image, holder.imageView)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                itemListFiltered = if (charString.isEmpty()) {
                    itemList
                } else {
                    val filteredList = mutableListOf<Item>()
                    for (row in itemList) {
                        if (row.name.contains(charString, ignoreCase = true)) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = itemListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                itemListFiltered = filterResults.values as List<Item>
                notifyDataSetChanged()
            }
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var name: TextView = view.findViewById(R.id.tvName)
        var time: TextView = view.findViewById(R.id.tvDate)
        var imageView: ImageView = view.findViewById(R.id.imageView)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemSelected(itemListFiltered[adapterPosition])
        }
    }

    interface ItemsAdapterListener {
        fun onItemSelected(item: Item)
    }
}