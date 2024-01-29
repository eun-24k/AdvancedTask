package com.example.advancedtask.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater

import androidx.viewpager2.widget.ViewPager2
import com.example.advancedtask.adapter.MainViewPager2Adapter
import com.example.advancedtask.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewPager2Adapter by lazy {
        MainViewPager2Adapter(this@MainActivity)
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView()



    }

    private fun initView() = with(binding){
        viewPager.adapter = viewPager2Adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        })

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.setText(viewPager2Adapter.getTitle(position))
        }.attach()
    }


}
