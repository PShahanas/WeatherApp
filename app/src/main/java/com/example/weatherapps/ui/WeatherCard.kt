package com.example.weatherapps.ui
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherapps.ui.Weather.WeatherState
import java.time.format.DateTimeFormatter
import com.example.weatherapps.R
//import com.example.weatherapps.ViewModels.SearchViewModel
import com.example.weatherapps.ui.Weather.ColoredImageVector


//val apiKey = "f70ca239bf30695349b25a9bb3361c69"

//val LocalData = compositionLocalOf<String> { error("No data provided") }

@SuppressLint("RememberReturnType")
@Composable
fun Weathercard(
    modifier: Modifier = Modifier,
    state: WeatherState,
    backgroundColor: Color,
    navController: NavHostController
) {

    state.weatherInfo?.currentWeatherData?.let { data ->

        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = " ${
                        data.time.format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    }",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Canada", color = Color.White,
                fontSize = 50.sp,
                    //modifier = Modifier.clickable {
                      //  navController.navigate(MainActivity.Screens.Map)
                    //}
                )

                Spacer(modifier = Modifier.height(9.dp))

                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
                /*Image(
                    painter = painterResource(id = data.weatherType.bgImage),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )*/

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 50.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp)
                ) {

                    val pressure = ImageVector.vectorResource(id = R.drawable.ic_pressure)
                    ColoredImageVector(
                        imageVector = pressure,
                        color = Color.White,
                        contentDescription = null,
                        size = 24.dp
                    )

                    Text(
                        text = "${data.pressure} hpa",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    val humidity = ImageVector.vectorResource(id = R.drawable.ic_drop)
                    ColoredImageVector(
                        imageVector = humidity,
                        color = Color.White,
                        contentDescription = null,
                        size = 24.dp
                    )

                    Text(
                        text = "${data.humidity}%",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    val windspeed = ImageVector.vectorResource(id = R.drawable.ic_wind)
                    ColoredImageVector(
                        imageVector = windspeed,
                        color = Color.White,
                        contentDescription = null,
                        size = 24.dp
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = "${data.windSpeed}km/h",
                        color = Color.White
                    )


                }

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_mapsicon),
                    contentDescription = "Maps",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                               navController.navigate(MainActivity.Screens.Map)
                        }
                )



            }

            }
        }
    }




/*@Composable
fun SearchScreen(viewModel: WeatherViewModel) {
    var query by remember { mutableStateOf("") }

    Row {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Address") },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { viewModel.searchAddresses(query) }
        ) {
            Text("Search")
        }
        //Spacer(modifier = Modifier.height(16.dp))

        val searchResults by viewModel.searchResults.observeAsState()
        if (searchResults != null) {
            LazyColumn {
                items(searchResults) { address ->
                    //Text(address.fullAddress, modifier = Modifier.padding(16.dp))
                    Text(address.toString(), modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}*/


/*@Composable
fun SearchScreen(viewModel: WeatherViewModel ) {
    var query by remember { mutableStateOf("")
        }
Row(
    modifier = Modifier
        .padding(10.dp)
) {
    TextField(
        value = query,
        onValueChange = { newValue ->
            query = newValue
        },
        modifier = Modifier.weight(1f).padding(end = 8.dp),
        label = { Text("Search Place", color = Color.White) }
    )

    Button(onClick = {
        //viewModel.searchAddresses(query)
                     Log.i("Button: ","Clicked")
                     },
        modifier = Modifier.padding(start = 8.dp)
        ) {

        Text(text ="Button" )

    }

    //Spacer(modifier = Modifier.width(9.dp))

    /*Button(
        onClick = { viewModel.searchAddresses(query) }
    ) {
        Text(text = "Weather")
    }*/
}

}*/

/*@Composable
fun MyButton(onClick: () -> Unit, viewModel: WeatherViewModel) {
    val query by remember { mutableStateOf("")
    }
    Button(
        onClick = { viewModel.searchAddresses(query) },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Weather")
    }
}*/


/*@Preview
@Composable
fun WeatherCardPreview(){
    //private val viewModel: WeatherViewModel by viewModels()

    weatherViewModel?.let {
        Weathercard(
            modifier = Modifier.background(Color.White), state = viewModel.state,
        )
    }


}*/

/*
@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    val places by searchViewModel.places.collectAsState()
    val selectedPlace by searchViewModel.selectedPlace.collectAsState()

    Column {
        TextField(
            value = selectedPlace?.name ?: "",
            onValueChange = { query ->
                searchViewModel.searchPlaces(query)
                searchViewModel.clearSelectedPlace()
            },
            label = { Text("Search") }
        )
        LazyColumn {
            items(places) { place ->
                Text(
                    text = places.name,
                    modifier = Modifier
                        .clickable { searchViewModel.selectPlace(places) }
                        .padding(16.dp)
                )
            }
        }

        Button(
            onClick = { searchViewModel.convertToCoordinates() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Convert to Coordinates")
        }
    }
}


*/







