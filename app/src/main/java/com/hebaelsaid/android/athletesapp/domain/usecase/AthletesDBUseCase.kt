package com.hebaelsaid.android.athletesapp.domain.usecase

import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesDBRepo
import com.hebaelsaid.android.athletesapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AthletesDBUseCase @Inject constructor(
    private val athletesDBRepo: AthletesDBRepo
    ) {
    operator fun invoke() = flow<Resource<List<AthleteItemModel>>> {
            try {
                emit(Resource.Loading<List<AthleteItemModel>>())
                val athletesModel = withContext(Dispatchers.Default) { athletesDBRepo.getAllAthletesList() }
                if (athletesModel.isNotEmpty()) {
                    emit(Resource.Success<List<AthleteItemModel>>(data = athletesModel))
                } else {
                    emit(Resource.Error<List<AthleteItemModel>>("Data Return With Null"))
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<AthleteItemModel>>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<List<AthleteItemModel>>("Couldn't reach server. Please check your internet connection"))
            }

    }

    operator fun invoke(id: Int) = flow<Resource<AthleteItemModel>> {
            try {
                emit(Resource.Loading<AthleteItemModel>())
                val athleteDetailsModel = withContext(Dispatchers.Default) { athletesDBRepo.getAthleteDetailsById(id) }
                if (athleteDetailsModel != null) {
                    emit(Resource.Success<AthleteItemModel>(data = athleteDetailsModel))
                } else {
                    emit(Resource.Error<AthleteItemModel>("Data Return With Null"))
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AthleteItemModel>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<AthleteItemModel>("Couldn't reach server. Please check your internet connection"))
            }
    }
}