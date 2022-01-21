package com.tvshowcase.mvc.model.validator

import com.tvshowcase.mvc.model.domain.Episodes


class EpisodesValidator : Validator {

    override fun validate(episode: Episodes): Boolean {
        return episode.isVisible;
    }

}