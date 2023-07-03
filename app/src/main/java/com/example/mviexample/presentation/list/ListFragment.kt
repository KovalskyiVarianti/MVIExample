package com.example.mviexample.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviexample.R
import com.example.mviexample.databinding.FragmentListBinding
import com.example.mviexample.presentation.detail.DetailFragment
import com.example.mviexample.presentation.list.adapter.EntityListAdapter
import com.example.mviexample.presentation.list.effect.ListEffect
import com.example.mviexample.presentation.list.intent.ListIntent
import com.example.mviexample.presentation.list.state.ListState
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    companion object {
        fun getInstance() = ListFragment()
    }

    private var binding: FragmentListBinding? = null
    private val listViewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view).apply {
            with(list) {
                addItemDecoration(
                    MaterialDividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                adapter = EntityListAdapter(
                    onClick = { id: Int ->
                        listViewModel.sendIntent(ListIntent.ClickEntity(id))
                    }
                )
            }
        }

        listViewModel.sendIntent(ListIntent.GetEntities)

        lifecycleScope.launch {
            listViewModel.getState().flowWithLifecycle(
                lifecycle, Lifecycle.State.CREATED
            ).collect(::handleState)
        }

        lifecycleScope.launch {
            listViewModel.getEffect().flowWithLifecycle(
                lifecycle, Lifecycle.State.CREATED
            ).collect(::handleEffect)
        }
    }

    private fun handleState(state: ListState) = binding?.apply {
        when (state) {
            ListState.Loading -> {
                progressBar.show()
            }
            is ListState.Data -> {
                progressBar.hide()
                (list.adapter as? EntityListAdapter)?.submitList(state.entities)
            }
        }
    }

    private fun handleEffect(effect: ListEffect) {
        when (effect) {
            is ListEffect.NavigateToDetail -> {
                parentFragmentManager.commit {
                    add(R.id.fragmentContainer, DetailFragment.getInstance(effect.id))
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}