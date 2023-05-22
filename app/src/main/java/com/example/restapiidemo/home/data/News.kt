package com.example.restapiidemo.home.data

class News constructor(Id:Int, author:String, title:String, body:String) {
    var newsID:Int=0
    var login:String?=""
    var header:String?=""
    var main:String?=""
    init {
        newsID = Id
        login = author
        header = title
        main = body
    }
}