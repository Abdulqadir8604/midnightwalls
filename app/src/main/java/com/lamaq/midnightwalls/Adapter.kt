package com.lamaq.midnightwalls

import android.app.WallpaperManager
import android.content.ContentProvider
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.net.Uri.parse
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.FontResourcesParserCompat.parse
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.firebase.firestore.Query
import com.squareup.okhttp.HttpUrl.parse
import java.net.HttpCookie.parse
import java.net.URI
import java.util.logging.Level.parse


// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
@GlideModule
class Adapter     // Constructor for initialization
    (private var context: Context, private var image: List<*>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)

        // Passing view to ViewHolder
        return ViewHolder(view)
    }

    // Binding data to the into specified position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TypeCast Object to int type
        val imageUrl = GlideUrl(image[position] as String, LazyHeaders.Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build())
        Glide.with(context).load(imageUrl).placeholder(R.drawable.image_placeholder).into(holder.images)

        holder.images.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("image", image[position] as String)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        // Returns number of items
        // currently available in Adapter
        return image.size
    }

    // Initializing the Views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var images: ImageView

        init {
            images = view.findViewById<View>(R.id.image) as ImageView
        }
    }
}
