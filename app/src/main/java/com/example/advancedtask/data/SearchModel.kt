package com.example.advancedtask.data

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel(
    val image: Uri,
    val title: String,
    val type: String = "Image",
    val site: String,
    val date: String,
    @SerializedName("doc_url")
    val docUrl: String,
    var bookMark: Boolean = false
): Parcelable