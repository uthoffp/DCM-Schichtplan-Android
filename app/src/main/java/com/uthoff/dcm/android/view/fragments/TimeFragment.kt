package com.uthoff.dcm.android.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.viewmodel.Utils
import com.uthoff.dcm.android.repository.model.User

class TimeFragment : Fragment() {
    private lateinit var user: User
    private lateinit var timeType: String

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
        val view: View = inflater.inflate(R.layout.fragment_time, container, false)
        setUpUi(view)
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpUi(view: View) {
        btnPrev = view.findViewById(R.id.frag_time_fab_prev)
        btnNext = view.findViewById(R.id.frag_time_fab_next)
        inDate = view.findViewById(R.id.frag_time_et_date)
        etDate = view.findViewById(R.id.frag_time_in_date)

        btnPrev.setOnClickListener {}
        btnNext.setOnClickListener {}
        inDate.inputType = InputType.TYPE_NULL
        inDate.setOnTouchListener(onOpenDateTimePicker())
    }

    private fun setUpViewModel() {

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
                        inDate.setText(datePicker.selection?.let { it1 ->
                            Utils.getFirstDayOfWeek(it1)
                        })
                    }
                    datePicker.show(childFragmentManager, "startDate")
                }
            }
            return@OnTouchListener true
        }
    }
}