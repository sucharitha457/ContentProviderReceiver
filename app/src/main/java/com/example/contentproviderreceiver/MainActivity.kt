package com.example.contentproviderreceiver

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.contentproviderreceiver.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val contentUri = Uri.parse("content://com.example.contentprovidersender.provider/users")
        val userData = StringBuilder()

        Log.d(TAG, "entered content resolver")

        contentResolver.query(contentUri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                Log.d(TAG, "entered content resolver")
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    userData.append("ID: $id, Name: $name\n")
                    Log.d("Main Activity","$name")
                } while (cursor.moveToNext())
            } else {
                userData.append("No data available.")
                Log.d("Main Activity","error ..........")
            }
        }

        if (userData.toString().isNotEmpty()) binding.text.text = userData.toString()
    }
}
