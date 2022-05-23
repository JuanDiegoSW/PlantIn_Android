package com.miempresa.menudown


import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_take_photo.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.util.*

private val GALLERY_REQUEST_CODE = 200
var img_uri : Uri? = null
var encodedimage: String= ""
class TakePhoto : AppCompatActivity() {
    //private lateinit var  binding: ActivityTakePhotoBinding
    var postURL: String = "https://my-api.plantnet.org/v2/identify/all?api-key=2b10dKoOhZxppgzLAck2k9JFzu"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityTakePhotoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_take_photo)
        //setContentView(binding.root)
        //val view = binding.root
        //setContentView(view)
        img1.setOnClickListener {
            //openCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                it.resolveActivity(packageManager).also { component ->
                    //val photoFile =
                    createPhotoFile()
                    val photoUri:Uri = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID + ".fileprovider",file)
                    it.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                }
            }
            openCamera.launch(intent)//Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        SavePhoto.setOnClickListener{
            savetogallery()
        }
        sendphoto.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        val queue = Volley.newRequestQueue(this)

        val request: StringRequest = object : StringRequest(

            Method.POST, postURL,
            Response.Listener { response ->
                Toast.makeText(this,"FileUploaded Successfully",Toast.LENGTH_SHORT).show();
            }, Response.ErrorListener { error -> // método para manejar errores.
                Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show();
            }) {

            override fun getBodyContentType(): String {

                return "multipart/form-data"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["images"] = encodedimage
                val json = JSONObject(params2 as Map<*, *>).toString()
                print(json)

                //return JSONObject(params2 as Map<*, *>).toString().toByteArray()
                return params2.toString().toByteArray()
            }


        }
        queue!!.add(request!!)

    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun sendAPI() {
        //Toast.makeText(this,file.toString(),Toast.LENGTH_SHORT).show()
        img_uri?.let { UploadUtility(this).uploadFile(it) }

    }
    val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if( result.resultCode == RESULT_OK){
                val data = result.data!!
                //val bitmap = data.extras!!.get("data") as Bitmap
                val bitmap = getBitmap()
                img1.setImageBitmap(bitmap)
            }
        }
    private lateinit var file : File
    private fun createPhotoFile(){
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_${System.currentTimeMillis()}",".jpg",dir)
        //return file
    }
    private fun savetogallery() {
        val content = createContent()
        val uri = save(content)
        clearContents(content,uri)
        Toast.makeText(this,"Imagen Guardada en la Galeria",Toast.LENGTH_SHORT).show()
    }

    private fun clearContents(content: ContentValues, uri: Uri) {
        content.clear()
        content.put(MediaStore.MediaColumns.IS_PENDING,0)
        contentResolver.update(uri,content,null,null)
    }

    private fun save(content: ContentValues): Uri {
        var outputStream: OutputStream?
        var uri:Uri?

        application.contentResolver.also { resolver ->
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,content)
            img_uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,content)
            outputStream = resolver.openOutputStream(uri!!)
        }
        outputStream.use {output ->
            getBitmap().compress(Bitmap.CompressFormat.JPEG,100,output)
        }
        return uri!!
    }
    private fun getBitmap():Bitmap{
        return BitmapFactory.decodeFile(file.toString())
    }


    private fun createContent(): ContentValues {
        val FileName = file.name
        val FileType = "images/jpg"
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,FileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE,FileType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.IS_PENDING,1)
        }
    }

    private fun encodebitmap(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteofimages = byteArrayOutputStream.toByteArray()
        encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT)
    }




}
