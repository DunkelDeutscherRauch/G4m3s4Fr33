package com.example.g4m3s4fr33.ui.fox_girls

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.TestLayoutBinding

class EasterEgg : Fragment() {

    private lateinit var binding: TestLayoutBinding
    private var mucke: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endlessMucke()

        binding.textView2.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.mySilver
            )
        )

        val handler = Handler()
        val delayMillis = 10L
        val colors = listOf(
            R.color.testColor,
            R.color.imBlue,
            R.color.mySilver,
            R.color.white,
            R.color.vwBlackMetallic
        )
        handler.postDelayed(object : Runnable {
            override fun run() {
                val randomColor = colors.random()
                binding.textView2.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        randomColor
                    )
                )
                handler.postDelayed(this, delayMillis)
            }
        }, delayMillis)

    }

    private fun endlessMucke() {
        val muckeList = listOf(R.raw.song_one, R.raw.song_two, R.raw.song_three)
        mucke = MediaPlayer.create(requireContext(), muckeList.random())
        mucke?.start()

        mucke?.setOnCompletionListener {
            mucke?.release()
            mucke = null
            endlessMucke()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mucke?.stop()
        mucke?.release()
        mucke = null

    }

}