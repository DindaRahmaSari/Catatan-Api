package com.example.latihan_api_dinda.entities

import com.google.gson.annotations.SerializedName

data class Catatan(
    val id: Int?,
    val judul:String,
    val isi: String,
    @SerializedName("user_id") // Pastikan import com.google.gson.annotations.SerializedName
    val userId: Int
)
