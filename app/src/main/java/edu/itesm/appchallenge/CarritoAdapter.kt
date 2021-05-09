package edu.itesm.appchallenge

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class CarritoAdapter(private val carritoList: MutableList<Comics>, private val context: Context): RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>(){

    private lateinit var database: FirebaseDatabase

    inner class CarritoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var titulo = item.findViewById<TextView>(R.id.titulo)
        var borrarButton = item.findViewById<Button>(R.id.borrarButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        database = FirebaseDatabase.getInstance()
        val inflater = LayoutInflater.from(parent.context)
        val carritoView = inflater.inflate(R.layout.carrito_item, parent, false)
        return CarritoViewHolder(carritoView)
    }

    override fun onBindViewHolder(holder: CarritoAdapter.CarritoViewHolder, position: Int) {
        val comic = carritoList[position]
        val usuario = Firebase.auth.currentUser
        holder.titulo.text = comic.title
        holder.borrarButton.setOnClickListener {
            Log.i("click", "quiero borrar este elemento")
            val reference = database.getReference("carrito/${usuario.uid}/").child(comic.id)
            Log.i("elemento", "${reference}")
            reference.removeValue()
        }
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