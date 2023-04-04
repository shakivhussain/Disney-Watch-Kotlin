package com.shakiv.husain.disneywatch.data.model.videos

import com.google.gson.annotations.SerializedName

data class MoviePreviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val previewList: List<MoviePreview>
)