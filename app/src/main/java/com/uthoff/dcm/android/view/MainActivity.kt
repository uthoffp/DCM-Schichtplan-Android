package com.uthoff.dcm.android.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.view.fragments.*

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
            when(menuItem.itemId) {
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
    }

    fun AppCompatActivity.setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}