package com.uthoff.dcm.android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.uthoff.dcm.android.repository.model.User
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.view.fragments.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user = intent.extras?.get("user") as User

        setUpUi()
        setListeners()
    }

    private fun setUpUi() {
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)

        navigationView.getHeaderView(0)
            .findViewById<TextView>(R.id.nav_header_name)
            .text = user.fullName()
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

    override fun onBackPressed() {
    }
}