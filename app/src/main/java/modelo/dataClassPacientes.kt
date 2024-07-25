package modelo

data class dataClassPacientes(
    val UUID_Pacientes: String,
    var Nombres: String,
    val Apellidos: String,
    val Edad: String,
    val Enfermedad: String,
    val Num_Habitacion: String,
    val Num_Cama: String,
    val Medicina_Asignada: String,
    val Fecha_Ingreso: String,
    val Hora_Aplicacion_Med: String
)
