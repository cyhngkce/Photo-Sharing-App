package com.cyhngkce.fotografpaylasmauygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailText =findViewById(R.id.emailText)
        passwordText=findViewById(R.id.passwordText)
        auth=FirebaseAuth.getInstance()
        val guncelKullanici=auth.currentUser
        if (guncelKullanici!=null){
            val intent=Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
    fun girisYap(view: View){
  auth.signInWithEmailAndPassword(emailText.text.toString(),passwordText.text.toString()).addOnCompleteListener { task ->
      if (task.isSuccessful){
          val guncelKullanici=auth.currentUser?.email.toString()
          Toast.makeText(this,"Hoşgeldin: ${guncelKullanici}",Toast.LENGTH_LONG).show()


          val intent=Intent(this,FeedActivity::class.java)
          startActivity(intent)
          finish()
      }
  }.addOnFailureListener { exception->
      Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
  }

    }
    fun kayitOl(view: View){

        val email=emailText.text.toString()
        val password=passwordText.text.toString()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent=Intent(this,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }


    }
}