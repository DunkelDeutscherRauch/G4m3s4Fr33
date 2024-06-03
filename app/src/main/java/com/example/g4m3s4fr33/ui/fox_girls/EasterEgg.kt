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
    private var deathClockTowerClock: CountDownTimer? = null

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

        val gifListBig = listOf(R.drawable.dancing_fox_girl, R.drawable.dancing_anime_girl)
        val gifListSmall = listOf(R.drawable.dancing_anime_girls, R.drawable.dancing_fox_girl_two)
        val colors = listOf(
            R.color.testColor,
            R.color.imBlue,
            R.color.mySilver,
            R.color.white,
            R.color.vwBlackMetallic
        )
        val firstStringList = resources.getStringArray(R.array.game_categories).toList()
        val secondStringList = resources.getStringArray(R.array.random_strings_for_random_list).toList()
        val finalStringList = firstStringList + secondStringList

        if (!viewModel.user.value!!.achievement) {
            val improvedUserName = getString(R.string.giga_chad) + " " + viewModel.user.value!!.name
            viewModel.updateUserAchievement()
            viewModel.updateUserName(improvedUserName)
            viewModel.toastMaker(requireContext(), getString(R.string.toast_achievement_text))
        }

        endlessMucke()
        leDeathClock(colors, delayMillis)
        leDeathClockTower(finalStringList)
        leDeathClockTowerClock(gifListBig, gifListSmall)

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
        val muckeList = listOf(
            R.raw.song_one,
            R.raw.song_two,
            R.raw.song_three,
            R.raw.song_four,
            R.raw.song_five,
            R.raw.song_six
        )
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

    private fun leDeathClockTowerClock(gifList: List<Int>, anotherGIFList: List<Int>) {
        deathClockTowerClock = object : CountDownTimer(250000, 25000) {
            override fun onTick(millisUntilFinished: Long) {
                wetDreams(gifList, anotherGIFList)
            }

            override fun onFinish() {
                leDeathClockTowerClock(gifList, anotherGIFList)
            }
        }.start()
    }

    private fun wetDreams(gifList: List<Int>, anotherGIFList: List<Int>) {
        val randomIndex = 0..1
        val randomGIF = gifList.elementAt(randomIndex.random())
        val randomGIFTwo = anotherGIFList.elementAt(randomIndex.random())

        binding.gifIVFoxGirlAbove.setImageResource(randomGIF)
        binding.gifIVFoxGirlBelow.setImageResource(randomGIF)

        binding.gifIVTopOne.setImageResource(randomGIFTwo)
        binding.gifIVTopTwo.setImageResource(randomGIFTwo)
        binding.gifIVTopThree.setImageResource(randomGIFTwo)
        binding.gifIVTopFour.setImageResource(randomGIFTwo)

        binding.gifIVTopMiddleLeftOne.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleLeftTwo.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleLeftThree.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleLeftFour.setImageResource(randomGIFTwo)

        binding.gifIVTopMiddleRightOne.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleRightTwo.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleRightThree.setImageResource(randomGIFTwo)
        binding.gifIVTopMiddleRightFour.setImageResource(randomGIFTwo)

        binding.gifIVMiddleLeftOne.setImageResource(randomGIFTwo)
        binding.gifIVMiddleLeftTwo.setImageResource(randomGIFTwo)
        binding.gifIVMiddleLeftThree.setImageResource(randomGIFTwo)
        binding.gifIVMiddleLeftFour.setImageResource(randomGIFTwo)

        binding.gifIVMiddleRightOne.setImageResource(randomGIFTwo)
        binding.gifIVMiddleRightTwo.setImageResource(randomGIFTwo)
        binding.gifIVMiddleRightThree.setImageResource(randomGIFTwo)
        binding.gifIVMiddleRightFour.setImageResource(randomGIFTwo)

        binding.gifIVBottomOne.setImageResource(randomGIFTwo)
        binding.gifIVBottomTwo.setImageResource(randomGIFTwo)
        binding.gifIVBottomThree.setImageResource(randomGIFTwo)
        binding.gifIVBottomFour.setImageResource(randomGIFTwo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mucke?.stop()
        mucke?.release()
        deathClock?.cancel()
        deathClockTower?.cancel()
        deathClockTowerClock?.cancel()
        mucke = null
        deathClockTower = null
        deathClockTower = null
        deathClockTowerClock = null
    }

}