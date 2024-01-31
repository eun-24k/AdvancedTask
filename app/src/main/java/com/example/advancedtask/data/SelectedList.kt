package com.example.advancedtask.data

object SelectedList {
    var selectedItems: MutableList<SearchModel> = mutableListOf()

    fun addModel(searchModel: SearchModel) {
        selectedItems.add(searchModel)
    }
    fun removeModel(searchModel: SearchModel) {
        selectedItems.remove(searchModel)
    }

    var keepTrackOfQueryAndPosition: ArrayList<SaveModel> = arrayListOf()

    fun addTag(saveModel: SaveModel) {
        keepTrackOfQueryAndPosition.add(saveModel)
    }
    fun removeTag(saveModel: SaveModel) {
        keepTrackOfQueryAndPosition.add(saveModel)
    }


}