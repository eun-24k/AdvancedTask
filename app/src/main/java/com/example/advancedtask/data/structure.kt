package com.example.advancedtask.data

import com.google.gson.annotations.SerializedName

data class Document(
    val collection: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val width: Int?,
    val height: Int?,
    @SerializedName("display_sitename")
    val displaySitename: String?,
    @SerializedName("doc_url")
    val docUrl: String?,
    val datetime: String?
)
data class ImageModel(
    val meta: MetaData,
    val documents: List<Document>
)
data class MetaData(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)