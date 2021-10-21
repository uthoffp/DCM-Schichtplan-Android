package com.uthoff.dcm.android.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.datasource.Webservice
import com.uthoff.dcm.android.view.fragments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setFragment(PlannedFragment())
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.abrequest -> setFragment(AbRequestFragment())
                R.id.actual -> setFragment(ActualFragment())
                R.id.clocking -> setFragment(ClockingFragment())
                R.id.contact -> setFragment(ContactFragment())
                R.id.pwchange -> setFragment(PasswordChangeFragment())
                R.id.planned -> setFragment(PlannedFragment())
            }

            menuItem.isChecked = true
            topAppBar.title = menuItem.title
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.56.1:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val service = retrofit.create(Webservice::class.java)
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

    fun AppCompatActivity.setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}