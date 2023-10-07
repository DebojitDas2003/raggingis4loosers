package com.adds.raggingis4loosers
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Surface
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.adds.raggingis4loosers.R
//import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import com.adds.raggingis4loosers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var binding: ActivityMainBinding

    private lateinit var cameraDevice: CameraDevice
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var videoFile: File
    private lateinit var backgroundHandler: Handler
    private lateinit var backgroundThread: HandlerThread
    private val cameraOpenCloseLock = Semaphore(1)
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(@)
        button.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
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

    private fun stopRecording() {
        TODO("Not yet implemented")
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

    private val TAG = "MainActivity"

    private fun startRecording() {
        if (checkPermissions()) {
            try {
                // Initialize the media recorder
                mediaRecorder = MediaRecorder()

                // Set up the camera and media recorder
                setupCamera()
                setupMediaRecorder()

                // Start video capture session
                startCaptureSession()

                // Start recording
                mediaRecorder.start()
                isRecording = true
                button.text = "Stop Recording"
            } catch (e: Exception) {
                Log.e(TAG, "Error starting recording: ${e.message}")
            }
        }
    }

    private fun startCaptureSession() {
        TODO("Not yet implemented")
    }

    private fun setupMediaRecorder() {
        TODO("Not yet implemented")
    }

    private fun setupCamera() {
        TODO("Not yet implemented")
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val notGrantedPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGrantedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGrantedPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
            return false
        }

        return true
    }

companion object {
    private const val TAG = "MainActivity"
    private const val PERMISSION_REQUEST_CODE = 123
}
}


