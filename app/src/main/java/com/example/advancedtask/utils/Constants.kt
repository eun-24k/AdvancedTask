package com.example.advancedtask.utils

import com.example.advancedtask.utils.ApiKey.Companion.REST_API_KEY

class Constants {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
        const val AUTH_HEADER = "KakaoAK $REST_API_KEY"
    }
}