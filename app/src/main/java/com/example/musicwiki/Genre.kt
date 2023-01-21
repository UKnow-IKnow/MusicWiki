package com.example.musicwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.adapters.AlbumAdapter
import com.example.musicwiki.api.RetrofitInstance
import com.example.musicwiki.model.Album
import com.example.musicwiki.model.TagInfoResponse
import com.example.musicwiki.model.TopAlbumResponse
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_genre.*
import retrofit2.Call
import retrofit2.Response

class Genre : AppCompatActivity() {

    lateinit var tagName: String

    var albums: MutableList<Album> = mutableListOf()
    val albumAdapter: AlbumAdapter = AlbumAdapter(albums)

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
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

        getTopInfo()
        getTopAlbums()
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