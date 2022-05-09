package com.miempresa.menudown.ui.favorite

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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.miempresa.menudown.*
import com.miempresa.menudown.ui.search.NotificationsViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.json.JSONException

class FavoriteFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    //var llenarLista = ArrayList<Elementos>()
    private var adapter: RecyclerView.Adapter<AdaptadorElementos.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<AdaptadorElementosTiendas.ViewHolder>? = null
    private val Plaza = LatLng(-16.3988031,-71.5374435)
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_Favoritos.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        var llenarLista = ArrayList<Elementos>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/plantas"
            val stringRequest = JsonArrayRequest(url,
                { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val rfc =
                                response.getJSONObject(i).getString("rfc")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val informacion =
                                response.getJSONObject(i).getString("informacion")
                            val nombre_c =
                                response.getJSONObject(i).getString("nombre_cientifico")
                            val imagen =
                                response.getJSONObject(i).getString("img")
                            llenarLista.add(Elementos(id, rfc, nombre, informacion, nombre_c, imagen))
                        }
                        adapter = AdaptadorElementos(llenarLista)
                        rv_Favoritos.adapter = adapter

                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity,
                            "Error al obtener los datos de las plantas",
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

            rv_tiendas.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            //listavehiculos.adapter = AdaptadorElementos()
            var llenarListaTiendas = ArrayList<ElementosTienda>()
            AsyncTask.execute {
                val queue = Volley.newRequestQueue(activity)
                val url = getString(R.string.urlAPI) + "/tiendas"
                val stringRequest = JsonArrayRequest(url,
                    Response.Listener { response ->
                        try {
                            for (i in 0 until response.length()) {
                                val id =
                                    response.getJSONObject(i).getString("id")
                                val nombre =
                                    response.getJSONObject(i).getString("nombre")
                                val imagen =
                                    response.getJSONObject(i).getString("img")
                                llenarListaTiendas.add(ElementosTienda(id, nombre, imagen))
                            }
                            adapter2 = AdaptadorElementosTiendas(llenarListaTiendas)
                            rv_tiendas.adapter = adapter2

                        } catch (e: JSONException) {
                            Toast.makeText(
                                activity,
                                "Error al obtener los datos de las tiendas",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(
                            activity,
                            "Verifique que esta conectado a internet",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                queue.add(stringRequest)
            }
        }

}

    
