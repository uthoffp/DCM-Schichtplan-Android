package com.uthoff.dcm.android.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.User

class PasswordChangeFragment : Fragment() {
    private lateinit var user: User

    private lateinit var inOldPw: TextInputLayout
    private lateinit var inNewPw: TextInputLayout
    private lateinit var inConfPw: TextInputLayout
    private lateinit var btnChange: Button

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
        val view: View = inflater.inflate(R.layout.fragment_password_change, container, false)
        setUpUi(view)
        return view
    }

    private fun setUpUi(view: View) {
        inOldPw = view.findViewById(R.id.frag_time_in_date)
        inNewPw = view.findViewById(R.id.frag_pwchange_in_newpw)
        inConfPw = view.findViewById(R.id.frag_pwchange_in_confirmpw)
        btnChange = view.findViewById(R.id.frag_pwchange_btn_change)

        btnChange.setOnClickListener { onClickChange() }
    }

    private fun onClickChange() {

    }
}