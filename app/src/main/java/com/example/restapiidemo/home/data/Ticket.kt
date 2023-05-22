package com.example.restapiidemo.home.data

import java.sql.Date
import java.sql.Timestamp

class Ticket {
    constructor(
        flightID: Int,
        customerID: Int,
        seatClass: String,
        price: Float


    ) {
        this.flightID = flightID
        this.customerID = customerID
        this.seatClass = seatClass
        this.price = price
    }

    var flightID:Int=0
    var customerID:Int = 0
    var seatClass:String = ""
    var price:Float = 0F

}