package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try{
            val ip = "jdbc:oracle:thin:@192.168.1.107:1521:xe"
            val usuario = "system"
            val contrasena = "2yb98R6L"

            val conexion = DriverManager.getConnection(ip,usuario,contrasena)
            return conexion
        }catch (e:Exception){
            println("Este es el error: $e")
            return null
        }
    }
}