package com.miempresa.menudown

import com.google.gson.JsonArray
import org.json.JSONArray


//data class Elementos(val imagen:String, val placa:String, val driver_name:String, val status:String, val modelo:String)
data class Usuarios(
        val id: String,
        val nombre: String,
        val contraseña: String,
        val correo: String,
        val jardin:String
        )



