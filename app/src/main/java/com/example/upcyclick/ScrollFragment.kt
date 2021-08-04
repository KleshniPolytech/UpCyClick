package com.example.upcyclick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
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
        //todo передать контекст в метод
        //recyclerView.layoutManager = LinearLayoutManager(контекст)
        //todo передать список скроллов в конструктор
        //recyclerView.adapter = CustomRecyclerAdapter( список)

    }
}