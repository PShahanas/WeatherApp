package com.example.weatherapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.weatherapps.API.ApiUtilities
import com.example.weatherapps.Location.LocationService
//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.ViewModels.WeatherViewModel
import com.example.weatherapps.ViewModels.weatherViewModel
import com.example.weatherapps.ui.Weather.WeatherCardNew
//import com.example.weatherapps.ViewModels.MainViewModel
import com.google.android.gms.location.LocationResult
import com.plcoding.weatherapp.presentation.ui.theme.DarkBlue
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue
import com.plcoding.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger.global
import kotlin.properties.Delegates

var mainActivity : MainActivity? = null


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()


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
                backgroundColor = DeepBlue
            )
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

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
                            backgroundColor = DeepBlue
                        )

                       // WeatherCardNew(state = viewModel.state, backgroundColor = DeepBlue)

                    }
                    if(viewModel.state.isLoading) {
                        /*CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )*/
                        Log.e("Weather : ","Loading.....")
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

        requestPermissions()
        mainActivity = this

        service = Intent(this, LocationService::class.java)
        if (permissionsEnabled) {
            startService(service)

        }



    }




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
                                        Weathercard(
                                            state = viewModel.state,
                                            backgroundColor = DeepBlue
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
                      }

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
                    }

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
                                            backgroundColor = DeepBlue
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
