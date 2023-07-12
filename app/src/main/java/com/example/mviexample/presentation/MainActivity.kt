package com.example.mviexample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.codemonkeylabs.fpslibrary.TinyDancer
import com.example.mviexample.R
import com.example.mviexample.presentation.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TinyDancer.create().show(applicationContext)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, ListFragment.getInstance())
        }
    }
}