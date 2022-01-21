package com.tvshowcase.mvc.model

import com.google.gson.Gson
import com.tvshowcase.mvc.model.domain.Json4Kotlin_Base

class SeasonRepository(private var jsonSource: String) {
    internal var show: Json4Kotlin_Base = setVisible(Gson().fromJson(jsonSource, Json4Kotlin_Base::class.java))

    private fun setVisible(fromJson: Json4Kotlin_Base?): Json4Kotlin_Base {
        if (fromJson != null) {
            var episodeCounter = 0
            for (aSeason in fromJson.seasons)
            {
                for (anEpisode in aSeason.episodes)
                {
                    episodeCounter++
                    anEpisode.isVisible = true
                    anEpisode.id = episodeCounter
                }
            }
        }
        return fromJson!! // throwing NPE at this point, in the model, is acceptable - there is no model to work with
    }
}
