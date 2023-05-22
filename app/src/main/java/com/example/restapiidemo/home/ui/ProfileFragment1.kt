package com.example.restapiidemo.home.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.Message
import com.example.restapiidemo.home.data.PassportData
import com.example.restapiidemo.home.data.PassportDataALTER
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.p_fragment1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment1 : Fragment() {

    var pData:PassportData? = null

    var resultText: TextView? = null
    var mathText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData(MainActivity.applicationContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.p_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun getData(context: Context) {
        val sManagement = SessionManagement(context)
        val userId: Int = sManagement.getSession()
        RetrofitClient.instance?.getMyApi()?.getPData(userId)?.enqueue(object :
            Callback<PassportData?> {
            override fun onResponse(call: Call<PassportData?>, response: Response<PassportData?>) {
                if (response.isSuccessful) {
                    pData = response.body()
                    val passportId:TextView = username1
                    val sex:TextView = username2
                    val givenName:TextView = username3
                    val surname:TextView = username4
                    val dateOfBirth:TextView = username5
                    val dateOfIssue:TextView = username6
                    val dateOfExpery:TextView = username7
                    val idNumber:TextView = username8
                    val country:TextView = username9
                    val authority:TextView = username10

                    passportId.text = pData!!.passportId.toString()
                    sex.text = pData!!.sex.toString()
                    givenName.text = pData!!.givenName.toString()
                    surname.text = pData!!.surname.toString()
                    dateOfBirth.text = pData!!.dateOfBirth.toString()
                    dateOfIssue.text = pData!!.dateOfIssue.toString()
                    dateOfExpery.text = pData!!.dateOfExpery.toString()
                    idNumber.text = pData!!.idNumber.toString()
                    country.text = pData!!.country.toString()
                    authority.text = pData!!.authority.toString()
                }
                bababoey.setOnClickListener {
                    passDUpdate(MainActivity.applicationContext())
                }

            }
            override fun onFailure(call: Call<PassportData?>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun passDUpdate(context: Context) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val sManagement = SessionManagement(context)
        val userId: Int = sManagement.getSession()
        val passportId:TextView = username1
        val sex:TextView = username2
        val givenName:TextView = username3
        val surname:TextView = username4
        val dateOfBirth:TextView = username5
        val dateOfIssue:TextView = username6
        val dateOfExpery:TextView = username7
        val idNumber:TextView = username8
        val country:TextView = username9
        val authority:TextView = username10
        try {
            var pData2 = PassportData( passportId.text.toString().toInt() , sex.text.toString().toBoolean(), givenName.text.toString(), surname.text.toString(), formatter.parse(dateOfBirth.text.toString()), formatter.parse(dateOfIssue.text.toString()), formatter.parse(dateOfExpery.text.toString()), idNumber.text.toString(), country.text.toString(), authority.text.toString())
            var pData3 = PassportDataALTER( passportId.text.toString().toInt() , sex.text.toString().toBoolean(), givenName.text.toString(), surname.text.toString(), Date(formatter.parse(dateOfBirth.text.toString()).time), Date(formatter.parse(dateOfIssue.text.toString()).time), Date(formatter.parse(dateOfExpery.text.toString()).time), idNumber.text.toString(), country.text.toString(), authority.text.toString())
            RetrofitClient.instance?.getMyApi2()?.passUpdate(userId, pData3)?.enqueue(object : Callback<Message?> {
                override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Данные успешно обновлены", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(context, "Введите корректные данные", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    Toast.makeText(context, "Введите корректные данные", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
        catch (e: Exception){
            Toast.makeText(context, "Введите корректные данные", Toast.LENGTH_LONG)
                .show()
        }

    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Bet1Fragment().apply {
            }
    }
}