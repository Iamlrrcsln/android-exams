package com.example.myapp.network

import com.example.myapp.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getPersons(@Query("page") page: Int, @Query("results") results: Int): ApiResponse
}
