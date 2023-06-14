package com.example.weatherapps.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapps.R

@Composable
fun LocationDenialCard(modifier: Modifier = Modifier){

    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(10.dp,10.dp,10.dp,10.dp)
            //.padding(top = 270.dp, bottom = 270.dp, start = 60.dp, end = 60.dp)
            , elevation = 8.dp
    ) {
        Box(
            modifier = Modifier

                .border(
                    BorderStroke(3.dp, Color.Blue), RoundedCornerShape(16.dp)
                )
                .background(Color.White)
                .padding(start = 5.dp,5.dp, end = 5.dp,5.dp)
        ) {

            Text(
                text = "App requires access to user location to continue ",
                modifier = Modifier
                    .align(Alignment.TopCenter),
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Button(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(90.dp,top = 20.dp),
                onClick = {

                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text("Allow", color = Color.White)
            }

            Spacer(modifier = Modifier.width(50.dp))

            Button(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(end =70.dp, top = 20.dp),
                onClick = {
                    Toast.makeText(context, "Location permission denied", Toast.LENGTH_LONG).show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text("Deny", color = Color.White)

            }

            Image(painter = painterResource(id = R.drawable.info),
                contentDescription = null,
                modifier = Modifier.size(10.dp).align(Alignment.TopCenter).padding(top = 20.dp),

                )

        }

    }
}

@Preview
@Composable
fun LocationDenialCardPreview(){
    LocationDenialCard(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()

    )
}
