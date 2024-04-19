package com.arkadii.android002.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arkadii.android002.R
import com.arkadii.android002.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialFragment = HomeFragment.getInstance()
        setFragment(initialFragment)


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    setFragment(HomeFragment.getInstance())
                    true
                }

                R.id.navigation_search -> {
                    setFragment(SearchFragment.getInstance())
                    true
                }

                R.id.navigation_profile -> {
                    setFragment(ProfileFragment.getInstance())
                    true
                }

                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            addToBackStack(null)
            commit()
        }
    }
}