package my.first.github.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.first.github.R
import my.first.github.databinding.ActivityMainBinding
import my.first.github.utils.Constants
import my.first.github.utils.PreferencesManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferencesManager: PreferencesManager = PreferencesManager(applicationContext)
        preferencesManager.putString(Constants.KEY_AUTH_TOKEN, "arega")
        binding.answer.setText(preferencesManager.getString(Constants.KEY_AUTH_TOKEN))

    }
}