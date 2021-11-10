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
import com.uthoff.dcm.android.viewmodel.AbRequestViewModel

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.uthoff.dcm.android.view.dialogs.AbRequestBottomSheet
import com.uthoff.dcm.android.view.dialogs.PictureSelectBottomSheet
import com.uthoff.dcm.android.viewmodel.DateFormatter
import java.util.*


class AbRequestFragment : Fragment() {
    private lateinit var user: User
    private lateinit var viewModel: AbRequestViewModel

    private lateinit var fragView: View
    private lateinit var spType: TextInputLayout
    private lateinit var inStartDate: TextInputEditText
    private lateinit var inStartType: TextInputLayout
    private lateinit var spStartType: AutoCompleteTextView
    private lateinit var inStopDate: TextInputEditText
    private lateinit var spStopType: AutoCompleteTextView
    private lateinit var inStopType: TextInputLayout
    private lateinit var inComment: TextInputLayout
    private lateinit var btnAttach: Button
    private lateinit var txtRemove: TextView
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
        fragView = inflater.inflate(R.layout.fragment_abrequest, container, false)
        setUpUi()
        setUpViewModel()
        return fragView
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpUi() {
        spType = fragView.findViewById(R.id.frag_anrequest_sp_type)
        inStartDate = fragView.findViewById(R.id.frag_anrequest_et_start_day)
        inStartType = fragView.findViewById(R.id.frag_anrequest_et_start_type)
        spStartType = fragView.findViewById(R.id.frag_anrequest_sp_start_type)
        inStopDate = fragView.findViewById(R.id.frag_anrequest_et_stop_day)
        inStopType = fragView.findViewById(R.id.frag_abrequest_et_stop_type)
        spStopType = fragView.findViewById(R.id.frag_abrequest_sp_stop_type)
        inComment = fragView.findViewById(R.id.login_in_username)
        btnAttach = fragView.findViewById(R.id.frag_abrequest_btn_attach)
        txtRemove = fragView.findViewById(R.id.frag_abrequest_txt_remove)
        btnCheck = fragView.findViewById(R.id.frag_abrequest_btn_check)

        inStartDate.inputType = InputType.TYPE_NULL
        inStartDate.setOnTouchListener(onOpenDateTimePicker("start"))
        inStopDate.inputType = InputType.TYPE_NULL
        inStopDate.setOnTouchListener(onOpenDateTimePicker("stop"))
        inStartDate.setText(DateFormatter.dateGetDateString(Date().time))
        inStopDate.setText(DateFormatter.dateGetDateString(Date().time))

        val dayTypes = listOf(
            getString(R.string.menu_day_full),
            HtmlCompat.fromHtml(getString(R.string.menu_day_half), HtmlCompat.FROM_HTML_MODE_LEGACY)
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, dayTypes)
        spStartType.setAdapter(adapter)
        spStopType.setAdapter(adapter)

        txtRemove.setOnClickListener { viewModel.setImage(null) }
        btnCheck.setOnClickListener { onClickCheck() }
        btnAttach.setOnClickListener { onClickAttach() }
    }

    private fun setUpViewModel() {
        viewModel = AbRequestViewModel(user)
        viewModel.abTypes.observe(viewLifecycleOwner, abTypeObserver)
        viewModel.message.observe(viewLifecycleOwner, messageObserver)
        viewModel.image.observe(viewLifecycleOwner, imageObserver)

    }

    private val abTypeObserver = Observer<List<String>> {
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, it)
        (spType.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private val messageObserver = Observer<String> {
        Snackbar.make(fragView, it, Snackbar.LENGTH_LONG).show()
    }

    private val imageObserver = Observer<Any> {
        if (it != null) {
            btnAttach.text = getString(R.string.btn_attach_change)
            txtRemove.visibility = View.VISIBLE
            Snackbar.make(fragView, "Anhang erfolgreich angefügt", Snackbar.LENGTH_SHORT).show()
        } else {
            btnAttach.text = getString(R.string.btn_attach)
            txtRemove.visibility = View.INVISIBLE
        }
    }

    private fun onClickCheck() {
        if (viewModel.validateUserInput(
                spType.editText?.text.toString(),
                inComment.editText?.text.toString()
            )
        ) {
            val abRequestBottomSheet = AbRequestBottomSheet(viewModel)
            abRequestBottomSheet.show(childFragmentManager, "AbRequestBottomSheet")
        }

    }

    private fun onClickAttach() {
        val abRequestBottomSheet = PictureSelectBottomSheet(viewModel)
        abRequestBottomSheet.show(childFragmentManager, "PictureSelectBottomSheet")
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
                            "start" -> {
                                inStartDate.setText(dateRangePicker.selection?.let { it1 ->
                                    DateFormatter.dateGetDateString(it1)
                                })
                                viewModel.start = it
                            }
                            "stop" -> {
                                inStopDate.setText(dateRangePicker.selection?.let { it1 ->
                                    DateFormatter.dateGetDateString(it1)
                                })
                                viewModel.stop = it
                            }
                        }
                    }
                    dateRangePicker.show(childFragmentManager, "startDate")
                }
            }
            return@OnTouchListener true
        }
    }
}