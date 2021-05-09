package edu.itesm.appchallenge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comics_item.*
import kotlinx.android.synthetic.main.fragment_comic_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComicDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComicDetailFragment : Fragment() {

    private val args by navArgs<ComicDetailFragmentArgs>()
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance()
        val usuario = Firebase.auth.currentUser
        reference = database.getReference("carrito/${usuario.uid}/${args.comic.id}")
        bundle = Bundle()
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AgregaAlCarrito.setOnClickListener{
            addComic()
            AgregaAlCarrito.text = "Comic agregado"
        }

        IrAlCarrito.setOnClickListener{
            val action = ComicDetailFragmentDirections.actionComicDetailFragmentToCarritoFragment()
            IrAlCarrito.findNavController().navigate(action)
        }

        comicDetailTitle.text = args.comic.title
        if(args.comic.desc != "null") {
            comicDetailDesc.text = args.comic.desc
        } else {
            comicDetailDesc.text = "No tiene descripción"
        }
        Picasso.get().load("${args.comic.path}/standard_medium.jpg").into(comicDetailCover)
    }

    public fun addComic(){

        val titulo = args.comic.title
        val descripcion = args.comic.desc
        val path = args.comic.path
        if(titulo!!.isNotEmpty() && titulo!!.isNotBlank()){
        val id = reference.push().key
        val comic = Comics(
            id.toString(),
            titulo.toString(),
            descripcion.toString(),
                path.toString()
        )

        reference.child(id!!).setValue(comic)
            bundle.putString("edu_itesm_appchallenge", "added_comic")
        }else{
            Toast.makeText(context, "error al agregar el comic!", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ComicDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComicDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}