package com.muhammadnaseem.cis_2818_project4

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.http.GET

data class DadJoke(val id: String, val joke: String)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
}

interface DadJokesService {
    @GET("/")
    suspend fun getRandomJoke(): ApiResult<DadJoke>
}

class DadJokesApiClient(private val baseUrl: String, private val apiKey: String) {

    private val retrofit: Retrofit

    init {
        val httpClient = createHttpClient()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey") // Add authorization header with API key
                .build()
            chain.proceed(request)
        })
        return httpClient.build()
    }

    fun getDadJokesService(): DadJokesService {
        return retrofit.create(DadJokesService::class.java)
    }
}
