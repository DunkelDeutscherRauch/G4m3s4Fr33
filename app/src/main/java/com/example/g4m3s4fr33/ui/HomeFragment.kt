package com.example.g4m3s4fr33.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnGames.setOnClickListener {
            findNavController().navigate(R.id.gamesListFragment)
        }

        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment)
        }

    }


}