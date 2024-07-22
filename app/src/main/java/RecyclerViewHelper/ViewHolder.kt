package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import diego.franklin.enfermerasaplicacion.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val txtTicketsCard: TextView = view.findViewById(R.id.txtTicketsCard)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar: ImageView = view.findViewById(R.id.imgEliminar)
}
