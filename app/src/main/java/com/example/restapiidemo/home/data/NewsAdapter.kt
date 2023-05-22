package com.example.restapiidemo.home.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restapiidemo.R

class NewsAdapter(var context: Context, var arrayList:ArrayList<News>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view:View = View.inflate(context, R.layout.news_item_view, null)
        var title:TextView = view.findViewById(R.id.tv_home_item_title)
        var author:TextView = view.findViewById(R.id.tv_home_item_body)

        var news:News = arrayList[p0]

        title.text = news.header
        author.text = "directed by " + news.login

        return view
    }
}