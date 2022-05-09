package com.miempresa.menudown

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class AdaptadorElementosTiendas(val ListaElementosTienda: ArrayList<ElementosTienda>): RecyclerView.Adapter<AdaptadorElementosTiendas.ViewHolder>() {

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
        return ListaElementosTienda.size;
    }
    class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView) {
        val fImagen = itemView.findViewById<ImageView>(R.id.tienda_image);
        val fTitle = itemView.findViewById<TextView>(R.id.tienda_title)
        //val fConductor = itemView.findViewById<TextView>(R.id.item_conductor)
        //val fModelo = itemView.findViewById<TextView>(R.id.item_modelo)

        //set the onclick listener for the singlt list item
    }
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder?.fImagen?.setImageBitmap(obtener_imagen(ListaElementosTienda[position].img))
        holder?.fTitle?.text=ListaElementosTienda[position].nombre
        //holder?.fConductor?.text=ListaElementos[position].driver_name
        //holder?.fModelo?.text=ListaElementos[position].model

    /*
        var id = ListaElementos[position].id
        var placa = ListaElementos[position].number
        var modelo = ListaElementos[position].model
        var capacidad = ListaElementos[position].capacity
        var fecha_cad = ListaElementos[position].insurance_renewal_date
        var imagen = ListaElementos[position].photo */

        //holder.itemView.setOnClickListener(){
        /*
        holder.itemView.setOnClickListener(){
            val llamaractividad = Intent(holder.itemView.context,moreInfo::class.java)
            llamaractividad.putExtra("id",id.toString())
            llamaractividad.putExtra("placa",placa)
            llamaractividad.putExtra("modelo",modelo)
            llamaractividad.putExtra("capacidad",capacidad.toString())
            llamaractividad.putExtra("fecha",fecha_cad.toString())
            llamaractividad.putExtra("image", imagen)
            holder.itemView.context.startActivity(llamaractividad)
        }*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslistatiendas, parent, false);
        return ViewHolder(v);
    }
}
