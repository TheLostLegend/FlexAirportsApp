package com.example.restapiidemo.home.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restapiidemo.R

class TicketAdapter(var context: Context, var arrayList:ArrayList<Ticket>): BaseAdapter() {
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
        val view:View = View.inflate(context, R.layout.ticket_item_view, null)
        var fl:TextView = view.findViewById(R.id.flightID)
        var ps:TextView = view.findViewById(R.id.passengerID)
        var cl:TextView = view.findViewById(R.id.Class)

        var tk:Ticket = arrayList[p0]

        fl.text = "FlightID: " +tk.flightID.toString()
        ps.text = "CustomerID: " + tk.customerID.toString()
        cl.text = "Место: " + tk.seatClass

        return view
    }
}