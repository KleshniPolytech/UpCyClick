package com.example.upcyclick

import android.content.Context

class YourManager private constructor(context: Context) {

    var count: Int = 0

    var currentQuizDifficulty: Int = 0

    init {
         val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
         count = pref!!.getInt("Count", 0)

    }
    companion object : SingletonHolder<YourManager, Context>(::YourManager)
}