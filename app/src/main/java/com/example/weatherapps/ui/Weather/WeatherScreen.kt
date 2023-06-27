package com.example.weatherapps.ui.Weather

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapps.ViewModels.SearchViewModel
import com.example.weatherapps.ViewModels.WeatherViewModel
import com.example.weatherapps.ui.Weathercard
import com.plcoding.weatherapp.presentation.ui.theme.DarkBlue
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapps.ViewModels.weatherViewModel


@Composable
fun WeatherScreen(viewModel: WeatherViewModel, viewModelSearch: SearchViewModel, navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            CompositionLocalProvider(LocalMapOverlayContext provides LocalContext.current) {
                //weatherViewModel?.let {
                    SearchScreen(viewModelSearch
                    )
               // }
            }
                Spacer(modifier = Modifier.height(5.dp))
                Weathercard(
                    state = viewModel.state,
                    backgroundColor = DeepBlue, navController = navController

                )

                // WeatherCardNew(state = viewModel.state, backgroundColor = DeepBlue)

            }
            if (viewModel.state.isLoading) {
                /*CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )*/
                Log.e("Weather : ", "Loading.....")
            }
            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }




}