package diego.franklin.enfermerasaplicacion.ui.dashboard

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import diego.franklin.enfermerasaplicacion.R
import diego.franklin.enfermerasaplicacion.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassPacientes
import java.util.UUID

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvMensaje
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val txtNombres = root.findViewById<EditText>(R.id.txtNombres)
        val txtApellidos = root.findViewById<EditText>(R.id.txtApellidos)
        val txtEdad = root.findViewById<EditText>(R.id.txtEdad)
        val txtEnfermedad = root.findViewById<EditText>(R.id.txtEnfermedad)
        val txtNum_Habitacion = root.findViewById<EditText>(R.id.txtNumHabitacion)
        val txtNum_Cama = root.findViewById<EditText>(R.id.txtNumCama)
        val txtMedicamentosAsignados = root.findViewById<EditText>(R.id.txtMedicamentosAsignados)
        val txtFechaIngreso = root.findViewById<EditText>(R.id.txtFechaIngreso)
        val txtHoraApliocacionMedicamentos = root.findViewById<EditText>(R.id.txtHoraApliocacionMedicamentos)
        val btnAgregarPaciente = root.findViewById<Button>(R.id.btnAgregarPaciente)

        fun obtenerDatos(): List<dataClassPacientes> {
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from Pacientes")

            val Paciente = mutableListOf<dataClassPacientes>()

            while (resulSet?.next()!!) {
                val UUID_Pacientes = resulSet.getString("UUID_Pacientes")
                val Nombres = resulSet.getString("Nombres")
                val Apellidos = resulSet.getString("Apellidos")
                val Edad = resulSet.getString("Edad")
                val Enfermedad = resulSet.getString("Enfermedad")
                val Num_Habitacion = resulSet.getString("Num_Habitacion")
                val Num_Cama = resulSet.getString("Num_Cama")
                val Medicina_Asignada = resulSet.getString("Medicina_Asignada")
                val Fecha_Ingreso = resulSet.getString("Fecha_Ingreso")
                val Hora_Aplicacion_Med = resulSet.getString("Hora_Aplicacion_Med")

                val Pacientess = dataClassPacientes(UUID_Pacientes, Nombres, Apellidos, Edad, Enfermedad, Num_Habitacion, Num_Cama, Medicina_Asignada, Fecha_Ingreso, Hora_Aplicacion_Med)
                Paciente.add(Pacientess)

            }
            return Paciente
        }

        btnAgregarPaciente.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val agregarPaciente = objConexion?.prepareStatement(
                        "INSERT INTO Pacientes (UUID_Pacientes, Nombres, Apellidos, Edad, Enfermedad, Num_Habitacion, Num_Cama, Medicina_Asignada, Fecha_Ingreso, Hora_Aplicacion_Med) VALUES (?,?,?,?,?,?,?,?,?,?)"
                    )
                    agregarPaciente?.setString(1, UUID.randomUUID().toString())
                    agregarPaciente?.setString(2, txtNombres.text.toString())
                    agregarPaciente?.setInt(3, txtApellidos.text.toString().toInt())
                    agregarPaciente?.setString(4, txtEdad.text.toString())
                    agregarPaciente?.setString(5, txtEnfermedad.text.toString())
                    agregarPaciente?.setInt(6, txtNum_Habitacion.text.toString().toInt())
                    agregarPaciente?.setInt(7, txtNum_Cama.text.toString().toInt())
                    agregarPaciente?.setString(8, txtMedicamentosAsignados.text.toString())
                    agregarPaciente?.setString(9, txtFechaIngreso.text.toString())
                    agregarPaciente?.setString(10, txtHoraApliocacionMedicamentos.text.toString())
                    agregarPaciente?.executeUpdate()
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        // Muestra un mensaje de error al usuario
                    }
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}