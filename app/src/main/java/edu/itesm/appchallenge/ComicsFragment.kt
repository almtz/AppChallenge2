package edu.itesm.appchallenge

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONException
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComicsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var comicList = mutableListOf<Comics>()

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
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    //Populate recylcer view items
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Send data to the adapter right here!!!!!!
        recycler_comics.apply {
            layoutManager = GridLayoutManager(activity, 2)

            val puKey = "0f11a0223b1fa7cb14519289f853b5a4"
            val prKey = "db1bf695c4924b7b17ce090048c44cc4ecf00c30"
            val queue = Volley.newRequestQueue(activity)
            val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val hash = md5("$timestamp$prKey$puKey")
            val url = "https://gateway.marvel.com/v1/public/comics?format=comic&formatType=comic&noVariants=true&limit=20&ts=$timestamp&apikey=$puKey&hash=$hash"

            val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    val data = response.getJSONObject("data").getJSONArray("results")
                    for (i in 0 until data.length()) {
                        val comic = data.getJSONObject(i)
                        val title = comic.getString("title")
                        val description = comic.getString("description")
                        val images = comic.getJSONArray("images")
                        var path = ""

                        for (j in 0 until images.length()) {
                            val image = images.getJSONObject(j)
                            path = image.getString("path")
                        }

                        comicList.add(Comics(title, description, path))
                    }
                    adapter = ComicsAdapter(comicList, context)
                } catch (e : Exception ) {
                    e.printStackTrace();
                }
            }, {error ->
                Log.e(TAG, error.toString())
            })
            queue.add(request)
        }

    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ComicsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComicsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}