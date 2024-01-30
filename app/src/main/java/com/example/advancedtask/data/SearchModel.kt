package com.example.advancedtask.data

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchModel(
    val thumbnail: Uri,
    val title: String,
    val date: String,
    var bookMark: Boolean = false
): Parcelable