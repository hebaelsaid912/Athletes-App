package com.hebaelsaid.android.athletesapp.domain.usecase

import android.util.Log
import com.hebaelsaid.android.athletesapp.data.local.entities.AthleteItemModel
import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesApiRepo
import com.hebaelsaid.android.athletesapp.domain.repository.AthletesDBRepo
import com.hebaelsaid.android.athletesapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
private const val TAG = "AthletesApiUseCase"
class AthletesApiUseCase @Inject constructor(
    private val athletesApiRepo: AthletesApiRepo,
    private val athletesDBRepo: AthletesDBRepo
) {
    operator fun invoke() = flow<Resource<AthletesListResponseModel>> {
        try {
            emit(Resource.Loading<AthletesListResponseModel>())
            val athletesModel = athletesApiRepo.getAthletesList()
            Log.d(TAG, "invoke: ${athletesModel.athletes?.size}")
            cashingAthletesIntoDB(athletesModel)
            if (athletesModel != null) {
                emit(Resource.Success<AthletesListResponseModel>(data = athletesModel))
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

    private suspend fun cashingAthletesIntoDB(athletesModel: AthletesListResponseModel) {
        athletesDBRepo.clearAthletesDatabase()
        athletesModel.athletes?.forEach { athlete ->
            withContext(Dispatchers.Default) {
                athletesDBRepo.insertAthleteItem(
                    AthleteItemModel(
                        name = athlete?.name,
                        image = athlete?.image,
                        brief = athlete?.brief
                    )
                )
            }
        }
    }
}