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

    fun actualicePantalla(uuid: String, nuevoNombre: String) {
        val index = Datos.indexOfFirst { it.UUID_Pacientes == uuid }
        Datos[index].Nombres = nuevoNombre
        notifyDataSetChanged()
    }


    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(titulo: String, posicion: Int) {
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO) {
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteMascota =
                objConexion?.prepareStatement("delete from Ticket where Titulo = ?")!!
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
    fun actualizarDatos(uuid: String, nuevoNombre: String, numHabitacion: String, numCama: String, medicinaAsignada: String, horaAplicacionMed: String) {
        GlobalScope.launch(Dispatchers.IO) {
            //1- Creo un objeto de la clase de conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo una variable que contenga un PrepareStatement
            val updateTicket = objConexion?.prepareStatement(
                "UPDATE Pacientes SET Nombres = ?, Num_Habitacion = ?, Num_Cama = ?, Medicina_Asignada = ?, Hora_Aplicacion_Med = ? WHERE UUID_Pacientes = ?"
            )
            updateTicket?.setString(1, nuevoNombre)
            updateTicket?.setString(2, numHabitacion)
            updateTicket?.setString(3, numCama)
            updateTicket?.setString(4, medicinaAsignada)
            updateTicket?.setString(5, horaAplicacionMed)
            updateTicket?.setString(6, uuid)
            updateTicket?.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantalla(uuid, nuevoNombre)
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
        holder.txthombreCard.text = item.Nombres
        //todo: clic al icono de eliminar
        holder.imgBorrar.setOnClickListener {

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea Borrar su paciente?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(item.Nombres, position)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }


        //Todo: icono de editar
        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Quiere actualizar los datos del paciente?")

            // Inflar el layout personalizado con múltiples EditText
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.dialog_editar_paciente, null)
            builder.setView(view)

            // Obtener referencias a los EditText en el layout
            val cuadroTextoNombre = view.findViewById<EditText>(R.id.cuadroTextoNombre)
            val cuadroTextoHabitacion = view.findViewById<EditText>(R.id.cuadroTextoHabitacion)
            val cuadroTextoCama = view.findViewById<EditText>(R.id.cuadroTextoCama)
            val cuadroTextoMedicina = view.findViewById<EditText>(R.id.cuadroTextoMedicina)
            val cuadroTextoHora = view.findViewById<EditText>(R.id.cuadroTextoHora)

            // Configurar los hints con los datos actuales
            cuadroTextoNombre.setHint(item.Nombres)
            cuadroTextoHabitacion.setHint(item.Num_Habitacion)
            cuadroTextoCama.setHint(item.Num_Cama)
            cuadroTextoMedicina.setHint(item.Medicina_Asignada)
            cuadroTextoHora.setHint(item.Hora_Aplicacion_Med)

            // Botones del diálogo
            builder.setPositiveButton("Actualizar") { _, _ ->
                actualizarDatos(
                    item.UUID_Pacientes,
                    cuadroTextoNombre.text.toString(),
                    cuadroTextoHabitacion.text.toString(),
                    cuadroTextoCama.text.toString(),
                    cuadroTextoMedicina.text.toString(),
                    cuadroTextoHora.text.toString()
                )
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}

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



