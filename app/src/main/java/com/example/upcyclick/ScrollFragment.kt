package com.example.upcyclick

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.database.entity.Scroll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScrollFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var plusShop: ImageView
    private lateinit var donthave: TextView
    private lateinit var toShop: Button

    private lateinit var coins : TextView
    private lateinit var list : List<Scroll>

    private lateinit var coroutine: Job

    lateinit var appInstance: AppSingleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scroll, container, false)

        init(view)
        initListeners(view)

        recyclerView
        return view
    }

    private fun init(v: View) {
        recyclerView = v.findViewById(R.id.recyclerViewScrolls)
        plusShop = v.findViewById(R.id.plusShop)
        donthave = v.findViewById(R.id.donthavelbl)
        toShop = v.findViewById(R.id.to_shop_button)
        coins = v.findViewById(R.id.coinCount)

        recyclerView.layoutManager = LinearLayoutManager(this.context)

        appInstance = AppSingleton.getInstance(this.requireContext())

        val job = lifecycleScope.launch(Dispatchers.IO) {
            list =  appInstance.upDB?.scrollDao()?.getAllPurchasedScrolls() ?: listOf()
            if (list.size != 0) hide()
        }

        lifecycleScope.launch {
            job.join()
            recyclerView.adapter = appInstance.context?.let { CustomRecyclerAdapter(list, it,v) }

        }
        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }
    }

    private fun initListeners(v: View) {
        toShop.setOnClickListener {
            v.findNavController().
                navigate(ScrollFragmentDirections.actionScrollFragmentToShopFragment())
        }
    }

    private suspend fun updateCoinCount() {
        lifecycleScope.launch {
            while (true) {
                coins.text = appInstance.count.toString()
                delay(1000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
    private fun hide() {
        plusShop.visibility = View.GONE
        donthave.visibility = View.GONE
        toShop.visibility = View.GONE
    }
}