package com.br.gookapp.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.br.gookapp.MainActivity
import com.br.gookapp.R
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.scheduler.dto.request.SchedulerRequest
import com.br.gookapp.service.gook.scheduler.dto.response.CourtResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SchedulerRequestActivity : AppCompatActivity() {

    private val mapper = JacksonConfig().objectMapper
    private lateinit var court: CourtResponse
    private lateinit var token: String

    @SuppressLint("NewApi")
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scheduler_request)

        court = this.intent.getSerializableExtra("court") as CourtResponse
        val email = this.intent.getSerializableExtra("email") as String

        findViewById<View>(R.id.buttonVoltar)
            .setOnClickListener { finish() }

        val editTextEmail: EditText = findViewById(R.id.editTextEmailRequest)
        val editTextQuadra: EditText = findViewById(R.id.editTextQuadraRequest)
        val editTextScheduleRequest: EditText = findViewById(R.id.editTextScheduleRequest)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result
        })

        editTextEmail.setText(email)
        editTextQuadra.setText(court.name)
        editTextScheduleRequest.setText(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"))
        )

    }

    @SuppressLint("NewApi")
    fun send(v: View) {
        val queue = Volley.newRequestQueue(this)
        val editTextEmail: EditText = findViewById(R.id.editTextEmailRequest)
        val editTextQuadra: EditText = findViewById(R.id.editTextQuadraRequest)
        val editTextScheduleRequest: EditText = findViewById(R.id.editTextScheduleRequest)

        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://192.168.5.7:8080/gookBFF/v1/scheduler",
            JSONObject(
                mapper.writeValueAsString(
                    SchedulerRequest(
                        token,
                        customerEmail = editTextEmail.text.toString(),
                        courtId = court.id,
                        schedule = LocalDateTime.parse(
                            editTextScheduleRequest.text,
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        )
                    )
                )
            ),
            Response.Listener {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            {
                throw it
            })
        queue.add(request)
    }
}