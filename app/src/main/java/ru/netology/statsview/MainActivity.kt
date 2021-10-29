package ru.netology.statsview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.netology.statsview.utils.StatsView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<StatsView>(R.id.statsView)
        val runnable = object : Runnable {
            override fun run(){
                val data = listOf(600F, 400F, Random.nextFloat() * 700F,  500F)
                val dataSum =  data.sum()
                view.data = data.map{it/dataSum}
                Handler(Looper.getMainLooper()).postDelayed(this, 2_500)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 2_500)
    }
}