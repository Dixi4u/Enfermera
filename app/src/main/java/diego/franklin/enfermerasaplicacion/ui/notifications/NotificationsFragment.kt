package diego.franklin.enfermerasaplicacion.ui.notifications

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diego.franklin.enfermerasaplicacion.R
import diego.franklin.enfermerasaplicacion.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassPacientes

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rcvPacientes = root.findViewById<RecyclerView>(R.id.rcvPacientes)

        rcvPacientes.layoutManager = LinearLayoutManager(context)

        fun obtenerPacientes(): List<dataClassPacientes>{

            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resultset = statement?.executeQuery("SELECT * FROM Pacientes")!!

            val listaPacientes = mutableListOf<dataClassPacientes>()

            while (resultset.next()) {
                val UUID_Pacientes = resultset.getString("UUID_Pacientes")
                val Nombres = resultset.getString("Nombres")
                val Apellidos = resultset.getString("Apellidos")
                val Edad = resultset.getInt("Edad")
                val Enfermedad = resultset.getString("Enfermedad")
                val Num_Habitacion = resultset.getString("Num_Habitacion")
                val Num_Cama = resultset.getString("Num_Cama")
                val Medicina_Asignada = resultset.getString("Medicina_Asignada")
                val Fecha_Ingreso = resultset.getString("Fecha_Ingreso")
                val Hora_Aplicacion_Med = resultset.getString("Hora_Aplicacion_Med")

                val valoresJuntos = dataClassPacientes(
                    UUID_Pacientes,
                    Nombres,
                    Apellidos,
                    Edad.toString(),
                    Enfermedad,
                    Num_Habitacion,
                    Num_Cama,
                    Medicina_Asignada,
                    Fecha_Ingreso,
                    Hora_Aplicacion_Med
                )

                listaPacientes.add(valoresJuntos)
            }
            return listaPacientes

            }

        CoroutineScope(Dispatchers.IO).launch {
            val pacientesDB = obtenerPacientes()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(pacientesDB)
                rcvPacientes.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}