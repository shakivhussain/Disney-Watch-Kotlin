package com.shakiv.husain.disneywatch.data.model.image

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("backdrops") val backdrops: List<Image>,
    @SerializedName("id") val id: Int,
    @SerializedName("logos") val logos: List<Image>,
    @SerializedName("posters") val posters: List<Image>
)