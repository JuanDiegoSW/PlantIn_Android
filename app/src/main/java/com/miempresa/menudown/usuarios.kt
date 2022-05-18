package com.miempresa.menudown

import com.google.gson.JsonArray
import org.json.JSONArray
import java.sql.Array


//data class Elementos(val imagen:String, val placa:String, val driver_name:String, val status:String, val modelo:String)
data class Usuarios(
        val id: String,
        val nombre: String,
        val contraseña: String,
        val correo: String,
        val jardin:ArrayList<Elementos>
        )



