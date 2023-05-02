package za.ac.iie.opsc7311.orbit_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.CategoryModel
import com.example.screentime.R


class CategoryAdapter(private val categoryList: List<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.itemName)

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.categoryName.text = currentItem.name
    }

    override fun getItemCount() = categoryList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}