package com.example.a2702212156_virlykaraniyamettaarista_finalassignmentmobileprog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.a2702212156_virlykaraniyamettaarista_finalassignmentmobileprog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val users = fetchUsers()

            val userClickListener: OnUserClickListener = { user ->
                // Intent to navigate to DetailActivity
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    // Attach the entire User object to the Intent
                    putExtra(DetailActivity.EXTRA_USER, user)
                }
                startActivity(intent)
            }

            // Pass the list of users and the click listener to the adapter
            binding.recyclerView.adapter = UserAdapter(users, userClickListener)
        }
    }

    private suspend fun fetchUsers(): List<User> = withContext(Dispatchers.IO) {
        // Set the URL to the users endpoint
        val url = URL("https://jsonplaceholder.typicode.com/users")
        val connection = url.openConnection() as HttpURLConnection
        val users = mutableListOf<User>()

        try {
            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(response)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                val geoJson = obj.getJSONObject("address").getJSONObject("geo")
                val geo = Geo(
                    lat = geoJson.getString("lat"),
                    lng = geoJson.getString("lng")
                )

                val addressJson = obj.getJSONObject("address")
                val address = Address(
                    street = addressJson.getString("street"),
                    suite = addressJson.getString("suite"),
                    city = addressJson.getString("city"),
                    zipcode = addressJson.getString("zipcode"),
                    geo = geo
                )

                val companyJson = obj.getJSONObject("company")
                val company = Company(
                    name = companyJson.getString("name"),
                    catchPhrase = companyJson.getString("catchPhrase"),
                    bs = companyJson.getString("bs")
                )

                users.add(
                    User(
                        id = obj.getInt("id"),
                        name = obj.getString("name"),
                        username = obj.getString("username"),
                        email = obj.getString("email"),
                        phone = obj.getString("phone"),
                        website = obj.getString("website"),
                        address = address, // Pass the nested Address object
                        company = company  // Pass the nested Company object
                    )
                )
            }

        } finally {
            connection.disconnect()
        }

        return@withContext users
    }
}