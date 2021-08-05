package com.example.upcyclick.shop.pager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Scroll
import com.example.upcyclick.database.entity.Upgrade

class UpgradeAdapter (private val upgrades: MutableList<Upgrade>) :
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
                    .inflate(R.layout.recyclerview_item_upgrade, parent, false)
            return MyViewHolder(itemView)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.nameTextView?.text = upgrades[position].name
            when (upgrades[position].income){
                2 -> {
                    holder.descriptionTextView?.text = "Getting 2 UpCoins for one click"
                    holder.incomeTextView?.text = "+2/click "
                    holder.imageView?.setImageResource(R.drawable.ic_double_click)
                    holder.priceTextView?.text = "500 "
                }
                3 -> {
                    holder.descriptionTextView?.text = "Getting 3 UpCoins for one click"
                    holder.incomeTextView?.text = "+3/click "
                    holder.imageView?.setImageResource(R.drawable.ic_triple_click)
                    holder.priceTextView?.text = "1250 "
                }
                4 -> {
                    holder.descriptionTextView?.text = "Getting 4 UpCoins for one click"
                    holder.incomeTextView?.text = "+4/click "
                    holder.imageView?.setImageResource(R.drawable.ic_qudr_click)
                    holder.priceTextView?.text = "3000 "
                }
                5 -> {
                    holder.descriptionTextView?.text = "Getting 5 UpCoins for one click"
                    holder.incomeTextView?.text = "+5/click "
                    holder.imageView?.setImageResource(R.drawable.ic_mega_click)
                    holder.priceTextView?.text = "10000 "
                }
            }
            if (upgrades[position].purchased){
                holder.buyTextView?.text = ""
                holder.buyTextView?.setBackgroundResource(R.drawable.ic_baseline_check_24)
                holder.itemView.isClickable = false
            }
        }

        override fun getItemCount() = upgrades.size
}