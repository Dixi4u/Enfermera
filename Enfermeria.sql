create table Pacientes(
UUID_Pacientes varchar2(50),
Nombres varchar2(50) not null,
Apellidos varchar2(50) not null,
Edad Int not null,
Enfermedad varchar2(50) not null,
Num_Habitacion int not null,
Num_Cama int not null,
Medicina_Asignada varchar2(50) not null,
Fecha_Ingreso varchar2(50) not null,
Hora_Aplicacion_Med varchar2(50) not null
);