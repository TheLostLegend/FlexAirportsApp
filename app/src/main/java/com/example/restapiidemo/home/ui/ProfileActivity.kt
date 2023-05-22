package com.example.restapiidemo.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.home.data.User
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_login.*

class ProfileActivity: AppCompatActivity() {
    var tabTitle = arrayOf("User Data", "Tickets Cart")
    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val sManagement = SessionManagement(this)
        val userId: Int = sManagement.getSession()
        supportActionBar?.title= "User: $userId"
        tabLayout = findViewById(R.id.tabLayout1)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager1)
        viewPager.adapter = ViewPagerAdapterP(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    class ViewPagerAdapterP(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0-> ProfileFragment1()
                1-> ProfileFragment2()
                else -> ProfileFragment1()
            }
        }
    }

}