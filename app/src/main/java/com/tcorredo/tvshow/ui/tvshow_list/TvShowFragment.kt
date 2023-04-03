package com.tcorredo.tvshow.ui.tvshow_list

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.databinding.FragmentTvShowBinding
import com.tcorredo.tvshow.ui.base.BaseFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : BaseFragment<FragmentTvShowBinding>(FragmentTvShowBinding::inflate) {

    private val tvShowViewModel by viewModel<TvShowViewModel>()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView(onQueryChanged = tvShowViewModel.accept)
        setupList(
            pagingData = tvShowViewModel.pagingDataFlow
        )
    }

    private fun setupSearchView(
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        binding.searchTvShow.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        binding.searchTvShow.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        binding.clearButton.setOnClickListener {
            binding.searchTvShow.text?.clear()
            updateListFromInput(onQueryChanged)
            binding.clearButton.visibility = GONE
        }
    }

    private fun updateListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        if (!binding.clearButton.isVisible) {
            binding.clearButton.visibility = VISIBLE
        }
        binding.searchTvShow.text?.trim().let {
            binding.recyclerView.scrollToPosition(0)
            onQueryChanged(UiAction.Search(query = it.toString()))

        }
    }

    private fun setupList(
        pagingData: Flow<PagingData<TvShow>>
    ) {
        tvShowAdapter = TvShowAdapter(object : TvShowAdapter.OnItemClickListener {
            override fun onItemClick(tvShow: TvShow) {
                val action = TvShowFragmentDirections.actionNavTvShowsToNavTvShowsDetails(tvShow)
                findNavController().navigate(action)
            }
        })

        binding.messageLayout.retryButton.setOnClickListener { tvShowAdapter.retry() }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = tvShowAdapter
        }

        lifecycleScope.launch {
            pagingData.collectLatest(tvShowAdapter::submitData)
        }

        lifecycleScope.launch {
            try {
                tvShowAdapter.loadStateFlow.collect { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && tvShowAdapter.itemCount == 0
                    binding.messageLayout.infoLayout.isVisible = isListEmpty
                    binding.recyclerView.isVisible =
                        loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                    binding.messageLayout.loadingProgress.isVisible =
                        loadState.source.refresh is LoadState.Loading
                    binding.messageLayout.retryButton.isVisible =
                        loadState.source.refresh is LoadState.Error

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        binding.messageLayout.infoText.text = it.error.message
                    }
                }
            } catch (exception: Exception) {
                Log.e(tag, exception.message.toString())
            }
        }
    }

    companion object {
        const val SPAN_COUNT = 3
    }
}