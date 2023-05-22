package com.example.restapiidemo.home.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.*
import com.example.restapiidemo.network.RetrofitClient
import kotlinx.android.synthetic.main.create_news_dialog.*
import kotlinx.android.synthetic.main.create_news_dialog.view.*
import kotlinx.android.synthetic.main.news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Bet1Fragment : Fragment() {

    private var listView:ListView? = null
    private var nAdapter:NewsAdapter? = null
    private var arayList:ArrayList<News>? = null

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

        return inflater.inflate(R.layout.news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun getList(context1: Context) {
        RetrofitClient.instance?.getMyApi()?.getNewsList()?.enqueue(object : Callback<List<News?>> {
            override fun onResponse(call: Call<List<News?>>, response: Response<List<News?>>) {
                val list: List<News?>? = response.body()
                arayList = list as ArrayList<News>?
                listView = cardview_news
                nAdapter = NewsAdapter(context1, arayList!!)
                listView?.adapter = nAdapter
                class LNews():AdapterView.OnItemClickListener{
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        var item:News = arayList?.get(p2)!!
                        val dialog = Dialog(context!!)
                        val view = LayoutInflater.from(context).inflate(R.layout.create_news_dialog, null)
                        dialog.setContentView(view)

                        view.btn_close.setOnClickListener {
                            dialog.cancel()
                        }
                        val window = dialog.window
                        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        dialog.show()
                        dialog.tv_home_item_title11.text = item.header
                        dialog.tv_home_item_body11.text = item.main
                    }
                }
                listView?.onItemClickListener = LNews()
            }
            override fun onFailure(call: Call<List<News?>>, t: Throwable) {
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