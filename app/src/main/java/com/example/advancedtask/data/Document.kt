package com.example.advancedtask.data

import com.google.gson.annotations.SerializedName

data class Document(
    val collection: String,
    @SerializedName("thumnail_uri")
    val thumbnailUrl: String?,
    @SerializedName("image_uri")
    val imageUrl: String,
    val width: Int,
    val height: Int,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("doc_url")
    val docUrl: String,
    val datetime: String

)