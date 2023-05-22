package com.example.restapiidemo.home.ui

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.Offer
import com.example.restapiidemo.home.data.OfferAdapter
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.discounts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Bet2Fragment : Fragment() {
    private var listView: ListView? = null
    private var nAdapter:OfferAdapter? = null
    private var arrayList:ArrayList<Offer>? = null

    var resultText: TextView? = null
    var mathText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getList(MainActivity.applicationContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.discounts, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun getList(context1: Context) {
        RetrofitClient.instance?.getMyApi()?.getOfferList()?.enqueue(object : Callback<List<Offer?>> {
            override fun onResponse(call: Call<List<Offer?>>, response: Response<List<Offer?>>) {
                val list: List<Offer?>? = response.body()
                arrayList = list as ArrayList<Offer>?
                listView = cardview_offers
                nAdapter = OfferAdapter(context1, arrayList!!)
                listView?.adapter = nAdapter
                class Promo():AdapterView.OnItemClickListener{
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        var item: Offer = arrayList?.get(p2)!!
                        setClipboard(context!!, item.promocodeValue!!)
                    }
                }
                listView?.onItemClickListener = Promo()
            }
            override fun onFailure(call: Call<List<Offer?>>, t: Throwable) {
                call.cancel()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Bet2Fragment().apply {
            }
    }

    private fun setClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        val toast = Toast.makeText(context, "Скопирован в буфер обмена", Toast.LENGTH_SHORT)
        toast.show()
    }
}