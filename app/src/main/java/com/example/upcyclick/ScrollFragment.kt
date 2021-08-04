package com.example.upcyclick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.database.entity.Scroll

class ScrollFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var plusShop: ImageView
    private lateinit var donthave: TextView
    private lateinit var toShop: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scroll, container, false)

        init(view)

        recyclerView
        return view
    }
    private fun init(v: View) {
        recyclerView = v.findViewById(R.id.recyclerViewScrolls)
        plusShop = v.findViewById(R.id.plusShop)
        donthave = v.findViewById(R.id.donthavelbl)
        toShop   = v.findViewById(R.id.to_shop_button)


        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val list = mutableListOf<Scroll>(Scroll("1",true,"3","4")
            ,Scroll("1",true,"3","4")
            ,Scroll("1",true,"3","4")
            ,Scroll("1",true,"3","4")
        )
        recyclerView.adapter = CustomRecyclerAdapter(list )
        list.add(Scroll("1",true,"3aa","4"))

        if(list.size!=0) hide()
    }
    private fun hide(){
        plusShop.visibility = View.INVISIBLE
        donthave.visibility  = View.INVISIBLE
        toShop.visibility  = View.INVISIBLE
    }
}