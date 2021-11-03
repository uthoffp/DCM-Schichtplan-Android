package com.uthoff.dcm.android.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.TimeInfo
import com.uthoff.dcm.android.viewmodel.Utils
import com.uthoff.dcm.android.repository.model.User
import com.uthoff.dcm.android.view.adapter.ClockingTimeAdapter
import com.uthoff.dcm.android.view.adapter.TimeAdapter
import com.uthoff.dcm.android.viewmodel.TimeViewModel

class TimeFragment : Fragment() {
    private lateinit var timeViewModel: TimeViewModel
    private lateinit var user: User
    private lateinit var timeType: String

    private lateinit var fragView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var recView: RecyclerView
    private lateinit var btnPrev: FloatingActionButton
    private lateinit var btnNext: FloatingActionButton
    private lateinit var inDate: TextInputEditText
    private lateinit var etDate: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.get("user") as User
            timeType = it.getString("timeType").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragView = inflater.inflate(R.layout.fragment_time, container, false)
        setUpUi()
        setUpViewModel()
        return fragView
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpUi() {
        recView = fragView.findViewById(R.id.frag_time_recview)
        progressBar = fragView.findViewById(R.id.frag_time_progressbar)
        btnPrev = fragView.findViewById(R.id.frag_time_fab_prev)
        btnNext = fragView.findViewById(R.id.frag_time_fab_next)
        inDate = fragView.findViewById(R.id.frag_time_et_date)
        etDate = fragView.findViewById(R.id.frag_time_in_date)

        btnPrev.setOnClickListener { timeViewModel.prevWeek() }
        btnNext.setOnClickListener { timeViewModel.nextWeek() }

        inDate.inputType = InputType.TYPE_NULL
        inDate.setOnTouchListener(onOpenDateTimePicker())
        recView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun setUpViewModel() {
        timeViewModel = TimeViewModel(user, timeType)

        timeViewModel.date.observe(viewLifecycleOwner, dateObserver)
        timeViewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
        timeViewModel.isLoading.observe(viewLifecycleOwner, loadingObserver)
        timeViewModel.times.observe(viewLifecycleOwner, timeObserver)
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(fragView, it, Snackbar.LENGTH_LONG).show()
    }

    private val dateObserver = Observer<String> {
        inDate.setText(it)
    }

    private val loadingObserver = Observer<Boolean> {
        if(it) {
            progressBar.visibility = View.VISIBLE
            recView.adapter = null
        }
        else progressBar.visibility = View.GONE
    }

    private val timeObserver = Observer<List<TimeInfo>> {
        recView.adapter = TimeAdapter(it)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onOpenDateTimePicker(): View.OnTouchListener {
        return View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    val datePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Datum Auswählen")
                            .build()
                    datePicker.addOnPositiveButtonClickListener {
                        timeViewModel.setDate(it)
                    }
                    datePicker.show(childFragmentManager, "startDate")
                }
            }
            return@OnTouchListener true
        }
    }
}