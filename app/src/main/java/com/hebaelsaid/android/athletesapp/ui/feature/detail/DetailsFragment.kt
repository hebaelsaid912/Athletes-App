package com.hebaelsaid.android.athletesapp.ui.feature.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hebaelsaid.android.athletesapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailsFragment"
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel:DetailsViewModel by viewModels()
    private  var athleteId : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        athleteId = arguments?.getString("athlete_id")!!.toInt()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAthleteData(athleteId)
        renderAthletesListData()
        binding.itemDetailsBackImg.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun renderAthletesListData() {
        lifecycleScope.launchWhenCreated {
            viewModel.athleteState.collect { state ->
                when (state) {
                    is DetailsViewModel.AthleteDetailsState.Success -> {
                        Log.d(TAG, "renderProductListData: Success")
                        binding.model = state.responseModel
                    }
                    is DetailsViewModel.AthleteDetailsState.Error -> {
                        Log.d(TAG, "renderProductListData: Error ${state.message}")
                    }
                    is DetailsViewModel.AthleteDetailsState.Loading -> {
                        Log.d(TAG, "renderProductListData: Loading")
                    }
                    is DetailsViewModel.AthleteDetailsState.Idle -> {
                        Log.d(TAG, "renderProductListData: Idle")
                    }
                }
            }
        }
    }

}