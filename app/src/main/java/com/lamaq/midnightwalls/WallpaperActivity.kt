package com.lamaq.midnightwalls

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class WallpaperActivity : AppCompatActivity(){
    private var wallpaperUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        supportActionBar?.hide()

        setContentView(R.layout.wallpaper_activity)

        val intent: Intent = intent
        wallpaperUrl = intent.getStringExtra("image").toString()

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.fitsSystemWindows = true
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
            .load(wallpaperUrl)
            .into(imageView)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            setWallpaper(wallpaperUrl)
        }
    }

    private fun setWallpaper(uri: String) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val wallpaperManager = WallpaperManager.getInstance(this)
        val bitmap:Bitmap? = getBitmapFromURL(uri)
        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getBitmapFromURL(url: String): Bitmap? {
        return try {
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}