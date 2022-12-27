package com.hebaelsaid.android.athletesapp.ui.feature.detail

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
class DetailsViewModel @Inject constructor(
    private val useCase: AthletesDBUseCase
) : ViewModel(){
    private val _athleteDetailsState = MutableStateFlow<AthleteDetailsState>(AthleteDetailsState.Idle)
    val athleteState: StateFlow<AthleteDetailsState> get() = _athleteDetailsState

    sealed class AthleteDetailsState {
        data class Success(var responseModel: AthleteItemModel) : AthleteDetailsState()
        data class Error(val message: String) : AthleteDetailsState()
        class Loading(val isLoading: Boolean) : AthleteDetailsState()
        object Idle : AthleteDetailsState()
    }
     fun getAthleteData(id:Int) {
        useCase(id = id).onEach { resultState ->
            when (resultState) {
                is Resource.Success -> {
                    _athleteDetailsState.value = AthleteDetailsState.Success(responseModel = resultState.data!!)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "getAthletesList: Resource.Loading: true")
                    _athleteDetailsState.value = AthleteDetailsState.Loading(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d(TAG, "getAthletesList: Resource.Error")
                    _athleteDetailsState.value = AthleteDetailsState.Error(message = resultState.message ?: "un expected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}