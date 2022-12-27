package com.hebaelsaid.android.athletesapp.ui.feature.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hebaelsaid.android.athletesapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SplashFragment"
@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val viewModel:SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            renderAthletesListData()
        }, 3000)
    }

    private fun renderAthletesListData() {
        lifecycleScope.launchWhenCreated {
            viewModel.athletesListState.collect { state ->
                when(state){
                    is SplashViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderAthletesListData: Success")
                        Log.d(TAG, "renderAthletesListData: list size ${state.athletesListResponseModel.athletes?.size}")
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeListFragment())
                    }
                    is SplashViewModel.AthletesListState.Error -> {
                        Log.d(TAG, "renderAthletesListData: Error ${state.message}")
                    }
                    is SplashViewModel.AthletesListState.Loading -> {
                        Log.d(TAG, "renderAthletesListData: Loading")
                    }
                    is SplashViewModel.AthletesListState.Idle -> {
                        Log.d(TAG, "renderAthletesListData: Idle")
                    }
                }
            }
        }
    }

}