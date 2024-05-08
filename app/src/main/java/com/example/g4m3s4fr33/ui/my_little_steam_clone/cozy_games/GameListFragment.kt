package com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.data.adapter.PlugAndPlayAdapter
import com.example.g4m3s4fr33.databinding.FragmentGameListBinding
import com.example.g4m3s4fr33.databinding.MyCustomSearchDialogBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel

class GameListFragment : Fragment() {

    private lateinit var binding: FragmentGameListBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO buttons´s and textView in layout

        val inputCategory = binding.etSearchDialogCategory
        val categories: Array<out String> = resources.getStringArray(R.array.game_categories)

        viewModel.getGameList()

        viewModel.gameList.observe(viewLifecycleOwner) {
            binding.rvGameList.adapter = PlugAndPlayAdapter(it)
        }

        ArrayAdapter(
            requireContext(), R.layout.my_custom_drop_down_menu, categories
        ).also { adapter ->
            inputCategory.setAdapter(adapter)
        }

        binding.btnSearchDialogSearch.setOnClickListener {

            if (inputCategory.text.isNotBlank()) {
                viewModel.category = inputCategory.text.toString().lowercase()
            } else {
                viewModel.category = categories.toList().random()
            }

            viewModel.getGameListByFilter()

        }

        binding.btnOpenSearchDialog.setOnClickListener {
            val dialogBinding = MyCustomSearchDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton(getString(R.string.apply)) { _, _ ->
                viewModel.platform = platformer(dialogBinding.rgSearchDialogFilterByPlatform)
                viewModel.sortBy = postal(dialogBinding.rgSearchDialogSortOptions)
            }

            alertDialogBuilder.setNeutralButton(getString(R.string.reset)) { _, _ ->
                viewModel.getGameList()
                binding.etSearchDialogCategory.text.clear()
            }

            alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

            alertDialogBuilder.show()
        }

    }

    private fun platformer(radioGroup: RadioGroup): String {

        return when (radioGroup.checkedRadioButtonId) {

            R.id.rbSearchDialogFilterByPlatformAll -> {
                getString(R.string.all).lowercase()
            }

            R.id.rbSearchDialogFilterByPlatformBrowser -> {
                getString(R.string.browser).lowercase()
            }

            else -> {
                getString(R.string.pc).lowercase()
            }
        }

    }

    private fun postal(radioGroup: RadioGroup): String {

        return when (radioGroup.checkedRadioButtonId) {

            R.id.rbSearchDialogSortByRelevance -> {
                getString(R.string.relevance).lowercase()
            }

            R.id.rbSearchDialogSortByAlphabet -> {
                getString(R.string.alphabetical).lowercase()
            }

            R.id.rbSearchDialogSortByRelease -> {
                getString(R.string.release_date).lowercase()
            }

            else -> {
                getString(R.string.popularity).lowercase()
            }
        }
    }

}