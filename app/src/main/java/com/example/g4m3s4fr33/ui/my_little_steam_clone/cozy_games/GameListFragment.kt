package com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.data.adapter.PlugAndPlayAdapter
import com.example.g4m3s4fr33.databinding.FragmentGameListBinding
import com.example.g4m3s4fr33.four_twenty.MainActivity
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

        viewModel.getGameList()

        viewModel.gameList.observe(viewLifecycleOwner) {
            binding.rvGameList.adapter = PlugAndPlayAdapter(it)
        }

        val inputCategory = binding.etSearchDialogCategory
        val categories: Array<out String> = resources.getStringArray(R.array.game_categories)
        Log.i("Ωlul", "My Search Dialog in Fragment: $categories")

        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories
        ).also { adapter ->
            inputCategory.setAdapter(adapter) }

        // TODO remove and replace directly here cause AutoComplete does not work in alertDialog

        /*binding.btnOpenSearchDialog.setOnClickListener {

            val dialogBinding = MyCustomSearchDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton("GO!") { _, _ ->
                val checkBoxAll = dialogBinding.cbSearchDialogPlatformAll
                val checkBoxBrowser = dialogBinding.cbSearchDialogPlatformBrowser
                val checkBoxPC = dialogBinding.cbSearchDialogPlatformPC

                    // TODO --> GameListByPlatform works :D --> but maybe a radioGroup is better here
                    // TODO --> getGameListByCategory should work

                    viewModel.getGameListByPlatform(
                        platformFilter(
                            checkBoxAll,
                            checkBoxBrowser,
                            checkBoxPC
                        )
                    )
                    viewModel.getGameListByCategory(getCategory(inputCategory))


                }

                alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                alertDialogBuilder.show()

            }*/

        }

        private fun platformFilter(
            checkBoxAll: CheckBox,
            checkBoxBrowser: CheckBox,
            checkBoxPC: CheckBox
        ): String {

            var platform = ""

            if (checkBoxAll.isChecked && !(checkBoxBrowser.isChecked || checkBoxPC.isChecked)) {
                platform = "all"
            } else if (checkBoxBrowser.isChecked && !(checkBoxAll.isChecked || checkBoxPC.isChecked)) {
                platform = "browser"
            } else if (checkBoxPC.isChecked && !(checkBoxAll.isChecked || checkBoxBrowser.isChecked)) {
                platform = "pc"
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please select only one platform option",
                    Toast.LENGTH_LONG
                ).show()
            }
            return platform

        }


    private fun getCategory(editText: AutoCompleteTextView): String {
        var category = ""

            if (editText.text.isNotBlank()) {
                category = editText.text.toString()
            }

        return category
    }


}