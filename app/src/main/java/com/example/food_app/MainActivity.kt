package com.example.food_app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View?>(R.id.menu_drawer),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, HomeFragment())
                .commit()
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.menu_drawer)
        val toolbar = findViewById<MaterialToolbar?>(R.id.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState() // Hamburger ikonunu ve animasyonu bağla

        toggle.getDrawerArrowDrawable()
            .setColor(getResources().getColor(android.R.color.white, getTheme()))
    }
}