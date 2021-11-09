package com.uthoff.dcm.android.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.viewmodel.AbRequestViewModel

class AbRequestBottomSheet(private val viewModel: AbRequestViewModel) : BottomSheetDialogFragment() {
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.bottomsheet_abrequest, container, false)
        return v
    }

    private fun setUpUi() {

    }
}