package diego.franklin.enfermerasaplicacion.ui.dashboard

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val btnAgregarPaciente = root.findViewById<EditText>(R.id.btnAgregarPaciente)
        val rcvPacientes = root.findViewById<RecyclerView>(R.id.rcvPacientes)

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
                val objConexion = ClaseConexion().cadenaConexion()

                val agregarPaciente = objConexion?.prepareStatement("insert into (UUID_Pacientes, Nombres, Apellidos, Edad, Enfermedad, Num_Habitacion, Num_Cama, Medicina_Asignada, Fecha_Ingreso, Hora_Aplicacion_Med) values (?,?,?,?,?,?,?,?,?,?)")
                agregarPaciente?.setString(1, txtNombres.text.toString())
                agregarPaciente?.setString(2, txtApellidos.text.toString())
                agregarPaciente?.setString(3, txtEdad.text.toString())
                agregarPaciente?.setString(4, txtEnfermedad.text.toString())
                agregarPaciente?.setString(5, txtNum_Habitacion.text.toString())
                agregarPaciente?.setString(6, txtNum_Cama.text.toString())
                agregarPaciente?.setString(7, txtMedicamentosAsignados.text.toString())
                agregarPaciente?.setString(8, txtFechaIngreso.text.toString())
                agregarPaciente?.setString(9, txtHoraApliocacionMedicamentos.text.toString())

                val nuevoPaciente = obtenerDatos()
                withContext(Dispatchers.Main) {
                    (rcvPacientes.adapter as? Adaptador)?.actualizarLista(nuevoPaciente)
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