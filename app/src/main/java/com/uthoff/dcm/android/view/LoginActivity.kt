package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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
        spCompany = findViewById(R.id.login_sp_company)
        inEmail = findViewById(R.id.login_input_username)
        inPw = findViewById(R.id.login_input_pw)
        btnLogin = findViewById(R.id.login_btn_save)

        btnLogin.setOnClickListener { onClickLogin() }
    }

    private fun onClickLogin() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}