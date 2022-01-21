package com.tvshowcase.mvc.view

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.tvshowcase.mvc.R
import com.tvshowcase.mvc.controller.ControllerModule
import com.tvshowcase.mvc.controller.TvShowCaseController
import com.tvshowcase.mvc.model.DataAccessLayer
import com.tvshowcase.mvc.model.ModelModule
import com.tvshowcase.mvc.model.domain.Episodes
import com.tvshowcase.mvc.model.observer.EpisodesObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.AdapterView.OnItemClickListener
import android.graphics.BitmapFactory

import kotlinx.coroutines.*
import java.net.URL


class TVShowCaseActivity : AppCompatActivity(), EpisodesListRendererInterface, EpisodesObserver {

    /**
     * Holds an ID for mapping between the widget and the data
     */
    class StringWithId(var anId: Int, var aString: String) : CharSequence {

        fun getId(): Int {
            return anId
        }

        override val length: Int
            get() = aString.length

        override fun get(index: Int): Char {
            return aString.get(index);
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return aString.subSequence(startIndex, endIndex)
        }

        override fun toString(): String {
            return aString
        }
    }

    private lateinit var jsonSource: String
    private lateinit var modelModule: ModelModule
    private lateinit var model: DataAccessLayer
    private lateinit var controllerModule: ControllerModule
    private lateinit var controller: TvShowCaseController
    private lateinit var itemsAdapter: ArrayAdapter<StringWithId>
    private lateinit var edvTextView: TextView
    private lateinit var edvImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonSource = resources.openRawResource(R.raw.hbo_show).bufferedReader().use { it.readText() } //"it" is BufferedReader
        modelModule = ModelModule.getInstance(jsonSource)
        model = modelModule.dataAccessLayer
        controllerModule = ControllerModule.getInstance(jsonSource)
        controller = controllerModule.tvShowCaseController()
        controller.bind(this)
        //TODO use custom layout and add episode image in the ListView
        itemsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, model.getEpisodesAsStringList())

        listViewEpisodes.adapter = itemsAdapter

        //TODO this should be handled in EpisodeDetailsView
        edvTextView = findViewById(R.id.season_episode_plot_text_view)
        edvImageView = findViewById(R.id.cover_image)

        //Stream API is supported on Marshmallow with Gradle >6
        listViewEpisodes.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as StringWithId
            val currentEpisode = model.getEpisodesAsList().stream().filter{anEpisode->anEpisode.id==selectedItem.getId()}.toArray()[0] as Episodes
            edvTextView.setText("Season: ${currentEpisode.season}, Episode: ${currentEpisode.episode}, Plot: ${currentEpisode.plot}")
            loadEpisodeImage(currentEpisode.poster)
        })
    }

    private fun loadEpisodeImage(stringUrl: String) {
        val bitmap = runBlocking {
            withContext(Dispatchers.IO) {
                val url = URL(stringUrl)
                BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }
        }
        edvImageView.setImageBitmap(bitmap)
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }

    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun episodeSkipped() {
        showMessage(getString(R.string.no_of_filtered_episodes)+model.getCountOfFilteredEpisodes())
    }

    /**
     * Retrieve a list of all episodes used in this activity
     *
     * It can be eg. a merge of the
     * 1. "static" JSON based list from modelModule
     * and a
     * 2a. "dynamic" (externally fetched) list from a non-yet-implemented
     * model module or
     * 2b. episodes added through this activity
     */
    override fun getViewEpisodes(): List<Episodes> {
        return modelModule.dataAccessLayer.getEpisodesAsList()
    }

    fun filterListView(view: android.view.View) {
        val keyword = findViewById<TextView>(R.id.searchTextView).text.toString()
        modelModule.dataAccessLayer.changeEpisodeVisibilityByKeyword(keyword);
        controller.onValidateEpisode() //shows a notification if episodes are filtered out

        var newEpisodeList: ArrayList<StringWithId> = ArrayList<StringWithId>()
        model.getEpisodesAsList().stream().filter{ episodes -> episodes.isVisible}.map{ episodes -> StringWithId(episodes.id, episodes.toString()) }.forEach{aString -> newEpisodeList.add(aString)}
        itemsAdapter.clear();
        itemsAdapter.addAll(newEpisodeList);
        itemsAdapter.notifyDataSetChanged();
    }
}
