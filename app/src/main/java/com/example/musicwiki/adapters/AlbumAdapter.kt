package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.model.Album
import kotlinx.android.synthetic.main.album_layout.view.*

class AlbumAdapter(
    val albumList:MutableList<Album>
): RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return  AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_layout,parent,false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album=albumList[position]
        holder.itemView.title.text=album.name
        holder.itemView.artist.text=album.artist.name
        val i = album.image.size
        Glide.with(holder.itemView.image).load(album.image[i-1].text).into(holder.itemView.image)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }
}