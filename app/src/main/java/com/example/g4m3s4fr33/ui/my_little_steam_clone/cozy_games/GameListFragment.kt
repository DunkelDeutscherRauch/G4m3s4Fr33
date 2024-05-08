package com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games

import android.app.AlertDialog
import android.os.Bundle
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

        val inputCategory = binding.etSearchDialogCategory
        val categories: Array<out String> = resources.getStringArray(R.array.game_categories)

        viewModel.getGameList()

        viewModel.gameList.observe(viewLifecycleOwner) {
            binding.rvGameList.adapter = PlugAndPlayAdapter(it)
        }

        ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1, categories
        ).also { adapter ->
            inputCategory.setAdapter(adapter)
        }

        binding.btnSearchDialogSearch.setOnClickListener {

            if (inputCategory.text.isNotBlank()) {
                viewModel.category = inputCategory.text.toString()
            } else {
                viewModel.category = categories.toList().random()
            }

            viewModel.getGameListByFilter()
        }

        binding.btnOpenSearchDialog.setOnClickListener {
            val dialogBinding = MyCustomSearchDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton("Apply") { _, _ ->
                viewModel.platform = platformer(dialogBinding.rgSearchDialogFilterByPlatform)
                viewModel.sortBy = postal(dialogBinding.rgSearchDialogSortOptions)
            }

            alertDialogBuilder.setNeutralButton("Reset") { _, _ ->
                viewModel.getGameList()
                binding.etSearchDialogCategory.text.clear()
            }

            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            alertDialogBuilder.show()
        }

    }

    // TODO --> replace hardcoded text with string resource

    private fun platformer(radioGroup: RadioGroup): String {

        return when (radioGroup.checkedRadioButtonId) {

            R.id.rbSearchDialogFilterByPlatformAll -> {
                "all"
            }

            R.id.rbSearchDialogFilterByPlatformBrowser -> {
                "browser"
            }

            else -> {
                "pc"
            }
        }

    }

    private fun postal(radioGroup: RadioGroup): String {

        return when (radioGroup.checkedRadioButtonId) {

            R.id.rbSearchDialogSortByRelevance -> {
                "relevance"
            }

            R.id.rbSearchDialogSortByAlphabet -> {
                "alphabetical"
            }

            R.id.rbSearchDialogSortByRelease -> {
                "release-date"
            }

            else -> {
                "popularity"
            }
        }
    }

}