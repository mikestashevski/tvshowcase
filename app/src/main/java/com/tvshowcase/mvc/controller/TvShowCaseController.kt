package com.tvshowcase.mvc.controller

import com.tvshowcase.mvc.model.DataAccessLayer
import com.tvshowcase.mvc.view.EpisodesListRendererInterface


class TvShowCaseController(private val model: DataAccessLayer) {

    private lateinit var listRendererInterface: EpisodesListRendererInterface

    fun bind(episodesListRendererInterface: EpisodesListRendererInterface) {
        listRendererInterface = episodesListRendererInterface
    }

    fun onValidateEpisode() {
        model.performValidation(listRendererInterface.getViewEpisodes())
    }

}