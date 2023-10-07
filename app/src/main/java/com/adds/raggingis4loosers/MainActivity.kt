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
import com.adds.raggingis4loosers.databinding.ActivityMainBinding
import com.adds.raggingis4loosers.ProfilePage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices



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
                    // Handle other cases if needed
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
                // You can add your implementation here.
            }

            override fun onBeginningOfSpeech() {
                // Called when the user starts speaking.
                // You can add your implementation here.
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Called when the RMS dB value of the audio changes.
                // You can add your implementation here.
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Called when audio buffer is received.
                // You can add your implementation here.
            }

            override fun onEndOfSpeech() {
                // Called when the user stops speaking.
                // You can add your implementation here.
            }

            override fun onError(errorCode: Int) {
                // Called when there's an error during recognition.
                // You can add your implementation here.
            }

            override fun onResults(results: Bundle?) {
                // Called when speech recognition is successful.
                // You can process the recognized text here.
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Called when partial recognition results are available.
                // You can add your implementation here.
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Called when an event related to recognition is received.
                // You can add your implementation here.
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


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    class VoiceRecognitionService : Service() {
        private lateinit var speechRecognizer: SpeechRecognizer

        override fun onCreate() {
            super.onCreate()

            // Check and request audio recording permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // You should request permission here and handle the result
                // ActivityCompat.requestPermissions(...)
            }

            // Create a notification channel (required for Android 8.0+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Voice Recognition Service",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

            // Create a notification for the Foreground Service
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Voice Recognition Service")
                .setContentText("Listening for voice commands...")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .build()

            // Start the Foreground Service with the notification
            startForeground(NOTIFICATION_ID, notification)
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            // Initialize and start your voice recognition logic here
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer.setRecognitionListener(object : RecognitionListener {
                // Implement speech recognition callbacks here
            })

            // Start listening for voice commands
            val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            recognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            speechRecognizer.startListening(recognizerIntent)

            return START_STICKY
        }

        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

        override fun onDestroy() {
            super.onDestroy()

            // Stop and release the SpeechRecognizer
            if (::speechRecognizer.isInitialized) {
                speechRecognizer.destroy()
            }
        }

        companion object {
            private const val CHANNEL_ID = "VoiceRecognitionChannel"
            private const val NOTIFICATION_ID = 12345
        }
    }


    class MainActivity : AppCompatActivity() {

        private val REQUEST_RECORD_AUDIO_PERMISSION = 1
        private lateinit var speechRecognizer: SpeechRecognizer
        private lateinit var recognizedText: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val startListeningButton: Button = findViewById(R.id.startListeningButton)
            recognizedText = findViewById(R.id.recognizedText)

            startListeningButton.setOnClickListener {
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
                } else {
                    startListening()
                }
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startListening()
                }
            }
        }

        private fun startListening() {
            // Initialize the speech recognizer and start listening
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            recognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            speechRecognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(errorCode: Int) {}

                override fun onResults(results: Bundle?) {
                    val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (data != null && data.isNotEmpty()) {
                        val recognized = data[0]
                        recognizedText.text = recognized
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            speechRecognizer.startListening(recognizerIntent)
        }

        override fun onDestroy() {
            super.onDestroy()
            if (::speechRecognizer.isInitialized) {
                speechRecognizer.destroy()
            }
        }
    }
 

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val smsPermissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    fun onSendLocationButtonClicked(view: View) {
        if (checkPermission()) {
            getLastLocation()
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            smsPermissionCode
        )
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    val message = "My current location: https://maps.google.com/?q=$latitude,$longitude"

                    // Send SMS
                    sendSMS("1234567890", message)

                    // Send WhatsApp message
                    sendWhatsAppMessage(message)
                }
            }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendWhatsAppMessage(message: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=PHONE_NUMBER&text=$message")
        startActivity(intent)
    }
}



}




