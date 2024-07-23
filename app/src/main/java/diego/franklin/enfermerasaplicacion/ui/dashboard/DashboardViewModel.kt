package diego.franklin.enfermerasaplicacion.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ingrese los datos del paciente"
    }
    val text: LiveData<String> = _text
    
}