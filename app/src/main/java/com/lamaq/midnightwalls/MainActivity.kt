package com.lamaq.midnightwalls

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val progressBar: ProgressBar by lazy { findViewById(R.id.progressBar) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val imageList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = mLayoutManager

        fetchWallpapers()

    }

    private fun fetchWallpapers() {
        progressBar.visibility = ProgressBar.VISIBLE
        Firebase.firestore.collection("/wallpapers").document("/midnightwalls-wallapers").get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val map = document.data
                        if (map != null) {
                            for (key in map.keys) {
                                imageList.add(map[key].toString())
                            }
                        }
                        progressBar.visibility = ProgressBar.GONE
                        recyclerView.adapter = Adapter(this, imageList)
                    }
                }
            }
    }
}