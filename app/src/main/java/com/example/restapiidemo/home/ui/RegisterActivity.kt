package com.example.restapiidemo.home.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.Message
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.home.data.RegModel
import com.example.restapiidemo.home.data.User
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

class RegisterActivity: AppCompatActivity() {
    var id:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Register"
        submit.setOnClickListener {
            regInit(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun regInit(context: Context){
        val name = username1.text.toString()
        val email = email.text.toString()
        val password1 = password1.text.toString()
        val password2 = password2.text.toString()
        if(!(name == "" || email == "" || password1 == "" || password2 == "") && password1 == password2){
            RetrofitClient.instance?.getMyApi()?.registerUser(RegModel(null, name, null, email, "user", password1))?.enqueue(object : Callback<Message?> {
                override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                    if (response.isSuccessful) {
                        id = response.body()?.id
                        if (id!= null){
                            val sManagement = SessionManagement(context)
                            sManagement.saveSesssion(User(PostModel(id!!)), context)
                        }
                    } else {
                        Toast.makeText(applicationContext, "Логин или пароль неверен", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        else{
            Toast.makeText(applicationContext, "Введите корректные данные", Toast.LENGTH_LONG)
                .show()
        }

    }
}