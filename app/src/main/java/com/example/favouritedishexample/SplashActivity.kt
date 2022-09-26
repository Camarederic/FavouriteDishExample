package com.example.favouritedishexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.favouritedishexample.databinding.ActivitySplashBinding

// 1) Создаем сплеш активити и binding
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

    }

    // 2) В манифесте перемещаем интент фильтр для запуска сплеш активити первым
}