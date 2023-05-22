package com.example.restapiidemo.home.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.*
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.create_news_dialog.*
import kotlinx.android.synthetic.main.create_news_dialog.view.*
import kotlinx.android.synthetic.main.create_ticket_dialog.*
import kotlinx.android.synthetic.main.create_ticket_dialog.view.*
import kotlinx.android.synthetic.main.flight.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class Bet3Fragment : Fragment(), AdapterView.OnItemClickListener {
    private var listView: ListView? = null
    private var nAdapter:FlightAdapter? = null
    private var arrayList:ArrayList<Flight>? = null
    private var cityList:List<String>? = null
    var resultText: TextView? = null
    var mathText: TextView? = null
    var auto: AutoCompleteTextView? = null
    var auto2: AutoCompleteTextView? = null
    var btnDate: TextView? = null
    var arrayList2:ArrayList<Flight>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getList(MainActivity.applicationContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (resultText == null) {
            Log.d("TAG", "resultText is null")
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.flight, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun updateLable(myCalendar: Calendar){
        val format = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(format, Locale.UK)
        btnDate!!.text = sdf.format(myCalendar.time)
    }

    private fun getList(context1: Context) {
        RetrofitClient.instance?.getMyApi()?.getFlightList()?.enqueue(object :
            Callback<List<Flight?>> {
            override fun onResponse(call: Call<List<Flight?>>, response: Response<List<Flight?>>) {
                val list: List<Flight?>? = response.body()
                arrayList = list as ArrayList<Flight>?
                listView = cardview_flights
                setFLData(context1, arrayList)
                arrayList2 = arrayList

            }
            override fun onFailure(call: Call<List<Flight?>>, t: Throwable) {
                call.cancel()
            }
        })
        RetrofitClient.instance?.getMyApi()?.getCitys()?.enqueue(object :
            Callback<List<String?>> {
            override fun onResponse(call: Call<List<String?>>, response: Response<List<String?>>) {
                val list: List<String?> = response.body()!!
                auto = auto_complete_txt
                cityList = list as ArrayList<String>?
                var adapter:ArrayAdapter<String> = ArrayAdapter(context1, R.layout.list_item, list)
                auto?.setAdapter(adapter)
                auto2 = auto_complete_txt2
                auto2?.setAdapter(adapter)

                class Item():AdapterView.OnItemClickListener{
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val from = auto?.text.toString()
                        val to = auto2?.text.toString()
                        arrayList2 = ArrayList<Flight>()
                        if (btnDate!!.text == "Pick a Date"){
                            if (from == "" && to == "") arrayList2 = arrayList!!
                            else if (from == ""){
                                arrayList!!.forEach {
                                    if (it.arrivalCityName == to) arrayList2!!.add(it)
                                }
                            }
                            else if (to == ""){
                                arrayList!!.forEach {
                                    if (it.departureCityName == from) arrayList2!!.add(it)
                                }
                            }
                            else {
                                arrayList!!.forEach {
                                    if (it.departureCityName == from && it.arrivalCityName == to) arrayList2!!.add(it)
                                }
                            }
                        }
                        else {
                            val formatter = SimpleDateFormat("yyyy-MM-dd")
                            val dt:Date = Date(formatter.parse(btnDate!!.text.toString()).time)
                            val cal1 = Calendar.getInstance()
                            val cal2 = Calendar.getInstance()
                            cal1.time = dt

                            if (from == "" && to == "") {
                                arrayList!!.forEach {
                                    cal2.timeInMillis = it.departureTime!!.time
                                    if (cal1[Calendar.YEAR] === cal2[Calendar.YEAR] &&
                                        cal1[Calendar.DAY_OF_YEAR] === cal2[Calendar.DAY_OF_YEAR]) arrayList2!!.add(it)
                                }
                            }
                            else if (from == ""){
                                arrayList!!.forEach {
                                    cal2.timeInMillis = it.departureTime!!.time
                                    if (it.arrivalCityName == to && cal1[Calendar.YEAR] === cal2[Calendar.YEAR] &&
                                        cal1[Calendar.DAY_OF_YEAR] === cal2[Calendar.DAY_OF_YEAR]) arrayList2!!.add(it)
                                }
                            }
                            else if (to == ""){
                                arrayList!!.forEach {
                                    cal2.timeInMillis = it.departureTime!!.time
                                    if (it.departureCityName == from && cal1[Calendar.YEAR] === cal2[Calendar.YEAR] &&
                                        cal1[Calendar.DAY_OF_YEAR] === cal2[Calendar.DAY_OF_YEAR]) arrayList2!!.add(it)
                                }
                            }
                            else {
                                arrayList!!.forEach {
                                    cal2.timeInMillis = it.departureTime!!.time
                                    if (it.departureCityName == from && it.arrivalCityName == to && cal1[Calendar.YEAR] === cal2[Calendar.YEAR] &&
                                        cal1[Calendar.DAY_OF_YEAR] === cal2[Calendar.DAY_OF_YEAR]) arrayList2!!.add(it)
                                }
                            }
                        }
                        setFLData(context1, arrayList2)
                    }
                }
                auto?.onItemClickListener = Item()
                auto2?.onItemClickListener = Item()

                btnDate = get_date
                val myCalendar = Calendar.getInstance()
                val datePicker = DatePickerDialog.OnDateSetListener{
                        view, year, month, dayofMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
                    updateLable(myCalendar)
                    Item().onItemClick(null, null, 0, 0)

                }
                btnDate!!.setOnClickListener{
                    DatePickerDialog(context!!, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
                }
            }
            override fun onFailure(call: Call<List<String?>>, t: Throwable) {
                call.cancel()
            }
        })
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Bet2Fragment().apply {
            }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var item: Flight = arrayList2?.get(p2)!!
        val dialog = Dialog(context!!)
        val view = LayoutInflater.from(context).inflate(R.layout.create_ticket_dialog, null)
        dialog.setContentView(view)

        view.btn_closeTK.setOnClickListener {
            dialog.cancel()
        }
        view.btn_createTK.setOnClickListener {
            val sManagement = SessionManagement(context!!)
            val userId: Int = sManagement.getSession()
            if (userId == -1){
                Toast.makeText(context!!, "Для этого дейстивия вы должны войти", Toast.LENGTH_LONG)
                    .show()
            }
            else{
                try {
                    var ToT = dialog.typeOfTicket.text.toString()
                    var ID = dialog.passID.text.toString().toInt()
                    RetrofitClient.instance?.getMyApi()?.chechPID(ID)
                        ?.enqueue(object : Callback<Message?> {
                            override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                                if (response.isSuccessful) {
                                    if (ToT == "economy" || ToT == "firstClass" || ToT == "lux"){
                                        var thing:Ticket = Ticket(item.flightID, ID, ToT, item.luxCost)
                                        when (ToT)
                                        {
                                            "economy" -> thing = Ticket(item.flightID, ID, ToT, item.economyCost)
                                            "firstClass" -> thing = Ticket(item.flightID, ID, ToT, item.firstClassCost)
                                            "lux" -> thing = Ticket(item.flightID, ID, ToT, item.luxCost)
                                        }
                                        Bucket.myClassList.add(thing)
                                        dialog.cancel()
                                        Toast.makeText(context!!, "Билет добавлен в корзину", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                    else {
                                        Toast.makeText(context!!, "Введите корректный тип места", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                } else {
                                    Toast.makeText(context!!, "Такого пользователя не существует", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }

                            override fun onFailure(call: Call<Message?>, t: Throwable) {
                                Toast.makeText(context!!, "An error has occured", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                }
                catch (e:Exception){
                    Toast.makeText(context!!, e.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
        dialog.lux.text = "lux: " + item.luxReserved.toString() + "/" + item.luxTotal.toString() + " Cost:" + item.luxCost.toString()
        dialog.econom.text = "economy: " + item.economyReserved.toString() + "/" + item.economyTotal.toString() + " Cost:" + item.economyCost.toString()
        dialog.firstclass.text ="firstClass: " +  item.firstClassReserved.toString() + "/" + item.firstClassTotal.toString() + " Cost:" + item.firstClassCost.toString()
    }

    fun setFLData(context: Context, list: ArrayList<Flight>?){
        nAdapter = FlightAdapter(context, list!!)
        listView?.adapter = nAdapter
        listView?.onItemClickListener = this@Bet3Fragment
    }
}