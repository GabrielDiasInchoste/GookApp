package com.br.gookapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.br.gookapp.MainActivity
import com.br.gookapp.R
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.scheduler.dto.response.LocalResponse
import com.br.gookapp.service.gook.scheduler.dto.response.PageLocalResponse
import com.fasterxml.jackson.module.kotlin.readValue

class LocalFragment : Fragment() {

    private lateinit var listLocal: ListView
    private val mapper = JacksonConfig().objectMapper
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainActivity = activity as MainActivity
        if (mainActivity.userResponse?.email?.isNotEmpty() == true) {
            listLocal = requireView().findViewById(R.id.listLocal)
            email = mainActivity.userResponse!!.email
            getLocals()
        }

    }

    private fun getLocals(): List<LocalResponse> {
        val queue = Volley.newRequestQueue(requireContext())
        var pageLocalResponse: PageLocalResponse? = null

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://192.168.5.7:8080/gookScheduler/v1/local/all/",
            null,
            Response.Listener {
                pageLocalResponse = mapper.readValue(it.toString())

                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    pageLocalResponse!!.locals.map { local ->
                        local.name
                    }
                )
                listLocal.adapter = adapter
                listLocal.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val intent = Intent(requireContext(), CourtActivity::class.java)
                        intent.putExtra(
                            "courts",
                            pageLocalResponse!!.locals[position].courts

                        )
                        intent.putExtra(
                            "email",
                            email
                        )
                        intent.putExtra(
                            "address",
                            pageLocalResponse!!.locals[position].address
                        )
                        startActivity(intent)
                    }
            },
            {
                throw it
            })
        queue.add(request)
        return pageLocalResponse?.locals ?: arrayListOf()
    }

}