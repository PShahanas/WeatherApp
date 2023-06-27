package com.example.weatherapps.ui.Weather

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.weatherapps.API.MapApiImpl
import com.example.weatherapps.Classes.Place
import com.example.weatherapps.ViewModels.MapViewModel

/*@Composable
fun MapScreenEntry (navController: NavHostController) {
    val viewModel: MapViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.displayMap()
    }

    MapScreen(mapApi = viewModel.mapApi)
}*/

@Composable
fun MapScreenEntry(viewModel: MapViewModel,
                   navController: NavHostController,
                   onPlaceSelected: (Place) -> Unit) {

    val context = LocalContext.current

    val apiKey = "AIzaSyDOW_dICP7NpTaMIyvfo4lktiDuXobZ-6s"

    val mapApi = remember {
        MapApiImpl(context, apiKey)
    }

    //val selectedPlaceState = remember { mutableStateOf<Place?>(null) }

    val mapView = rememberMapViewWithLifecycle(LocalLifecycleOwner.current)

    Box(modifier = Modifier.fillMaxSize()) {
        // Display the map using the mapApi
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        // Close icon
        Icon(
            Icons.Filled.Close,
            contentDescription = "Close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {

                        navController.popBackStack()


                }
        )

        Button(
            onClick = {
                // Handle "Get Weather" button click
            },
            enabled = true, // Enable or disable the button based on your logic
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Get Weather")
        }
    }

    /*DisposableEffect(Unit) {
        onDispose {
            Log.d("MapView", "Disposing MapView")
            mapView?.onDestroy()
        }
    }*/

    val navigateUp = remember { mutableStateOf(false) }

    // Close the MapScreen and navigate up
    val onClose = {
        navigateUp.value = true
    }

    //viewModel.displayMap()

    LaunchedEffect(Unit) {
        viewModel.displayMap(mapView) { place ->
            onPlaceSelected(place)
        }
    }

    if (navigateUp.value) {
        navController.popBackStack()
    } else {
        MapScreen(mapApi = mapApi, mapView = mapView, onClose = onClose, navController = navController,  onPlaceSelected = onPlaceSelected)
    }

    //MapScreen(mapApi = mapApi)

    // ...
}
