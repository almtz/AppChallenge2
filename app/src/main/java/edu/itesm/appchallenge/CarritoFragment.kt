package edu.itesm.appchallenge

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_carrito.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarritoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class SwipeToDelete(context: Context,
                             direccion: Int, direccionArrastre:Int):
        ItemTouchHelper.SimpleCallback(direccion, direccionArrastre){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}


class CarritoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    private fun deleteComic(comic: Comics){
        val usuario = Firebase.auth.currentUser
        val referencia = FirebaseDatabase.getInstance().getReference("carrito/${usuario.uid}/${comic.id}")
        referencia.removeValue()
    }

    //SWIPE TO DELETE
   /* val item = object : SwipeToDelete(it, ItemTouchHelper.UP,ItemTouchHelper.LEFT){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            super.onSwiped(viewHolder, direction)
            val comic = listaCarrito[ viewHolder.adapterPosition ]
            deleteComic(comic)
            CarritoAdapter.deleteItem(viewHolder.adapterPosition)
        }
    }
    val itemTouchHelper = ItemTouchHelper(item)
    itemTouchHelper.attachToRecyclerView(recycler_carrito)*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        val usuario = Firebase.auth.currentUser
        reference = database.getReference("carrito/${usuario.uid}")
        getComics()
    }

    fun getComics(){

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var cl = ArrayList<Comics>()
                for (product in snapshot.children){
                    var obj = product.getValue(Comics::class.java)
                    cl.add(obj as Comics)
                }
                recycler_carrito.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CarritoAdapter(cl, context)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}