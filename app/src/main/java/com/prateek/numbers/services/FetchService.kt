package com.prateek.numbers.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.prateek.numbers.utilities.BASE_URL
object FetchService {

    var result: String? = null

    fun getData(context: Context, data: String, complete: (Boolean) -> Unit) {

        val url = "${BASE_URL}${data}?json"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, Response.Listener { response ->
            result = response.getString("text")
            complete(true)

        }, Response.ErrorListener { error ->
            complete(false)
            Log.d("RIP", error.toString())

        })

        Volley.newRequestQueue(context).add(jsonRequest)


    }
}