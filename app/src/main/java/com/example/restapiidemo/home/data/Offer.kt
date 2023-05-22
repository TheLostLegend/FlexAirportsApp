package com.example.restapiidemo.home.data

class Offer constructor(Id:Int, promocode:String, title:String, body:String) {
    var offerID:Int=0
    var promocodeValue:String?=""
    var header:String?=""
    var main:String?=""
    init {
        offerID = Id
        promocodeValue = promocode
        header = title
        main = body
    }
}