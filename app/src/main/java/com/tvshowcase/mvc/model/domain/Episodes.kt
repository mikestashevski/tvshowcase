package com.tvshowcase.mvc.model.domain

import com.google.gson.annotations.SerializedName

data class Episodes (

	@SerializedName("Plot") val plot : String,
	@SerializedName("Rated") val rated : String,
	@SerializedName("Title") val title : String,
	@SerializedName("Ratings") val ratings : List<Ratings>,
	@SerializedName("Writer") val writer : String,
	@SerializedName("Actors") val actors : String,
	@SerializedName("Type") val type : String,
	@SerializedName("imdbVotes") val imdbVotes : Int,
	@SerializedName("seriesID") val seriesID : String,
	@SerializedName("Season") val season : Int,
	@SerializedName("Director") val director : String,
	@SerializedName("Released") val released : String,
	@SerializedName("Awards") val awards : String,
	@SerializedName("Genre") val genre : String,
	@SerializedName("imdbRating") val imdbRating : Double,
	@SerializedName("Poster") val poster : String,
	@SerializedName("Episode") val episode : Int,
	@SerializedName("Language") val language : String,
	@SerializedName("Country") val country : String,
	@SerializedName("Runtime") val runtime : String,
	@SerializedName("imdbID") val imdbID : String,
	@SerializedName("Metascore") val metascore : String,
	@SerializedName("Response") val response : Boolean,
	@SerializedName("Year") val year : Int,
	var isVisible: Boolean = true,
	var id: Int
) {

	override fun toString(): String {
		return "Season: " + season + " Episode: " + episode + " Title: " + title
	}
}