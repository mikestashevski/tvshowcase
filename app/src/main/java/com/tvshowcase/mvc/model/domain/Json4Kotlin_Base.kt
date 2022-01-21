package com.tvshowcase.mvc.model.domain

import com.google.gson.annotations.SerializedName

data class Json4Kotlin_Base (

	@SerializedName("seasons") val seasons : List<Seasons>,
	@SerializedName("title") val title : String
)