package com.example.viewbindingdemo2kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewbindingdemo2kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "hello"
        binding.textView2.text = "binding"
    }
}
