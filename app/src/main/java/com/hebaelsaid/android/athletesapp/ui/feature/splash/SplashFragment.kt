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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hebaelsaid.android.athletesapp.databinding.FragmentSplashBinding
import com.hebaelsaid.android.athletesapp.ui.MainViewModel
import com.hebaelsaid.android.athletesapp.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SplashFragment"
@AndroidEntryPoint
class SplashFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListener{
    private lateinit var binding: FragmentSplashBinding
    private var isConnected:Boolean = false
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
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
                    is MainViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderAthletesListData: Success")
                        Log.d(TAG, "renderAthletesListData: list size ${state.message}")
                        setUpSplashHandler()
                    }
                    is MainViewModel.AthletesListState.Error -> {
                        Log.d(TAG, "renderAthletesListData: Error ${state.message}")
                        setUpSplashHandler()
                    }
                    is MainViewModel.AthletesListState.Loading -> {
                        Log.d(TAG, "renderAthletesListData: Loading")
                    }
                    is MainViewModel.AthletesListState.Idle -> {
                        Log.d(TAG, "renderAthletesListData: Idle")
                    }
                }
            }
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
    }
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun setUpSplashHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeListFragment())
        }, 3000)
    }

}