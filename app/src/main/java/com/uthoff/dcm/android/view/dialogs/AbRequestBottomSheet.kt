package com.uthoff.dcm.android.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.viewmodel.AbRequestViewModel

class AbRequestBottomSheet(private val viewModel: AbRequestViewModel) : BottomSheetDialogFragment() {
    private lateinit var v: View
    private lateinit var txtPrevYear: TextView
    private lateinit var txtThisYear: TextView
    private lateinit var txtTotal: TextView
    private lateinit var txtCorrections: TextView
    private lateinit var txtTaken: TextView
    private lateinit var txtOpen: TextView
    private lateinit var txtThisRequest: TextView
    private lateinit var txtRemaining: TextView
    private lateinit var txtResultText: TextView
    private lateinit var btnSend: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.bottomsheet_abrequest, container, false)

        setUpUi()
        setUpViewModel()
        return v
    }

    private fun setUpUi() {
        txtPrevYear = v.findViewById(R.id.btmsht_abrequest_val_prevyear)
        txtThisYear = v.findViewById(R.id.btmsht_abrequest_val_thisyear)
        txtTotal = v.findViewById(R.id.btmsht_abrequest_val_total)
        txtCorrections = v.findViewById(R.id.btmsht_abrequest_val_corrctions)
        txtTaken = v.findViewById(R.id.btmsht_abrequest_val_taken)
        txtOpen = v.findViewById(R.id.btmsht_abrequest_val_open)
        txtThisRequest = v.findViewById(R.id.btmsht_abrequest_val_request)
        txtRemaining = v.findViewById(R.id.btmsht_abrequest_val_remaining)
        txtResultText = v.findViewById(R.id.btmsht_abrequest_txt_result)
        btnSend = v.findViewById(R.id.btmsht_abrequest_btn_send)

        btnSend.setOnClickListener { viewModel.sendRequest()}
    }

    private fun setUpViewModel() {
        viewModel.checkResult.observe(viewLifecycleOwner, checkResultObserver)
        viewModel.isValid.observe(viewLifecycleOwner, isValidObserver)
    }

    private var checkResultObserver = Observer<Map<String, Double?>> {
        txtPrevYear.text = it["prevYear"].toString()
        txtThisYear.text = it["thisYear"].toString()
        txtTotal.text = it["total"].toString()
        txtCorrections.text = it["correction"].toString()
        txtTaken.text = it["taken"].toString()
        txtOpen.text = it["open"].toString()
        txtThisRequest.text = it["thisRequest"].toString()
        txtRemaining.text = it["remaining"].toString()
    }

    private var isValidObserver = Observer<Boolean> {
        if(!it) {
            txtResultText.text = "Im gewünschten Zeitraum können sie leider keinen Urlaub beantragen."
            txtResultText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_500))
            btnSend.visibility = View.GONE
        }
    }

}