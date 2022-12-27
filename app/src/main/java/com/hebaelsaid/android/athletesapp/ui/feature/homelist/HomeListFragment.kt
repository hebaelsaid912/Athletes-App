package com.hebaelsaid.android.athletesapp.ui.feature.homelist

import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.databinding.FragmentHomeListBinding
import com.hebaelsaid.android.athletesapp.ui.MainViewModel
import com.hebaelsaid.android.athletesapp.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeListFragment"

@AndroidEntryPoint
class HomeListFragment : Fragment(), AthletesListAdapter.AthletesListViewHolder.OnItemClickListener,
    ConnectivityReceiver.ConnectivityReceiverListener {
    private lateinit var binding: FragmentHomeListBinding
    private var isConnected: Boolean = false
    private val viewModel: HomeListViewModel by viewModels()
    private val sharedViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeListBinding.inflate(inflater, container, false)
        requireActivity().registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderAthletesListData()
    }

    private fun renderAthletesListData() {
        lifecycleScope.launchWhenCreated {
            viewModel.athletesListState.collect { state ->
                when (state) {
                    is HomeListViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderProductListData: Success")
                        Log.d(TAG, "renderProductListData: list size " +
                                "${state.athletesListResponseModel.size}")
                        setUpAthletesList(state.athletesListResponseModel)
                    }
                    is HomeListViewModel.AthletesListState.Error -> {
                        Log.d(TAG, "renderProductListData: Error ${state.message}")
                        if (state.message.contains("internet") || !isConnected) {
                            binding.homeItemRv.visibility = View.GONE
                            binding.notInternetConnectionLayout.root.visibility = View.VISIBLE
                            setupNoInternetDialog()
                        }else if (state.message.contains("Null")){
                            sharedViewModel.getAthletesList()
                            renderAthletesList()
                        }
                    }
                    is HomeListViewModel.AthletesListState.Loading -> {
                        Log.d(TAG, "renderProductListData: Loading")
                    }
                    is HomeListViewModel.AthletesListState.Idle -> {
                        Log.d(TAG, "renderProductListData: Idle")
                    }
                }
            }
        }
    }
    private fun renderAthletesList() {
        lifecycleScope.launchWhenCreated {
            sharedViewModel.athletesListState.collect { state ->
                when (state) {
                    is MainViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderProductList: Success")
                        Log.d(TAG, "renderProductList: message " + state.message)
                        if(state.isSaved) {
                            viewModel.getAthletesList()
                        }
                    }
                    is MainViewModel.AthletesListState.Error -> {
                        Log.d(TAG, "renderProductList: Error ${state.message}")
                        if (state.message.contains("internet") || !isConnected) {
                            binding.homeItemRv.visibility = View.GONE
                            binding.notInternetConnectionLayout.root.visibility = View.VISIBLE
                            setupNoInternetDialog()
                        }else if (state.message.contains("Null")){
                            sharedViewModel.getAthletesList()
                        }
                    }
                    is MainViewModel.AthletesListState.Loading -> {
                        Log.d(TAG, "renderProductList: Loading")
                    }
                    is MainViewModel.AthletesListState.Idle -> {
                        Log.d(TAG, "renderProductList: Idle")
                    }
                }
            }
        }
    }

    private fun setUpAthletesList(athletes: List<AthleteItemModel>) {
        val adapter = AthletesListAdapter(this)
        adapter.submitList(athletes)
        binding.homeItemRv.adapter = adapter
    }

    override fun onItemClick(AthletesId: Int?) {
        TODO("Not yet implemented")
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        if(isConnected){
            binding.homeItemRv.visibility = View.VISIBLE
            binding.notInternetConnectionLayout.root.visibility = View.GONE
        }
        viewModel.getAthletesList()
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun setupNoInternetDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("First time open app")
            .setMessage("There is no internet connection or cashed data \n at least open internet one time to load data")
            .setPositiveButton("Retry") { dialog, which ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }
}