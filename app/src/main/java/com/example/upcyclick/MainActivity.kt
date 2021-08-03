package com.example.upcyclick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.upcyclick.database.UpDB
import com.example.upcyclick.database.entity.Scroll
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)

        bottom_nav.setupWithNavController(navController)

        val radius = 30f

        val shapeDrawable : MaterialShapeDrawable = bottom_nav.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
    }

    override fun onStop() {
        super.onStop()
        onSave()
    }


    private fun onSave() {
        val pref = applicationContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("Count",  YourManager.getInstance(applicationContext).count)
        editor?.apply()
    }
}
