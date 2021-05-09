package edu.itesm.appchallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private val carritoList: MutableList<Comics>, private val context: Context): RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>(){

    inner class CarritoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var titulo = item.findViewById<TextView>(R.id.titulo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val carritoView = inflater.inflate(R.layout.carrito_item, parent, false)
        return CarritoViewHolder(carritoView)
    }

    override fun onBindViewHolder(holder: CarritoAdapter.CarritoViewHolder, position: Int) {
        val comic = carritoList[position]
        holder.titulo.text = comic.title
    }

    override fun getItemCount(): Int {
        return carritoList.size
    }

    public fun deleteItem(index: Int){
        carritoList?.removeAt(index)
        if (carritoList != null) {
            carritoList.clear()
        }
        notifyDataSetChanged()
    }

    public fun getComic(index: Int): Comics? {
        return carritoList?.get(index)
    }

}