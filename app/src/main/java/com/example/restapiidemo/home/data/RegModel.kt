package com.example.restapiidemo.home.data

data class RegModel(
    var customerId:Int? = null,
    var login:String?="",
    var passportData:PassportData?=null,
    var email:String?="",
    var customerType:String="user",
    var customerPassword:String?=""

)