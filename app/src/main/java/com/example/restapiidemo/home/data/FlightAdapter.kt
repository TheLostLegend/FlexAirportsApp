package com.example.restapiidemo.home.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restapiidemo.R

class FlightAdapter(var context: Context, var arrayList:ArrayList<Flight>): BaseAdapter() {
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
        val view:View = View.inflate(context, R.layout.flight_item_view, null)
        var dep:TextView = view.findViewById(R.id.item_dep)
        var time:TextView = view.findViewById(R.id.item_time)
        var arr:TextView = view.findViewById(R.id.item_arr)

        var flight:Flight = arrayList[p0]

        dep.text = flight.departureAirportName + " in " +  flight.departureCityName
        time.text = flight.departureTime.toString() + " \n" + flight.arrivalTime.toString()
        arr.text = flight.arrivalAirportName + " in " + flight.arrivalCityName

        return view
    }
}