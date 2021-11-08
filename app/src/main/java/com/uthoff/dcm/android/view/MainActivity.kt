package com.uthoff.dcm.android.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.uthoff.dcm.android.repository.model.User
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.view.fragments.*

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
        setUpListeners()
    }

    private fun setUpUi() {
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)

        navigationView.getHeaderView(0)
            .findViewById<TextView>(R.id.nav_header_name)
            .text = user.fullName()
    }

    private fun setUpListeners() {
        topAppBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        setFragment(TimeFragment(), "planned")
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.planned -> setFragment(TimeFragment(), "planned")
                R.id.clocking -> setFragment(ClockingFragment(), null)
                R.id.abrequest -> setFragment(AbRequestFragment(), null)
                R.id.actual -> setFragment(TimeFragment(), "actual")
                R.id.contact -> setFragment(ContactFragment(), null)
                R.id.pwchange -> setFragment(PasswordChangeFragment(), null)
                R.id.logout -> startActivity(Intent(this, LoginActivity::class.java))
            }

            menuItem.isChecked = true
            topAppBar.title = menuItem.title
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.web -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://cif24.de/login?customer=47110815"))
                    startActivity(browserIntent)
                    true
                }
                R.id.licences -> {
                    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun AppCompatActivity.setFragment(fragment: Fragment, type: String?) {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        if (type != null) bundle.putString("timeType", type)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {}
}