package com.tvshowcase.mvc.model

import com.tvshowcase.mvc.model.validator.EpisodesValidator

class ModelModule private constructor(private val jsonSource: String) {
    var dataAccessLayer: DataAccessLayer

    companion object {
        private var instance: ModelModule? = null
        fun getInstance(jsonSource: String): ModelModule {
            if (instance == null) instance = ModelModule(jsonSource)
            return instance as ModelModule
        }
        fun clearInstance()
        {
            instance = null;
        }
    }

    init {
        dataAccessLayer = DataAccessLayer(EpisodesValidator(), SeasonRepository(jsonSource))
    }
}