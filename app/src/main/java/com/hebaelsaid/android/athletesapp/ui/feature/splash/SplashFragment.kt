package com.hebaelsaid.android.athletesapp.ui.feature.splash

import android.content.IntentFilter
import android.net.ConnectivityManager
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
import com.hebaelsaid.android.athletesapp.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SplashFragment"
@AndroidEntryPoint
class SplashFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListener{
    private lateinit var binding: FragmentSplashBinding
    private val viewModel:SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        requireActivity().registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderAthletesListData()
    }

    private fun renderAthletesListData() {
        lifecycleScope.launchWhenCreated {
            viewModel.athletesListState.collect { state ->
                when(state){
                    is SplashViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderAthletesListData: Success")
                        Log.d(TAG, "renderAthletesListData: list size ${state.athletesListResponseModel.athletes?.size}")
                        setUpSplashHandler()
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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            setUpSplashHandler()
        } else {
            viewModel.getAthletesList()
        }
    }

    private fun setUpSplashHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeListFragment())
        }, 3000)
    }

}