package com.example.g4m3s4fr33.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.FragmentFavoriteDetailBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel

class FavoriteDetailFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteDetailBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameId = arguments?.getInt("gameId")

        viewModel.getGameDetail(gameId!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.tvFavDetailTitle.text = it.title
            binding.ivFavDetail.load(it.thumbnail)
        }

        binding.btnFavGameRemove.setOnClickListener {
            viewModel.deleteFavGame(viewModel.gameDetail.value!!.id)
            findNavController().navigateUp()
        }

        binding.btnFavDetailToDetails.setOnClickListener {
            findNavController().navigate(
                FavoriteDetailFragmentDirections.actionFavoriteDetailFragmentToGameDetailFragment(
                    viewModel.gameDetail.value!!.id
                )
            )
        }
    }

}