package com.example.favouritedishexample.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivitySplashBinding

// 1) Создаем сплеш активити и binding
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        // 4) Этот код для исчезновения статус бара с часами
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // 6) Создаем анимацию
        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        splashBinding.textViewAppName.animation = splashAnimation

        // 7) Добавляем слушателя для анимации
        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                // 8) Создаем Handler
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
    }

    // 9) Создаем папки model, view. Во view создаем две папки - activities и fragments.
    // Перемещаем MainActivity и SplashActivity в папку activities и все фрагменты в папку fragments
    // Также создаем папку viewmodel и перемещаем туда все viewModels
    // И потом удаляем папку ui

    // 10) Создаем активити AddUpdateDishActivity

    // 5) Создаем файл anim и в ней anim_splash

    // 2) В манифесте перемещаем интент фильтр для запуска сплеш активити первым
    // 3) Идем в themes и добавляем новый стиль для action bar и добавляем его в манифест в сплеш активити
}