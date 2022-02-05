package com.example.tictactoe

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class User() {
    var mAuth: FirebaseAuth? = null
    private var database:FirebaseDatabase? = null
    var myRef:DatabaseReference? = null

    var email:String? = null
    var password:String? = null
    var currentUser:FirebaseUser? = null
    var username:String? = null

    var currentPosition:Int? = null
    var listOfPositions = arrayListOf<Int>()

    fun getInstance(){
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser

        database = FirebaseDatabase.getInstance()
        myRef = database!!.reference
    }

    fun createUser(context:Context, activity:Activity) {
        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful){
                    currentUser = mAuth!!.currentUser
                    username = splitString(currentUser!!.email!!)
                    //save in database
                    myRef!!.child("Users").child(username!!).setValue(currentUser!!.uid)
                    myRef!!.child("Users").child(username!!).child("Request")
                    Toast.makeText(context, "successful login, ${username} , $email", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "failed login $email  $password", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun getPositions(){

    }

    fun addPositions(currentPosition:Int){
        listOfPositions.add(currentPosition)
    }

    fun splitString(str:String): String {
        var split = str.split("@")
        return split[0]
    }
}

