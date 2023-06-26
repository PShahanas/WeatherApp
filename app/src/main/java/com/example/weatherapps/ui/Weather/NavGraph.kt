package com.example.weatherapps.ui.Weather

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapps.ViewModels.SearchViewModel
import com.example.weatherapps.ViewModels.WeatherViewModel

/*object Screens {
    const val Map = "map"
    const val Home = "home"
}

@Composable
fun NavGraph(startDestination: String = Screens.Home) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.Home) {
            WeatherScreen(viewModel, viewModelSearch)
        }
        composable(Screens.Map) {
            val mapScreen = hiltViewModel<MapScreen>()
            MapScreen(onLocationSelected = { latLng ->
                // Handle the selected location (latitude and longitude) here
            })
        }
    }
}*/