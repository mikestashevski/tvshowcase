package com.tvshowcase.mvc.model.domain

import com.google.gson.annotations.SerializedName

data class Ratings (

	@SerializedName("Source") val source : String,
	@SerializedName("Value") val value : String
)