package com.example.g4m3s4fr33.ui.my_little_steam_clone.i_paid_two_win

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // TODO - maybe a popup would fix problem - see bottom

        binding.btnFavDetailSaveTime.setOnClickListener {
            if (binding.etFavDetailPlayTime.text!!.isNotBlank()) {
                viewModel.updateHoursPlayed(
                    binding.etFavDetailPlayTime.text.toString().toInt(),
                    viewModel.gameDetail.value!!.id
                )
                binding.tvFavDetailRank.setText(
                    viewModel.gimmeRank(
                        binding.etFavDetailPlayTime.text.toString().toInt()
                    )
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a Number!",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
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

    /*
    TODO - see TODO above
    val alertDialogBuilder = AlertDialog.Builder(this)
alertDialogBuilder.setTitle("Placeholder")
alertDialogBuilder.setMessage("Placeholder")

val input = EditText(this)
alertDialogBuilder.setView(input)

alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
    val userInput = input.text.toString()
    // TODO - use the input for funny thinks, see TODO above
}

alertDialogBuilder.setNegativeButton("Placeholder") { dialog, which ->
    dialog.cancel()
}

alertDialogBuilder.show()
     */

}