package com.example.dicegameapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var name: EditText
    lateinit var number: EditText
    lateinit var btn_Play: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        name = findViewById(R.id.Main_ET_Name)
        number = findViewById(R.id.Main_ET_Number)
        btn_Play = findViewById(R.id.Main_Btn_Play)

        btn_Play.setOnClickListener {

            if (name.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show()
            } else
                if (number.text.toString().isEmpty()) {
                    Toast.makeText(this, "Enter Your Number", Toast.LENGTH_LONG).show()
                } else
                    if (number.text.toString().toInt() > 6 || number.text.toString().toInt() < 1) {
                        Toast.makeText(this, "Your number must be from 1 to 6", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra("name", name.text.toString())
                        intent.putExtra("number", number.text.toString().toInt())
                        startActivity(intent)
                    }

        }

    }
}