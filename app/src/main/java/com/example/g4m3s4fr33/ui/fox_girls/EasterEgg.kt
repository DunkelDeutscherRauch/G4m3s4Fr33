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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.databinding.TestLayoutBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel

class EasterEgg : Fragment() {

    private lateinit var binding: TestLayoutBinding
    private val viewModel: WaifuViewModel by activityViewModels()
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
        val firstStringList = resources.getStringArray(R.array.game_categories).toList()
        val secondStringList = resources.getStringArray(R.array.randomStringsForRandomList).toList()
        val finalStringList = firstStringList + secondStringList

        if (!viewModel.user.value!!.achievement) {
            val improvedUserName = getString(R.string.giga_chad) + " " + viewModel.user.value!!.name
            viewModel.updateUserAchievement()
            viewModel.updateUserName(improvedUserName)
            Toast.makeText(
                requireContext(),
                R.string.toast_achievement_text,
                Toast.LENGTH_LONG
            ).show()
        }

        endlessMucke()
        leDeathClock(colors, delayMillis)
        leDeathClockTower(finalStringList)

        handler.postDelayed(object : Runnable {
            override fun run() {
                val randomColor = colors.random()
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        randomColor
                    )
                )
                handler.postDelayed(this, delayMillis)
            }
        }, delayMillis)

        binding.tvCommercial.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://suno.com/playlist/bc9ae8b4-a9b2-43ee-975e-45adc87c684a")
                )
            )
        }

        binding.tvGitHub.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/DunkelDeutscherRauch?tab=repositories")
                )
            )
        }
    }

    private fun endlessMucke() {
        val muckeList = listOf(R.raw.song_one, R.raw.song_two, R.raw.song_three, R.raw.song_four)
        mucke = MediaPlayer.create(requireContext(), muckeList.random())
        mucke?.start()

        mucke?.setOnCompletionListener {
            it?.release()
            mucke = null
            endlessMucke()
        }
    }

    private fun leDeathClock(colorList: List<Int>, delay: Long) {
        deathClock = object : CountDownTimer(30000, delay) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvCommercial.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorList.random()
                    )
                )
                binding.tvRandomList.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorList.random()
                    )
                )
                binding.tvGitHub.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorList.random()
                    )
                )
            }

            override fun onFinish() {
                leDeathClock(colorList, delay)
            }
        }.start()
    }

    private fun leDeathClockTower(stringList: List<String>) {
        deathClockTower = object : CountDownTimer(25000, 2500) {
            override fun onTick(millisUntilFinished: Long) {
                val text = stringList.random().uppercase()
                binding.tvRandomList.text = text
            }

            override fun onFinish() {
                leDeathClockTower(stringList)
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