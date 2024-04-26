package com.example.g4m3s4fr33.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import com.example.g4m3s4fr33.data.adapter.RageQuitAdapter
import com.example.g4m3s4fr33.databinding.FragmentFavoriteListBinding


class FavoriteListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteListBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.rageQuitList.observe(viewLifecycleOwner) {
            viewModel.getGameDetailList(it)
        }


        viewModel.gameDetailList.observe(viewLifecycleOwner) {
            binding.rvFavList.adapter = RageQuitAdapter(it)
        }

    }

}