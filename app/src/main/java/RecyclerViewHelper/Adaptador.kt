package RecyclerViewHelper

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import diego.franklin.enfermerasaplicacion.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassPacientes


class Adaptador(var Datos: List<dataClassPacientes>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista: List<dataClassPacientes>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun actualicePantalla(uuid: String, nuevoNombre: String){
        val index = Datos.indexOfFirst { it.UUID_Paciente == uuid }
        Datos[index].Nombres = nuevoNombre
        notifyDataSetChanged()
    }


    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(titulo: String, posicion: Int){
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteMascota = objConexion?.prepareStatement("delete from Ticket where Titulo = ?")!!
            deleteMascota.setString(1, titulo)
            deleteMascota.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        // Notificar al adaptador sobre los cambios
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }


    //////////////////////TODO: Editar datos
    fun actualizarDato(nuevoTitulo: String, UUID_Ticket: String){
        GlobalScope.launch(Dispatchers.IO){

            //1- Creo un objeto de la clase de conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- creo una variable que contenga un PrepareStatement
            val updateTicket = objConexion?.prepareStatement("update Ticket set Titulo = ? where UUID_ticket = ?")!!
            updateTicket.setString(1, nuevoTitulo)
            updateTicket.setString(2, UUID_Ticket.toString())
            updateTicket.executeUpdate()

            withContext(Dispatchers.Main){
                actualicePantalla(UUID_Ticket.toString(), nuevoTitulo)
            }

        }

    }


 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

       return ViewHolder(vista)
 }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]
        holder.activity_item_card.text = item.Nombres}}
//        //todo: clic al icono de eliminar
//        holder.imgBorrar.setOnClickListener {
//
//            //Creamos un Alert Dialog
//            val context = holder.itemView.context
//
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Eliminar")
//            builder.setMessage("¿Desea Borrar su Ticket?")
//
//            //Botones
//            builder.setPositiveButton("Si") { dialog, which ->
//                eliminarDatos(item.Titulo, position)
//            }
//
//            builder.setNegativeButton("No") { dialog, which ->
//                dialog.dismiss()
//            }
//
//            val dialog = builder.create()
//            dialog.show()
//
//        }
//
//        //Todo: icono de editar
//        holder.imgBorrar.setOnClickListener {
//
//            //Creamos un Alert Dialog
//            val context = holder.itemView.context
//
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Eliminar")
//            builder.setMessage("¿Quiere borrar el Ticket?")
//
//            //Botones
//            builder.setPositiveButton("Si") { dialog, which ->
//                eliminarDatos(item.Titulo, position)
//            }
//
//            builder.setNegativeButton("No"){dialog, which ->
//                dialog.dismiss()
//            }
//
//            val dialog = builder.create()
//            dialog.show()
//        }
//
//        //Todo: icono de editar
//        holder.imgEditar.setOnClickListener {
//
//            val context = holder.itemView.context
//
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Actualizar")
//            builder.setMessage("¿Quiere actualizar el Ticket?")
//
//            //Agregarle un cuadro de texto para
//            //que el usuario escriba el nuevo nombre
//            val cuadroTexto = EditText(context)
//            cuadroTexto.setHint(item.Titulo)
//            builder.setView(cuadroTexto)
//
//            //Botones
//            builder.setPositiveButton("Actualizar") { dialog, which ->
//                actualizarDato(cuadroTexto.text.toString(), item.uuid)
//            }
//
//            builder.setNegativeButton("Cancelar"){dialog, which ->
//                dialog.dismiss()
//            }
//
//            val dialog = builder.create()
//            dialog.show()
//        }

//        //Todo: Clic a la card completa
//        holder.itemView.setOnClickListener {
//            val context = holder.itemView.context
//
//            //Cambiar de pantalla a la pantalla de detalle
//            val pantallaDetalle = Intent(context, detalleTicket::class.java)
//            //enviar a la otra pantalla todos mis valores
//            pantallaDetalle.putExtra("UUID_Ticket", item.uuid)
//            pantallaDetalle.putExtra("Titulo", item.Titulo)
//            pantallaDetalle.putExtra("Descripcion", item.Descripcion)
//            pantallaDetalle.putExtra("Autor", item.Autor)
//            pantallaDetalle.putExtra("Email", item.Email)
//            pantallaDetalle.putExtra("Fecha_Creacion", item.Creacion)
//            pantallaDetalle.putExtra("Estado", item.Estado)
//            pantallaDetalle.putExtra("Fecha_FInalizacion", item.Finalizacion)
//
//            context.startActivity(pantallaDetalle)
//
//        }
//    }



