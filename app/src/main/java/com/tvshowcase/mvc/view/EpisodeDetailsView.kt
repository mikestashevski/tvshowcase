package com.tvshowcase.mvc.view

import android.content.Context
import android.widget.LinearLayout
import android.support.annotation.RequiresApi
import android.os.Build
import android.util.AttributeSet
import com.tvshowcase.mvc.R

class EpisodeDetailsView : LinearLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.episode_details_view, this)
    }
}