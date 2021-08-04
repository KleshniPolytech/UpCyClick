package com.example.upcyclick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.database.entity.Scroll

class CustomRecyclerAdapter(private val scrolls: List<Scroll>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView? = null
        var descriptionTextView: TextView? = null
        var imageView: ImageView? = null
        init {
            nameTextView = itemView.findViewById(R.id.rare_legend)
            descriptionTextView = itemView.findViewById(R.id.descr_legend)
            imageView = itemView.findViewById(R.id.imageView_scrol_2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item_scrol, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameTextView?.text = scrolls[position].name
        holder.descriptionTextView?.text = scrolls[position].description
        //todo изменять картинку по типу
//        holder.imageView = when(scrolls[position].type){
//
//        }
    }

    override fun getItemCount() = scrolls.size
}