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

        if (viewModel.category.isBlank()) {
            viewModel.getGameList()
        } else {
            viewModel.getGameListByFilter()
        }

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

            setUpPlatformButton(dialogBinding)
            setUpSortButton(dialogBinding)

            alertDialogBuilder.setPositiveButton(getString(R.string.apply)) { _, _ ->
                viewModel.platform = platformer(dialogBinding.rgSearchDialogFilterByPlatform)
                viewModel.sortBy = postal(dialogBinding.rgSearchDialogSortOptions)
            }

            alertDialogBuilder.setNeutralButton(getString(R.string.reset)) { _, _ ->
                viewModel.getGameList()
                binding.etSearchDialogCategory.text.clear()
                viewModel.platform = getString(R.string.all).lowercase()
                viewModel.category = ""
                viewModel.sortBy = getString(R.string.relevance).lowercase()
            }

            alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

            alertDialogBuilder.show()
        }

    }

    private fun setUpPlatformButton(binding: MyCustomSearchDialogBinding) {
        when (viewModel.platform) {

            getString(R.string.browser).lowercase() -> {
                binding.rbSearchDialogFilterByPlatformBrowser.isChecked = true
            }

            getString(R.string.pc).lowercase() -> {
                binding.rbSearchDialogFilterByPlatformPC.isChecked = true
            }

            else -> {
                binding.rbSearchDialogFilterByPlatformAll.isChecked = true
            }
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

            R.id.rbSearchDialogFilterByPlatformPC -> {
                getString(R.string.pc).lowercase()
            }

            else -> {
                getString(R.string.all).lowercase()
            }
        }

    }

    private fun setUpSortButton(binding: MyCustomSearchDialogBinding) {
        when(viewModel.sortBy) {

            getString(R.string.alphabetical).lowercase() -> {
                binding.rbSearchDialogSortByAlphabet.isChecked = true
            }

            getString(R.string.release_date).lowercase() -> {
                binding.rbSearchDialogSortByRelease.isChecked = true
            }

            getString(R.string.popularity).lowercase() -> {
                binding.rbSearchDialogSortByPopularity.isChecked = true
            }

            else -> {
                binding.rbSearchDialogSortByRelevance.isChecked = true
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

            R.id.rbSearchDialogSortByPopularity -> {
                getString(R.string.popularity).lowercase()
            }

            else -> {
                getString(R.string.relevance).lowercase()
            }
        }

    }

}