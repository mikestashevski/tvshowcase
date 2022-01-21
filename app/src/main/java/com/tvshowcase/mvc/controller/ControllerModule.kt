package com.tvshowcase.mvc.controller

import com.tvshowcase.mvc.model.ModelModule

class ControllerModule private constructor(jsonSource: String) {
    private val modelModule: ModelModule
    fun tvShowCaseController(): TvShowCaseController {
        return TvShowCaseController(modelModule.dataAccessLayer)
    }

    companion object {
        private var instance: ControllerModule? = null
        fun getInstance(jsonSource: String): ControllerModule {
            if (instance == null) instance = ControllerModule(jsonSource)
            return instance as ControllerModule
        }

        fun clearInstance() {
            instance = null
        }
    }

    init {
        modelModule = ModelModule.getInstance(jsonSource)
    }
}