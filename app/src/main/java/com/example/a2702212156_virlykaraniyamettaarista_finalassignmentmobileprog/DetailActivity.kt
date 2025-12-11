package com.example.a2702212156_virlykaraniyamettaarista_finalassignmentmobileprog

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity_detail.xml
        setContentView(R.layout.activity_detail)

        // Name and Nim display
        findViewById<TextView>(R.id.tv_my_name).text = "Name: Virly Karaniyametta Arista"
        findViewById<TextView>(R.id.tv_my_nim).text = "NIM: 2702212156"

        // Retrieve the Parcelable User object from the Intent
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        // Display user data
        user?.let {
            findViewById<TextView>(R.id.tv_detail_name).text = "Name: ${it.name}"
            findViewById<TextView>(R.id.tv_detail_id).text = "ID: ${it.id}"
            findViewById<TextView>(R.id.tv_detail_username).text = "Username: ${it.username}"
            findViewById<TextView>(R.id.tv_detail_email).text = "Email: ${it.email}"
            findViewById<TextView>(R.id.tv_detail_phone).text = "Phone: ${it.phone}"
            findViewById<TextView>(R.id.tv_detail_website).text = "Website: ${it.website}"

            findViewById<TextView>(R.id.tv_detail_address_street).text = "Street: ${it.address.street}"
            findViewById<TextView>(R.id.tv_detail_address_suite).text = "Suite: ${it.address.suite}"
            findViewById<TextView>(R.id.tv_detail_address_city).text = "City: ${it.address.city}"
            findViewById<TextView>(R.id.tv_detail_address_zipcode).text = "Zipcode: ${it.address.zipcode}"

            findViewById<TextView>(R.id.tv_detail_address_geo).text =
                "Geo: (${it.address.geo.lat}, ${it.address.geo.lng})"

            findViewById<TextView>(R.id.tv_detail_company_name).text = "Name: ${it.company.name}"
            findViewById<TextView>(R.id.tv_detail_company_catch_phrase).text =
                "Catch Phrase: \"${it.company.catchPhrase}\""
            findViewById<TextView>(R.id.tv_detail_company_bs).text = "Business Strategy: ${it.company.bs}"
        }

        val returnButton: Button = findViewById(R.id.btn_return)
        returnButton.setOnClickListener {
            finish() // Destroys this activity and returns to MainActivity
        }
    }
}