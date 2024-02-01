package com.example.advancedtask.search

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.advancedtask.data.SaveModel
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.data.SelectedList.selectedItems
import com.example.advancedtask.retrofit.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _myCustomPosts: MutableLiveData<MutableList<SearchModel>> = MutableLiveData()
    val myCustomPosts: LiveData<MutableList<SearchModel>> get() = _myCustomPosts
    private val _selectedItem: MutableLiveData<SearchModel> = MutableLiveData()
    val selectedItem: LiveData<SearchModel> get() = _selectedItem

    private val _selectedList: MutableLiveData<MutableList<SearchModel>?> = MutableLiveData()
    val selectedList: LiveData<MutableList<SearchModel>?> get() = _selectedList

    private val _recyclerViewState: MutableLiveData<Parcelable> = MutableLiveData()
    val recyclerViewState: LiveData<Parcelable> get() = _recyclerViewState

    private val _search: MutableLiveData<String> = MutableLiveData()
    val search : LiveData<String> = _search

    private lateinit var tag: SaveModel

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
//                    synchronizeLikes()
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
//                    synchronizeLikes()
                }

            }

        }
    }

    fun searchTextLive(searchText: String) {
        _search.value = searchText
    }

    fun selectItem(position: Int, item: SearchModel) {
//        val isBookmarked = when (item.bookMark) {
//            true -> false
//            false -> true
//        }
//        _myCustomPosts.value?.get(position)?.bookMark = isBookmarked
//        item.bookMark = isBookmarked
//        tag = SaveModel(search.value!!, position)
        var post = _myCustomPosts.value
        var list = _selectedList.value
        when (item.bookMark) {
            true -> {
                post?.get(position)?.bookMark  = false
//                list?.remove(item)
                item.bookMark = false

            }
            false -> {
                post?.get(position)?.bookMark  = true
                item.bookMark = true
//                list?.add(item)
            }
        }
//        synchronizeLikes()
        Log.d("SpecialThanks","예야 SettingItemBookmark in Viewmodel : $item")
        _selectedItem.value = item
        _myCustomPosts.value = post!!
    }

    fun setSelectedList() {
        _selectedList.value = selectedItems

    }
//
//    private fun synchronizeLikes() {
//
//        keepTrackOfQueryAndPosition.find { it.query == _search.value }.apply {
//            keepTrackOfQueryAndPosition.indexOfFirst {  it.query == _search.value }.apply {
//                when (_myCustomPosts.value?.get(this)?.bookMark) {
//                    true -> {
//                        _myCustomPosts.value?.get(this)?.bookMark = false
//                    }
//                    false -> _myCustomPosts.value?.get(this)?.bookMark = true
//                    null -> Unit
//                }
//                _selectedItem.value = _myCustomPosts.value?.get(this)
//
//            }
//        }
//
//    }


    class SearchViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }else throw IllegalArgumentException("Not found ViewModel class.")
        }

    }
}