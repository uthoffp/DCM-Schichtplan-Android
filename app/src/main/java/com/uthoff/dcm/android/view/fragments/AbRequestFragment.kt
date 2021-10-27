package com.uthoff.dcm.android.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.User

class AbRequestFragment : Fragment() {
    private lateinit var user: User

    private lateinit var spType: TextInputLayout
    private lateinit var inStartDate: TextInputEditText
    private lateinit var spStartType: TextInputLayout
    private lateinit var inStopDate: TextInputEditText
    private lateinit var spStopType: TextInputLayout
    private lateinit var inComment: TextInputLayout
    private lateinit var btnAttach: Button
    private lateinit var btnCheck: Button

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
        val view: View = inflater.inflate(R.layout.fragment_abrequest, container, false)
        setUpUi(view)
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpUi(view: View) {
        spType = view.findViewById(R.id.login_in_company)
        inStartDate = view.findViewById(R.id.frag_anrequest_et_start_day)
        spStartType = view.findViewById(R.id.frag_anrequest_sp_start_type)
        inStopDate = view.findViewById(R.id.frag_anrequest_et_stop_day)
        spStopType = view.findViewById(R.id.frag_abrequest_sp_stop_type)
        inComment = view.findViewById(R.id.login_in_username)
        btnAttach = view.findViewById(R.id.frag_abrequest_btn_attach)
        btnCheck = view.findViewById(R.id.frag_abrequest_btn_check)

        inStartDate.inputType = InputType.TYPE_NULL
        inStartDate.setOnTouchListener(onOpenDateTimePicker("start"))
        inStopDate.inputType = InputType.TYPE_NULL
        inStopDate.setOnTouchListener(onOpenDateTimePicker("stop"))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onOpenDateTimePicker(type: String): View.OnTouchListener {
        return View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    val dateRangePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Datum Auswählen")
                            .build()
                    dateRangePicker.addOnPositiveButtonClickListener {
                        when (type) {
                            "start" -> inStartDate.setText(dateRangePicker.selection.toString())
                            "stop" -> inStopDate.setText(dateRangePicker.selection.toString())
                        }
                    }
                    dateRangePicker.show(childFragmentManager, "startDate")
                }
            }
            return@OnTouchListener true
        }
    }
}