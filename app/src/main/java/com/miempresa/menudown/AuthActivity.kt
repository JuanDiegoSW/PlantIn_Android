package com.miempresa.menudown

//import com.example.chapatucombi.R.style.AppTheme
//import com.example.chapatucombi.R.style.AppCompat
//import com.miempresa.menudown.R.style.AppCompat
//import com.google.firebase.auth.FirebaseAuth
//import com.example.chapatucombi.ui.home.HomeFragment

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_auth.*
import org.json.JSONObject


class AuthActivity : AppCompatActivity(){

    companion object {
        var usuario_correo = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //Thread.sleep(2000)
        //setTheme(R.style.AppCompat)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        title="Login"
        //setup()

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        LogginButton.setOnClickListener() {
            //if (EmailEditText.text.isNotEmpty() && PasswordEditText.text.isNotEmpty()) {
            val email =  EmailEditText.text.toString()
            val password = PasswordEditText.text.toString()

            loginRequest(email,password)
            /*
            val queue = Volley.newRequestQueue(this)
            //var url = getString(R.string.urlAPI) + "/usuarios?"
            //url = url + "email="+ email + "&password=" + password
            var url = getString(R.string.urlAPI) + "/login"
            val params = HashMap<Any, Any>()
            params.put("correo", email);
            params.put("password", password);
            url = url + "correo=" + email + "&password=" + password


            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        val llamaractividad = Intent(applicationContext, MainActivity::class.java)
                        //val activityMoreInfo = Intent(applicationContext, moreInfo::class.java)
                        //val intent = Intent(this,moreInfo::class.java)
                        //activityMoreInfo.putExtra("correo",email)
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")

                            usuario_correo = id
                        }
                        startActivity(llamaractividad)
                        finish()
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error en las credenciales proporcionadas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet",
                        Toast.LENGTH_SHORT
                    ).show()
                })*/
            //queue.add(stringRequest)
        }

        sup.setOnClickListener(){
            val it = Intent(this, SignUpActivitty::class.java)

            startActivity(it)
            finish()
        }




    }

    private fun loginRequest(email: String, password: String) {
        var url:String = getString(R.string.urlAPI) + "/login"


        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        /*
        var stringRequest:StringRequest =
            object : StringRequest(Request.Method.POST,url,Response.Listener { response ->
                Toast.makeText(this,response.trim(),Toast.LENGTH_LONG).show()
                if(response.getBolean)){
                    Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this,"Check UserNmae or password",Toast.LENGTH_LONG).show()
                }

        },Response.ErrorListener { error ->
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params.put("correo", email)
                params.put("password", password)
                return params
            }
        }
        requestQueue.add(stringRequest)
         */
        val params = HashMap<String, String>()
        params.put("correo", email)
        params.put("password", password)
        val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(Method.POST,
            url, JSONObject(params as Map<*, *>),
            Response.Listener { response ->
                val llamaractividad = Intent(applicationContext, MainActivity::class.java)
                usuario_correo = email
                startActivity(llamaractividad)
                finish()
                //msgResponse.setText(response.toString())
                //com.sun.deploy.ui.UIFactory.hideProgressDialog()
            }, Response.ErrorListener { error ->
                Toast.makeText(this,"check your username or password",Toast.LENGTH_LONG).show()
            }) {
            /**
             * Passing some request headers
             */
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }


        requestQueue.add(jsonObjReq)
    }



}