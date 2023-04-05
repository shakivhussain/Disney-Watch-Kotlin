package com.shakiv.husain.disneywatch.data.model.details

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
//    @SerializedName("belongs_to_collection") val belongs_to_collection: Any,
    @SerializedName("budget") val budget: Int?=0,
    @SerializedName("genres") val genres: List<Genre>?= emptyList(),
    @SerializedName("homepage") val homepage: String?="",
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String?="",
    @SerializedName("original_language") val original_language: String?="",
    @SerializedName("original_title") val original_title: String?="",
    @SerializedName("overview") val overview: String?="",
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String?="",
//    @SerializedName("production_companies") val production_companies: List<ProductionCompany>,
//    @SerializedName("production_countries") val production_countries: List<ProductionCountry>,
    @SerializedName("release_date") val release_date: String?="",
    @SerializedName("revenue") val revenue: Long?=0L,
    @SerializedName("runtime") val runtime: Int?=0,
//    @SerializedName("spoken_languages") val spoken_languages: List<SpokenLanguage>,
    @SerializedName("status") val status: String?="",
    @SerializedName("tagline") val tagline: String?="",
    @SerializedName("title") val title: String?="",
    @SerializedName("video") val video: Boolean?=false,
    @SerializedName("vote_average") val vote_average: Double?=0.0,
    @SerializedName("vote_count") val vote_count: Int?=0
)