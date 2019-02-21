package com.example.tweeper

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.tweets_fragment.*
import kotlinx.android.synthetic.main.tweets_fragment.view.*


class TweetsFragment : Fragment() {

    private lateinit var viewModel: TweetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tweets_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TweetsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
