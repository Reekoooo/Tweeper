package com.example.tweeper

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tweeper.internal.LoginStatus
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*


class LoginFragment : Fragment() {

    private val TAG= "LoginFragment"
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.login_button.callback = object : Callback<TwitterSession>(){
            override fun success(result: Result<TwitterSession>?) {
                Log.d(TAG,"Login Success")
                viewModel.loginStateNormal = LoginStatus.SUCCEEDED
                findNavController().navigateUp()
            }

            override fun failure(exception: TwitterException?) {
                Log.d(TAG,"Login Failure")
                viewModel.loginStateNormal = LoginStatus.FAILED
            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel.loginStateNormal = LoginStatus.STARTED
        Log.d(TAG,"Login Started")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG,"onActivityResult called")
        login_button.onActivityResult(requestCode, resultCode, data)

    }

}
