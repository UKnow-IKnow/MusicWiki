package com.example.musicwiki.model

import com.example.musicwiki.model.ArtistXX
import com.example.musicwiki.model.ImageXX
import com.example.musicwiki.model.Streamable

data class Track(
    val artist: ArtistXX,
    val duration: String,
    val image: List<ImageXX>,
    val mbid: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)