package com.shakiv.husain.disneywatch.data.model.details.tvshow

import com.google.gson.annotations.SerializedName
import com.shakiv.husain.disneywatch.data.model.details.movie.Genre

data class TvShowDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdrop_path: String? = "",
    @SerializedName("episode_run_time") val episode_run_time: List<String>? = null,
    @SerializedName("first_air_date") val first_air_date: String? = null,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("in_production") val in_production: Boolean? = false,
    @SerializedName("languages") val languages: List<String>? = emptyList(),
    @SerializedName("last_air_date") val last_air_date: String? = "",
    @SerializedName("last_episode_to_air") val last_episode_to_air: LastEpisodeToAir? = null,
    @SerializedName("name") val name: String? = "",
    @SerializedName("networks") val networks: List<Network>? = emptyList(),
    @SerializedName("next_episode_to_air") val next_episode_to_air: String? = "",
    @SerializedName("number_of_episodes") val number_of_episodes: Int? = 0,
    @SerializedName("number_of_seasons") val number_of_seasons: Int? = 0,
    @SerializedName("origin_country") val origin_country: List<String>? = emptyList(),
    @SerializedName("original_language") val original_language: String? = "",
    @SerializedName("original_name") val original_name: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val poster_path: String? = "",
    @SerializedName("production_companies") val production_companies: List<ProductionCompany>? = emptyList(),
    @SerializedName("production_countries") val production_countries: List<ProductionCountry>? = emptyList(),
    @SerializedName("seasons") val seasons: List<Season>? = emptyList(),
    @SerializedName("spoken_languages") val spoken_languages: List<SpokenLanguage>? = emptyList(),
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("vote_average") val vote_average: Double? = 0.0,
    @SerializedName("vote_count") val vote_count: Int? = 0
)