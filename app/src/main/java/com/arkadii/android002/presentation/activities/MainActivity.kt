package com.arkadii.android002.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arkadii.android002.R
import com.arkadii.android002.databinding.ActivityMainBinding
import com.arkadii.android002.presentation.fragments.HomeFragment
import com.arkadii.android002.presentation.fragments.ProfileFragment
import com.arkadii.android002.presentation.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialFragment = HomeFragment.getInstance()
        setFragment(initialFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
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

        savedInstanceState?.getInt(SELECTED_ITEM_ID_KEY)?.let { selectedItemId ->
            binding.bottomNavigation.selectedItemId = selectedItemId
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_ITEM_ID_KEY, binding.bottomNavigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val SELECTED_ITEM_ID_KEY = "selected_item_id"
    }
}