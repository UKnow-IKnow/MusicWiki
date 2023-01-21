package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.model.Track
import kotlinx.android.synthetic.main.track_layout.view.*

class TrackAdapter(
    val trackList: MutableList<Track>
) : RecyclerView.Adapter<TrackAdapter.ArtistViewHolder>() {

    class  ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return  ArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val track=trackList[position]
        val i = track.image.size
        holder.itemView.title.text=track.name
        holder.itemView.artist.text=track.artist.name
        Glide.with(holder.itemView.image).load(track.image[i-1].text).into(holder.itemView.image)
    }

    override fun getItemCount(): Int {
        return  trackList.size
    }


}