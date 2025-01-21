package com.sdc.aicookmate

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // OpenAI 공식 엔드포인트
    private const val BASE_URL = "https://api.openai.com/"

    // 사내 Enterprise 계정의 조직/프로젝트 정보
    private const val ORG_ID = "org-5c4IkgJ6HYzanbyAuEDGfzNX"
    private const val PROJECT_ID = "proj_KlVwLVw5eITqIpXSxg1CWqeM"

    // 로깅 인터셉터: 요청/응답을 BODY 레벨까지 상세히 로깅
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient: 로그 인터셉터 + 사용자 지정 인터셉터(조직/프로젝트 헤더)
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                // gradle 빌드시 주입된 API 키 (sk-proj-...)
                val apiKey = BuildConfig.OPENAI_API_KEY
                Log.d("API Key Check", "API Key: $apiKey")

                // 기존 요청에 헤더 추가
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .addHeader("OpenAI-Organization", ORG_ID)
                    .addHeader("OpenAI-Project", PROJECT_ID)

                // 수정한 요청을 진행
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    // Retrofit: OkHttpClient 사용 + GsonConverter
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
