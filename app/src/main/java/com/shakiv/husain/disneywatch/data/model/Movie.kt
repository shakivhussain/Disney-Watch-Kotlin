package com.shakiv.husain.disneywatch.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdrop_path: String? = "",
    @SerializedName("genre_ids") val genre_ids: List<Int>? = emptyList(),
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("original_language") val original_language: String? = "",
    @SerializedName("original_title") val original_title: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("poster_path") val poster_path: String? = "",
    @SerializedName("release_date") val release_date: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val vote_average: Double? = 0.0,
)