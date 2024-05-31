package com.example.g4m3s4fr33.ui.my_little_steam_clone.i_paid_two_win

import android.app.AlertDialog
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
import com.example.g4m3s4fr33.databinding.MyCustomAlertDialogBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import kotlin.properties.Delegates

class FavoriteDetailFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteDetailBinding
    private var gameId by Delegates.notNull<Int>()
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameId = arguments?.getInt("gameId")!!

        viewModel.getGameDetail(gameId)
        viewModel.getFavGame(gameId)

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

        viewModel.favGame.observe(viewLifecycleOwner) {
            binding.tvGameFavDateAdded.text =
                getString(R.string.date_game_added_to_favorites, it.first().dateGameAdded)

            if (it.first().hoursPlayed == 0) {
                binding.etFavDetailPlayTime.setText("")
            } else {
                binding.etFavDetailPlayTime.setText(
                    getString(
                        R.string.played_hours,
                        it.first().hoursPlayed.toString()
                    )
                )
            }

            binding.tvFavDetailRank.setText(
                if (binding.etFavDetailPlayTime.text!!.isNotBlank()) {
                    viewModel.gimmeRank(it.first().hoursPlayed)
                } else {
                    viewModel.gimmeRank(0)
                }
            )
        }

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.tvFavDetailTitle.text = it.title
            binding.ivFavDetail.load(it.thumbnail)

        }

        binding.etFavDetailPlayTime.setOnClickListener {

            val dialogBinding = MyCustomAlertDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(),R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton(getString(R.string.save)) { _, _ ->
                val userInput = dialogBinding.etAlertDialog.text.toString()

                if (userInput.isNotBlank()) {
                    viewModel.updateHoursPlayed(
                        userInput.toInt(),
                        gameId
                    )

                    binding.tvFavDetailRank.setText(
                        viewModel.gimmeRank(
                            userInput.toInt()
                        )
                    )

                    binding.etFavDetailPlayTime.setText(
                        getString(
                            R.string.played_hours,
                            userInput
                        )
                    )

                } else {
                   viewModel.toastMaker(requireContext(), getString(R.string.sum_ting_wong))
                }
            }

            alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            alertDialogBuilder.show()
        }

        binding.btnFavDetailToDetails.setOnClickListener {
            findNavController().navigate(
                FavoriteDetailFragmentDirections.actionFavoriteDetailFragmentToGameDetailFragment(
                    gameId
                )
            )
        }
    }
}