package com.example.advancedtask.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.advancedtask.data.ImageModel
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.retrofit.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: ApiRepository) : ViewModel() {
    private var _myCustomPosts: MutableLiveData<MutableList<SearchModel>> = MutableLiveData()
    val myCustomPosts: MutableLiveData<MutableList<SearchModel>> = _myCustomPosts

    private var _search = ""

    fun searchPosts(query: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                query.isEmpty().not() -> {
                    val response = repository.searchData(query, "accuracy", page)
                    Log.d("response", response.toString())
                    _myCustomPosts.postValue(response.toMutableList())
                }

            }

        }
    }

    fun searchPosts(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                _search.isEmpty().not() -> {
                    val response = repository.searchData(_search, "accuracy", page)
                    Log.d("response", response.toString())
                    _myCustomPosts.postValue(response.toMutableList())
                }

            }

        }
    }

    fun searchTextLive(searchText: String) {
        _search = searchText
    }


    class SearchViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }else throw IllegalArgumentException("Not found ViewModel class.")
        }

    }
}