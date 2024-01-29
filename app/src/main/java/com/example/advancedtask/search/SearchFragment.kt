package com.example.advancedtask.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.example.advancedtask.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by lazy {
        val repository = SearchRepository()
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

        // TODO: Use the ViewModel
        initView()
        with(viewModel) {
            myCustomPosts.observe(viewLifecycleOwner, Observer {result ->
                if (result.isSuccessful) {
                    Log.d("test", "$result")
                    for (i in result.body()!!.documents!!) {
                        Log.d("test","$i")

                    }

                } else {
                    Log.d("test","fail")
                }

            })
        }
    }




    private fun initView() {
        setSearch()

    }
    private fun setSearch() = with(binding){
        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchImage(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false // 검색어 추천등 추가 하면 좋을 듯
            }

        })
    }





}