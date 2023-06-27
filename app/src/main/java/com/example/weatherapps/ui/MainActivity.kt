package com.example.weatherapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
//import com.example.weatherapps.API.ApiUtilities
import com.example.weatherapps.Location.LocationService
import com.example.weatherapps.ViewModels.SearchViewModel
//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.ViewModels.WeatherViewModel
import com.example.weatherapps.ViewModels.weatherViewModel
//import com.example.weatherapps.ui.Weather.SearchScreen
//import com.example.weatherapps.ui.Weather.WeatherCardNew
//import com.example.weatherapps.ViewModels.MainViewModel
import com.google.android.gms.location.LocationResult
import com.plcoding.weatherapp.presentation.ui.theme.DarkBlue
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue
import com.plcoding.weatherapp.presentation.ui.theme.WeatherAppTheme
import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapps.ViewModels.MapViewModel
import com.example.weatherapps.ui.Weather.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


var mainActivity : MainActivity? = null


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private val viewModelSearch: SearchViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()

   // private val navController by lazy { rememberNavController() }
    private lateinit var navController: NavHostController

    //@Inject
    //lateinit var mapView: MapView

    //@Inject
    //lateinit var navController: NavHostController

    private val LocalMapOverlayContext = compositionLocalOf<Context> { error("No MapOverlay Context found") }

    private lateinit var permissionRequest: ActivityResultLauncher<Array<String>>

    val locationPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var permissionsEnabled = false

    var service: Intent? = null

    @Preview
    @Composable
    fun WeatherCardPreview(){
        //private val viewModel: WeatherViewModel by viewModels()

        weatherViewModel?.let {
            Weathercard(
                state = viewModel.state,
                backgroundColor = DeepBlue,
                navController = rememberNavController()
                //navController = NavHostController(this@MainActivity)
            )
        }
    }

    object Screens {
        const val Home = "home"
        const val Map = "map"
        // Define other screen destinations
        // ...
    }

    @Composable
    fun MyApp() {

        navController = rememberNavController()
        val mapViewModel = hiltViewModel<MapViewModel>()

        NavHost(navController = navController, startDestination = Screens.Home) {
            composable(Screens.Home) {

                /*CompositionLocalProvider(LocalMapOverlayContext provides applicationContext) {
                     WeatherScreen(viewModel, viewModelSearch)
                    }*/
                WeatherScreen(viewModel = viewModel, viewModelSearch = viewModelSearch , navController = navController) }


            composable(Screens.Map) { MapScreenEntry(mapViewModel, navController){ place ->
                // Handle the selected place here
                // You can perform any desired action with the selected place

                Log.e("Place Selected: ","${place.latitude } + ${place.longitude} + ${place.name}")

            } }
            // ...
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WeatherAppTheme {

                MyApp()

                   // CompositionLocalProvider(LocalMapOverlayContext provides applicationContext) {
                     //   WeatherScreen(viewModel, viewModelSearch)
                    //}

                /*NavHost(navController = navController, startDestination = Screens.Home) {
                    composable(Screens.Home) { WeatherScreen(viewModel = viewModel, viewModelSearch = viewModelSearch )}
                    composable(Screens.Map) { MapScreenEntry(navController = navController) }
                    // ...
                }*/

                /*val navGraph = remember { NavGraph() }
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController = navController, startDestination = Screens.Home) {
                        // Define your navigation routes here
                    }*/

                    /*val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screens.Home) {
                        // Define your navigation routes here
                    }*/

                /*MapView(onMapClicked = { latLng ->
                    val latitude = latLng.latitude
                    val longitude = latLng.longitude
                    // Use latitude and longitude as needed
                })*/




            }

           /* MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MapView(onMapClicked = { latLng ->
                        val latitude = latLng.latitude
                        val longitude = latLng.longitude
                        // Use latitude and longitude as needed
                    })
                }
            }*/
        }
        requestPermissions()
        mainActivity = this

        service = Intent(this, LocationService::class.java)
        if (permissionsEnabled) {
            startService(service)
        }
    }

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
                val mapScreen = hiltViewModel<MapViewModel>()
                MapScreen(onLocationSelected = { latLng ->
                    // Handle the selected location (latitude and longitude) here
                })
            }
        }
    }*/





    fun requestPermissions() {
        permissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

                    permissions ->
                val granted = permissions.entries.all {
                    it.value == true
                }
                val denied = permissions.entries.all {
                    it.value == false
                }
                if (granted) {
                    permissionsEnabled = true
                    Toast.makeText(this, "Permission Given", Toast.LENGTH_LONG).show()
                    startService(service)
                    /*GlobalScope.launch {
                       viewModel.getWeatherInViewmodel()
                    }
                    observeWeatherData()*/
                } else if (denied) {
                    permissionsEnabled = false
                    //Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                    setContent {
                        Box(modifier = Modifier.fillMaxSize())
                        {
                            WeatherAppTheme {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ){
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(DarkBlue)
                                    ) {
                                        //SearchScreen()
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Weathercard(
                                            state = viewModel.state,
                                            backgroundColor = DeepBlue,
                                            navController = navController
                                            //navController = NavHostController(this@MainActivity)
                                        )

                                    }
                                    if(viewModel.state.isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }else{

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
                            MyDialogUIPreview()
                        }


                    }
                }

            }


        permissionRequest.launch(locationPermissions)


    }

    fun checkpermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                )

    }

    fun getLocation(locationResult: LocationResult) {
        for (location in locationResult.locations) {
            LocationService.currentLatitude = location.latitude
            LocationService.currentLongitude = location.longitude

            viewModel.getLocation(locationResult)

            Log.e(
                "Current Position", "Latitude: ${LocationService.currentLatitude}, " +
                        "Longitude: ${LocationService.currentLongitude}"
            )



        }
    }


    override fun onRestart() {
        super.onRestart()

        if (!checkpermissions())
         {
             //Toast.makeText(this, "Permissions not available",Toast.LENGTH_LONG).show()
          setContent{
              Box(modifier = Modifier.fillMaxSize()) {
                  WeatherAppTheme {

                      CompositionLocalProvider(LocalMapOverlayContext provides applicationContext) {
                          WeatherScreen(viewModel, viewModelSearch, navController = navController)
                      }

                      /* Box(
                           modifier = Modifier.fillMaxSize()
                       ){
                           Column(
                               modifier = Modifier
                                   .fillMaxSize()
                                   .background(DarkBlue)
                           ) {
                               //SearchScreen()
                               Spacer(modifier = Modifier.height(10.dp))
                               Weathercard(
                                   state = viewModel.state,
                                   backgroundColor = DeepBlue
                               )

                               //WeatherCardNew(state = viewModel.state, backgroundColor = DeepBlue)

                           }
                           if(viewModel.state.isLoading) {
                               /*CircularProgressIndicator(
                                   modifier = Modifier.align(Alignment.Center)
                               )*/
                               Log.e("Weather : ","Loading....")
                           }

                           viewModel.state.error?.let { error ->
                               Text(
                                   text = error,
                                   color = Color.Red,
                                   textAlign = TextAlign.Center,
                                   modifier = Modifier.align(Alignment.Center)
                               )
                           }
                       }*/

                  }
                  MyDialogUIPreview()
              }
          }

        }


        else
          {

            Toast.makeText(this, "Permission Given", Toast.LENGTH_LONG).show()
            setContent{
                WeatherAppTheme {

                    CompositionLocalProvider(LocalMapOverlayContext provides applicationContext) {
                        WeatherScreen(viewModel, viewModelSearch, navController = navController)
                    }

                    /*Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkBlue)
                        ) {
                            weatherViewModel?.let {
                                SearchScreen(viewModelSearch, suggestions = viewModelSearch.suggestions
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Weathercard(
                                state = viewModel.state,
                                backgroundColor = DeepBlue
                            )

                        }
                        if(viewModel.state.isLoading) {
                            Log.e("Weather : ","Loading....")
                        }
                        viewModel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }*/

                }
                startService(service)
            }
        }



    }


    @Composable
    fun CustomDialogUI(
        openDialog: MutableState<Boolean>
    ) {


        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(30.dp, 200.dp, 30.dp, 10.dp),
            //modifier = Modifier.align(Alignment.CenterVertically),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.background(Color.White)
            ) {


                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Allow Location Permissions ",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),

                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "The Weather App requires permission to continue",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        //style = MaterialTheme.typography.bodyMedium
                    )
                }
                //.......................................................................
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val context = LocalContext.current

                    TextButton(onClick = {
                        Toast.makeText(this@MainActivity, "No permissions given", Toast.LENGTH_LONG)
                            .show()
                        setContent{
                            WeatherAppTheme {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ){
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(DarkBlue)
                                    ) {
                                        Weathercard(
                                            state = viewModel.state,
                                            backgroundColor = DeepBlue,
                                            navController = navController
                                            //navController = NavHostController(this@MainActivity)
                                        )

                                    }
                                    if(viewModel.state.isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
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
                        }
                    }) {

                        Text(
                            "Not Now",
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        //Toast.makeText(this@MainActivity, "Clicked Allow",Toast.LENGTH_LONG).show()

                        //val viewIntent =
                        //  Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        //startActivity(viewIntent)

                        try {
                            val myAppSettings = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(
                                    "package:$packageName"
                                )
                            )
                            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
                            myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(myAppSettings)

                        }catch (e: Exception){
                            e.printStackTrace()
                        }


                    }) {
                        Text(
                            "Allow",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }

    }

    @SuppressLint("UnrememberedMutableState")
    @Preview(name = "Custom Dialog")
    @Composable
    fun MyDialogUIPreview() {
        CustomDialogUI(openDialog = mutableStateOf(false))
    }



}
