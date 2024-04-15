package com.example.dicegameapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    private var image_uri: Uri? = null
    lateinit var textName: TextView
    lateinit var textNumber: TextView
    lateinit var imageProfile: ImageView
    val playFragment = PlayFragment()
    val bundle=Bundle()
//    lateinit var profile_frameLayout: FrameLayout
    val CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textName = findViewById(R.id.Profile_TV_Name)
        textNumber = findViewById(R.id.Profile_TV_Number)
        imageProfile = findViewById(R.id.Profile_IV_ImageProfile)
//        profile_frameLayout = findViewById(R.id.Profile_FL_ClickImage)

        val name = intent.getStringExtra("name")
        val number = intent.getIntExtra("number", 1)
        textName.text ="${name.toString()}!"
        textNumber.text = "Your Lucky Number Is $number"

        bundle.putInt("luckyNumber",number)
        playFragment.arguments=bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.Profile_FL_ClickImage, playFragment).commit()

        //Change image using camera
        imageProfile.setOnClickListener {

            checkPermission(Manifest.permission.CAMERA, CAMERA_REQUEST_CODE)
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        try {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
            image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            imageProfile.setImageURI(image_uri)
            Toast.makeText(this, "Profile Picture Update Successfully", Toast.LENGTH_SHORT)
                .show()

        }

    }


}