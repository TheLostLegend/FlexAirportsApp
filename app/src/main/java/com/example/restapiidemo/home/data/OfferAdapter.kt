package com.example.restapiidemo.home.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restapiidemo.R

class OfferAdapter(var context: Context, var arrayList:ArrayList<Offer>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view:View = View.inflate(context, R.layout.offer_item_view, null)
        var title:TextView = view.findViewById(R.id.tv_home_item_title1)
        var body:TextView = view.findViewById(R.id.tv_home_item_body1)
        var promo:TextView = view.findViewById(R.id.tv_home_item_body2)

        var offer:Offer = arrayList[p0]

        title.text = offer.header
        body.text = offer.main
        promo.text = offer.promocodeValue

        return view
    }
}