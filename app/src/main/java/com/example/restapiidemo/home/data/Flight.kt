package com.example.restapiidemo.home.data

import java.sql.Date
import java.sql.Timestamp

class Flight {
    constructor(
        flightID: Int,
        arrivalTime: Timestamp?,
        departureTime: Timestamp?,
        arrivalAirportName: String?,
        arrivalCityName: String?,
        arrivalCountryCode: String?,
        departureAirportName: String?,
        departureCityName: String?,
        departureCountryCode: String?,
        economyTotal: Int,
        firstClassTotal: Int,
        luxTotal: Int,
        economyReserved: Int,
        firstClassReserved: Int,
        luxReserved: Int,
        economyCost: Float,
        firstClassCost: Float,
        luxCost: Float
    ) {
        this.flightID = flightID
        this.arrivalTime = arrivalTime
        this.departureTime = departureTime
        this.arrivalAirportName = arrivalAirportName
        this.arrivalCityName = arrivalCityName
        this.arrivalCountryCode = arrivalCountryCode
        this.departureAirportName = departureAirportName
        this.departureCityName = departureCityName
        this.departureCountryCode = departureCountryCode
        this.economyTotal = economyTotal
        this.firstClassTotal = firstClassTotal
        this.luxTotal = luxTotal
        this.economyReserved = economyReserved
        this.firstClassReserved = firstClassReserved
        this.luxReserved = luxReserved
        this.economyCost = economyCost
        this.firstClassCost = firstClassCost
        this.luxCost = luxCost
    }

    var flightID:Int=0
    var arrivalTime:Timestamp? = null
    var departureTime:Timestamp? = null
    var arrivalAirportName:String?=""
    var arrivalCityName:String?=""
    var arrivalCountryCode:String?=""
    var departureAirportName:String?=""
    var departureCityName:String?=""
    var departureCountryCode:String?=""
    var economyTotal:Int = 0
    var firstClassTotal:Int = 0
    var luxTotal:Int = 0
    var economyReserved:Int = 0
    var firstClassReserved:Int = 0
    var luxReserved:Int = 0
    var economyCost:Float = 0F
    var firstClassCost:Float = 0F
    var luxCost:Float = 0F
}