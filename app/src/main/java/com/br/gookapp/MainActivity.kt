package com.br.gookapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.br.gookapp.service.JacksonConfig
import com.br.gookapp.service.gook.user.dto.UserRequest
import com.br.gookapp.service.gook.user.dto.UserResponse
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val mapper = JacksonConfig().objectMapper
    val RC_SIGN_IN: Int = 1
    var userResponse: UserResponse? = null

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signIn()

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        } else {
            Toast.makeText(this, "Problem in execution order :(", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            userResponse = getUserByEmailOrCreateUser(account)
            updateUI(userResponse, account)
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(userResponse: UserResponse?, account: GoogleSignInAccount) {
        val userNameTextView = findViewById<View>(R.id.userNameTextView) as TextView
        userNameTextView.text = userResponse?.email
            ?: account.displayName // TODO VER PQ SO FAZ A CHAMADA DPS Q CARREGA A TELA
    }

    private fun getUserByEmailOrCreateUser(account: GoogleSignInAccount): UserResponse? {
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://192.168.5.7:8080/gookBFF/v1/user/email/${account.email}",
            null,
            Response.Listener {
                userResponse = mapper.readValue(it.toString())
            },
            {
                val request = JsonObjectRequest(
                    Request.Method.POST,
                    "http://192.168.5.7:8080/gookBFF/v1/user",
                    JSONObject(
                        mapper.writeValueAsString(
                            UserRequest(
                                name = account.displayName!!,
                                email = account.email!!,
                                pix = account.email!!
                            )
                        )
                    ),
                    Response.Listener {
                        userResponse = mapper.readValue(it.toString())
                    },
                    {
                        throw it
                    })
                queue.add(request)
            })
        queue.add(request)
        return userResponse
    }
}