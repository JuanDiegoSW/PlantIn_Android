package com.miempresa.menudown.ui.map

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.libraries.places.api.Places
import com.miempresa.menudown.*
import com.miempresa.menudown.AuthActivity.Companion.usuario_correo
import kotlinx.android.synthetic.main.fragment_map.*
import org.json.JSONException


class MapFragment : Fragment(){



    private lateinit var dashboardViewModel: DashboardViewModel
    private var adapter: RecyclerView.Adapter<AdaptadorElementosUsuarios.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        Places.initialize(requireActivity(),getString(R.string.claveapi_place))

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        return root

    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        //listavehiculos.layoutManager = LinearLayoutManager(activity)
        var llenarLista = ArrayList<Usuarios>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/usuarios/"+ usuario_correo
            val stringRequest = JsonArrayRequest(url,
                { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val contraseña =
                                response.getJSONObject(i).getString("password")
                            val correo =
                                response.getJSONObject(i).getString("email")
                            val informacion =
                                response.getJSONObject(i).getString("jardin")


                            llenarLista.add(Usuarios(id, nombre, contraseña, correo, informacion))
                        }
                        adapter = AdaptadorElementosUsuarios(llenarLista)
                        Log.w("error",llenarLista.toString())
                        lista_plantas_usuarios.adapter = adapter

                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, {
                    Toast.makeText(
                        activity,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
        //cargarLista()
        //adapter = AdaptadorElementos(llenarLista)

    }








}

