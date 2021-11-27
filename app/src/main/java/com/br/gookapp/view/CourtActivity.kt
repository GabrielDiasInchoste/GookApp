package com.br.gookapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.br.gookapp.R
import com.br.gookapp.service.gook.scheduler.dto.response.AddressResponse
import com.br.gookapp.service.gook.scheduler.dto.response.CourtResponse

class CourtActivity : AppCompatActivity() {

    private lateinit var listCourt: ListView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_court)
        listCourt = findViewById(R.id.listCourt)

        val courts = this.intent.getSerializableExtra("courts") as List<CourtResponse>
        val email = this.intent.getSerializableExtra("email") as String
        val address = this.intent.getSerializableExtra("address") as AddressResponse

        findViewById<View>(R.id.buttonVoltar)
            .setOnClickListener { finish() }

        findViewById<View>(R.id.buttonMaps)
            .setOnClickListener {
                openGoogleMapsNavigation(
                    this,
                    address
                )
            }

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

    private fun openGoogleMapsNavigation(
        context: Context,
        address: AddressResponse
    ) {
        val googleMapsUrl = "google.navigation:q=${address.latitude},${address.longitude}"
        val uri = Uri.parse(googleMapsUrl)

        val googleMapsPackage = "com.google.android.apps.maps"
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage(googleMapsPackage)
        }
        context.startActivity(intent)
    }

}