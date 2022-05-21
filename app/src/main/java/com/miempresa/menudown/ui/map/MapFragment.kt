package com.miempresa.menudown.ui.map

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.miempresa.menudown.*
import com.miempresa.menudown.AuthActivity.Companion.usuario_correo
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONException


class MapFragment : Fragment(){



    private lateinit var dashboardViewModel: DashboardViewModel
    private var adapter: RecyclerView.Adapter<AdapterPlantas.ViewHolder>? = null
    //private var adapter: RecyclerView.Adapter<AdaptadorElementosPlantas.ViewHolder>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_map, container, false)
        //Places.initialize(requireActivity(),getString(R.string.claveapi_place))

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        //val root = inflater.inflate(R.layout.fragment_map, container, false)
        //return root

    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        listplants.layoutManager = LinearLayoutManager(activity)
        var llenarLista = ArrayList<Elementos>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "usuarios/"+ usuario_correo
            val stringRequest = JsonArrayRequest(url,
                { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("_id")
                            val rfc =
                                response.getJSONObject(i).getString("rfc")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val informacion =
                                response.getJSONObject(i).getString("informacion")
                            val imagen =
                                response.getJSONObject(i).getString("img")
                            llenarLista.add(Elementos(id, rfc, nombre, informacion, imagen))
                        }
                        adapter = AdapterPlantas(llenarLista)
                        //adapter1 = AdapterPlantas(llenarLista)
                        //listplants.adapter = adapter
                        listplants?.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this.context,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, {
                    Toast.makeText(
                        this.context,
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

