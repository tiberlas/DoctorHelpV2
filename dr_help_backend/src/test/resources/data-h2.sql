--SQL skripta koja se pokrece sa Spring boot app i daje dummy podatke
-- enkripcija sa sajta: https://bcrypt-generator.com/

--password: sifra
--
truncate table centre_administrator;
truncate table clinic_administrator;
--truncate table clinic;
--truncate table patiens;
--truncate table nurse;
--truncate table procedures_type;
--truncate table doctors;
--truncate table operations;
--truncate table room;
--truncate table healthrecord;
--truncate table allergypojo;
--truncate table appointments;
--truncate table doctor_requested;
--truncate table examination_reportpojo;
--truncate table allergypojo;
--truncate table diagnosispojo;
--truncate table therapypojo;
--truncate table medicationpojo;
--truncate table perscriptionpojo;
--truncate table perscriptionpojo_medication_list;
--truncate table doctor_reviewpojo;
--truncate table clinic_rewiew;
--truncate table leave_requests;



insert into centre_administrator(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, must_change_password) 
	values(
		'Đura','Đurić', '$2y$10$.LtaQ8h1eF5Y9mz7cZwTqeXf0TyGRLbyOD27/eRb4N9WMuOZHwYMG', 'CENTRE_ADMINISTRATOR', 'glavni@sef',
		 '06555555', 'Serbia', 'Novi Sad', 'A dom', '2003-2-1'::timestamp, false
	);


insert into clinic(address, city, state, name, description) values('7A Bulevar despota Stefana', 'Novi Sad', 'Serbia', 'Klinika zdravog uma', 'Klinika je namenjena za kreativne opise. ');
insert into clinic(address, city, state, name, description) values('7 Bulevar despota Stefana', 'Novi Sad', 'Serbia', 'Arkham', 'Assylum for the criminally insane.');
insert into clinic(address, city, state, name, description) values('5A Bulevar despota Stefana', 'Novi Sad', 'Serbia', 'Princeton Plainsborrough general hospital', 'Free, publically open clinic.');
insert into clinic(address, city, state, name, description) values('2A Bulevar despota Stefana', 'Novi Sad', 'Serbia', 'Nasa Mala Klinika', 'Mali svet pun radosti.');
insert into clinic(address, city, state, name, description) values('2A Bulevar despota Stefana', 'Podgorica', 'Montenegro', 'Nasa Mala Klinika', 'Mali svet pun radosti.');

	 
--password: 1234
insert into clinic_administrator(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, must_change_password) 
	values(
		'Borislav','Borisavljević', '$2y$10$5ozpUNr/gCI4YGtih/eSiuWZ6C8L6FLlt4sGuJJbkbD0WxCQe3Mqe', 'CLINICAL_ADMINISTRATOR', 
		'admin@admin', '06555555', 'Serbia', 'Novi Sad', 'Dom Kulture', '2003-2-1'::timestamp, 1, false
	);

--password: maxBezbedno
insert into clinic_administrator (address, birthday, city, email, first_name, last_name, password, phone_number, status, state, clinic_id) 
	values (
		'Stevana Milovanova 6', '1967-05-17', 'Novi Sad', 'mikiveliki@yahoo.com', 'Milorad', 'Vucković', 
		'$2y$10$OjRFtBABOOg/9fL4iQHqjeJ/PGK/b0xJDW10/XwO8urdDbXhKJ7sm', '860415301', 'CLINICAL_ADMINISTRATOR', 'Serbia', '1'
	);
	

--password: whoppa42
insert into patiens (
address, birthday, city, email, first_name, insurance_number, is_activated, last_name, password, phone_number, status, state, health_record_id
) values (
	'Bajić i Vlahović soba 11', '1998-07-21', 'Novi Sad', 'happymeal@gmail.com', 'Tanja', 434, true, 'Blejić', 
	'$2y$10$ILLsTus2GDQ7735uE36xd.g89zdP.QXDqYTYSznl9XGZlQ5EQUFBy', '06216684654', 'PATIENT', 'Serbia', null
);
insert into patiens (
address, birthday, city, email, first_name, insurance_number, is_activated, last_name, password, phone_number, status, state, health_record_id
) values (
	'Grobljanska 5', '1983-11-12', 'Beograd', 'gmail@gmail.com', 'Borislav', 433, true, 'Rašeta', 
	'$2y$10$ILLsTus2GDQ7735uE36xd.g89zdP.QXDqYTYSznl9XGZlQ5EQUFBy', '0656152164', 'PATIENT', 'Serbia', null
);
insert into patiens (
address, birthday, city, email, first_name, insurance_number, is_activated, last_name, password, phone_number, status, state, health_record_id
) values (
	'Brace Ribnikar 16a', '1987-01-23', 'Novi Sad', 'digimon@gmail.com', 'Milivoje', 43223, true, 'Radulović', 
	'$2y$10$ILLsTus2GDQ7735uE36xd.g89zdP.QXDqYTYSznl9XGZlQ5EQUFBy', '0656152164', 'PATIENT', 'Serbia', null
);
--password: imejl
insert into patiens (
address, birthday, city, email, first_name, insurance_number, is_activated, last_name, password, phone_number, status, state, health_record_id
) values (
	'Stevana Milovanova 17', '1985-03-29', 'Novi Sad', 'enekadresa@gmail.com', 'Jovan', 123321, false, 'Matic', 
	'$2y$10$vjb/stdBU46vh74lsuHoWuIjYcDCwqpESS3I2ukf0C07p6AfNcvl2', '860484061105', 'PATIENT', 'Serbia', null
);

	
	
--password: 1234
insert into nurse(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Ana', 'Anica', '$2y$10$xF3sVXDDtuqCmpL2aI7pK.4/qJYA7r/vlmIIONs5XDfEwTqCLRIHe', 'NURSE', 'ana@gmail', '555555', 
	'Serbia', 'Novi Sad', 'Ulica 8', '2003-2-1'::timestamp, 1, false, 'NONE', 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE'
);
--password: 1234
insert into nurse(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Mila', 'Milić', '$2y$10$xF3sVXDDtuqCmpL2aI7pK.4/qJYA7r/vlmIIONs5XDfEwTqCLRIHe', 'NURSE', 'mila@gmail', '555555', 
	'Srbija', 'Novi Sad', 'Jovana Petrovica 9', '2003-2-1'::timestamp, 1, false, 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE', 'NONE'
);


insert into procedures_type(name, price, is_operation, duration, clinic_id, deleted) 
values('psiho analiza', 255, false, '01:00:00'::time, 1, false);
insert into procedures_type(name, price, is_operation, duration, clinic_id, deleted) 
values('opsti pregled', 25, false, '00:30:00'::time, 1, false);
insert into procedures_type(duration, is_operation, name, price, clinic_id, deleted) 
values ('01:00:00', false, 'Pregled opste prakse', 330, 3, false);
insert into procedures_type(duration, is_operation, name, price, clinic_id, deleted) 
values ('01:00:00', false, 'Pregled opste prakse', 350, 2, false);
insert into procedures_type(duration, is_operation, name, price, clinic_id, deleted) 
values ('01:15:00', false, 'Pregled opste prakse', 250, 1, false);
insert into procedures_type(duration, is_operation, name, price, clinic_id, deleted) 
values ('02:00:00', false, 'Pregled dermatologa', 630, 2, false);
insert into procedures_type(duration, is_operation, name, price, clinic_id, deleted) 
values ('06:00:00', true, 'Lobotomija', 2200, 1, false);
	
--password: doca 	
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Pera', 'Perić', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'pera@gmail', '555555', 
	'Serbia', 'Novi Sad', 'Pap Pavla 3', '2003-2-1'::timestamp, 1, 1, false, 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE', 'NONE'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Jovan', 'Milinković', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'j.milinkovic@gmail', '555556', 
	'Serbia', 'Novi Sad', 'Pere Milića 3', '2001-3-15'::timestamp, 1, 2, false, 'NONE', 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Đorđe', 'Bogdanović', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'djokica@gmail', '555557', 
	'Serbia', 'Novi Sad', 'Narodnih heroja 13', '1994-7-19'::timestamp, 1, 1, false, 'NONE', 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Relja', 'Đurić', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'rekulj@gmail', '555558', 
	'Serbia', 'Novi Sad', 'Gundulićeva 23', '1997-6-3'::timestamp, 2, 4, false, 'NONE', 'FIRST', 'SECOND', 'THIRD', 'NONE', 'NONE', 'NONE'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Dušan', 'Glamočanin', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'malimocha@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 3, 3, false, 'NONE', 'NONE', 'NONE', 'FIRST', 'SECOND', 'THIRD', 'NONE'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Predrag', 'Djordjevic', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'djpredrag@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 1, 7, false, 'NONE', 'NONE', 'FIRST', 'FIRST', 'NONE', 'THIRD', 'SECOND'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Milovan', 'Micic', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'mmica@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 1, 7, false, 'NONE', 'NONE', 'NONE', 'FIRST', 'SECOND', 'THIRD', 'SECOND'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Gabriel', 'Garic', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'ggarica@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 1, 7, false, 'NONE', 'NONE', 'NONE', 'SECOND', 'SECOND', 'THIRD', 'SECOND'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Danijela', 'Despotic', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'ddespot@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 1, 7, false, 'NONE', 'NONE', 'NONE', 'SECOND', 'SECOND', 'THIRD', 'SECOND'
);
insert into doctors(first_name, last_name, password, status, email, phone_number, state, city, address, birthday, clinic_id, procedure_type_id, deleted, monday, tuesday, wednesday, thursday, friday, saturday, sunday) 
values(
	'Ivan', 'Fish', '$2y$10$6NDf1Bm3cHFYdZEJwUE9MOrr6CZOSTqrvqvTTkXETVy18yr8eZuGe', 'DOCTOR', 'ifish@gmail', '555559', 
	'Serbia', 'Novi Sad', 'Kosovke Djeve 3', '1998-11-12'::timestamp, 1, 2, false, 'NONE', 'NONE', 'NONE', 'SECOND', 'SECOND', 'THIRD', 'SECOND'
);

insert into room(name, number, deleted, clinic_id, proceduras_types_id) 
	values('Psihoterapija', 25, false, 1, 1);
insert into room(name, number, deleted, clinic_id, proceduras_types_id) 
	values('Opšta A', 30, false, 1, 2);
insert into room(name, number, deleted, clinic_id, proceduras_types_id) 
	values('Opšta B', 31, false, 1, 2);
insert into room(name, number, deleted, clinic_id, proceduras_types_id) 
	values('OPERACIONA SALA', 101, false, 1, 7);
insert into room(name, number, deleted, clinic_id, proceduras_types_id) 
	values('OPERACIONA SALA', 102, false, 1, 7);

	
	--operacije 
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-19 17:00', 1, 1, 6, 7, 8, null, 7, 'APPROVED', false, 0);
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-19 12:00', 1, 1, 6, 7, 8, null, 7, 'APPROVED', true, 0);
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-18 17:00', 1, 1, 6, 7, 8, null, 7, 'APPROVED', false, 0);
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-21 17:00', 2, 2, 9, 7, 8, null, 7, 'REQUESTED', false, 0);
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-18 13:00', 1, 1, 6, 7, 8, null, 7, 'REQUESTED', false, 0);
insert into operations(date, patient_id, requested_doctor_id, first_doctor_id, second_doctor_id, third_doctor_id, room_id, operation_type_id, status, deleted, version)
	values('2020-01-21 08:00', 1, 1, 6, 7, 8, null, 7, 'REQUESTED', false, 0);		
	
	
insert into healthrecord (blood_type, diopter, height, weight)
values (
	'A_NEGATIVE', 1.15, 1.75, 73
);
update patiens 
set health_record_id = 1
where patiens.id = 1;

insert into healthrecord (blood_type, diopter, height, weight)
values (
	'O_POSITIVE', -3.2, 1.83, 86
);
update patiens 
set health_record_id = 2
where patiens.id = 2;
insert into healthrecord (blood_type, diopter, height, weight)
values (
	'A_NEGATIVE', 1.15, 1.75, 73
);
update patiens 
set health_record_id = 3
where patiens.id = 3;

insert into healthrecord (blood_type, diopter, height, weight)
values (
	'O_POSITIVE', -3.2, 1.83, 86
);
update patiens 
set health_record_id = 4
where patiens.id = 4;

insert into allergypojo (allergy, health_record_id)
values ('Nuts', 1);
insert into allergypojo (allergy, health_record_id)
values ('Cats', 1);
insert into allergypojo (allergy, health_record_id)
values ('Pollen', 2);

insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-11 15:32:00', 1, 'DONE', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-01 16:16:00', 1, 'DONE', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-02 15:32:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-03 15:00:00', 1, 'DONE', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-04 15:32:00', 1, 'DONE', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-04 08:32:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-04 16:16:00', 1, 'DONE', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-04 14:32:00', 1, 'DONE', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-05 15:32:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-05 15:32:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-01-05 15:32:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
values ('2019-11-11 14:30:00', 1, 'DONE', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2019-11-01 16:30:00', 1, 'DONE', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2019-11-24 19:30:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);



insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-06-06 14:30:00', 1, 'BLESSED', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-06-06 16:30:00', 1, 'BLESSED', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-06-06 19:30:00', 1, 'BLESSED', 1, null, 1, 1, 1, 2, false, 0);


--novi podaci
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2019-11-11 14:30:00', 1, 'DONE', 1, null, 1, 1, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2019-11-01 16:30:00', 1, 'DONE', 1, null, 1, 1, 2, 1, true, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2019-11-24 19:30:00', 1, 'DONE', 1, null, 1, 1, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-03 09:30:00', 20, 'APPROVED', 2, null, 1, 4, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-03 10:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-04 15:30:00', 0, 'REQUESTED', 2, null, 1, 3, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-04 08:00:00', 0, 'REQUESTED', 1, null, 1, 3, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-04 09:00:00', 0, 'REQUESTED', 2, null, 1, 4, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-05 09:30:00', 20, 'REQUESTED', 1, null, 1, 3, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-22 10:30:00', 0, 'REQUESTED', 2, null, 1, 3, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-03-28 10:00:00', 1, 'AVAILABLE', 1, null, 1, null, 1, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-03-27 00:00:00', 1, 'AVAILABLE', 1, null, 1, null, 2, 1, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-03-03 04:00:00', 1, 'AVAILABLE', 1, null, 1, null, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-03-03 02:00:00', 30, 'AVAILABLE', 4, null, 1, null, 1, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-03-03 06:00:00', 1, 'AVAILABLE', 3, null, 1, null, 1, 2, false, 0);

--for requesting a new appointment as doctor
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-06 10:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-06 10:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-02-06 10:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
--podaci za otkazivanje appointmenta
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-20 12:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-21 12:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-22 12:30:00', 0, 'APPROVED', 1, null, 1, 1, 2, 2, false, 0);
--podaci za rezervisanje sala
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-31 12:30:00', 0, 'DOCTOR_REQUESTED_APPOINTMENT', 1, null, 1, 1, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-1-30 12:30:00', 0, 'DOCTOR_REQUESTED_APPOINTMENT', 1, null, 1, 1, 2, null, false, 0);
insert into appointments (date, discount, status, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id, deleted, version)
values ('2020-2-1 12:30:00', 0, 'REQUESTED', 1, null, 2, 1, 2, null, false, 0);



--veza doktor - requested appointment
insert into doctor_requested(doctor_id, appointment_id)
	values(1, 12);
insert into doctor_requested(doctor_id, appointment_id)
	values(1, 13);
insert into doctor_requested(doctor_id, appointment_id)
	values(1, 14);

insert into examination_reportpojo (appointment_id, clinic_id, health_record_id)
values (3, 1, 1);
insert into examination_reportpojo (appointment_id, clinic_id, health_record_id)
values (1, 1, 1);
insert into examination_reportpojo (appointment_id, clinic_id, health_record_id)
values (2, 1, 2);
insert into examination_reportpojo (appointment_id, clinic_id, health_record_id)
values (4, 1, 1);

update appointments 
set examination_report_id = 1
where id = 1;
update appointments 
set examination_report_id = 2
where id = 2;
update appointments 
set examination_report_id = 3
where id = 3;
update appointments 
set examination_report_id = 4
where id = 4;

update examination_reportpojo
set health_record_id = 1
where id = 1;
update examination_reportpojo
set health_record_id = 1
where id = 2;
update examination_reportpojo
set health_record_id = 1
where id = 3;

insert into diagnosispojo (description, diagnosis)
values ('U are fine', 'Hypohondriac');
insert into diagnosispojo (description, diagnosis)
values ('What you get from too much reddit', 'Brain Cancer');
insert into diagnosispojo (description, diagnosis)
values ('Dubstep.', 'Ear Cancer');

insert into therapypojo (advice)
values ('Go out more');
insert into therapypojo (advice)
values ('Git gud, n00b');
insert into therapypojo (advice)
values ('Drugs are bad, mkay?');

insert into medicationpojo (med_description, medication_name)
values ('It makes you feel good', 'Cocain');
insert into medicationpojo (med_description, medication_name)
values ('Gives you wings', 'Reed bool');
insert into medicationpojo (med_description, medication_name)
values ('For the missus, wink wink nudge nudge', 'Vaseline');

insert into perscriptionpojo (diagnosis_id, examination_report_id, signing_nurse_id, therapy_id)
values (1, 1, 1, 1);
insert into perscriptionpojo (diagnosis_id, examination_report_id, signing_nurse_id, therapy_id)
values (2, 2, 1, 2);
insert into perscriptionpojo (diagnosis_id, examination_report_id, signing_nurse_id, therapy_id)
values (3, 2, 1, null);

insert into perscriptionpojo (diagnosis_id, examination_report_id, signing_nurse_id, therapy_id)
values (2, 1, null, 1);
insert into perscriptionpojo (diagnosis_id, examination_report_id, signing_nurse_id, therapy_id)
values (3, 2, null, 2);



update examination_reportpojo
set perscription_id = 1
where id = 1;
update examination_reportpojo
set perscription_id = 2
where id = 2;
update examination_reportpojo
set perscription_id = 3
where id = 3;

insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (1, 1);
insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (2, 1);
insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (3, 1);

insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (1, 4);


insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (2, 5);


insert into perscriptionpojo_medication_list (medication_list_id, perscription_id)
values (1, 5);


insert into doctor_reviewpojo (rating, doctor_id, patient_id)
values (2, 2, 2);
insert into doctor_reviewpojo (rating, doctor_id, patient_id)
values (3, 2, 2);
insert into doctor_reviewpojo (rating, doctor_id, patient_id)
values (4, 2, 3);
insert into doctor_reviewpojo (rating, doctor_id, patient_id)
values (1, 2, 4);
insert into doctor_reviewpojo (rating, doctor_id, patient_id)
values (1, 1, 2);

insert into clinic_rewiew (rating, clinic_id, patient_id)
values (3, 1, 1);
insert into clinic_rewiew (rating, clinic_id, patient_id)
values (5, 1, 2);
insert into clinic_rewiew (rating, clinic_id, patient_id)
values (4, 1, 3);
insert into clinic_rewiew (rating, clinic_id, patient_id)
values (3, 1, 4);
insert into leave_requests (first_day, last_day, leave_status, leave_type, request_note, staff_role, doctor_id, nurse_id, version)
	values ('2020-02-03', '2020-02-04', 'APPROVED', 'PERSONAL', 'أنا أعرف القليل من اللغة العربية ، كافر', 'DOCTOR', 1, null, 0);
insert into clinic_rewiew (rating, clinic_id, patient_id)
values (1, 2, 3);
insert into clinic_rewiew (rating, clinic_id, patient_id)
values (2, 2, 4);



insert into leave_requests (first_day, last_day, leave_status, leave_type, request_note, staff_role, doctor_id, nurse_id, version)
	values ('2020-02-03', '2020-02-04', 'APPROVED', 'PERSONAL', 'أنا أعرف القليل من اللغة العربية ، كافر', 'DOCTOR', 1, null, 0);
	
	insert into leave_requests (first_day, last_day, leave_status, leave_type, request_note, staff_role, doctor_id, nurse_id, version)
	values ('2020-01-01', '2020-01-5', 'REQUESTED', 'ANNUAL', 'Vucic是我们的最高领导者', 'DOCTOR', 1, null, 0);
	
insert into leave_requests (first_day, last_day, leave_status, leave_type, request_note, staff_role, doctor_id, nurse_id, version)
	values ('2020-03-01', '2020-03-12', 'REQUESTED', 'ANNUAL', 'Please let me go, Im working for 45 days.', 'DOCTOR', 1, null, 0);
	


insert into leave_requests (first_day, last_day, leave_status, leave_type, request_note, staff_role, doctor_id, nurse_id, version)
	values ('2020-02-01', '2020-02-04', 'REQUESTED', 'PERSONAL', 'release me from my flesh prison', 'NURSE', null, 1, 0);

-- potreban za 1 test
	insert into appointments(date, deleted, discount, status, version, doctor_id, examination_report_id, nurse_id, patient_id, procedure_type_id, room_id)
values ('2020-02-12 1:30', false, 50, 'APPROVED', 0, 1, null, 1, 2, 1, 1);
