package com.example.latihan_api_dinda.repository

import com.example.latihan_api_dinda.entities.Catatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CatatanRepository {
    @POST("catatan")
    suspend fun createCatatan(@Body catatan: Catatan): Response<Catatan>
}