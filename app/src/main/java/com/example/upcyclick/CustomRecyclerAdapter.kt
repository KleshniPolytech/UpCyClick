package com.example.upcyclick

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.database.entity.Scroll
import com.github.barteksc.pdfviewer.PDFView

class CustomRecyclerAdapter(private val scrolls: List<Scroll>,private val context: Context,private val view: View) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView? = null
        var descriptionTextView: TextView? = null
        var imageView: ImageView? = null
        init {
            nameTextView = itemView.findViewById(R.id.title_upgrade)
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
        Log.d("typeID ",""+scrolls[position].typeId)

        holder.imageView?.setImageResource(when(scrolls[position].typeId){
            1->R.drawable.ic_common_scroll//todo чекните id скроллов
            2->R.drawable.ic_rare_scroll
            else -> R.drawable.ic_legendary_scroll
        }
        )

        holder.itemView.setOnClickListener {
            run {
                val bundle = Bundle()
                bundle.putString("pdfName",scrolls[position].filePath)
                view.findNavController().navigate(R.id.action_scrollFragment_to_pdfReaderFragment,bundle)
            }
        }
    }

    override fun getItemCount() = scrolls.size
}