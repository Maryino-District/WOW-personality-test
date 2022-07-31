package com.example.wowpersonalitytest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.databinding.ActivityMainBinding
import com.example.wowpersonalitytest.ui.fragments.QuestionFragment
import com.example.wowpersonalitytest.ui.fragments.ResultsFragment
import com.example.wowpersonalitytest.ui.interfaces.FragmentSwitchListener
import com.example.wowpersonalitytest.ui.viewmodel.QuestionViewModel

private const val LOG_TAG_MAIN = "MainActivity"

private  lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentSwitchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG_MAIN, "onCreateActivity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, QuestionFragment.newInstance())
                .commit()
            }
    }

    override fun switch(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG_MAIN, "onStartActivity")

    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG_MAIN, "onResumeActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG_MAIN, "onPauseActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG_MAIN, "onStopActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG_MAIN, "onDestroyActivity")
    }

    override fun isFinishing(): Boolean {
        return super.isFinishing()
        Log.d(LogPrinter, "Is Finishing")
    }

}
