package com.tcorredo.tvshow.ui.tvshow_details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.tcorredo.tvshow.R
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.databinding.ActivityTvShowDetailsBinding
import com.tcorredo.tvshow.ui.base.BaseActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowDetailsActivity : BaseActivity<ActivityTvShowDetailsBinding>() {

    override fun getViewBinding() = ActivityTvShowDetailsBinding.inflate(layoutInflater)

    private val tvShowDetailsViewModel by viewModel<TvShowDetailsViewModel>()
    private val episodeAdapter = EpisodeAdapter()
    private lateinit var genderAdapter: GenderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        val tvShow = intent.getParcelableExtra<TvShow>(TV_SHOW_EXTRA)
        title = tvShow?.name

        lifecycleScope.launch {
            tvShowDetailsViewModel.getTvShowDetails(tvShow).collect { state ->
                when (state) {
                    is TvShowDetailsState.Loading -> {
                        binding.messageLayout.loadingProgress.show()
                        binding.tvShowPoster.visibility = GONE
                        binding.tvShowDescription.visibility = GONE
                        binding.tvShowGoesOn.visibility = GONE
                        binding.tvShowSeason.visibility = GONE
                    }
                    is TvShowDetailsState.Success -> {
                        setupSuccessView(state)
                    }
                    is TvShowDetailsState.Error -> {
                        val errorMessage = state.message
                        binding.messageLayout.loadingProgress.hide()
                        binding.tvShowPoster.visibility = GONE
                        binding.tvShowDescription.visibility = GONE

                        binding.messageLayout.infoText.text = errorMessage

                        binding.messageLayout.retryButton.setOnClickListener {
                            tvShowDetailsViewModel.getTvShowDetails(tvShow)
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }


    private fun setupSuccessView(state: TvShowDetailsState.Success) {
        val tvShowDetails = state.tvShowDetails
        binding.messageLayout.loadingProgress.hide()
        binding.tvShowPoster.visibility = VISIBLE
        binding.tvShowDescription.visibility = VISIBLE
        binding.tvShowGoesOn.visibility = VISIBLE
        binding.tvShowSeason.visibility = VISIBLE

        binding.tvShowPoster.load(tvShowDetails.tvShow?.imageOriginal) {
            crossfade(true)
            error(R.drawable.ic_broken_image_24dp)
            transformations(RoundedCornersTransformation())
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }

        genderAdapter = GenderAdapter(tvShowDetails.tvShow?.genres ?: emptyList())
        binding.tvShowGender.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genderAdapter
        }

        binding.tvShowDescription.text = HtmlCompat.fromHtml(
            tvShowDetails.tvShow?.summary.toString(),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        if (!tvShowDetails.tvShow?.scheduleDays.isNullOrEmpty()
            && !tvShowDetails.tvShow?.scheduleTime.isNullOrEmpty()
        ) {
            binding.tvShowGoesOn.text = getString(
                R.string.show_goes_on,
                tvShowDetails.tvShow?.scheduleDays?.joinToString(", "),
                tvShowDetails.tvShow?.scheduleTime
            )
        }

        val spinnerSeasonAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            tvShowDetails.tvShowSeasons.map { getString(R.string.season_number, it.seasonNumber) }
        )

        binding.tvShowSeason.adapter = spinnerSeasonAdapter
        binding.tvShowSeason.setSelection(initialSeasonIndex)

        binding.tvShowSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedSeason = tvShowDetails.tvShowSeasons[position]
                val selectedSeasonEpisodes = selectedSeason.seasonEpisodes
                episodeAdapter.submitList(selectedSeasonEpisodes)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.tvShowEpisode.apply {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = episodeAdapter
        }
    }

    companion object {
        const val TV_SHOW_EXTRA = "tvShow"
        const val initialSeasonIndex = 0
        const val SPAN_COUNT = 2
    }
}