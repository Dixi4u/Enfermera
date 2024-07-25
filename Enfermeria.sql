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

select * from Pacientes

UPDATE Pacientes SET Nombres = 's', Num_Habitacion = 2, Num_Cama = 2, Medicina_Asignada = 's', Hora_Aplicacion_Med = 's' WHERE UUID_Paciente = 'd46ae2cc-0678-41c6-b6e9-571a776557c1';

update Pacientes set Nombres = ?, Num_Habitacion = ?, Num_Cama = ?, Medicina_Asignada = ?, Hora_Aplicacion_Med = ? where UUID_Paciente = ?