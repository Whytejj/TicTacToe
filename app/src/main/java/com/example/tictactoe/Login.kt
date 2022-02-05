package com.example.tictactoe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    //private var mAuth: FirebaseAuth? = null
    //var user:User? = null
    var user = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //mAuth = FirebaseAuth.getInstance()
        //user
        user.getInstance()

    }

    fun buLoginEvent (view: View){
        //TODO: check if user input valid email and password
        user.email = etEmail.text.toString()
        user.password = etPassword.text.toString()
        loginToFirebase()
    }

    fun loginToFirebase(){
        var activity:Activity = this
        var context:Context = this
        /*mAuth!!.createUserWithEmailAndPassword(user.email!!, user.password!!)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext, "successful login", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "failed login", Toast.LENGTH_SHORT).show()
                }
            }*/


        user.createUser(context, activity)
        loadMain()
    }

    override fun onStart() {
        super.onStart()

        loadMain()
    }

    fun loadMain(){
        if (user.currentUser != null) {

            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", user.currentUser!!.email)
            intent.putExtra("uid", user.currentUser!!.uid)

            startActivity(intent)
        }

    }





}