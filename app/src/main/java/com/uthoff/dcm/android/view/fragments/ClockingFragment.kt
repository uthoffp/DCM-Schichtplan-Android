package com.uthoff.dcm.android.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uthoff.dcm.android.R


class ClockingFragment : Fragment() {

    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private lateinit var btnComes: FloatingActionButton
    private lateinit var btnGoes: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_clocking, container, false)
        setUpUi(view)
        return view
    }

    private fun setUpUi(view: View) {
        txtDate = view.findViewById(R.id.frag_clocking_txt_date)
        txtTime = view.findViewById(R.id.frag_clocking_txt_time)
        btnComes = view.findViewById(R.id.frag_clocking_btn_comes)
        btnGoes = view.findViewById(R.id.frag_clocking_btn_goes)

        btnComes.setOnClickListener { onClickComes() }
        btnGoes.setOnClickListener { onCLickGoes() }
    }

    private fun onClickComes() {

    }

    private fun onCLickGoes() {

    }
}