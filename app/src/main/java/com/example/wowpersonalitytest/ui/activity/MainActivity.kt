package com.example.wowpersonalitytest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.databinding.ActivityMainBinding
import com.example.wowpersonalitytest.ui.fragments.QuestionFragment

private  lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, QuestionFragment())
            .commit()
    }

}