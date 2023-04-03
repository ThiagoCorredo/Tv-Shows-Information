package com.tcorredo.tvshow.ui.actor

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.databinding.FragmentActorBinding
import com.tcorredo.tvshow.ui.base.BaseFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActorFragment : BaseFragment<FragmentActorBinding>(FragmentActorBinding::inflate) {

    private val actorViewModel by viewModel<ActorViewModel>()
    private val actorAdapter = ActorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList(
            pagingData = actorViewModel.pagingDataFlow
        )
    }

    private fun setupList(
        pagingData: Flow<PagingData<Actor>>
    ) {

        binding.messageLayout.retryButton.setOnClickListener { actorAdapter.retry() }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = actorAdapter
        }

        lifecycleScope.launch {
            pagingData.collectLatest(actorAdapter::submitData)
        }

        lifecycleScope.launch {
            actorAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && actorAdapter.itemCount == 0
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
        }
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}