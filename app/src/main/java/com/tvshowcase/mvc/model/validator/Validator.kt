package com.tvshowcase.mvc.model.validator

import com.tvshowcase.mvc.model.domain.Episodes

interface Validator {

    fun validate(episode: Episodes): Boolean
}
