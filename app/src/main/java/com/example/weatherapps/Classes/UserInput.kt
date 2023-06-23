package com.example.weatherapps.Classes

import javax.inject.Inject

/*class UserInput @Inject constructor(userInput: String) {
    var address: String = ""
}*/

 class UserInput(var address: String) {
    fun copy(address: String = this.address): UserInput {
        return UserInput(address)
    }
}