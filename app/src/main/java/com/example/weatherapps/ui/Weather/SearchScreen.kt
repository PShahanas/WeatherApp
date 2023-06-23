package com.example.weatherapps.ui.Weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapps.Classes.UserInput
import com.example.weatherapps.ViewModels.SearchViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.compositionLocalOf
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.weatherapps.Classes.MapOverlay
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.plcoding.weatherapp.presentation.ui.theme.DarkBlue
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


val LocalMapOverlayContext = compositionLocalOf<Context> { error("No MapOverlay Context found") }

@Composable
    fun SearchScreen(viewModel: SearchViewModel
                     //, suggestions: StateFlow<List<String>>
) {
        val context = LocalMapOverlayContext.current
        val mapOverlay = remember { MapOverlay(context, LatLng(0.0, 0.0), viewModel) }

    val placePredictions by viewModel.placePredictions.collectAsState()


    // val placePredictions = remember { mutableStateListOf<AutocompletePrediction>() }
    //val placePredictions by remember { derivedStateOf { viewModel.placePredictions } }



    //val coroutineScope = rememberCoroutineScope()

    //val suggestionsList by suggestions.collectAsState()




    /*LaunchedEffect(suggestionsList) {
        coroutineScope.launch {
            try {
                val predictions = getPlacePredictions(suggestionsList, viewModel.placesClient)
                placePredictions.clear()
                placePredictions.addAll(predictions)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }*/

    /*LaunchedEffect(searchInput) {
        viewModel.fetchPlacePredictions(searchInput)
    }*/
        //val placeAutocompleteHelper = remember { PlaceAutocompleteHelper(AIzaSyA0DVBPdLQrhafLeoeU6KDl1X6qZ9COiUE) }
        //val placePredictions = remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }



    Column(
            modifier = Modifier
                .padding(16.dp)
                .background(DarkBlue)
        ) {

            var userInput by remember { mutableStateOf(UserInput(" ")) }

            //var searchInput by remember(userInput) { mutableStateOf(userInput.address) }
           var searchInput by remember(userInput) { mutableStateOf(userInput.address) }

        var searchInputnew by remember { mutableStateOf("") }



        /*LaunchedEffect(searchInput) {
            viewModel.fetchPlacePredictions(searchInput)
        }*/

            TextField(
                //value = userInput.address,
                value = searchInputnew,
                onValueChange = { newValue ->
                   // userInput = userInput.copy(address = newValue)
                    searchInput = newValue

                    searchInputnew = newValue
                    viewModel.fetchPlacePredictions(newValue)
                    //mapOverlay.setLocationFromPlaceName(searchInput)

                    //viewModel.searchPlaces(newValue)
                    //placePredictions = newPredictionsList.toMutableList()
                },
                //onValueChange = { newValue -> userInput = UserInput(newValue) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
            )

        LazyColumn {
            items(placePredictions) { prediction ->
                Text(prediction.getFullText(null).toString() ?: "")
                Log.d("Predictions", prediction.getFullText(null).toString())
            }
        }

        LaunchedEffect(searchInput) {
            delay(500)
            mapOverlay.setLocationFromPlaceName(searchInput)
        }
        }
    }








