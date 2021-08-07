package com.example.upcyclick

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

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
        onSave()
        super.onStop()
    }


    private fun onSave() {
        val pref = applicationContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("Count",  AppSingleton.getInstance(applicationContext).count)
        editor?.apply()
    }
}
