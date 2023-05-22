package com.example.restapiidemo.home.ui

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget
import com.bluehomestudio.luckywheel.WheelItem
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.*
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.discounts.*
import kotlinx.android.synthetic.main.wheel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.sqrt


class Bet4Fragment() : Fragment() {
    var arr:List<com.example.restapiidemo.home.data.WheelItem>? = null
    var resultText: TextView? = null
    var points:String = ""
    lateinit var luckyWheel:LuckyWheel
    val wheelItems: MutableList<WheelItem> = ArrayList()
    val wheelItems2: MutableList<WheelItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun spin(){
        val random = Random()
        if (arr != null){
            points = random.nextInt(arr!!.size).toString()
            luckyWheel.rotateWheelTo(points.toInt())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (resultText == null) {
            Log.d("TAG", "resultText is null")
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wheel, container, false)
    }

    private fun getWheel(context1: Context) {
        RetrofitClient.instance?.getMyApi()?.getWheel()?.enqueue(object :
            Callback<List<com.example.restapiidemo.home.data.WheelItem>> {
            override fun onResponse(
                call: Call<List<com.example.restapiidemo.home.data.WheelItem>>,
                response: Response<List<com.example.restapiidemo.home.data.WheelItem>>
            ) {
                if (response.isSuccessful){
                    arr = response.body()
                    arr?.forEach(){
                        wheelItems.add(WheelItem(
                            Color.alpha(it.trackID),
                            BitmapFactory.decodeResource(resources, R.drawable.code_android_logo),
                            it.departureAirportName + "\\" + it.arrivalAirportName))
                    }
                    luckyWheel.addWheelItems(wheelItems)
                    spin.setOnClickListener {
                        spin()
                    }
                    class on:OnLuckyWheelReachTheTarget{
                        override fun onReachTarget() {
                            if (points.toInt() == 0) points = arr!!.size.toString()
                            var item: String? = wheelItems[points.toInt()-1].text
                            val points_am: String? = item
                            val toast = Toast.makeText(MainActivity.applicationContext(), points_am, Toast.LENGTH_SHORT)
                            toast.show()
                            var order: Order = Order(5F, "USD", "paypal", "sale", arr!![points.toInt()-1].trackID.toString(), null)
                            RetrofitClient.instance?.getMyApi()?.payF(order)
                                ?.enqueue(object : Callback<Message2?> {
                                    override fun onResponse(call: Call<Message2?>, response: Response<Message2?>) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(context!!, response.body()!!.promoValue, Toast.LENGTH_LONG)
                                                .show()
                                            val clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = ClipData.newPlainText("Copied Text", response.body()!!.promoValue)
                                            clipboard.setPrimaryClip(clip)
                                            val toast = Toast.makeText(context, "Скопирован в буфер обмена", Toast.LENGTH_SHORT)
                                            toast.show()
                                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(response.body()!!.paymentUrl))
                                            startActivity(browserIntent)
                                        } else {
                                            Toast.makeText(context!!, "Что-то не так", Toast.LENGTH_LONG)
                                                .show()
                                        }
                                    }
                                    override fun onFailure(call: Call<Message2?>, t: Throwable) {
                                        Toast.makeText(context!!, "An error has occured", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                })
                        }
                    }
                    val test:on = on()
                    luckyWheel.setLuckyWheelReachTheTarget(test)
                }
                else{
                    val toast = Toast.makeText(context, "Упс", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

            override fun onFailure(
                call: Call<List<com.example.restapiidemo.home.data.WheelItem>>,
                t: Throwable
            ) {
                val toast = Toast.makeText(context, "Упс", Toast.LENGTH_SHORT)
                toast.show()
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        luckyWheel = lucky_wheel
        getWheel(context!!)
        wheelItems2.add(WheelItem(
            Color.alpha(0),
            BitmapFactory.decodeResource(resources, R.drawable.code_android_logo),
            "Вам лучше не ехать"))
        luckyWheel.addWheelItems(wheelItems2)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Bet2Fragment().apply {
            }
    }
}