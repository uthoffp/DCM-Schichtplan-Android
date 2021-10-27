package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.repository.model.User
import com.uthoff.dcm.android.viewmodel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginActivityViewModel = LoginActivityViewModel()

    private lateinit var view: View
    private lateinit var loadingBar: LinearProgressIndicator
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
        loadingBar = findViewById(R.id.login_loading)
        inCompany = findViewById(R.id.login_in_company)
        spCompany = findViewById(R.id.login_sp_company)
        inEmail = findViewById(R.id.login_in_username)
        etEmail = findViewById(R.id.login_et_username)
        inPw = findViewById(R.id.login_in_pw)
        etPw = findViewById(R.id.login_et_pw)
        btnLogin = findViewById(R.id.frag_pwchange_btn_change)

        btnLogin.setOnClickListener { onClickLogin() }
        spCompany.setOnItemClickListener { _, _, posititon, _ -> btnLogin.isEnabled = true }
    }

    private fun setUpViewModel() {
        loginViewModel.companies.observe(this, companyObserver)
        loginViewModel.user.observe(this, loginObserver)
        loginViewModel.errorMessage.observe(this, errorMessageObserver)
    }

    private val companyObserver = Observer<List<Company>> {
        val adapter = ArrayAdapter(this,
            R.layout.dropdown_item,
            it.map { company: Company -> company.CompanyName1 })
        (inCompany.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private val loginObserver = Observer<User> {
        loadingBar.visibility = View.INVISIBLE
        Snackbar.make(view, it.firstName, Snackbar.LENGTH_LONG).show()
        launchMainActivity(it)
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        loadingBar.visibility = View.INVISIBLE
        //inEmail.isErrorEnabled = true
        //inPw.isErrorEnabled = true
    }

    private fun launchMainActivity(user: User) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        //intent.putExtra("user", user.)
    }

    private fun onClickLogin() {
        loadingBar.visibility = View.VISIBLE
        loginViewModel.login(
            spCompany.text.toString(),
            etEmail.text.toString(),
            etPw.text.toString()
        )
    }
}
