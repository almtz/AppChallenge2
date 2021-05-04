package edu.itesm.appchallenge

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registro.*

@Parcelize
data class Usuario(var nombre: String, var correo:String) : Parcelable

class RegistroFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase : FirebaseFirestore
    private lateinit var nuevoUsuario: Usuario
    lateinit var correoMandar : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Inicializa objetos:
        nuevoUsuario = Usuario("","")
        auth = Firebase.auth
        dataBase = Firebase.firestore
        crearCuenta.setOnClickListener { crearUsuario() }

    }

    override fun onStart() {
        super.onStart()
    }

    private fun usuarioCreado(){

        val builder = AlertDialog.Builder(this.requireContext())
        with(builder){
            Toast.makeText(this.context,"Usuario creado con éxito", Toast.LENGTH_LONG).show()
            val action = RegistroFragmentDirections.registroToMain2()
            view?.findNavController()?.navigate(action)
        }
    }

    fun crearUsuario(){
        if (contrasena.text.toString() == corroborarContrasena.text.toString()){

            if (correo.text.isNotEmpty() && contrasena.text.isNotEmpty() ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    correo.text.toString(),
                    contrasena.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        iniciarSesion()
                        correo.text.clear()
                        contrasena.text.clear()
                        usuarioCreado()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this.context,it.toString(), Toast.LENGTH_LONG).show()
                }
            }else{Toast.makeText(this.context,"No dejes campos vacios", Toast.LENGTH_LONG).show()}
        }else{Toast.makeText(this.context,"Contraseñas no coinciden", Toast.LENGTH_LONG).show() }
    }

    fun iniciarSesion(){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    correo.text.toString(),
                    contrasena.text.toString()
            ).addOnCompleteListener{
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }


}