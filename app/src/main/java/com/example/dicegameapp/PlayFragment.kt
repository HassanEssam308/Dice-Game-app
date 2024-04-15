package com.example.dicegameapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class PlayFragment : Fragment() {

    var receiedLuckyNumber: Int = 1
    lateinit var click_Image: ImageView
    lateinit var clickText: TextView
     var winFragment= WinFragment()
    var bundle=Bundle()
     var tries: Int =0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiedLuckyNumber = arguments?.getInt("luckyNumber") ?: 1
        click_Image = view.findViewById(R.id.PlayFrag_IV_DiceImage)
        clickText = view.findViewById(R.id.PlayFrag_TV_ClickText)

        click_Image.setOnClickListener {

            val randomNumber = (1..6).random()
            tries++

            if (randomNumber != receiedLuckyNumber) {
                clickText.text = randomNumber.toString()
            } else {
                clickText.text = "$randomNumber\n\nYou Got It"
                click_Image.isEnabled = false
                bundle.putInt("tries_Key",tries)
                bundle.putInt("LuckyNumber_Key",receiedLuckyNumber)
                winFragment.arguments=bundle
                Handler(Looper.getMainLooper()).postDelayed({
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.Profile_FL_ClickImage, winFragment).commit()
                }, 1500)


            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }


}