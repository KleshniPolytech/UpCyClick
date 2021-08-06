package com.example.upcyclick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.upcyclick.shop.pager.SampleFragmentPagerAdapter
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PDFFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pdf_reader, container, false)
        val pdfName = arguments?.getString("pdfName")

        val pdf = view.findViewById<PDFView>(R.id.pdfView)

        pdf?.fromAsset(pdfName)?.load()
        return view
    }
}