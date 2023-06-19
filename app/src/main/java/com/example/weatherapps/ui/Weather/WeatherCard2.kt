package com.example.weatherapps.ui.Weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapps.ViewModels.weatherViewModel
import com.example.weatherapps.ui.Weathercard
import java.time.format.DateTimeFormatter

@Composable
fun WeatherCardNew(
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
//    state.weatherInfo?.currentWeatherData?.let { data ->

        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Time",
                        modifier = Modifier.align(Alignment.End),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    /*Image(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = null,
                        modifier = Modifier.width(200.dp)
                    )*/

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "°C",
                        fontSize = 50.sp,
                        color = Color.White
                    )


                }

            }

        }

    }
//}



/*@Preview
@Composable
fun WeatherCardNewPreview(){
    //private val viewModel: WeatherViewModel by viewModels()

    weatherViewModel?.let {
        WeatherCardNew(
            modifier = Modifier.background(Color.White), state = viewModel.state
            backgroundColor = Color.White
        )
    }


}*/








