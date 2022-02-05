package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    var user = User()

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        user.getInstance()

        var bundle: Bundle? = intent.extras
        user.email = bundle!!.getString("email")
        user.username = user.splitString(user.email!!)

        incomingRequests()
        Toast.makeText(this, "${user.username}", Toast.LENGTH_SHORT).show()

    }

    /*override fun onStart() {
        super.onStart()

        incomingRequests()
    }*/

    fun buClick(view: View){
        val buSelected = view as Button
        var cellID = 0
        when(buSelected.id){
            R.id.p1 -> cellID = 1
            R.id.p2 -> cellID = 2
            R.id.p3 -> cellID = 3
            R.id.p4 -> cellID = 4
            R.id.p5 -> cellID = 5
            R.id.p6 -> cellID = 6
            R.id.p7 -> cellID = 7
            R.id.p8 -> cellID = 8
            R.id.p9 -> cellID = 9
        }

        //PlayGame(cellID, buSelected)
    }


    fun buRequestEvent(view: View){
        //Done: split email to create username (currently only accepts username)
        var userEmail = etEmail.text.toString()
        user.myRef!!.child("Users").child(splitString(userEmail)).child("Request").push().setValue(user.email)

    }
    fun buAcceptEvent(view: View){
        var userEmail = etEmail.text.toString()
        user.myRef!!.child("Users").child(splitString(userEmail)).child("Request").push().setValue(user.email)
    }

    fun incomingRequests(){
        user.myRef!!.child("User").child(user.username!!).child("Request")
            .addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    try{
                        //data stored in table
                        val td = snapshot.value as HashMap<String,Any>

                        if (td != null){
                            var value:String
                            for(key in td.keys){
                                //TODO: value might have to be split and converted to the username portion
                                value = td[key] as String
                                etEmail.setText(value)

                                user.myRef!!.child("Users").child(user.username!!).child("Request").setValue(true)
                                break
                            }
                        }
                    }catch (ex:Exception){}
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun splitString(str:String): String {
        var split = str.split("@")
        return split[0]
    }


}