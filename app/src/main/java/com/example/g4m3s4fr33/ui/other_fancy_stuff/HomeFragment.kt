package com.example.g4m3s4fr33.ui.other_fancy_stuff

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.FragmentHomeBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: WaifuViewModel by activityViewModels()

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

        // TODO implement a random game for users with no clue what to do xD

        /*val category =
            resources.getStringArray(R.array.game_categories).toList().random().lowercase()
        val platform = listOf(getString(R.string.all), getString(R.string.browser), getString(R.string.pc)).random().lowercase()
        val sortBy = listOf(
            getString(R.string.relevance),
            getString(R.string.alphabetical),
            getString(R.string.release_date),
            getString(R.string.popularity)
        ).random().lowercase()*/


        var userName: String

        viewModel.user.observe(viewLifecycleOwner) {
            userName = it.name

            if (userName.isBlank()) {
                binding.tvHomeHeader.text = getString(R.string.greetings_user_default)
            } else {
                binding.tvHomeHeader.text = getString(R.string.greetings_user, userName)
            }

        }

        binding.btnGames.setOnClickListener {
            findNavController().navigate(R.id.gamesListFragment)
        }

        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.favoriteListFragment)
        }

    }


}