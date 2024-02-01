package com.example.advancedtask.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

import androidx.viewpager2.widget.ViewPager2
import com.example.advancedtask.adapter.MainViewPager2Adapter
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.data.SelectedList
import com.example.advancedtask.data.SelectedList.addModel
import com.example.advancedtask.data.SelectedList.addTag
import com.example.advancedtask.data.SelectedList.keepTrackOfQueryAndPosition
import com.example.advancedtask.data.SelectedList.removeModel
import com.example.advancedtask.databinding.ActivityMainBinding
import com.example.advancedtask.retrofit.ApiRepositoryImpl
import com.example.advancedtask.search.SearchFragment
import com.example.advancedtask.search.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewPager2Adapter by lazy {
        MainViewPager2Adapter(this@MainActivity)
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var item: SearchModel? = null

    private val selectedItems: ArrayList<SearchModel> by lazy {
        arrayListOf()
    }
    private val viewModel by lazy {
        ViewModelProvider(this, SearchViewModel.SearchViewModelFactory(ApiRepositoryImpl()))[SearchViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView()


    }

    private fun initView() {
        setViewPagerAdapter()
        getSelectedItem()
    }

    private fun getSelectedItem() {

        viewModel.selectedItem.observe(this) {
            setSelectedItems(it)
            Log.d("SpecialThanks","우와 Getting item from the Viewmodel to the main activity $it")
            Log.d("SpecialThanks","북마크 값 맞음? ${it.bookMark}")

        }
    }

    private fun setSelectedItems(item: SearchModel?) {
        when (item?.bookMark) {
            true -> {
                addModel(item)
            }
            false -> {
                removeModel(item)
            }
            else -> Unit
        }

        Log.d("SpecialThanks", "으어 Setting selected ITems: $selectedItems")

    }

    private fun setViewPagerAdapter() = with(binding) {
        viewPager.adapter = viewPager2Adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPager2Adapter.getTitle(position))
        }.attach()
    }


}
