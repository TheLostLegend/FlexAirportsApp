package com.example.restapiidemo.home.data

import java.sql.Date

data class PassportDataALTER (
    var passportId:Int?=0,
    var sex:Boolean?=true,
    var givenName:String?="",
    var surname:String?="",
    var dateOfBirth:Date?=null,
    var dateOfIssue:Date?=null,
    var dateOfExpery:Date?=null,
    var idNumber:String?= "",
    var country:String?= "",
    var authority:String?= ""
)