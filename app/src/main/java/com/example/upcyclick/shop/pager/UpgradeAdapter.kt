package com.example.upcyclick.shop.pager

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.AppSingleton
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Scroll
import com.example.upcyclick.database.entity.Upgrade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class  UpgradeAdapter(private val upgrades: List<Upgrade>, var coins: TextView?) :
    RecyclerView.Adapter<UpgradeAdapter.MyViewHolder>() {

        val doubleClickPrice =  500
        val tripleClickPrice = 1250
        val quadrClickPrice =  3000
        val megaClickPrice =  10000

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameTextView: TextView? = null
            var descriptionTextView: TextView? = null
            var incomeTextView: TextView? = null
            var imageView: ImageView? = null
            var buyTextView: TextView? = null
            var priceTextView: TextView? = null

            lateinit var singleton: AppSingleton
            init {
                nameTextView = itemView.findViewById(R.id.title_upgrade)
                descriptionTextView = itemView.findViewById(R.id.descr_upgrade)
                incomeTextView = itemView.findViewById(R.id.income)
                imageView = itemView.findViewById(R.id.image_view_icon_upgrade)
                buyTextView = itemView.findViewById(R.id.buy_icon_3)
                priceTextView = itemView.findViewById(R.id.price_count_3)

                singleton = AppSingleton.getInstance(this.itemView.context)
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

            updateItems(holder, position)

            if (upgrades[position].purchased){
                buyUpgrade(holder)
            }
            else
                holder.itemView.setOnClickListener {
                    when (upgrades[position].income) {
                        2 -> {
                            if (holder.singleton.count >= doubleClickPrice) {
                                //изменение монет и списков в синглетоне
                                holder.singleton.count -= doubleClickPrice
                                var upg = holder.singleton.availableUpgradeList?.removeAt(0)
                                upg?.purchased = true
                                holder.singleton.updatesList.add(upg!!)
                                holder.singleton.allUpgradeList?.get(position)?.purchased = true
                                coins?.text = holder.singleton.count.toString() + " "
                                //проигрывание анимации
                                buyUpgrade(holder)
                                //заполнение бд
                                CoroutineScope(Dispatchers.IO).launch {
                                    holder.singleton.upDB?.upgradeDao()?.update(upg!!)
                                }
                            }
                            //updateItems(holder, position)
                        }
                        3 -> {
                            if (holder.singleton.count >= tripleClickPrice && holder.singleton.availableUpgradeList?.get(0)?.income == 3) {
                                //изменение монет и списков в синглетоне
                                holder.singleton.count -= tripleClickPrice
                                var upg = holder.singleton.availableUpgradeList?.removeAt(0)
                                upg?.purchased = true
                                holder.singleton.updatesList.add(upg!!)
                                holder.singleton.allUpgradeList?.get(position)?.purchased = true
                                coins?.text = holder.singleton.count.toString() + " "
                                //проигрывание анимации
                                buyUpgrade(holder)
                                //заполнение бд
                                CoroutineScope(Dispatchers.IO).launch {
                                    holder.singleton.upDB?.upgradeDao()?.update(upg!!)
                                }
                            }
                            //updateItems(holder, position)
                        }
                        4 -> {
                            if (holder.singleton.count >= quadrClickPrice && holder.singleton.availableUpgradeList?.get(0)?.income == 4) {
                                //изменение монет и списков в синглетоне
                                holder.singleton.count -= quadrClickPrice
                                var upg = holder.singleton.availableUpgradeList?.removeAt(0)
                                upg?.purchased = true
                                holder.singleton.updatesList.add(upg!!)
                                holder.singleton.allUpgradeList?.get(position)?.purchased = true
                                coins?.text = holder.singleton.count.toString() + " "
                                //проигрывание анимации
                                buyUpgrade(holder)
                                //заполнение бд
                                CoroutineScope(Dispatchers.IO).launch {
                                    holder.singleton.upDB?.upgradeDao()?.update(upg!!)
                                }
                            }
                            //updateItems(holder, position)
                        }
                        5 -> {
                            if (holder.singleton.count >= megaClickPrice && holder.singleton.availableUpgradeList?.get(0)?.income == 5) {
                                //изменение монет и списков в синглетоне
                                holder.singleton.count -= megaClickPrice
                                var upg = holder.singleton.availableUpgradeList?.removeAt(0)
                                upg?.purchased = true
                                holder.singleton.updatesList.add(upg!!)
                                holder.singleton.allUpgradeList?.get(position)?.purchased = true
                                coins?.text = holder.singleton.count.toString() + " "
                                //проигрывание анимации
                                buyUpgrade(holder)
                                //заполнение бд
                                CoroutineScope(Dispatchers.IO).launch {
                                    holder.singleton.upDB?.upgradeDao()?.update(upg!!)
                                }
                            }
                        }
                    }
                }
        }

    private fun updateItems(holder: MyViewHolder, position: Int) {
        val index = upgrades[position].income
        when (index){
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
                //if (holder.singleton.allUpgradeList?.get(position-1)?.purchased == false) holder.itemView.setBackgroundResource(R.drawable.button_selector_unavailable)
            }
            4 -> {
                holder.descriptionTextView?.text = "Getting 4 UpCoins for one click"
                holder.incomeTextView?.text = "+4/click "
                holder.imageView?.setImageResource(R.drawable.ic_qudr_click)
                holder.priceTextView?.text = "3000 "
                //if (holder.singleton.allUpgradeList?.get(position-1)?.purchased == false) holder.itemView.setBackgroundResource(R.drawable.button_selector_unavailable)
            }
            5 -> {
                holder.descriptionTextView?.text = "Getting 5 UpCoins for one click"
                holder.incomeTextView?.text = "+5/click "
                holder.imageView?.setImageResource(R.drawable.ic_mega_click)
                holder.priceTextView?.text = "10000 "
                //if (holder.singleton.allUpgradeList?.get(position-1)?.purchased == false) holder.itemView.setBackgroundResource(R.drawable.button_selector_unavailable)
            }
        }
    }

        private fun buyUpgrade(holder: MyViewHolder){
            holder.buyTextView?.text = ""
            holder.buyTextView?.setBackgroundResource(R.drawable.ic_baseline_check_24)
            holder.itemView.isClickable = false
        }

        override fun getItemCount() = upgrades.size
}