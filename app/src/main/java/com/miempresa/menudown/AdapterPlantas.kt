package com.miempresa.menudown

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class AdapterPlantas(val ListaElementos:ArrayList<Elementos>): RecyclerView.Adapter<AdapterPlantas.ViewHolder>() {

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

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView) {
        val fImagen = itemView.findViewById<ImageView>(R.id.plant_image);
        val fTitle = itemView.findViewById<TextView>(R.id.plant_title)
        //val fname_c = itemView.findViewById<TextView>(R.id.plant_c_name)
        //val fConductor = itemView.findViewById<TextView>(R.id.item_conductor)
        //val fModelo = itemView.findViewById<TextView>(R.id.item_modelo)

        //set the onclick listener for the singlt list item
    }
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        //holder?.fImagen?.setImageBitmap(obtener_imagen(ListaElementos[position].img))
        holder?.fTitle?.text=ListaElementos[position].nombre

        /*
        var id = ListaElementos[position].id
        var nombre = ListaElementos[position].nombre
        var informacion = ListaElementos[position].informacion
        var rfc = ListaElementos[position].rfc
        var imagen = ListaElementos[position].img


        holder.itemView.setOnClickListener(){
            val llamaractividad = Intent(holder.itemView.context,moreInfo::class.java)
            llamaractividad.putExtra("id",id)
            llamaractividad.putExtra("nombre",nombre)
            llamaractividad.putExtra("informacion",informacion)
            llamaractividad.putExtra("rfc",rfc)
            llamaractividad.putExtra("img", imagen)
            holder.itemView.context.startActivity(llamaractividad)
        }
        */

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslistaplantasgeneral, parent, false);
        return ViewHolder(v);
    }
}
