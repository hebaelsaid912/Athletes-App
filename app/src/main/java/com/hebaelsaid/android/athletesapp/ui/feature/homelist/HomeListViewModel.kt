package com.hebaelsaid.android.athletesapp.ui.feature.homelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.domain.usecase.AthletesDBUseCase
import com.hebaelsaid.android.athletesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "HomeListViewModel"
@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val useCase: AthletesDBUseCase
) : ViewModel(){
    private val _athletesListState = MutableStateFlow<AthletesListState>(AthletesListState.Idle)
    val athletesListState: StateFlow<AthletesListState> get() = _athletesListState

    sealed class AthletesListState {
        data class Success(var athletesListResponseModel: List<AthleteItemModel>) : AthletesListState()
        data class Error(val message: String) : AthletesListState()
        class Loading(val isLoading: Boolean) : AthletesListState()
        object Idle : AthletesListState()
    }
     fun getAthletesList() {
        useCase().onEach { resultState ->
            when (resultState) {
                is Resource.Success -> {
                    _athletesListState.value = AthletesListState.Success(athletesListResponseModel = resultState.data!!)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "getAthletesList: Resource.Loading: true")
                    _athletesListState.value = AthletesListState.Loading(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d(TAG, "getAthletesList: Resource.Error")
                    _athletesListState.value = AthletesListState.Error(message = resultState.message ?: "un expected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}