package com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import at.blogc.android.views.ExpandableTextView
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import com.example.g4m3s4fr33.databinding.FragmentGameDetailBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameId = arguments?.getInt("gameId")

        viewModel.getGameDetail(gameId!!)
        viewModel.isGameFav(gameId)
    }

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

        viewModel.isFavGame.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnGameDetailAddToFav.isClickable = false
                binding.btnGameDetailAddToFav.setText(R.string.added_to_favorites)
            } else {
                binding.btnGameDetailAddToFav.isClickable = true
                binding.btnGameDetailAddToFav.setText(R.string.add_to_favorites)
            }

        }

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.tvGameDetailTitle.text = it.title
            binding.tvGameDetailShortDescription.text = it.descriptionShort
            binding.expTVDescription.text =
                getString(R.string.game_detail_description, it.descriptionLong)
            binding.tvGameDetailStatus.text = getString(R.string.game_detail_status, it.status)
            binding.tvGameDetailGenre.text = getString(R.string.game_detail_genre, it.genre)
            binding.tvGameDetailPlatform.text =
                getString(R.string.game_detail_platform, it.platform)
            binding.tvGameDetailPublisher.text =
                getString(R.string.game_detail_publisher, it.publisher)
            binding.tvGameDetailDev.text = getString(R.string.game_detail_dev, it.developer)
            binding.tvGameDetailRelease.text = getString(R.string.game_detail_release, it.release)
            binding.expTVDescriptionTwo.text = getString(
                R.string.game_detail_sys_req,
                it.minimumSystemRequirements?.os ?: "N/A",
                it.minimumSystemRequirements?.processor ?: "N/A",
                it.minimumSystemRequirements?.memory ?: "N/A",
                it.minimumSystemRequirements?.graphics ?: "N/A",
                it.minimumSystemRequirements?.storage ?: "N/A"
            )
            binding.ivGameDetailPic.load(it.thumbnail)

        }

        binding.btnGameDetailAddToFav.setOnClickListener {
            viewModel.addFavGame(viewModel.gameDetail.value!!.id)
        }

        binding.tvSeeMoreOrLess.setOnClickListener {
            superExpandableBrothers(binding.expTVDescription, binding.tvSeeMoreOrLess)
        }

        binding.tvSeeMoreOrLessTwo.setOnClickListener {
            superExpandableBrothers(binding.expTVDescriptionTwo, binding.tvSeeMoreOrLessTwo)
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

    private fun superExpandableBrothers(expandableTextView: ExpandableTextView, toggle: TextView) {
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
}