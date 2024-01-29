package com.example.advancedtask.search

import com.example.advancedtask.data.ImageModel
import com.example.advancedtask.retrofit.KakaoApi
import retrofit2.Response

class SearchRepository {
    suspend fun searchImage(query : String, sort : String) : Response<ImageModel> {
        return KakaoApi.kakaoNetworkApi.searchImage(query = query, sort = sort, page = 1, size = 80)
    }
}