package com.shakiv.husain.disneywatch.data.model.cast

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Job

data class Cast(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("original_name") val original_name: String? = "",
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("cast_id") val cast_id: Int? = 0,
    @SerializedName("character") val character: String? = "",
    @SerializedName("credit_id") val credit_id: String? = "",
    @SerializedName("gender") val gender: Int? = 0,
    @SerializedName("known_for_department") val known_for_department: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("order") val order: Int? = 0,
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("profile_path") val profile_path: String? = "",
    @SerializedName("department") val department: String,
    @SerializedName("job") val job: String?="",

)