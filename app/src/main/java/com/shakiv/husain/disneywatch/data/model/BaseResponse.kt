package com.shakiv.husain.disneywatch.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") var data: T?,
    @SerializedName("total_results") val total_results: Int?,
    @SerializedName("total_pages") val total_pages: Int?
)