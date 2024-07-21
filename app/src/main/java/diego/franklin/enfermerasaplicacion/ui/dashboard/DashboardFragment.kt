package diego.franklin.enfermerasaplicacion.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import diego.franklin.enfermerasaplicacion.R
import diego.franklin.enfermerasaplicacion.databinding.FragmentDashboardBinding

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

        val view = FragmentDashboardBinding.inflate(inflater, container, false)

        val textView: TextView = binding.tvMensaje
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

            val txtNombres = view.findViewById<EditText>(R.id.txtNombres)
        }
        return view.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}