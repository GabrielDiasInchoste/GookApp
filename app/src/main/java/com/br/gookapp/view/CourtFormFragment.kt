package com.br.gookapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.br.gookapp.R
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.scheduler.dto.SchedulerResponse
import com.google.android.material.navigation.NavigationView
import java.time.format.DateTimeFormatter


class CourtFormFragment : AppCompatActivity() {

    private val mapper = JacksonConfig().objectMapper

    @SuppressLint("NewApi")
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_court_form)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawarLayout)
        val imgMenu = findViewById<ImageView>(R.id.imgMenu)

        val navView = findViewById<NavigationView>(R.id.navDawar)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(navView, navController)

        val textTitle = findViewById<TextView>(R.id.title)
        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                textTitle.text = destination.label
            }


        // Pegando o objeto que veio pelo intent
        val scheduler = this.intent.getSerializableExtra("scheduler") as SchedulerResponse

//        val scheduler = this.arguments?.get("scheduler") as SchedulerResponse

        findViewById<View>(R.id.buttonVoltar)
            .setOnClickListener { finish() }

        val editTextId: EditText = findViewById(R.id.editTextId)
        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextDescription: EditText = findViewById(R.id.editTextDescription)
        val editTextType: EditText = findViewById(R.id.editTextType)
        val editTextSchedule: EditText = findViewById(R.id.editTextSchedule)
        val editTextStatus: EditText = findViewById(R.id.editTextStatus)

        editTextId.setText(scheduler.id.toString())
        editTextName.setText(scheduler.court.name)
        editTextDescription.setText(scheduler.court.description)
        editTextType.setText(scheduler.court.type)
        editTextSchedule.setText(scheduler.schedule?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")))
        editTextStatus.setText(scheduler.status.name)
    }

//    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
//
//        super.onActivityCreated(savedInstanceState)
//        childFragmentManager.beginTransaction()
//            .replace(
//                R.id.fragment_court_form,
//                this,
//                "fragment_court_form"
//            )
//            .commit()
//    }

//    @SuppressLint("NewApi")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
////        val scheduler = requireActivity().intent.getSerializableExtra("scheduler") as SchedulerResponse
//
//        val scheduler = this.arguments?.get("scheduler") as SchedulerResponse
//
//        requireView().findViewById<View>(R.id.buttonVoltar)
//            .setOnClickListener { requireActivity().finish() }
//
//        val editTextId: EditText = requireView().findViewById(R.id.editTextId)
//        val editTextName: EditText = requireView().findViewById(R.id.editTextName)
//        val editTextDescription: EditText = requireView().findViewById(R.id.editTextDescription)
//        val editTextType: EditText = requireView().findViewById(R.id.editTextType)
//        val editTextSchedule: EditText = requireView().findViewById(R.id.editTextSchedule)
//        val editTextStatus: EditText = requireView().findViewById(R.id.editTextStatus)
//
//        editTextId.setText(scheduler.id.toString())
//        editTextName.setText(scheduler.court.name)
//        editTextDescription.setText(scheduler.court.description)
//        editTextType.setText(scheduler.court.type)
//        editTextSchedule.setText(scheduler.schedule?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")))
//        editTextStatus.setText(scheduler.status.name)
//
//    }


//    private fun cancel(v: View) {
//        val queue = Volley.newRequestQueue(requireContext())
//        val cancelId: EditText = requireView().findViewById(R.id.editTextId)
//
//        val request = JsonObjectRequest(
//            Request.Method.GET,
//            "http://192.168.5.7:8080/cancel/schedulerId/${cancelId.text}",
//            JSONObject(
//                mapper.writeValueAsString(
//                    CancelRequest(
//                        description = "Arrependimento via App"
//                    )
//                )
//            ),
//            Response.Listener {
//
//                val intent = Intent(requireContext(), HomeFragment::class.java)
//                startActivity(intent)
//            },
//            {
//                throw it
//            })
//        queue.add(request)
//    }
}