package com.example.g4m3s4fr33.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.WaifuViewModel
import com.example.g4m3s4fr33.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = arguments?.getInt("gameId")

        viewModel.getGameDetail(gameId!!)

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.tvGameDetailTitle.text = it.title
            binding.tvGameDetailShortDescription.text = it.descriptionShort
            binding.expTVDescription.text = getString(R.string.game_detail_description, it.descriptionLong)
            binding.tvGameDetailStatus.text = getString(R.string.game_detail_status, it.status)
            binding.tvGameDetailGenre.text = getString(R.string.game_detail_genre, it.genre)
            binding.tvGameDetailPlatform.text = getString(R.string.game_detail_platform, it.platform)
            binding.tvGameDetailPublisher.text = getString(R.string.game_detail_publisher, it.publisher)
            binding.tvGameDetailDev.text = getString(R.string.game_detail_dev, it.developer)
            binding.tvGameDetailRelease.text = getString(R.string.game_detail_release, it.release)
            binding.ivGameDetailPic.load(it.thumbnail)

        }

        binding.tvSeeMoreOrLess.setOnClickListener {

            val expandableTextView = binding.expTVDescription
            val toggle = binding.tvSeeMoreOrLess

            expandableTextView.setAnimationDuration(750L)
            expandableTextView.setInterpolator(OvershootInterpolator())

            if (expandableTextView.isExpanded) {
                expandableTextView.collapse()
                toggle.setText(R.string.show_more)

            } else {
                expandableTextView.expand()
                toggle.setText(R.string.show_less)
            }
        }

        binding.btnGameDetailHomePage.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(viewModel.gameDetail.value!!.url)
                )
            )
        }

    }
}