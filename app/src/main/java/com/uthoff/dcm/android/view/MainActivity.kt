package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.datasource.ApiRequests
import com.uthoff.dcm.android.view.fragments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUi()
        setListeners()
    }

    private fun setUpUi() {
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
    }

    private fun setListeners() {
        topAppBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        setFragment(PlannedFragment())
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.abrequest -> setFragment(AbRequestFragment())
                R.id.actual -> setFragment(ActualFragment())
                R.id.clocking -> setFragment(ClockingFragment())
                R.id.contact -> setFragment(ContactFragment())
                R.id.pwchange -> setFragment(PasswordChangeFragment())
                R.id.planned -> setFragment(PlannedFragment())
                R.id.logout -> startActivity(Intent(this, LoginActivity::class.java))
            }

            menuItem.isChecked = true
            topAppBar.title = menuItem.title
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun AppCompatActivity.setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun test() {
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.56.1:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val service = retrofit.create(ApiRequests::class.java)
        val call = service.getLandingPage()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 200) {
                    val i: Int = 0;
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val i: Int = 0;
            }
        })
    }

    override fun onBackPressed() {
    }
}