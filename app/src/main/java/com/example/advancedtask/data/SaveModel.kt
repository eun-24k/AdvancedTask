package com.example.advancedtask.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class SaveModel(
    val query: String,
    val position: Int
): Parcelable
