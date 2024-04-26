package com.example.g4m3s4fr33.four_twenty

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.ActivityMainBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WaifuViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WaifuViewModel::class.java]

        handleOnBackPressed()

        viewModel.user.observe(this) {
            try {
                if (it.userImage.isBlank()) {
                    binding.ivToolbar.setImageResource(R.drawable.test_frog)
                } else {
                    binding.ivToolbar.load(it.userImage)
                }
            } catch (e: Exception) {
                Log.e("Ωlul", "$e")

            }
        }

        binding.ivToolbar.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.profileFragment)
        }

    }

    private fun handleOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        }
        onBackPressedDispatcher.addCallback(callback)
    }

}