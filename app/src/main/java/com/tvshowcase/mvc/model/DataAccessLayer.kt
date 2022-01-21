package com.tvshowcase.mvc.model

import com.tvshowcase.mvc.model.domain.Episodes
import com.tvshowcase.mvc.model.domain.Json4Kotlin_Base
import com.tvshowcase.mvc.model.observer.DomainObserver
import com.tvshowcase.mvc.model.observer.EpisodesObserver
import com.tvshowcase.mvc.model.validator.EpisodesValidator
import com.tvshowcase.mvc.view.TVShowCaseActivity


class DataAccessLayer(
    private val episodesValidator: EpisodesValidator,
    private val seasonRepository: SeasonRepository
) {

    private val observers = mutableListOf<DomainObserver>()

    fun register(observer: DomainObserver) = observers.add(observer)

    fun unregister(observer: DomainObserver) = observers.remove(observer)

    fun performValidation(episodes: List<Episodes>) {
        for(anEpisode in episodes) {
            if (!episodesValidator.validate(anEpisode)) {
                notify(EpisodesObserver::episodeSkipped)
                break
            }
        }
    }

    fun changeEpisodeVisibilityByKeyword(keyword: String): Json4Kotlin_Base {
        for (aSeason in seasonRepository.show.seasons) {
            for (anEpisode in aSeason.episodes) {
                anEpisode.isVisible =
                    anEpisode.title.contains(keyword) || anEpisode.plot.contains(keyword)
            }
        }
        return seasonRepository.show
    }

    private fun notify(action: (EpisodesObserver) -> Unit) {
        observers.filterIsInstance<EpisodesObserver>().onEach { action(it) }
    }

    fun getEpisodesAsList():List<Episodes> {
        var retVal = ArrayList<Episodes>()
        for (aSeason in seasonRepository.show.seasons) {
            for (anEpisode in aSeason.episodes) {
                retVal.add(anEpisode)
            }
        }
        return retVal
    }

    fun getEpisodesAsStringList():List<TVShowCaseActivity.StringWithId> {
        var retVal = ArrayList<TVShowCaseActivity.StringWithId>()
        for (anEpisode in getEpisodesAsList()) {
            retVal.add(TVShowCaseActivity.StringWithId(anEpisode.id, anEpisode.toString()))
        }
        return retVal
    }

    fun getCountOfFilteredEpisodes(): Int {
        var retVal = 0
        for (anEpisode in getEpisodesAsList()) {
            if (!anEpisode.isVisible)
                retVal++
        }
        return retVal
    }

}