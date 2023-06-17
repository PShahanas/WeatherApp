package com.example.weatherapps.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapps.Location.LocationService
import com.example.weatherapps.Repository.WeatherRepository
//import com.example.weatherapps.Repository.Repository
import com.example.weatherapps.ui.Weather.WeatherState
import com.example.weatherapps.ui.mainActivity
import com.example.weatherapps.utils.Resource
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.properties.Delegates


var weatherViewModel : WeatherViewModel? = null


@HiltViewModel
class WeatherViewModel @Inject constructor(
private val repository: WeatherRepository
//private val locationService:LocationService
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

     @OptIn(DelicateCoroutinesApi::class)
     fun getLocation(locationResult: LocationResult?) {

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
        }

        if (locationResult != null) {
            for (location in locationResult.locations) {
                LocationService.currentLatitude = location.latitude
                LocationService.currentLongitude = location.longitude

                GlobalScope.launch {
                    when (val result = repository.getWeatherData(
                        LocationService.currentLatitude!!,
                        LocationService.currentLongitude!!
                    )) {
                        is Resource.Success -> {
                            state = state.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }

                    }

                }

            }
        }

    }

}







/*var weatherViewModel : WeatherViewModel? = null

class WeatherViewModel : ViewModel() {


    var getService : MutableLiveData<WeatherModel>?= null
    public val  getServiceList: LiveData<WeatherModel>? = getService

    var state by mutableStateOf(WeatherState())

    private lateinit var newsResponse: WeatherModel

    private val _getdata = MutableLiveData<WeatherModel>()
    public val getData : MutableLiveData<WeatherModel> = _getdata


    fun getWeather(locationResult: LocationResult) : MutableLiveData<WeatherModel>? {

        getService = Repository.getServicesApiCall()
        return getService
    }

    fun getLocationViewModel() {

        state = state.copy(

        )

    }

}*/