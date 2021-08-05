package com.example.upcyclick.shop.pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Scroll

class UpgradeAdapter (private val upgrades: List<Scroll>) :
    RecyclerView.Adapter<UpgradeAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameTextView: TextView? = null
            var descriptionTextView: TextView? = null
            var incomeTextView: TextView? = null
            var imageView: ImageView? = null
            var buyTextView: TextView? = null
            var priceTextView: TextView? = null
            init {
                nameTextView = itemView.findViewById(R.id.title_upgrade)
                descriptionTextView = itemView.findViewById(R.id.descr_upgrade)
                incomeTextView = itemView.findViewById(R.id.income)
                imageView = itemView.findViewById(R.id.image_view_icon_upgrade)
                buyTextView = itemView.findViewById(R.id.buy_icon_3)
                priceTextView = itemView.findViewById(R.id.price_count_3)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item_scrol, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.nameTextView?.text = upgrades[position].name
            holder.descriptionTextView?.text = upgrades[position].description
            //todo изменять картинку по типу
//        holder.imageView = when(scrolls[position].type){
//
//        }
        }

        override fun getItemCount() = upgrades.size
}