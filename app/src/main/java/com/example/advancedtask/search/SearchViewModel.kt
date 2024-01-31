package com.example.advancedtask.search

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.retrofit.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _myCustomPosts: MutableLiveData<MutableList<SearchModel>> = MutableLiveData()
    val myCustomPosts: LiveData<MutableList<SearchModel>> get() = _myCustomPosts
    private val _selectedItem: MutableLiveData<SearchModel> = MutableLiveData()
    val selectedItem: LiveData<SearchModel> get() = _selectedItem

    private val _recyclerViewState: MutableLiveData<Parcelable> = MutableLiveData()
    val recyclerViewState: LiveData<Parcelable> get() = _recyclerViewState

    private var _search = ""

    fun saveRecyclerViewState(state:Parcelable) {
        _recyclerViewState.value = state
    }
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

    fun selectItem(position: Int, item: SearchModel) {
        when (item.bookMark) {
            true -> {
                _myCustomPosts.value?.get(position)?.bookMark = false
                item.bookMark = false

            }
            false -> {
                _myCustomPosts.value?.get(position)?.bookMark = true
                item.bookMark = true
            }

        }
        Log.d("SpecialThanks","예야 SettingItemBookmark in Viewmodel : $item")
        _selectedItem.postValue(item)

    }


    class SearchViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }else throw IllegalArgumentException("Not found ViewModel class.")
        }

    }
}