package com.example.restapiidemo.home.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.Message
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.home.data.User
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_forgor.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgorActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Grave of Memories"
        confirm_forgor.setOnClickListener {
            gettest(email_forgor.text.toString(), this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun gettest(email:String, context: Context) {
        RetrofitClient.instance?.getMyApi()?.resetPass(email)
            ?.enqueue(object : Callback<Message?> {
                override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Сообщение отправоено на почту", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Email неверен", Toast.LENGTH_LONG)
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