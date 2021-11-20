package com.br.gookapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.br.gookapp.MainActivity
import com.br.gookapp.R
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.scheduler.dto.PageSchedulerResponse
import com.br.gookapp.service.gook.scheduler.dto.SchedulerRequest
import com.br.gookapp.service.gook.scheduler.dto.SchedulerResponse
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private lateinit var listScheduler: ListView
    private val mapper = JacksonConfig().objectMapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        listScheduler = requireView().findViewById(R.id.listScheduler)
        val mainActivity = activity as MainActivity
        if (mainActivity.userResponse?.email?.isNotEmpty() == true) {

            getSchedulers(mainActivity.userResponse!!.email)

        }
    }

    @SuppressLint("NewApi", "ResourceType")
    private fun getSchedulers(email: String): List<SchedulerResponse> {
        val queue = Volley.newRequestQueue(requireContext())
        var pageSchedulerResponse: PageSchedulerResponse? = null

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://192.168.5.7:8080/gookScheduler/v1/scheduler/all/",
            JSONObject(
                mapper.writeValueAsString(
                    SchedulerRequest(
                        customerEmail = email
                    )
                )
            ),
            Response.Listener {
                pageSchedulerResponse = mapper.readValue(it.toString())

                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    pageSchedulerResponse!!.schedulers.map { scheduler ->
                        scheduler.court.name + " - " + scheduler.schedule?.format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")
                        ) + " - " + scheduler.status
                    }
                )
                listScheduler.adapter = adapter
                listScheduler.onItemClickListener =
                    OnItemClickListener { _, _, position, _ ->
                        val intent = Intent(requireContext(), CourtFormFragment::class.java)
                        intent.putExtra(
                            "scheduler",
                            pageSchedulerResponse!!.schedulers[position]

                        )
                        startActivity(intent)
//                        val courtFormFragment = CourtFormFragment()
//                        courtFormFragment.arguments = intent.extras
//
//                        requireFragmentManager().beginTransaction()
//                            .replace(
//                                R.id.fragment_court_form,
//                                courtFormFragment
//                            )
//                            .commit()
//
////                        requireActivity().supportFragmentManager.beginTransaction()

                    }
            },
            {
                throw it
            })
        queue.add(request)
        return pageSchedulerResponse?.schedulers ?: arrayListOf()
    }

}