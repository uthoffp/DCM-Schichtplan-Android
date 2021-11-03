package com.uthoff.dcm.android.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.TimeInfo
import com.uthoff.dcm.android.viewmodel.Utils

class TimeAdapter(private val timeInfos: List<TimeInfo>) :
    RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.item_time, parent, false)
        return TimeViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bindInfoTimes(timeInfos[position], position % 2 == 0)
    }

    override fun getItemCount(): Int = timeInfos.size

    class TimeViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var itemLayout: ConstraintLayout = v.findViewById(R.id.item_time_layout)
        private var txtDate: TextView = v.findViewById(R.id.item_time_date)
        private var txtShift1Time: TextView = v.findViewById(R.id.item_time_shift1_time)
        private var txtShift2Time: TextView = v.findViewById(R.id.item_time_shift2_time)
        private var txtShift1Dep: TextView = v.findViewById(R.id.item_time_shift1_dep)
        private var txtShift2Dep: TextView = v.findViewById(R.id.item_time_shift2_dep)

        @SuppressLint("SetTextI18n")
        fun bindInfoTimes(timeInfo: TimeInfo, bgChange: Boolean) {
            txtDate.text = Utils.dayDateString(timeInfo.DATE)
            if (timeInfo.StartTime1 != null) txtShift1Time.text =
                "${timeInfo.StartTime1} - ${timeInfo.EndTime1}"
            if (timeInfo.StartTime1 != null) txtShift2Time.text =
                "${timeInfo.StartTime2} - ${timeInfo.EndTime2}"
            txtShift1Dep.text = timeInfo.DivDepartmentsSt1 as CharSequence?
            txtShift2Dep.text = timeInfo.DivDepartmentsSt2 as CharSequence?

            setBgColor(bgChange)
        }

        private fun setBgColor(bgChange: Boolean) {
            //TODO switch bg color
        }
    }
}
