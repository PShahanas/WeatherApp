package com.example.weatherapps.Repository

//import com.example.weatherapps.API.Address
import com.example.weatherapps.API.GeoAddressApi
import com.example.weatherapps.API.Place
import com.example.weatherapps.Classes.UserInput
import javax.inject.Inject

/*interface GeoaddressRepository {

    suspend fun searchAddresses(query: String): List<Address>

}*/
class GeoAddressRepository @Inject constructor(private val geoAddressApi: GeoAddressApi,
private val userInput: UserInput) {

    suspend fun searchPlaces(query: UserInput): List<Place> {

        /*if (query.isEmpty()) {
            return emptyList()
        }*/

        // Replace with your actual API key

            val apiKey = "AIzaSyA0DVBPdLQrhafLeoeU6KDl1X6qZ9COiUE"
            val response = geoAddressApi.searchPlaces(query, apiKey)
            if (response.isSuccessful) {
                val geoAddressResponse = response.body()
                return geoAddressResponse?.results ?: emptyList()
            } else {
                // Handle the error case
                throw Exception("Failed to search places")
            }



    }
}
