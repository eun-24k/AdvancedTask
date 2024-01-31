package com.example.advancedtask.data

object SelectedList {
    var selectedItems: ArrayList<SaveModel> = arrayListOf()

    fun addModel(searchModel: SaveModel) {
        selectedItems.add(searchModel)
    }
    fun removeModel(searchModel: SaveModel) {
        selectedItems.remove(searchModel)
    }

}