package com.miempresa.menudown
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_data_garden_user.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class DataGardenUser : AppCompatActivity() {
    var idPlant:String = ""

    private val REQUEST_GET_ACCOUNT = 112
    private val PERMISSION_REQUEST_CODE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_garden_user)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val image_path = intent.getStringExtra("img")
            nombre_plantv1.setText(bundle.getString("nombre").toString())
            descp_plant.setText(bundle.getString("informacion").toString())
            //print(correo)
            idPlant = bundle.getString("id").toString()
            img_main_01?.setImageBitmap(obtener_imagen(image_path.toString()))
        }


        del_plant.setOnClickListener(){
            deleteDataUsingVolley(idPlant)
        }

    }
    private fun deleteDataUsingVolley(idPlant:String) {
        val bundle :Bundle ?=intent.extras
        var url = getString(R.string.urlAPI) + "usuarios/"+ AuthActivity.usuario_correo + "/" + idPlant
        // creando una nueva variable para nuestra cola de solicitudes
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(

            Method.DELETE, url,
            Response.Listener { response ->
                Toast.makeText(applicationContext, "Planta Eliminada", Toast.LENGTH_SHORT).show()
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
                val json = JSONObject(params2 as Map<*, *>).toString()
                print(json)

                return JSONObject(params2 as Map<*, *>).toString().toByteArray()

            }
        }

        queue!!.add(request!!)
    }

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
}