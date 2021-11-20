package com.br.gookapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.br.gookapp.R
import com.br.gookapp.service.gook.scheduler.dto.response.CourtResponse

class CourtActivity : AppCompatActivity() {

    private lateinit var listCourt: ListView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_court)

        listCourt = findViewById(R.id.listCourt)

        findViewById<View>(R.id.buttonVoltar)
            .setOnClickListener { finish() }

        val courts = this.intent.getSerializableExtra("courts") as List<CourtResponse>
        val email = this.intent.getSerializableExtra("email") as String

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            courts.map { it.name }
        )
        listCourt.adapter = adapter
        listCourt.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(this, SchedulerRequestActivity::class.java)
                intent.putExtra(
                    "court",
                    courts[position]
                )
                intent.putExtra(
                    "email",
                    email
                )
                startActivity(intent)
            }

    }

}