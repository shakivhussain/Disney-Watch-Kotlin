package com.shakiv.husain.disneywatch.util

object ApiConstants {


    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "6e86582399de8bbaad1885031840a532" // TODO : Change it before push

    // Api's Params
    const val KEY_API ="api_key"
    const val KEY_PAGE="page"
    const val KEY_MOVIE_ID ="movie_id"
    const val KEY_MOVIE_ID_PATH ="movie_id"
    const val KEY_QUERY = "query"

    // Image URL'S
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/"
    const val IMAGE_URL_SIZE_500W = "t/p/w500/"
    private const val IMAGE_URL_ORIGINAL = "t/p/original/"

    const val BASE_URL_WITH_IMG_SIZE_500W = "$IMAGE_BASE_URL$IMAGE_URL_ORIGINAL"
    const val BASE_URL_WITH_ORIGINAL = "$IMAGE_BASE_URL$IMAGE_URL_ORIGINAL"

    // End Points
    const val POPULAR_MOVIES = "movie/popular"
    const val TOP_RATED_MOVIES = "movie/top_rated"
    const val UPCOMING_MOVIES = "movie/upcoming"


}