package com.adds.raggingis4loosers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.adds.antirape.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private lateinit var speechRecognizer: SpeechRecognizer

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomePage())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomePage())
                R.id.contact -> replaceFragment(ContactPage())
                R.id.profile -> replaceFragment(ProfilePage())
                R.id.settings -> replaceFragment(SettingsPage())

                else -> {

                }
            }
            true
        }

        // Initialize the SpeechRecognizer and request audio recording permission
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                // Called when the recognition service is ready for user speech input.
            }

            override fun onBeginningOfSpeech() {
                // Called when the user starts speaking.
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Called when the RMS dB value of the audio changes.
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Called when audio buffer is received.
            }

            override fun onEndOfSpeech() {
                // Called when the user stops speaking.
            }

            override fun onError(errorCode: Int) {
                // Called when there's an error during recognition.
            }

            override fun onResults(results: Bundle?) {
                // Called when speech recognition is successful.
                // You can process the recognized text here.
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Called when partial recognition results are available.
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Called when an event related to recognition is received.
            }
        })

        // Start listening for speech recognition
        speechRecognizer.startListening(recognizerIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
