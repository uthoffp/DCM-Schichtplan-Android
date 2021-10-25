package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R

class LoginActivity : AppCompatActivity() {
    private lateinit var spCompany: TextInputLayout
    private lateinit var inEmail: TextInputLayout
    private lateinit var inPw: TextInputLayout
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpUi()
    }

    private fun setUpUi() {
        spCompany = findViewById(R.id.frag_ab_request_type)
        inEmail = findViewById(R.id.frag_abrequest_in_comment)
        inPw = findViewById(R.id.login_input_pw)
        btnLogin = findViewById(R.id.login_btn_save)

        btnLogin.setOnClickListener { onClickLogin() }
    }

    private fun onClickLogin() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}