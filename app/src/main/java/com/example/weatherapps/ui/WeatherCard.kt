package com.example.weatherapps.ui
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapps.ViewModels.weatherViewModel
import com.example.weatherapps.ui.Weather.WeatherState
import java.time.format.DateTimeFormatter
import com.example.weatherapps.R
import com.example.weatherapps.ui.Weather.ColoredImageVector


//val apiKey = "f70ca239bf30695349b25a9bb3361c69"

//val LocalData = compositionLocalOf<String> { error("No data provided") }

@SuppressLint("RememberReturnType")
@Composable
fun Weathercard(
    modifier: Modifier = Modifier,
    state: WeatherState,
    backgroundColor: Color

)
{
    state.weatherInfo?.currentWeatherData?.let { data ->

            Card(
                backgroundColor = backgroundColor,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.padding(16.dp)
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
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
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
                    modifier = Modifier.fillMaxWidth()
                ){

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


            }
        }
    }
    }




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




