package com.teamfillin.fillin.data.response

data class ResponseSearchInfo(
    val studios: List<StudioInfo>
) {
    data class StudioInfo(
        val id: Int,
        val name: String,
        val address: String
    )
}
