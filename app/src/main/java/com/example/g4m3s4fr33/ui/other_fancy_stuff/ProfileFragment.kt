package com.example.g4m3s4fr33.ui.other_fancy_stuff

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import com.example.g4m3s4fr33.databinding.FragmentProfileBinding
import com.example.g4m3s4fr33.databinding.MyCustomAlertDialogProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: WaifuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) {
            if (it.achievement) {
                binding.cvProfileUserAchieved.visibility = View.VISIBLE
            }

            binding.etProfileName.setText(it.name)
            if (it.userImage.isBlank()) {
                binding.ivProfilePic.setImageResource(R.drawable.test_frog)
            } else {
                binding.ivProfilePic.load(it.userImage)
            }
        }


        val changeImage =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) {
                    it.data?.data?.let { it1 ->
                        requireContext().contentResolver.takePersistableUriPermission(
                            it1, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        binding.ivProfilePic.load(it1)
                        viewModel.updateUserImage(it1.toString())
                    }

                }
            }

        binding.btnEditUserPic.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            changeImage.launch(intent)
        }

        binding.etProfileName.setOnClickListener {

            val dialogBinding = MyCustomAlertDialogProfileBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertDialogBuilder.setView(dialogBinding.root)

            alertDialogBuilder.setPositiveButton("Save") { _, _ ->
                val userInput = dialogBinding.etAlertDialogProfile.text.toString()

                if (userInput.isNotBlank()) {
                    viewModel.updateUserName(userInput)
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
    }

}
