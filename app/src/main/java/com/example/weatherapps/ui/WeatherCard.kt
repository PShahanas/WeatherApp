package com.example.weatherapps.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.googlefonts.GoogleFont
//import androidx.compose.ui.tooling.data.EmptyGroup.data
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapps.R
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue

private object textDownloadablefont {

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

val fontName = GoogleFont("Lobster Two")

//val fontFamily = FontFamily(
//    Font(googleFont = fontName, fontProvider = provider),weight = FontWeight.Bold,
//        style = FontStyle.Italic
//)

@Composable
fun Weathercard(
    modifier: Modifier = Modifier
    //temp: Double? = MainActivity.temp

)
{
    /*mainActivity.let {
        if (it != null) {
            it.sendData(it)
        }
    }*/
   // var temp by remember{ mutableStateOf("${MainActivity.temp} ") }
    val context = LocalContext.current
   Card(
      shape = RoundedCornerShape(10.dp),
      modifier = modifier
          .padding(10.dp)
   ) {

        Box(
           modifier = Modifier
               .fillMaxSize()
               .background(DeepBlue)
        ) {

            Text(
                text = "Time",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_broken_clouds),
                contentDescription = "",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .align(alignment = Alignment.Center)
                    .padding(bottom = 150.dp)
            )

            Text(
                text = "26.8 C",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(top = 15.dp)

            )

            Row(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 48.dp, bottom = 60.dp)) {

                Text(text = "1011", color = Color.White)
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "hpa", color = Color.White)

                Spacer(modifier = Modifier.width(55.dp))

                Text(text = "88", color = Color.White)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "%", color = Color.White)

                Spacer(modifier = Modifier.width(50.dp))

                Text(text = "3", color = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "km/h", color = Color.White)


            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 50.dp, bottom = 10.dp)
            ){

                Image(
                    painter = painterResource(id = R.drawable.ic_pressure),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(bottom = 8.dp),
                    colorFilter = ColorFilter.tint(Color.White)

                )

                Spacer(modifier = Modifier.width(59.dp))

                Image(painter = painterResource(id = R.drawable.humidity),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(top = 3.dp)
                    ,
                    colorFilter = ColorFilter.tint(Color.White))

                Spacer(modifier = Modifier.width(53.dp))

                Image(painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = "",
                    modifier = Modifier
                        .size(45.dp),
                    colorFilter = ColorFilter.tint(Color.White))




            }


        }
   }


}

@Preview
@Composable
fun WeatherCardPreview(){

        Weathercard(
            modifier = Modifier.background(Color.White)
        )


}




