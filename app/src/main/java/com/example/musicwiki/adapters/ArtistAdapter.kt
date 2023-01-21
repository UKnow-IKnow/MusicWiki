package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.model.Artist
import kotlinx.android.synthetic.main.artist_layout.view.*

class ArtistAdapter(
    val artistList: MutableList<Artist>
): RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return  ArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.artist_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist=artistList[position]
        holder.itemView.name.text=artist.name
        val i = artist.image.size
        Glide.with(holder.itemView.image).load(artist.image[i-1].text).into(holder.itemView.image)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }
}