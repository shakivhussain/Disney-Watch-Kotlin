package com.shakiv.husain.disneywatch.data.model.image

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("aspect_ratio") val aspect_ratio: Double?=0.0,
    @SerializedName("file_path") val file_path: String?="",
    @SerializedName("height") val height: Int?=0,
    @SerializedName("iso_639_1") val iso_639_1: String?="",
    @SerializedName("vote_average") val vote_average: Double?=0.0,
    @SerializedName("vote_count") val vote_count: Int?=0,
    @SerializedName("width") val width: Int?=0
)