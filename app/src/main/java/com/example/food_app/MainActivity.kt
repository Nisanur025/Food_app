package com.example.food_app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.menu_drawer)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, HomeFragment())
                .commit()
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.white, theme)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.guest_planner -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, GuestFragment())
                        .commit()
                }
                R.id.recipes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, AllRecipesFragment())
                        .commit()
                }
                R.id.game_forwho ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, WhoseTurnFragment())
                    .commit()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}