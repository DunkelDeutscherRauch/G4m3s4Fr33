package com.example.g4m3s4fr33.ui.my_little_steam_clone.i_paid_two_win

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.FragmentFavoriteDetailBinding
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
                    viewModel.gimmeRank(it.first().hoursPlayed.toString().toInt())
                } else {
                    viewModel.gimmeRank(0)
                }
            )
        }

        viewModel.gameDetail.observe(viewLifecycleOwner) {
            binding.tvFavDetailTitle.text = it.title
            binding.ivFavDetail.load(it.thumbnail)

        }

        /*
        TODO --> replace popup with customized popup!
             --> maybe replace etFavDetailPlaytime with TextView or
             --> set android:focusableInTouchMode="false" in XML on etFavDetailPlaytime + remove Button
         */

        binding.btnFavDetailSaveTime.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("TODO - Just for Testing :D ")
            alertDialogBuilder.setMessage("Please enter your playtime")

            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_NUMBER
            alertDialogBuilder.setView(input)

            alertDialogBuilder.setPositiveButton("Save") { _, _ ->
                val userInput = input.text.toString()

                if (input.text.isNotBlank()) {
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
                    Toast.makeText(
                        requireContext(),
                        "You failed badly! Please try again!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            alertDialogBuilder.show()
        }

        binding.btnFavGameRemove.setOnClickListener {
            viewModel.deleteFavGame(gameId)
            findNavController().navigateUp()
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