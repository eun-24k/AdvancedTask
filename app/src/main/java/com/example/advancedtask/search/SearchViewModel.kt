package com.example.advancedtask.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.advancedtask.data.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {
    private var _myCustomPosts: MutableLiveData<Response<ImageModel>> = MutableLiveData()
    val myCustomPosts: MutableLiveData<Response<ImageModel>> = _myCustomPosts

    private var query: String = ""

    fun searchImage(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                query.isNullOrEmpty().not() -> {
                    val response = repository.searchImage(query!!, "accuracy")
                    Log.d("response", response.toString())
                    _myCustomPosts.value = response
                }

            }

        }
    }

    class SearchViewModelFactory(private val repository: SearchRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }else throw IllegalArgumentException("Not found ViewModel class.")
        }

    }
}