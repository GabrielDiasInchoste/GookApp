package com.br.gookapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.br.gookapp.MainActivity
import com.br.gookapp.R
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.scheduler.dto.SchedulerStatusPort
import com.br.gookapp.service.gook.scheduler.dto.request.CancelRequest
import com.br.gookapp.service.gook.scheduler.dto.response.SchedulerResponse
import org.json.JSONObject
import java.time.format.DateTimeFormatter


class SchedulerDetailsActivity : AppCompatActivity() {

    private val mapper = JacksonConfig().objectMapper

    @SuppressLint("NewApi")
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scheduler_details)

        val scheduler = this.intent.getSerializableExtra("scheduler") as SchedulerResponse

        findViewById<View>(R.id.buttonVoltar)
            .setOnClickListener { finish() }

        val editTextId: EditText = findViewById(R.id.editTextEmail)
        val editTextName: EditText = findViewById(R.id.editTextQuadra)
        val editTextDescription: EditText = findViewById(R.id.editTextDescription)
        val editTextType: EditText = findViewById(R.id.editTextType)
        val editTextSchedule: EditText = findViewById(R.id.editTextSchedule)
        val editTextStatus: EditText = findViewById(R.id.editTextStatus)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        if (scheduler.status == SchedulerStatusPort.CANCEL_REQUESTED || scheduler.status == SchedulerStatusPort.CANCELED) {
            buttonCancel.isEnabled = false
        }

        editTextId.setText(scheduler.id.toString())
        editTextName.setText(scheduler.court.name)
        editTextDescription.setText(scheduler.court.description)
        editTextType.setText(scheduler.court.type)
        editTextSchedule.setText(scheduler.schedule?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        editTextStatus.setText(scheduler.status.name)
    }

    fun cancel(v: View) {
        val queue = Volley.newRequestQueue(this)
        val cancelId: EditText = findViewById(R.id.editTextEmail)

        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://192.168.5.7:8080/gookScheduler/v1/cancel/schedulerId/${cancelId.text}",
            JSONObject(
                mapper.writeValueAsString(
                    CancelRequest(
                        description = "Arrependimento via App"
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