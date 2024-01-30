package com.example.advancedtask.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.advancedtask.adapter.SearchAdapter
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.databinding.FragmentSearchBinding
import com.example.advancedtask.retrofit.ApiRepository
import com.example.advancedtask.retrofit.ApiRepositoryImpl

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: FragmentSearchBinding

    private var _adapter: SearchAdapter? = null
    private val adapter get() = _adapter

    private lateinit var item: MutableList<SearchModel>



    private val viewModel by lazy {
        val repository = ApiRepositoryImpl()
        ViewModelProvider(this, SearchViewModel.SearchViewModelFactory(repository)).get(SearchViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initViewModel() {
//        val adapter = SearchAdapter()
//        with(viewModel) {
//            myCustomPosts.observe(viewLifecycleOwner) { result ->
//                if (result.isSuccessful) {
//                    Log.d("test", "$result")
//                    // Save Search Data
//                    adapter.submitList(result.body()!!.documents)
//                    for (i in result.body()!!.documents!!) {
//                        Log.d("test", "$i")
//                    }
//                } else {
//                    Log.d("test", "fail")
//                }
//
//            }
//        }
    }


    private fun initView() {
        setSearch()
        setAdapter()
        setSearchButton()

    }

    private fun setSearchButton() {
        binding.btnSearch.setOnClickListener {

            viewModel.searchPosts(1)
        }
    }

    private fun setAdapter() {
        _adapter = SearchAdapter()
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        viewModel.myCustomPosts.observe(viewLifecycleOwner){models ->
            item = models.map { it.copy() }.toMutableList()

            Log.d("myCustomPosts","$models")
            adapter?.submitList(item)
        }
    }



    private fun setSearch() = with(binding){
        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchPosts(it, 1) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchTextLive(it)}
                return false // 검색어 추천등 추가 하면 좋을 듯

            }

        })
    }
}