package com.example.musicwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.adapters.AlbumAdapter
import com.example.musicwiki.adapters.ArtistAdapter
import com.example.musicwiki.adapters.TrackAdapter
import com.example.musicwiki.api.RetrofitInstance
import com.example.musicwiki.model.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_genre.*
import retrofit2.Call
import retrofit2.Response

class Genre : AppCompatActivity() {

    lateinit var tagName: String

    var albums: MutableList<Album> = mutableListOf()
    val albumAdapter: AlbumAdapter = AlbumAdapter(albums)

    var artist: MutableList<Artist> = mutableListOf()
    val artistAdapter: ArtistAdapter = ArtistAdapter(artist)

    var track: MutableList<Track> = mutableListOf()
    val trackAdapter: TrackAdapter = TrackAdapter(track)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()

        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = albumAdapter
        tagName = intent.getStringExtra("tag")!!

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.equals("ALBUMS")) {
                    recyclerView.adapter = albumAdapter
                }else if (tab.text!!.equals("ARTISTS")) {
                    recyclerView.adapter = artistAdapter
                }else if (tab.text!!.equals("TRACKS")){
                    recyclerView.adapter=trackAdapter
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        getTopInfo()
        getTopAlbums()
        getTopArtists()
        getTopTracks()
    }

    private fun getTopTracks() {
        val response = RetrofitInstance.api.getTagTracks(tagName)
        response.enqueue(
            object: retrofit2.Callback<TopTrackResponse>{
                override fun onResponse(
                    call: Call<TopTrackResponse>,
                    response: Response<TopTrackResponse>
                ) {
                    if (response.body() != null) {
                        val topTrackResponse = (response.body())!!
                        track.addAll(topTrackResponse.tracks.track)
                        trackAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<TopTrackResponse>, t: Throwable) {
                    Toast.makeText(this@Genre, "Failed $t", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    private fun getTopArtists() {
        val response = RetrofitInstance.api.getTagArtists(tagName)
        response.enqueue(
            object : retrofit2.Callback<TopArtistResponse> {
                override fun onResponse(
                    call: Call<TopArtistResponse>,
                    response: Response<TopArtistResponse>
                ) {
                    if (response.body() != null) {
                        val topArtistResponse = (response.body())!!
                        artist.addAll(topArtistResponse.topartists.artist)
                        artistAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<TopArtistResponse>, t: Throwable) {
                    Toast.makeText(this@Genre, "Failed $t", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    private fun getTopAlbums() {

        val response = RetrofitInstance.api.getTopAlbums(tagName)
        response.enqueue(
            object :retrofit2.Callback<TopAlbumResponse>{
                override fun onResponse(
                    call: Call<TopAlbumResponse>,
                    response: Response<TopAlbumResponse>
                ) {
                    if (response.body() != null) {
                        val topAlbumResponse = (response.body())!!
                        albums.addAll(topAlbumResponse.albums.album)
                        albumAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<TopAlbumResponse>, t: Throwable) {
                    Toast.makeText(this@Genre, "Failed $t", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    private fun getTopInfo() {

        val response = RetrofitInstance.api.getTagInfo(tagName)
        response.enqueue(
            object: retrofit2.Callback<TagInfoResponse>{
                override fun onResponse(
                    call: Call<TagInfoResponse>,
                    response: Response<TagInfoResponse>
                ) {
                    if (response.body() != null) {
                        val taginfo = (response.body())!!
                        tagDescription.text = taginfo.tag.wiki.summary
                        title_tv.text = taginfo.tag.name
                    }
                }

                override fun onFailure(call: Call<TagInfoResponse>, t: Throwable) {
                    Toast.makeText(this@Genre, "Failed $t", Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}