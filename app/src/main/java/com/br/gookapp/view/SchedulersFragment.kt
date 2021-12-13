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
import com.br.gookapp.service.gook.scheduler.dto.response.PageSchedulerResponse
import com.br.gookapp.service.gook.scheduler.dto.response.SchedulerResponse
import com.br.gookapp.service.gook.scheduler.dto.toResponse
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.format.DateTimeFormatter

class SchedulersFragment : Fragment() {

    private lateinit var listScheduler: ListView
    private val mapper = JacksonConfig().objectMapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedulers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        listScheduler = requireView().findViewById(R.id.listScheduler)

        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            if (mainActivity.userResponse?.email?.isNotEmpty() == true) {

                getSchedulers(mainActivity.userResponse!!.email)

            }
        }
    }

    @SuppressLint("NewApi", "ResourceType")
    private fun getSchedulers(email: String): List<SchedulerResponse> {
        val queue = Volley.newRequestQueue(requireContext())
        var pageSchedulerResponse: PageSchedulerResponse? = null

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://192.168.5.7:8080/gookBFF/v1/scheduler/all/?customerEmail=$email",
            null,
            Response.Listener {
                pageSchedulerResponse = mapper.readValue(it.toString())

                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    pageSchedulerResponse!!.schedulers.sortedByDescending { scheduler -> scheduler.schedule }
                        .map { scheduler ->
                            scheduler.status.toResponse() + " - " + scheduler.court.name + " - " + scheduler.schedule?.format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")
                            )
                        }
                )

                listScheduler.adapter = adapter
                listScheduler.onItemClickListener =
                    OnItemClickListener { _, _, position, _ ->
                        val intent = Intent(requireContext(), SchedulerDetailsActivity::class.java)
                        intent.putExtra(
                            "scheduler",
                            pageSchedulerResponse!!.schedulers[position]

                        )
                        startActivity(intent)
                    }
            },
            {
                throw it
            })
        queue.add(request)
        return pageSchedulerResponse?.schedulers ?: arrayListOf()
    }

}