package com.example.restapiidemo.home.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.restapiidemo.R
import com.example.restapiidemo.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restapiidemo.home.data.Bucket
import com.example.restapiidemo.home.data.Ticket
import okhttp3.internal.Internal.instance

class MainActivity : AppCompatActivity() {

//    private lateinit var vm:HomeViewModel
    private lateinit var tabLayout: TabLayout
    var tabTitle = arrayOf("News", "Discounts", "Flight", "Wish")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        vm = ViewModelProvider(this)[HomeViewModel::class.java]
//        vm.fetchAllPosts()
        tabLayout = findViewById(R.id.tabLayout)
        var viewPager: ViewPager2 = findViewById(R.id.viewpager)
        instance = this
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }

    companion object{
        var instance: MainActivity? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sManagement = SessionManagement(this)
        val userId: Int = sManagement.getSession()
        if (userId == -1){
            menuInflater.inflate(R.menu.guest_menu, menu)
        }
        else{
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            menuInflater.inflate(R.menu.user_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.login -> toLogin(this)
            R.id.profile -> toProfile(this)
            android.R.id.home -> logout(this)
        }
        return true
    }

    fun toLogin(context:Context){
        val intent = Intent(context, LoginActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }

    fun logout(context: Context) {
        Bucket.myClassList.clear()
        val sManagement = SessionManagement(context)
        sManagement.removeSession(context)
    }

    fun toProfile(context:Context){
        val intent = Intent(context, ProfileActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0-> Bet1Fragment()
                1-> Bet2Fragment()
                2-> Bet3Fragment()
                3-> Bet4Fragment()
                else -> Bet1Fragment()
            }
        }
    }

}
