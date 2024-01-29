package com.example.advancedtask.retrofit

import android.net.Uri
import com.example.advancedtask.data.ImageModel
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.utils.ApiKey.Companion.REST_API_KEY
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApiRepository {
    private val searchList: MutableList<SearchModel> = mutableListOf()

    private suspend fun searchImage(
        query: String,
        sort: String,
        page: Int
    ): Response<ImageModel> {
        return KakaoApi.kakaoNetworkApi.searchImage(
            apiKey = "KakaoAK ${REST_API_KEY}",
            query = query,
            sort = sort,
            page = page,
            size = 80
        )
    }

    suspend fun searchData(query: String, sort: String, page: Int): MutableList<SearchModel> {
        val imageApi = searchImage(query, sort, page)
        searchList.clear()

        if (imageApi.isSuccessful) {
            imageApi.body()?.documents?.map { document ->
                SearchModel(
                    image = Uri.parse(document.imageUrl),
                    title = document.collection,
                    site = document.displaySitename,
                    date = dateTimeFormatter(document.datetime),
                    docUrl = document.docUrl
                )
            }?.let { searchList.addAll(it) }
        }
        return searchList
    }


    fun dateTimeFormatter(date: String): String {
        val originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val targetFormatter = DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss")
        val dateTime = LocalDateTime.parse(date, originalFormatter)
        return dateTime.format(targetFormatter)
    }
}