package com.example.g4m3s4fr33.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.WaifuViewModel
import com.example.g4m3s4fr33.databinding.FragmentProfileBinding

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
            binding.etProfileName.setText(it.name)
            if (it.userImage.isNotBlank()) {
                binding.ivProfilePic.load(it.userImage)
            } else {
                binding.ivProfilePic.setImageResource(R.drawable.test_frog)
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

        binding.btnSaveUser.setOnClickListener {
            if (binding.etProfileName.text!!.isNotBlank()) {
                viewModel.updateUserName(binding.etProfileName.text.toString())
            }
        }

    }
}
