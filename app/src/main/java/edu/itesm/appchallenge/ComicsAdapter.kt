package edu.itesm.appchallenge

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ComicsAdapter(private val comicsList: List<Comics>, private val context: Context): RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder>() {
    inner class ComicsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title = item.findViewById<TextView>(R.id.comicTitle)
        var comicCover = item.findViewById<ImageView>(R.id.comicCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val comicView = inflater.inflate(R.layout.comics_item, parent, false)
        return ComicsViewHolder(comicView)
    }

    override fun onBindViewHolder(holder: ComicsAdapter.ComicsViewHolder, position: Int) {
        val comic = comicsList[position]
        holder.title.text = comic.title
        Picasso.get().isLoggingEnabled = true
        val path = "${comic.path}/standard_large.jpg"
        if(path != "/standard_large.jpg")
            Picasso.get().load(path).fit().into(holder.comicCover)
        holder.itemView.setOnClickListener {
            val action = ComicsFragmentDirections.actionComicsFragmentToComicDetailFragment(comic)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }
}