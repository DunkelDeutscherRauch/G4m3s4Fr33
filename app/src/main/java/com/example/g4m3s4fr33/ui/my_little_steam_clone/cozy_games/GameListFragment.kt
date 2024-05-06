package com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import com.example.g4m3s4fr33.data.adapter.PlugAndPlayAdapter
import com.example.g4m3s4fr33.databinding.FragmentGameListBinding
import com.example.g4m3s4fr33.databinding.MyCustomAlertDialogBinding
import com.example.g4m3s4fr33.databinding.MyCustomSearchDialogBinding

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

        binding.btnOpenSearchDialog.setOnClickListener {

            val dialogBinding = MyCustomSearchDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton("GO!") { _, _ ->
                val checkBoxAll = dialogBinding.cbSearchDialogPlatformAll
                val checkBoxBrowser = dialogBinding.cbSearchDialogPlatformBrowser
                val checkBoxPC = dialogBinding.cbSearchDialogPlatformPC
                val inputCategory = dialogBinding.etSearchDialogCategory

                // TODO --> GameListByPlatform works :D --> but maybe a radioGroup is better here

                viewModel.getGameListByPlatform(platformFilter(checkBoxAll, checkBoxBrowser, checkBoxPC))




            }

            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            alertDialogBuilder.show()

        }

    }

    private fun platformFilter(checkBoxAll: CheckBox, checkBoxBrowser: CheckBox, checkBoxPC: CheckBox): String {

        var platform = ""

        if (checkBoxAll.isChecked && !(checkBoxBrowser.isChecked||checkBoxPC.isChecked)) {
            platform = "all"
        } else if (checkBoxBrowser.isChecked && !(checkBoxAll.isChecked||checkBoxPC.isChecked)) {
            platform = "browser"
        } else if (checkBoxPC.isChecked && !(checkBoxAll.isChecked||checkBoxBrowser.isChecked)) {
            platform = "pc"
        } else {
            Toast.makeText(requireContext(),"Please select only one platform option",Toast.LENGTH_LONG).show()
        }
        return platform

    }


}