package com.br.gookapp.view.login

import androidx.appcompat.app.AppCompatActivity

class LoginActivityOld : AppCompatActivity() {

//    private lateinit var emailTextView: EditText
//    private lateinit var loginButton: Button
//    private lateinit var logoutButton: Button
//
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var googleSignInClient: GoogleSignInClient
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        emailTextView = findViewById(R.id.emailTextViewOld)
////        loginButton = findViewById(R.id.login_buttonOld)
//        logoutButton = findViewById(R.id.logout_buttonOld)
//
////        loginButton.setOnClickListener { }
//        logoutButton.setOnClickListener { }
//
//        auth = Firebase.auth
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
//                }
//            }
//    }
//
//    @Override
//    fun onClick(view: View) {
//        print("AKIIIIIIIIII")
//        when (view.id) {
//            R.id.login_button -> login()
//            R.id.logout_button -> logout()
//        }
//    }
//
//    private fun login() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    private fun logout() {
//        googleSignInClient.signOut()
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
//        emailTextView.setText(user?.email)
//    }
//
//    companion object {
//        private const val TAG = "GoogleActivity"
//        private const val RC_SIGN_IN = 9001
//    }
}