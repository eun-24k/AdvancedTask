package com.example.advancedtask.data

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.UUID


@Parcelize
data class SearchModel(
    val id: UUID,
    val thumbnail: Uri,
    val title: String,
    val date: String,
    var bookMark: Boolean = false
): Parcelable