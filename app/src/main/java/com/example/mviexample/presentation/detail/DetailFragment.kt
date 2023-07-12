package com.example.mviexample.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mviexample.R
import com.example.mviexample.databinding.FragmentDetailBinding
import com.example.mviexample.presentation.detail.effect.DetailEffect
import com.example.mviexample.presentation.detail.intent.DetailIntent
import com.example.mviexample.presentation.detail.state.DetailState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {

        private const val KEY_ENTITY_ID = "KEY_ENTITY_ID"

        fun getInstance(id: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_ENTITY_ID, id)
            }
        }
    }

    private var binding: FragmentDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        val id = arguments?.getInt(KEY_ENTITY_ID)

        detailViewModel.sendIntent(DetailIntent.GetEntity(id))

        binding?.apply {
            updateDescriptionButton.setOnClickListener {
                val newDescription = descriptionInput.text?.toString().orEmpty()
                detailViewModel.sendIntent(DetailIntent.ChangeEntityDescription(id, newDescription))
            }
        }

        lifecycleScope.launch {
            detailViewModel.getState().flowWithLifecycle(
                lifecycle,
                Lifecycle.State.CREATED
            ).collect(::handleState)
        }

        lifecycleScope.launch {
            detailViewModel.getEffect().flowWithLifecycle(
                lifecycle,
                Lifecycle.State.CREATED
            ).collect(::handleEffect)
        }

        CoroutineScope(Dispatchers.Main).launch {


            var testClass = TestClass("")
            var i = 0
            while (true) {
                testClass = TestClass(testClass.name + i++)
                println(testClass.name)
                delay(1)
            }
        }
    }

    private fun handleState(state: DetailState) {
        binding?.apply {
            when (state) {
                DetailState.Loading -> {
                    progressBar.show()
                }
                is DetailState.Data -> {
                    progressBar.hide()
                    name.text = state.entity.name
                    description.text = state.entity.description
                }
            }
        }
    }

    private fun handleEffect(effect: DetailEffect) {
        when (effect) {
            is DetailEffect.Error -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

class TestClass(val name: String)