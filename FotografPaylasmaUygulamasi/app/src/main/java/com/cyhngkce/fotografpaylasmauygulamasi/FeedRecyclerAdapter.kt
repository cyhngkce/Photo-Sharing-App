package com.cyhngkce.fotografpaylasmauygulamasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(val postListesi:ArrayList<Post>) :RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {


    class PostHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val recycler_row_email: TextView = itemView.findViewById(R.id.recycler_row_email)
        val recycler_row_aciklama: TextView = itemView.findViewById(R.id.recycler_row_aciklama)
        val recycler_row_image_view: ImageView = itemView.findViewById(R.id.recycler_row_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)

    }

    override fun getItemCount(): Int {
        return postListesi.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.recycler_row_email.text=postListesi[position].kullaniciEmail
        holder.recycler_row_aciklama.text=postListesi[position].kullaniciAciklama
        Picasso.get().load(postListesi[position].gorselUrl).into(holder.recycler_row_image_view)



    }
}