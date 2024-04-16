package com.example.g4m3s4fr33

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import coil.load
import com.example.g4m3s4fr33.databinding.ActivityMainBinding


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
            if (it.userImage.isBlank()) {
                binding.ivToolbar.load(R.drawable.test_frog)
            } else {
                binding.ivToolbar.load(it.userImage)
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