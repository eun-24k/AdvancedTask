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

    private val _search: MutableLiveData<String> = MutableLiveData()
    val search : LiveData<String> = _search

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
                _search.value.isNullOrEmpty().not() -> {
                    val response = repository.searchData(_search.value!!, "accuracy", page)
                    Log.d("response", response.toString())
                    _myCustomPosts.postValue(response.toMutableList())
                }

            }

        }
    }

    fun searchTextLive(searchText: String) {
        _search.value = searchText
    }

    fun selectItem(position: Int, item: SearchModel) {
        val isBookmarked = when (item.bookMark) {
            true -> false
            false -> true
        }
        _myCustomPosts.value?.get(position)?.bookMark = isBookmarked
        item.bookMark = isBookmarked

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