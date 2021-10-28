package com.uthoff.dcm.android.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.ClockingTime
import com.uthoff.dcm.android.repository.model.User
import com.uthoff.dcm.android.view.adapter.ClockingTimeAdapter
import com.uthoff.dcm.android.viewmodel.ClockingViewModel

class ClockingFragment : Fragment() {
    private lateinit var user: User
    private lateinit var clockingViewModel: ClockingViewModel

    private lateinit var fragView: View
    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private lateinit var btnComes: FloatingActionButton
    private lateinit var btnGoes: FloatingActionButton
    private lateinit var recView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.get("user") as User
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragView = inflater.inflate(R.layout.fragment_clocking, container, false)
        setUpUi()
        setUpViewModel()
        return fragView
    }

    private fun setUpUi() {
        txtDate = fragView.findViewById(R.id.frag_clocking_txt_date)
        txtTime = fragView.findViewById(R.id.frag_clocking_txt_time)
        btnComes = fragView.findViewById(R.id.frag_clocking_btn_comes)
        btnGoes = fragView.findViewById(R.id.frag_clocking_btn_goes)
        recView = fragView.findViewById(R.id.frag_clocking_recview)

        btnComes.setOnClickListener { clockingViewModel.clocking(1) }
        btnGoes.setOnClickListener { clockingViewModel.clocking(2) }
    }

    private fun setUpViewModel() {
        clockingViewModel = ClockingViewModel(user)
        clockingViewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
        clockingViewModel.date.observe(viewLifecycleOwner, dateObserver)
        clockingViewModel.time.observe(viewLifecycleOwner, timeObserver)
        clockingViewModel.clockingTime.observe(viewLifecycleOwner, clockingTimesObserver)
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(fragView, it, Snackbar.LENGTH_LONG).show()
    }

    private val dateObserver = Observer<String> {
        txtDate.text = it
    }

    private val timeObserver = Observer<String> {
        txtTime.text = it
    }

    private val clockingTimesObserver = Observer<List<ClockingTime>> {
        recView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recView.adapter = ClockingTimeAdapter(it)

    }
}