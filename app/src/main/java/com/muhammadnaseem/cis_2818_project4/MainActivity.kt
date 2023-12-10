package com.muhammadnaseem.cis_2818_project4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

// ... (imports)

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawarLayout: DrawerLayout
    private val BASE_URL = "dad-jokes.p.rapidapi.com" // Replace with your API base URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawarLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Add this line

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawarLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawarLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        // Initialize Dad Jokes API client
        val client = DadJokesApiClient(BASE_URL, "449b23e5e1msh8198fefffdcbe1ep12727djsn5e1819e30d3e") // Replace with your API key

        // Fetch joke on button click
        val fetchJokeButton = findViewById<Button>(R.id.random_button)
        fetchJokeButton.setOnClickListener {
            lifecycleScope.launch {
                when (val result = client.getDadJokesService().getRandomJoke()) {
                    is ApiResult.Success -> {
                        val joke = result.data
                        val tvSetup = findViewById<TextView>(R.id.jokes_text) // Replace with your TextView IDs
                        val tvPunchline = findViewById<TextView>(R.id.jokes_punchline) // Replace with your TextView IDs
                        tvSetup.text = joke.setup // Replace with the appropriate property
                        tvPunchline.text = joke.punchline // Replace with the appropriate property
                    }
                    is ApiResult.Error -> {
                        // Handle error
                        Toast.makeText(this@MainActivity, "Error fetching joke", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // ... (onNavigationItemSelected and onBackPressed methods)



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.nav_search -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment()).commit()
            R.id.nav_favorites -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FavoritesFragment()).commit()
        }
        drawarLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawarLayout.isDrawerOpen(GravityCompat.START)) {
            drawarLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
