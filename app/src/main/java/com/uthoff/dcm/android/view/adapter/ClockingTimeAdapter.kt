package com.uthoff.dcm.android.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.ClockingTime

class ClockingTimeAdapter(private val clockingTimes: List<ClockingTime>) :
    RecyclerView.Adapter<ClockingTimeAdapter.ClockingTimesViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockingTimesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.item_clocking_time, parent, false)
        return ClockingTimesViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ClockingTimesViewHolder, position: Int) {
        holder.bindClockingTimes(clockingTimes[position])
    }

    override fun getItemCount(): Int = clockingTimes.size

    class ClockingTimesViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var txtDate: TextView = v.findViewById(R.id.item_clocking_date)
        private var txtTime: TextView = v.findViewById(R.id.item_clocking_time)
        private var txtStatus: TextView = v.findViewById(R.id.item_clocking_status)

        fun bindClockingTimes(clockingTime: ClockingTime) {
            txtDate.text = clockingTime.E_Date
            txtTime.text = clockingTime.E_Time
            txtStatus.text = clockingTime.E_StatusText
        }
    }
}
