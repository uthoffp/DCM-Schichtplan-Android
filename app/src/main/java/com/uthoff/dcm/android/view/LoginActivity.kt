package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.viewmodel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginActivityViewModel = LoginActivityViewModel()

    private lateinit var view: View
    private lateinit var inCompany: TextInputLayout
    private lateinit var spCompany: AutoCompleteTextView
    private lateinit var inEmail: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var inPw: TextInputLayout
    private lateinit var etPw: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpUi()
        setUpViewModel()
    }

    private fun setUpUi() {
        view = findViewById(R.id.login_view)
        inCompany = findViewById(R.id.login_in_company)
        spCompany = findViewById(R.id.login_sp_company)
        inEmail = findViewById(R.id.login_in_username)
        etEmail = findViewById(R.id.login_et_username)
        inPw = findViewById(R.id.login_in_pw)
        etPw = findViewById(R.id.login_et_pw)
        btnLogin = findViewById(R.id.frag_pwchange_btn_change)

        btnLogin.setOnClickListener { onClickLogin() }
        spCompany.setOnItemClickListener {_, _, posititon, _ -> btnLogin.isEnabled = true }
    }

    private fun setUpViewModel() {
        loginViewModel.companies.observe(this, companyObserver)
        loginViewModel.errorMessage.observe(this, errorMessageObserver)
    }

    private val companyObserver = Observer<List<Company>> {
        val adapter = ArrayAdapter(this,
            R.layout.dropdown_item,
            it.map { company: Company -> company.CompanyName1 })
        (inCompany.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        //inEmail.isErrorEnabled = true
        //inPw.isErrorEnabled = true
    }

    private fun onClickLogin() {
        if(!loginViewModel.validateInput(etEmail, etPw)) return
        startActivity(Intent(this, MainActivity::class.java))
    }
}
