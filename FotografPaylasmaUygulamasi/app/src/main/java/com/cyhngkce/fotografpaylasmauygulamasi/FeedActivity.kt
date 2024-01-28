package com.cyhngkce.fotografpaylasmauygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseFirestore
    private lateinit var recyclerViewAdapter:FeedRecyclerAdapter
    lateinit var recyclerView:RecyclerView
    var postListesi=ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth= FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
        verileriAl()
        val layoutManager= LinearLayoutManager(this)
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=layoutManager
        recyclerViewAdapter=FeedRecyclerAdapter(postListesi)
        recyclerView.adapter=recyclerViewAdapter

    }

    fun verileriAl(){
        database.collection("Post").orderBy("tarih",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
            if(exception!=null){
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(snapshot!=null){
                    if(!snapshot.isEmpty){
                        val documents=snapshot.documents
                        postListesi.clear()
                        for (document in documents){
                           val kullaniciEmail= document.get("kullaniciemail") as String
                            val kullaniciAciklama=document.get("kullaniciaciklama") as String
                            val gorselUrl = document.get("gorselurl") as String
                            val indirilenPost =Post(kullaniciEmail,kullaniciAciklama,gorselUrl)
                            postListesi.add(indirilenPost)
                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater =menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.fotograf_paylas) {
           val intent=Intent(this,FotografPaylasmaActivity::class.java)
        startActivity(intent)
        }
        else if (item.itemId==R.id.cikis_yap){
           auth.signOut()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}