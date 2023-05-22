package com.example.restapiidemo.home.data

data class WheelItem(
    var trackID: Int = 0,
    var arrivalAirportName:String = "",
    var arrivalCityNameval:String = "",
    var arrivalCountryCode:String = "",
    var departureAirportName: String = "",
    var departureCityName: String = "",
    var departureCountryCode: String = ""
)