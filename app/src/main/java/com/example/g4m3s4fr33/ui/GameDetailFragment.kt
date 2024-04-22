package com.example.g4m3s4fr33.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.g4m3s4fr33.WaifuViewModel
import com.example.g4m3s4fr33.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = arguments?.getInt("gameId")

        viewModel.getGameDetail(gameId!!)

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.imageView.load(viewModel.gameDetail.value!!.thumbnail)
            binding.textView4.text = viewModel.gameDetail.value!!.descriptionLong
        }


        binding.button.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(viewModel.gameDetail.value!!.url)
                )
            )
        }

    }

    /*
        opens a link on buttonclick

        binding.button.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$")
                )
            )
        } */

}