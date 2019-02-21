package com.example.tweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.ui.AppBarConfiguration
import com.example.tweeper.internal.LoginStatus
import com.twitter.sdk.android.core.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private var loginState = LoginStatus.SUCCEEDED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
            .debug(true)
            .build()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        Twitter.initialize(config)
        val session =  TwitterCore.getInstance().sessionManager.activeSession
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val set = AppBarConfiguration.Builder(navController.graph).build().topLevelDestinations
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(destination.id !in set)
                setHomeAsUpIndicator(if (destination.id in set) {
                    0
                } else R.drawable.rtl_up)
            }
            if(destination.id == R.id.loginFragment){
                toolbar.visibility = View.GONE
                bottom_nav.visibility = View.GONE

            }
            if(destination.id == R.id.tweetsFragment){
                Log.d("MainActivity","Navigating to Tweets loginState is ${loginState}")

                if(viewModel.loginStateNormal == LoginStatus.STARTED){
                   // navController.popBackStack(R.id.home,true)
                    Log.d(TAG,"LoginStatus is Started")
                    finish()
                }

            }

        }
        Log.d(TAG, "hi there ,${session?.userId}")
        TwitterCore.getInstance().sessionManager.clearActiveSession()
        Log.d(TAG, "hi there ,${session?.userId}")
        if (session == null){
            navController.navigate(R.id.loginFragment, null,
                 NavOptions.Builder().setPopUpTo(R.id.home, true).build())
           // navController.navigate(R.id.loginFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}
