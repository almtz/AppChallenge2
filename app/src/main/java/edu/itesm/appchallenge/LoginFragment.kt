package edu.itesm.appchallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    lateinit var correoMandar : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Inicializa objetos:
        auth = Firebase.auth
        iniciarSesionBoton.setOnClickListener {iniciarSesion()}

        registroBoton.setOnClickListener { view?.findNavController()?.navigate(R.id.toRegistroFragment) }


    }

    fun iniciarSesion(){
        if(correoInicio.text.isNotEmpty() && contraInicio.text.isNotEmpty() ){
            correoMandar = correoInicio.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                correoInicio.text.toString(),
                contraInicio.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this.context,"Bienvenido", Toast.LENGTH_LONG).show()

                    val action = LoginFragmentDirections.actionLoginFragmentToComicsFragment()
                    view?.findNavController()?.navigate(action)

                }else{
                    Toast.makeText(this.context,"Usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(this.context,"No dejes campos en blanco", Toast.LENGTH_LONG).show()
        }

    }


}
