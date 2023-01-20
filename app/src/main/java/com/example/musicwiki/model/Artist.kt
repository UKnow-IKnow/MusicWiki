package com.example.musicwiki.model

data class Artist(
    val mbid: String,
    val name: String,
    val url: String,
    val image: List<Image>
)