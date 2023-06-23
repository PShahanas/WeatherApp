package com.example.weatherapps.Classes

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.util.Log
import androidx.compose.ui.graphics.Canvas
import com.example.weatherapps.ViewModels.SearchViewModel
import java.util.Locale
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions

internal class MapOverlay(
    private val context: Context,
    private var p: LatLng,
    private val searchViewModel: SearchViewModel
) {

    private lateinit var googleMap: GoogleMap
    private var groundOverlay: GroundOverlay? = null

    fun setGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    fun draw(canvas: Canvas) {
        val latLng = LatLng(p.latitude, p.longitude)
        val projection = googleMap.projection
        val screenPts = projection.toScreenLocation(latLng)

        val bmp: Bitmap = BitmapFactory.decodeResource(
            context.resources, R.drawable.screen_background_dark
        )
        val overlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(bmp))
            .position(latLng, 100f) // Adjust the width as per your requirement
        groundOverlay?.remove() // Remove existing overlay if any
        groundOverlay = googleMap.addGroundOverlay(overlayOptions)
    }

    fun setLocationFromPlaceName(placeName: String) {
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
        val addresses = geocoder.getFromLocationName(placeName, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val latitude = address.latitude
                val longitude = address.longitude
                p = LatLng(latitude, longitude)

                searchViewModel.onLocationUpdated(p)
            }

            else{
                Log.e("Alert: ", "Place to Search not Provided")
            }
        }
    }catch (e:Exception){
        e.printStackTrace()
    }
    }
}



