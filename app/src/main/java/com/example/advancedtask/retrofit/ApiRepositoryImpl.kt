package com.example.advancedtask.retrofit

import androidx.core.net.toUri
import com.example.advancedtask.data.ImageModel
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.utils.ApiKey.Companion.REST_API_KEY
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApiRepositoryImpl: ApiRepository {
    private val searchList: ArrayList<SearchModel> = arrayListOf()
    private val formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss")

    private suspend fun searchImage(
        query: String,
        sort: String,
        page: Int
    ): Response<ImageModel> {
        return KakaoClient.kakaoNetworkApi.searchImage(
            apiKey = "KakaoAK $REST_API_KEY",
            query = query,
            sort = sort,
            page = page,
            size = 80
        )
    }

    override suspend fun searchData(query: String, sort: String, page: Int): MutableList<SearchModel> {
        if (page == 1) searchList.clear()
        searchList.addAll(convertToSearchModel(query,sort,page))
        return searchList
    }



    private suspend fun convertToSearchModel(query: String, sort: String, page: Int): MutableList<SearchModel> {
        val imageApi = searchImage(query, sort, page)
        searchList.clear()

        if (imageApi.isSuccessful) {
            imageApi.body()?.documents?.map { document ->
                SearchModel(
                    thumbnail = document.thumbnailUrl!!.toUri(),
                    title = document.displaySitename.toString(),
                    date = dateTimeFormatter(document.datetime.toString()),
                    bookMark = false
                )
            }?.let { searchList.addAll(it) }
        }
        return searchList
    }


    private fun dateTimeFormatter(date: String): String {
        val originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val targetFormatter = formatter
        val dateTime = LocalDateTime.parse(date, originalFormatter)
        return dateTime.format(targetFormatter)
    }
}

interface ApiRepository {
    suspend fun searchData(query: String, sort: String, page: Int): MutableList<SearchModel>
}