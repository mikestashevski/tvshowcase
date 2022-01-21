package com.tvshowcase.mvc.model.domain

import com.google.gson.annotations.SerializedName

data class Seasons (

	@SerializedName("episodes") val episodes : List<Episodes>
)