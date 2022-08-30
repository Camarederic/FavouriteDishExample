package com.example.favouritedishexample.view.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    // 25.1) Создаем переменную для navController
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 25.2) Удаляем эту строчку
        //val navView: BottomNavigationView = mBinding.navView

        mNavController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_all_dishes, R.id.navigation_favourite_dishes, R.id.navigation_random_dish))
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        mBinding.navView.setupWithNavController(mNavController)
    }

    // 25.3) Имплементируем метод
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController,null)
    }
    // 25.4) Создаем метод чтобы спрятать BottomNavigation
    fun hideBottomNavigationView(){
        mBinding.navView.clearAnimation()
        mBinding.navView.animate().translationY(mBinding.navView.height.toFloat()).duration = 300
    }
    // 25.5) Создаем метод для появления BottomNavigation
    fun showBottomNavigationView(){
        mBinding.navView.clearAnimation()
        mBinding.navView.animate().translationY(0f).duration = 300
    }
}