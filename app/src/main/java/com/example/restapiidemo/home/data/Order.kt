package com.example.restapiidemo.home.data

import java.sql.Date
import java.sql.Timestamp

data class Order(
    var price:Float = 0F,
    var currency:String = "USD",
    var method:String = "paypal",
    var intent:String = "sale",
    var description:String = "",
    var promoValue:String? = null
)



