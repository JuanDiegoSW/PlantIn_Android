package com.miempresa.menudown

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.miempresa.menudown.AuthActivity.Companion.usuario_correo
import com.miempresa.menudown.ui.search.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_more_info.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import kotlin.math.log


class moreInfo : AppCompatActivity(){
    private lateinit var mMap: GoogleMap

    private lateinit var notificationsViewModel: NotificationsViewModel
    var llenarLista = ArrayList<Elementos>()
    private var adapter: RecyclerView.Adapter<AdaptadorElementos.ViewHolder>? = null

    private val Plaza = LatLng(-16.3988031,-71.5374435)

    private fun obtener_imagen(url: String): Bitmap? {
        var bm: Bitmap? = null
        try {
            val _url = URL(url)
            val con: URLConnection = _url.openConnection()
            con.connect()
            val ist: InputStream = con.getInputStream()
            val bis = BufferedInputStream(ist)
            bm = BitmapFactory.decodeStream(bis)
            bis.close()
            ist.close()
        } catch (e: IOException) {
        }
        return bm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        //recepcionando los datos de un vehiculo


        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val image_path = intent.getStringExtra("img")
            nombre_especie.setText(bundle.getString("nombre").toString())
            descripcion_planta.setText(bundle.getString("informacion").toString())
            //descripcion_planta.setText(correo)
            //print(correo)
            img_view_moreinfo1?.setImageBitmap(obtener_imagen(image_path.toString()))
            img_view_moreinfo2?.setImageBitmap(obtener_imagen(image_path.toString()))
            img_view_moreinfo3?.setImageBitmap(obtener_imagen(image_path.toString()))
        }
        boton1.setOnClickListener(){
            postDataUsingVolley()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?


    }

    private fun postDataUsingVolley() {
        val bundle :Bundle ?=intent.extras
        var url = getString(R.string.urlAPI) + "/usuarios/"+usuario_correo
        // creando una nueva variable para nuestra cola de solicitudes
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(

            Method.PUT, url,
            Response.Listener { response ->
                Toast.makeText(applicationContext, "Exito al añadir a su lista", Toast.LENGTH_SHORT).show()
                //val it = Intent(this, AuthActivity::class.java)
                //startActivity(it)
            }, Response.ErrorListener { error -> // método para manejar errores.
                Toast.makeText(
                    applicationContext,"Compuebe si tiene internet",
                    Toast.LENGTH_LONG
                ).show()
            }) {

            override fun getBodyContentType(): String {

                return "application/json"}

            @RequiresApi(Build.VERSION_CODES.KITKAT)
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {

                val rfc = intent.getStringExtra(("rfc")).toString()
                val nombre = intent.getStringExtra(("nombre")).toString()
                val informacion = intent.getStringExtra(("informacion")).toString()
                val imagen = intent.getStringExtra(("img")).toString()
                val params2 = HashMap<Any, Any>()

                params2["rfc"] = rfc
                params2["nombre"] = nombre
                params2["informacion"] = informacion
                params2["img"] = imagen
                val json =JSONObject(params2 as Map<*, *>).toString()
                print(json)

                return JSONObject(params2 as Map<*, *>).toString().toByteArray()

                }
            }

        queue!!.add(request!!)
    }

}




private fun SupportMapFragment?.getMapAsync(routes: routes) {

}