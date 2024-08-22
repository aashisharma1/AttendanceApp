package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.databinding.ActivityMainBinding
import com.developstudio.attendanceapp.utils.ShowDialog
import com.developstudio.attendanceapp.utils.UserIdManager
import com.developstudio.attendanceapp.viewmodels.UserViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewmodel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        // Find the NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        // Get the NavController
        navController = navHostFragment.navController

        // Setup NavigationView with NavController
        binding.navigationView.setupWithNavController(navController)

        // Set up ActionBar with NavController and DrawerLayout
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set the NavigationItemSelectedListener
        binding.navigationView.setNavigationItemSelectedListener(this)

        // Access and modify the NavigationView header
        val headerView = binding.navigationView.getHeaderView(0)
        val headerTextView = headerView.findViewById<TextView>(R.id.examTextView)
        val headerTextViewPerson = headerView.findViewById<TextView>(R.id.operatorTextView)
        // Set or modify header content
        headerTextView.text = "Welcome, User!"



        if (UserIdManager.getUserId(this).isNullOrEmpty()) {
            ShowDialog.showSnackbar(binding.toolbar, "Please Login")
            finish()
        } else {
            UserIdManager.getUserId(this)?.let { viewmodel.getUserDetails(it) }
        }

        viewmodel.userDetails.observe(this) {

            if (it != null) {
                headerTextViewPerson.text = "Details :" + it.name + " " + it.phoneNumber
                headerTextView.text = "Exam : " + it.examName
            }

        }


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Logout and other options
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.downloadCandidates -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                ShowDialog.showSnackbar(binding.toolbar, "Downloading Candidates")
                true
            }

            R.id.logOut -> {
                UserIdManager.saveUserId(this@MainActivity, "")
                this@MainActivity.finish()

                true
            }

            else -> {
                NavigationUI.onNavDestinationSelected(item, navController)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

}