package com.tvshowcase.mvc.model.observer


interface EpisodesObserver : DomainObserver {

    fun episodeSkipped()

}