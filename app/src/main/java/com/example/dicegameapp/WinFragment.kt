package com.example.dicegameapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView


class WinFragment : Fragment() {

lateinit var VideoCongratulation :VideoView
lateinit var tv_tries :TextView
lateinit var btn_PlayAgain :Button
lateinit var btn_LogOut :Button
var luckyNumber=1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_tries=view.findViewById(R.id.Win_TV_Tries)
        btn_PlayAgain=view.findViewById(R.id.Win_Btn_PlayAgain)
        btn_LogOut=view.findViewById(R.id.Win_Btn_LogOut)
        VideoCongratulation=view.findViewById(R.id.Win_VV_VideoCongratulations)
        luckyNumber= arguments?.getInt("LuckyNumber_Key") ?: 1
        tv_tries.text ="You Win After ${arguments?.getInt("tries_Key").toString()} Tries"

        btn_LogOut.setOnClickListener{
            var intent=Intent(activity,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        btn_PlayAgain.setOnClickListener{
            var playFragment =PlayFragment()
            var bundle=Bundle()
            bundle.putInt("luckyNumber",luckyNumber)
            playFragment.arguments=bundle

            parentFragmentManager.beginTransaction()
               .replace(R.id.Profile_FL_ClickImage,playFragment).commit()
        }
        VideoCongratulation.setVideoURI(Uri.parse(
            "android.resource://${activity?.packageName}/${R.raw.congratulations}"))
        VideoCongratulation.start()
        VideoCongratulation.setOnCompletionListener { VideoCongratulation.start() }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_win, container, false)
    }

}