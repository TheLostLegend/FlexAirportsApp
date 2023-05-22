package com.example.restapiidemo.home.data

class User constructor(model: PostModel) {
    var customerId:Int=0
    //var login:String?=""
    //var passportData:PassportData?=null
    //var email:String?=""
    //var customerType:String?=""
    //var password:String?=""
    init {
        customerId = model.customerId
        //login = model.login
        //passportData = model.passportData
        //email = model.email
        //customerType = model.customerType
        //password = model.customerPassword
    }

    fun getId(): Int {
        return customerId;
    }
}