package com.hebaelsaid.android.athletesapp.domain.usecase

import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesApiRepo
import com.hebaelsaid.android.athletesapp.utils.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeListUseCase @Inject constructor(
    private val repository: AthletesApiRepo
) {
    operator fun invoke() = flow<Resource<AthletesListResponseModel>> {
        try {
            emit(Resource.Loading<AthletesListResponseModel>())
            val productResponseModel = repository.getAthletesList()
            if (productResponseModel != null) {
                emit(Resource.Success<AthletesListResponseModel>(data = productResponseModel))
            } else {
                emit(Resource.Error<AthletesListResponseModel>("Data Return With Null"))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<AthletesListResponseModel>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<AthletesListResponseModel>("Couldn't reach server. Please check your internet connection"))
        }
    }
}