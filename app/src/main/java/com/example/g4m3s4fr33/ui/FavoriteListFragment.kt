package com.example.g4m3s4fr33.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.g4m3s4fr33.WaifuViewModel
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

        // TODO this i get
        viewModel.rageQuitList.observe(viewLifecycleOwner) {
            Log.i("Ωlul", "gameIDList in FavListFrag: $it")
            viewModel.getGameDetailList(it)
        }

        // TODO this not :(
        viewModel.gameDetailList.observe(viewLifecycleOwner) {
            Log.i("Ωlul", "gamelist in FavListFrag: $it")
            binding.rvFavList.adapter = RageQuitAdapter(it)
        }

    }

}