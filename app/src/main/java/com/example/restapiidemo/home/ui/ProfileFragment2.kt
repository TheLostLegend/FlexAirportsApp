package com.example.restapiidemo.home.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.*
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.create_cart_dialog.*
import kotlinx.android.synthetic.main.create_cart_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment2 : Fragment() {
    private var listView: ListView? = null
    private var nAdapter: TicketAdapter? = null
    private var arrayList:ArrayList<Ticket>? = null
    var resultText: TextView? = null
    var mathText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayList = Bucket.myClassList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.p_fragment2, null)
        listView = (v.findViewById(R.id.cardview_tickets) as ListView)
        nAdapter = TicketAdapter(context!!, arrayList!!)
        listView?.adapter = nAdapter
        class Promo(): AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var item:Ticket = arrayList?.get(p2)!!
                val dialog = Dialog(context!!)
                val view = LayoutInflater.from(context).inflate(R.layout.create_cart_dialog, null)
                dialog.setContentView(view)

                view.btn_closeBK.setOnClickListener {
                    dialog.cancel()
                }
                view.btn_deleteBK.setOnClickListener {
                    Bucket.myClassList.remove(item)
                    arrayList = Bucket.myClassList
                    nAdapter = TicketAdapter(context!!, arrayList!!)
                    listView?.adapter = nAdapter
                    dialog.cancel()
                }
                view.btn_payBK.setOnClickListener{
                    val str = dialog.Promocode.text.toString()
                    if (str != ""){
                        RetrofitClient.instance?.getMyApi()?.checkPromo(str, item.flightID )
                            ?.enqueue(object : Callback<Message?> {
                                override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                                    if (response.isSuccessful) {
                                    next(item, str, dialog)
                                    } else {
                                        Toast.makeText(context!!, "Промокод неверен", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                                override fun onFailure(call: Call<Message?>, t: Throwable) {
                                    Toast.makeText(context!!, "An error has occured", Toast.LENGTH_LONG)
                                        .show()
                                }
                            })
                    }
                    else{
                        next(item, null, dialog)
                    }
                }
                val window = dialog.window
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                dialog.show()
            }
        }
        listView?.onItemClickListener = Promo()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun next(item: Ticket, str: String?, dialog: Dialog){
        val sManagement = SessionManagement(context!!)
        val userId: Int = sManagement.getSession()
        RetrofitClient.instance?.getMyApi()?.getPData(userId)?.enqueue(object :
            Callback<PassportData?> {
            override fun onResponse(call: Call<PassportData?>, response: Response<PassportData?>) {
                if (response.isSuccessful) {
                    var order:Order = Order(item.price, "USD", "paypal", "sale", item.seatClass + ";" + item.flightID + ";" + item.customerID, str)
                    RetrofitClient.instance?.getMyApi()?.pay(order)
                        ?.enqueue(object : Callback<Message?> {
                            override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.status.toString() == "success"){
                                        Bucket.myClassList.remove(item)
                                        arrayList = Bucket.myClassList
                                        nAdapter = TicketAdapter(context!!, arrayList!!)
                                        listView?.adapter = nAdapter
                                        dialog.cancel()
                                        Toast.makeText(context!!, "Поздравляем с приобретением!", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                    else{
                                        Bucket.myClassList.remove(item)
                                        arrayList = Bucket.myClassList
                                        nAdapter = TicketAdapter(context!!, arrayList!!)
                                        listView?.adapter = nAdapter
                                        dialog.cancel()

                                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(response.body()!!.status))
                                        startActivity(browserIntent)
                                    }
                                } else {
                                    Toast.makeText(context!!, "Что-то не так", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                            override fun onFailure(call: Call<Message?>, t: Throwable) {
                                Toast.makeText(context!!, "An error has occured", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })

                }
                else Toast.makeText(context!!, "Нет паспортных данных", Toast.LENGTH_LONG)
                    .show()
            }
                override fun onFailure(call: Call<PassportData?>, t: Throwable) {
                    call.cancel()
                }
            })
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Bet1Fragment().apply {
            }
    }
}