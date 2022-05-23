package com.miempresa.menudown
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info_tienda.*
import java.net.URL
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection


class infoTienda : AppCompatActivity() {

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
        setContentView(R.layout.activity_info_tienda)

        MyToolbar().show(this, "Informacion Tienda", true)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){

            val image_path1 = intent.getStringExtra("img1")
            val image_path2 = intent.getStringExtra("img2")

            nombreTienda.setText(bundle.getString("nombre").toString())

            tiendaImagen1?.setImageBitmap(obtener_imagen(image_path1.toString()))
            tiendaImagen2?.setImageBitmap(obtener_imagen(image_path2.toString()))
            descripcionTienda.setText(bundle.getString("descripcion").toString())

        }
    }


}

// Class to download an image from url and display it into an image view
private class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlOfImage = urls[0]
        return try {
            val inputStream = URL(urlOfImage).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) { // Catch the download exception
            e.printStackTrace()
            null
        }
    }
    override fun onPostExecute(result: Bitmap?) {
        if(result!=null){
            // Display the downloaded image into image view
            Toast.makeText(imageView.context,"download success",Toast.LENGTH_SHORT).show()
            imageView.setImageBitmap(result)
        }else{
            Toast.makeText(imageView.context,"Error downloading",Toast.LENGTH_SHORT).show()
        }
    }

}