package com.example.musicwiki.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicwiki.R
import com.example.musicwiki.api.RetrofitInstance
import com.example.musicwiki.model.Tag
import com.example.musicwiki.model.TopTagResponse
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var isListExpended: Boolean = false
    private lateinit var tagList: List<Tag>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val response = RetrofitInstance.api.getTopTags()
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()

        response.enqueue(
            object : retrofit2.Callback<TopTagResponse> {
                override fun onResponse(
                    call: Call<TopTagResponse>,
                    response: Response<TopTagResponse>
                ) {
                    if (response.body() != null) {
                        val topTags = (response.body())!!
                        tagList = topTags.toptags.tag
                        refreshList()
                    }
                }

                override fun onFailure(call: Call<TopTagResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Activity Started ${t}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    private fun refreshList() {
        if (isListExpended) {
            toggleIcon.setImageResource(R.drawable.ic_expand_less)
        } else {
            toggleIcon.setImageResource(R.drawable.ic_expand_more)
        }
        chipGroup.removeAllViews()

        for (i in 1 until tagList.size) {
            if (i == 9 && !isListExpended) {
                break
            }
            val tag = tagList[i]
            val chip = Chip(this)
            chip.text = tag.name
            chip.setChipBackgroundColorResource(R.color.gray)

            chip.setOnClickListener {
                val intent = Intent(this, Genre::class.java)
                intent.putExtra("tag", tag.name)
                startActivity(intent)
            }
            chipGroup.addView(chip)
        }
    }

    fun buttonPressed(view: View) {
        isListExpended = !isListExpended
        refreshList()
    }
}