package com.example.restapiidemo.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.Message
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.home.data.User
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: AppCompatActivity() {
    var id:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Login"
        login.setOnClickListener {
            loginInit()
        }
        toRegPage.setOnClickListener{
            regInit(this)
        }
        forgor.setOnClickListener{
            forgorInit(this)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
    private fun loginInit(){
        gettest(username.text.toString(), password.text.toString(), this)

    }
    private fun regInit(context:Context){
        val intent = Intent(context, RegisterActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }
    private fun forgorInit(context:Context){
        val intent = Intent(context, ForgorActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }

    private fun gettest(login:String, password:String, context: Context) {
        RetrofitClient.instance?.getMyApi()?.getTest2(login, password)
            ?.enqueue(object : Callback<Message?> {
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
                    Toast.makeText(applicationContext, "An error has occured", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

}