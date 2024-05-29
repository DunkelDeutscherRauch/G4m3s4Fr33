package com.example.g4m3s4fr33.ui.fox_girls

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.TestLayoutBinding

class EasterEgg : Fragment() {

    private lateinit var binding: TestLayoutBinding
    private var mucke: MediaPlayer? = null
    private var deathClock: CountDownTimer? = null
    private var deathClockTower: CountDownTimer? = null

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

        val handler = Handler(Looper.getMainLooper())
        val delayMillis = 30L
        val colors = listOf(
            R.color.testColor,
            R.color.imBlue,
            R.color.mySilver,
            R.color.white,
            R.color.vwBlackMetallic
        )

        endlessMucke()
        leDeathClock(colors)
        leDeathClockTower()

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

        binding.textView5.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://suno.com/playlist/bc9ae8b4-a9b2-43ee-975e-45adc87c684a")
                )
            )
        }
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

    private fun leDeathClock(colorList: List<Int>) {
        deathClock = object : CountDownTimer(30000, 30) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textView3.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorList.random()
                    )
                )
                binding.textView4.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorList.random()
                    )
                )
            }

            override fun onFinish() {
                leDeathClock(colorList)
            }
        }.start()
    }

    private fun leDeathClockTower() {
        deathClockTower = object : CountDownTimer(25000, 2500) {
            override fun onTick(millisUntilFinished: Long) {
                val text = resources.getStringArray(R.array.game_categories).toList().random()
                binding.textView3.text = text
                val otherText = listOf("PARTY HARD", "DESIGN NIGHTMARE", "LULZ", "PWND", "KEKW").random()
                binding.textView4.text = otherText
            }

            override fun onFinish() {
                leDeathClockTower()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mucke?.stop()
        mucke?.release()
        mucke = null
        deathClock?.cancel()
        deathClock = null
        deathClockTower?.cancel()
        deathClockTower = null
    }

}