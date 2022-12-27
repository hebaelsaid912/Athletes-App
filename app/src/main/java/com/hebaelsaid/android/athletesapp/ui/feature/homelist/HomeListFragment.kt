package com.hebaelsaid.android.athletesapp.ui.feature.homelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.databinding.FragmentHomeListBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeListFragment"
@AndroidEntryPoint
class HomeListFragment : Fragment() , AthletesListAdapter.AthletesListViewHolder.OnItemClickListener{
    private lateinit var binding: FragmentHomeListBinding
    private val viewModel:HomeListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeListBinding.inflate(inflater, container, false)
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
                    is HomeListViewModel.AthletesListState.Success -> {
                        Log.d(TAG, "renderProductListData: Success")
                        Log.d(TAG, "renderProductListData: list size ${state.athletesListResponseModel.size}")
                        setUpAthletesList(state.athletesListResponseModel)
                    }
                    is HomeListViewModel.AthletesListState.Error -> {
                        Log.d(TAG, "renderProductListData: Error ${state.message}")
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

    private fun setUpAthletesList(athletes: List<AthleteItemModel>) {
        val adapter = AthletesListAdapter(this)
        adapter.submitList(athletes)
        binding.homeItemRv.adapter = adapter
    }

    override fun onItemClick(AthletesId: Int?) {
        TODO("Not yet implemented")
    }
}