----SQL skripta koja se pokrece sa Spring boot app i daje dummy podatke
---- enkripcija sa sajta: https://bcrypt-generator.com/

--password: sifra
INSERT INTO centre_administrator (address,birthday,city,email,first_name,last_name,must_change_password,password,phone_number,status,state) VALUES 
('A dom','2003-02-01 00:00:00.000','Novi Sad','glavni_sef@maildrop.cc','Đura','Đurić',NULL,'$2y$10$.LtaQ8h1eF5Y9mz7cZwTqeXf0TyGRLbyOD27/eRb4N9WMuOZHwYMG','06555555','CENTRE_ADMINISTRATOR','Serbia')
,('6A Stevana Milovanova','1991-02-04 01:00:00.000','Novi Sad','sporedni_sef@maildrop.cc','Goran','Betić',false,'$2y$10$T3JRn/kzupvZCVjfGqepsOYGWrfbdt2Yb6y3YeVGjQyAz6EU/uBr.','0623082357','CENTRE_ADMINISTRATOR','Serbia')
;


INSERT INTO clinic (address,city,description,name,state) VALUES 
('75 Bulevar cara Lazara','Novi Sad','Limanski dom zdravlja opste namene','Dom zdravlja Liman','Serbia')
,('24 Zmaj Ognjena Vuka','Novi Sad','Lečenje od bolesti zavisnosti','Dom Zdravlja','Serbia')
,('7A Bulevar Cara Lazara','Novi Sad','Privatna zubarska klinika','Poliklinika Pepic','Serbia')
,('27 Dobračina','Belgrade','Privatna oftalmoloska klinika','Sveti Vid','Serbia')
,('2 Gyulai Pál u. ','Budapest','Néhány magyar leírás','Szent Rókus','Hungary')
;

--password: 1234
INSERT INTO clinic_administrator (address,birthday,city,email,first_name,last_name,must_change_password,password,phone_number,status,state,clinic_id) VALUES 
('4 Jovana Vukovića','1978-10-12 01:00:00.000','Novi Sad','admin2@maildrop.cc','Nikola','Jovanović',false,'$2y$10$RJDMkHuJE1ld.2gc5v1ui.Y0L8gkTjgBavLb2FhmfLYAVnQAPYcna','06722252552','CLINICAL_ADMINISTRATOR','Serbia',2)
,('50 Balzakova','1950-04-19 01:00:00.000','Novi Sad','admin3@maildrop.cc','Pero','Kovačević',false,'$2y$10$FVB78sFVqxnGPJEKf1IF1uEuLxKjlK.bGuxVewae9I8FMT2XfIZvu','066841651','CLINICAL_ADMINISTRATOR','Serbia',3)
,('48 Gyáli út','1970-05-21 01:00:00.000','Budapest','admin4@maildrop.cc','Ištvan','Moč',false,'$2y$10$2cqlRcaQM9fLrfViyYo7AuuURRhjlUd5EPAHBE3dFHLivApueiS/e','06061641551','CLINICAL_ADMINISTRATOR','Hungary',5)
,('8 Knjaz Miloševa','1985-08-05 02:00:00.000','Belgrade','admin5@maildrop.cc','Jelena','Anđelić',false,'$2y$10$.Kyidj8fG1cp0uqFtDctyu4WLw0T1FiQSYy5FBhAikNA5EeHAPfHC','0628415051','CLINICAL_ADMINISTRATOR','Serbia',4)
,('1 Trg Slobodana Miloševića','1992-03-10 01:00:00.000','Novi Sad','admin1@maildrop.cc','Stefan','Bojanić',false,'$2y$10$GbF7jIA6aILaRrjk9LFA4e5Qx5Kg/L2IMJw0BT2jtE1na0RHSWX.a','0618525825','CLINICAL_ADMINISTRATOR','Serbia',1)
;


INSERT INTO diagnosispojo (description,diagnosis) VALUES 
('Rhino virus, caused by seasonal drop of immunity','Common Cold')
,('You are a hypocondriac. Chill out. ','No diagnosis')
,('Femeral bone broken, probabbly due to an immense physical trauma','Broken left femur')
,('Patient seems severely sleep deprieved. Is he maybe a student?','Sleep deprivation')
;


INSERT INTO medicationpojo (med_description,medication_name) VALUES 
('Vitamin that boost the immune system','Vitamin C')
,('They kill off the unwanted bacteria in a patients system','Antibiotics')
,('Aids most symptoms of the common cold','Caffetin 250mg')
,('Relax, bro','Chill pill')
,('Strong perscription painkiller','Vicodin')
,('Pills with a daily reccomended dose of calcium','Calcium 500mg')
,('A perscription strength sleeping pill','Xanax 0.25mg')
;


INSERT INTO procedures_type (deleted,duration,is_operation,name,price,clinic_id) VALUES 
(false,'00:15:00',false,'General exam',15,1)
,(false,'01:00:00',false,'Orthopetic exam',25,1)
,(false,'01:00:00',false,'Psychotherapy',30,1)
,(false,'01:15:00',false,'Psychotherapy',35,2)
,(false,'00:30:00',false,'General exam',20,2)
,(false,'01:30:00',false,'Hearing exam',40,2)
,(false,'00:45:00',false,'Psychotherapy',35,5)
,(false,'01:30:00',true,'Colonoscopy',50,1)
,(false,'03:00:00',true,'Colectomy',70,1)
;

--password: doca
INSERT INTO doctors (address,birthday,city,deleted,email,first_name,friday,last_name,monday,must_change_password,password,phone_number,status,saturday,state,sunday,thursday,tuesday,wednesday,clinic_id,procedure_type_id) VALUES 
('3 Branislava Nušića','1992-05-06 02:00:00.000','Novi Sad',false,'jocka@maildrop.cc','Jovana','SECOND','Bratić','NONE',false,'$2y$10$5BqVCpktyTaXtJhdcncd2.ICUS.20RaDu9N/wdp/m9w/pZ60pZ9Fm','06636268024','DOCTOR','NONE','Serbia','THIRD','NONE','SECOND','THIRD',1,1)
,('15A Bore Prodanovića','1991-10-15 01:00:00.000','Novi Sad',false,'boki@maildrop.cc','Bogdan','FIRST','Karamarković','FIRST',false,'$2y$10$BG2D4qD6qNbq0gmOBT8vN.UgQFiP5.XecMWsPeyAKHOJLTI24BMSq','066804610','DOCTOR','FIRST','Serbia','NONE','NONE','FIRST','NONE',1,1)
,('160 Cara Lazara','1967-09-28 01:00:00.000','Futog',false,'mile@maildrop.cc','Miladtin','FIRST','Borojević','NONE',false,'$2y$10$w2l01qOqLM7LGncVKqyfzeZnl4bJt6aQLARA0KACGFwHQP0CJge66','02168466','DOCTOR','SECOND','Serbia','FIRST','SECOND','NONE','FIRST',1,2)
,('3 Zmaj Jovina','1983-11-08 01:00:00.000','Novi Sad',false,'milunka@maildrop.cc','Milunka','NONE','Alempić','FIRST',false,'$2y$10$OKo/z0Lpd2OFVPyJW7kPjeLobciEDgp5dz7fcA6DE6JqQurIkMbee','067051658','DOCTOR','NONE','Serbia','NONE','FIRST','FIRST','FIRST',1,3)
,('7 Nikole Pašića','1984-10-09 01:00:00.000','Novi Sad',false,'stojan@maildrop.cc','Stojan','FIRST','Ostojić','FIRST',false,'$2y$10$c1UwgYTtS7fR.5gkNjoJ4.NKyvPpLIQHNzQ1qMAs.5LX0Mb0GSL6u','0632525805','DOCTOR','NONE','Serbia','NONE','FIRST','FIRST','FIRST',2,4)
,('12 Turgenjeva','1989-05-16 02:00:00.000','Novi Sad',false,'akica@maildrop.cc','Aleksandra','FIRST','Jović','NONE',false,'$2y$10$LsUE7k5QbmvTWoCDYmDiXON8/zMaunviQcRuPODD88BDUjj0nRno2','06825535525','DOCTOR','FIRST','Serbia','THIRD','FIRST','NONE','NONE',2,5)
,('19 Bulevar Oslobođenja','1959-05-14 01:00:00.000','Novi Sad',false,'zivko@maildrop.cc','Živojin','NONE','Karađorđević','NONE',false,'$2y$10$HuKLwN.sEffouqqzZU0KHePvcEliMo.nWTjHSka3G/eQV0C3gsAmi','060289029','DOCTOR','NONE','Serbia','THIRD','THIRD','THIRD','THIRD',2,6)
,('24 Harmat u. ','1994-02-03 01:00:00.000','Budapest',false,'isa@maildrop.cc','Isztvan','NONE','Balasz','FIRST',false,'$2y$10$sUnjqDY8rkJFASMZ5ghsIuTz.uNFCAaWi7x2x4pRBFlUlS843fohu','406165320166','DOCTOR','NONE','Hungary','NONE','NONE','FIRST','FIRST',5,7)
,('4 Bore Prodanovića','1983-02-03 01:00:00.000','Novi Sad',false,'milo@maildrop.cc','Milo','FIRST','Bašić','FIRST',false,'$2y$10$Ms8sY/S7.XYerhHKXW.WAu2LzGoKBnn3c8ZA/lyucxb1KxH2hen42','0684804840','DOCTOR','FIRST','Serbia','FIRST','FIRST','FIRST','FIRST',1,8)
,('24 Čenejski put','1989-05-21 02:00:00.000','Čenej',false,'sale@maildrop.cc','Sale','FIRST','Ačanski','FIRST',false,'$2y$10$JGrHEu/KdMmhLuIbewpa8.2JklwU.rCaz9K3AQEOzcNbbEM9Cx6S2','06848405','DOCTOR','FIRST','Serbia','FIRST','FIRST','FIRST','FIRST',1,8)
;
INSERT INTO doctors (address,birthday,city,deleted,email,first_name,friday,last_name,monday,must_change_password,password,phone_number,status,saturday,state,sunday,thursday,tuesday,wednesday,clinic_id,procedure_type_id) VALUES 
('11 Beogradski kej','1993-02-16 01:00:00.000','Novi Sad',false,'dado@maildrop.cc','Dado','FIRST','Jovičić','FIRST',false,'$2y$10$5403wNUlk/Fa12lVYPQCXe4OIV8/bYjqzYLRVMYLWYzTFDh4wIgGO','06854984','DOCTOR','FIRST','Serbia','FIRST','FIRST','FIRST','FIRST',1,8)
;


INSERT INTO room (deleted,name,number,clinic_id,proceduras_types_id) VALUES 
(false,'Orthopedic room',1,1,2)
,(false,'Psychollogy',2,1,3)
,(false,'General intake',3,1,1)
,(false,'Psych room',1,2,4)
,(false,'Psych room',2,2,4)
,(false,'Exam room',3,2,5)
,(false,'Exam room',4,2,5)
,(false,'Exam room',5,2,5)
,(false,'Ortorhinolaringology',6,2,6)
,(false,'pszichoterápiás szoba',1,5,7)
;
INSERT INTO room (deleted,name,number,clinic_id,proceduras_types_id) VALUES 
(false,'pszichoterápiás szoba',2,5,7)
,(false,'Surgery',8,1,8)
,(false,'Surgery',9,1,9)
;

--password: 4321
INSERT INTO nurse (address,birthday,city,deleted,email,first_name,friday,last_name,monday,must_change_password,password,phone_number,status,saturday,state,sunday,thursday,tuesday,wednesday,clinic_id) VALUES 
('8 Jevrejska','1982-04-20 01:00:00.000','Novi Sad',false,'erzi@maildrop.cc','Eržebet','FIRST','Anđelić','FIRST',false,'$2y$10$NObeGyGkY5gD5dB1AqGUledF314v6C93dtVcT83/Y2Cpt4Fa./Mzi','066704035','NURSE','FIRST','Serbia','FIRST','FIRST','FIRST','FIRST',1)
,('3 Miletićeva','1983-09-04 02:00:00.000','Novi Sad',false,'simona@maildrop.cc','Simona','THIRD','Bašić','THIRD',false,'$2y$10$f.Umhw9GYabu4pGwhQgoTuCPNXzNufvhaUHuqkt0f/g4i6nespDme','064851515522','NURSE','THIRD','Serbia','THIRD','THIRD','THIRD','THIRD',1)
,('3 Aleksandra Tišme','1985-08-30 02:00:00.000','Novi Sad',false,'dragana@maildrop.cc','Dragana','SECOND','Crnogorac','SECOND',false,'$2y$10$D4f5Szywhr6s0jNcqfX.fu89e6/avSGulHH.dyVDAx9N.UBw0ulT2','2196761','NURSE','SECOND','Serbia','SECOND','SECOND','SECOND','SECOND',1)
,('7 Novosadskog Sajma','1984-12-03 01:00:00.000','Novi Sad',false,'aca@maildrop.cc','Aleksandar','FIRST','Milošević','FIRST',false,'$2y$10$6V67ngsX.aXf.u5IhqjnN.LFtyl9nDZ1LB2IeULOCYLUhMnRY8Jw6','0625814512','NURSE','FIRST','Serbia','FIRST','FIRST','FIRST','FIRST',2)
,('2 Stevana Musića','1985-04-12 02:00:00.000','Novi Sad',false,'acka@maildrop.cc','Aleksandra','SECOND','Milošev','SECOND',false,'$2y$10$13gnvIT.d9SGAHyLSGyId.EppNiX/fwjT0mEwcEkChRm/InEi1h/e','02165101','NURSE','SECOND','Serbia','SECOND','SECOND','SECOND','SECOND',2)
,('2 Albertirsai köz','1999-03-01 01:00:00.000','Budapest',false,'vesna@maildrop.cc','Veszna','NONE','Korosi','FIRST',false,'$2y$10$b3cvXf00D8ArvSobhoObxOeAj8QYHI62Pdw9F5Qxffs36mF1NlZli','640646441','NURSE','NONE','Hungary','NONE','NONE','FIRST','FIRST',5)
,('3 Nikole Pašića','1976-02-04 01:00:00.000','Novi Sad',false,'sanja@maildrop.cc','Sanja','THIRD','Gajić','THIRD',false,'$2y$10$LZFBB7k224bmJynIKc5bxO.pwmb.izdHqn89dAI8qb6wHhFrtwPZC','06698795578','NURSE','THIRD','Serbia','THIRD','THIRD','THIRD','THIRD',2)
;


INSERT INTO healthrecord (blood_type,diopter,height,weight) VALUES 
('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
,('O_POSITIVE',0,0,0)
;
INSERT INTO healthrecord (blood_type,diopter,height,weight) VALUES 
('O_POSITIVE',0,0,0),
('O_POSITIVE',0,0,0)
;

--password: whoppa42
INSERT INTO patiens (address,birthday,city,email,first_name,insurance_number,is_activated,last_name,password,phone_number,status,state,health_record_id) VALUES 
('2 Simeona Simića','1997-12-03 01:00:00.000','Novi Sad','happymeal@maildrop.cc','Tanja',604,true,'Blejic','$2y$10$hlvthdh7lrpHPn3WDgmYLebFwr78p.B5MWzL0zC44QOovakT4vqWq','063870485510','PATIENT','Serbia',1)
,('9 Turgenjeva','1995-02-04 01:00:00.000','Novi Sad','juro@maildrop.cc','Juro',6454,true,'Joksimović','$2y$10$jcZESKu1emvnNuQ78KDX1uhrAANJfj4ExBaFvbrN2.1aGki2.zNpW','069560165','PATIENT','Serbia',2)
,('9 Jovana Dučića','1995-03-07 01:00:00.000','Beograd','zeljko@maildrop.cc','Željko',547,true,'Stublić','$2y$10$CYLhk2wPxobNi2ha2WS1YeYjRYwzXxaV1r13l1UhB604ssHsUr5O.','066545605','PATIENT','Serbia',3)
,('9 Trg Slobode','1998-03-05 01:00:00.000','Novi Sad','stole@maildrop.cc','Stojadin',684,false,'Milunović','$2y$10$m0s2dP6xXy0oNKpSp6tw..yhIeHF3DpwpUJdU/44oisJfWrJd0sTa','066846846486','PATIENT','Serbia',4)
,('5 Stevana Sremca','1996-04-05 02:00:00.000','Novi Sad','milovan@maildrop.cc','Milovan',56046,true,'Borojević','$2y$10$0X5Dtp5A28luoka0eP1fbe/lWMVE2c2HVlRLd7ylC7jx276b45yVW','06680465416','PATIENT','Serbia',5)
,('8 Laze Telečkog','1994-02-03 01:00:00.000','Novi Sad','borislav@maildrop.cc','Borislav',21,true,'Gajić','$2y$10$OYBaOnoA2otfDZ.DKROlqegSHdV9R4SVav9CHlQzHGHhkZH/mA.yi','066546564','PATIENT','Serbia',6)
,('3 Bulevar Evrope','1985-02-06 01:00:00.000','Novi Sad','gavra@maildrop.cc','Dejan',6476,true,'Gavrilović','$2y$10$npbIU.sDfhV3Kar86ZQEdufO1KGetGwO7QCZ58FMqugB8S/JycP6a','06684684684','PATIENT','Serbia',7)
,('9 Bulevar Evrope','1995-03-08 01:00:00.000','Novi Sad','goran@maildrop.cc','Goran',56797,true,'Divović','$2y$10$akkM0G2Hjn1Ke9CIpLj9DeFdajveZTtclHUjPfQ8O.TmdB3/y0Juu','066846847','PATIENT','Serbia',8)
,('4 Temerinska','1993-01-20 01:00:00.000','Novi Sad','gar@maildrop.cc','Doban',6840,true,'Kovačević','$2y$10$o14qrBfyIIRUEmIXLNuG7.9cROZWeXWNn7Wa/DL.VgGgQdmxliqMm','0668468','PATIENT','Serbia',9)
,('9 Đorđa Magaraševića','1997-05-21 02:00:00.000','Novi Sad','coa@maildrop.cc','Aca',546,true,'Jovanovic','$2y$10$KpnUpRPWr7hkRC0DkYgBbOdqVl3/vRV4TuZ5PHyVDNi9EDAcs7cwS','06654684','PATIENT','Serbia',10)
;
INSERT INTO patiens (address,birthday,city,email,first_name,insurance_number,is_activated,last_name,password,phone_number,status,state,health_record_id) VALUES 
('9 Uspenska','1995-05-05 02:00:00.000','Novi Sad','kaca@maildrop.cc','Katarina',646,true,'Stojanović','$2y$10$egVESQ0mM/VeKP6ytKWAIOZm98yaVgsef26epB/A0zvLV.ArtvkQq','06646446846','PATIENT','Serbia',11)
,('2 Zlatne Grede','1964-05-07 01:00:00.000','Novi Sad','vuja@maildrop.cc','Vukasin',651,true,'Vučević','$2y$10$6a3kRwiYGYZs9nP1vwkkeuh0ta7fYX09flmGY5ODqiU4fItywPl5q','0658554684','PATIENT','Serbia',12)
;


INSERT INTO perscriptionpojo (diagnosis_id,examination_report_id,signing_nurse_id,therapy_id) VALUES 
--(NULL,1,NULL,1)
--,(1,2,NULL,2)
(3,null,NULL,null)
,(1,null,NULL,null)
,(1,null,NULL,null)
,(1,null,NULL,null)
;


-----------------------------------------------------------------------------------------------------------------------------
-- Dopuniti therapy id
-----------------------------------------------------------------------------------------------------------------------------
INSERT INTO examination_reportpojo (appointment_id,clinic_id,health_record_id,perscription_id) VALUES 
--(13,1,1,1)
--,(15,1,1,2)
(null,1,1,1)
,(null,1,1,2)
,(null,1,1,2)
,(null,2,1,2)
;


UPDATE perscriptionpojo SET examination_report_id = 1 WHERE id = 1;
UPDATE perscriptionpojo SET examination_report_id = 2 WHERE id = 2;
UPDATE perscriptionpojo SET examination_report_id = 3 WHERE id = 3;
UPDATE perscriptionpojo SET examination_report_id = 4 WHERE id = 4;



insert into therapypojo (advice, perscription_id) values 
('Take your vitamin D and calcium, and try to move as little as possible. ', 1),
('A week of bedrest should do wonders for you. ', 2);

update perscriptionpojo set therapy_id = 1 where id = 1;
update perscriptionpojo set therapy_id = 2 where id = 2;
update perscriptionpojo set therapy_id = 1 where id = 3;
update perscriptionpojo set therapy_id = 1 where id = 4;


INSERT INTO appointments (date,deleted,discount,status,version,doctor_id,examination_report_id,nurse_id,patient_id,procedure_type_id,room_id) VALUES 
('2020-05-01 08:00:00.000',false,20,'AVAILABLE',0,2,NULL,1,NULL,1,3)
,('2020-04-03 10:00:00.000',false,20,'AVAILABLE',0,2,NULL,1,NULL,1,3)
,('2020-04-23 10:00:00.000',false,20,'AVAILABLE',0,4,NULL,1,NULL,3,2)
,('2020-04-23 10:00:00.000',false,20,'AVAILABLE',0,4,NULL,1,NULL,3,2)
,('2020-04-06 08:15:00.000',false,0,'AVAILABLE',0,5,NULL,4,NULL,4,4)
,('2020-04-06 11:15:00.000',false,0,'AVAILABLE',0,5,NULL,4,NULL,4,4)
,('2020-04-29 08:30:00.000',false,50,'AVAILABLE',0,5,NULL,4,NULL,4,4)
,('2020-04-17 08:30:00.000',false,35,'AVAILABLE',0,6,NULL,4,NULL,5,7)
,('2020-04-19 00:00:00.000',false,15,'AVAILABLE',0,7,NULL,7,NULL,6,9)
,('2020-04-27 08:15:00.000',false,45,'AVAILABLE',0,8,NULL,6,NULL,7,10)
;
INSERT INTO appointments (date,deleted,discount,status,version,doctor_id,examination_report_id,nurse_id,patient_id,procedure_type_id,room_id) VALUES 
('2020-05-04 08:15:00.000',false,60,'AVAILABLE',0,8,NULL,6,NULL,7,11)
,('2020-04-20 08:15:00.000',true,-1,'APPROVED',1,2,NULL,1,1,1,3)
,('2020-02-07 00:16:44.535',false,-1,'DONE',2,2,1,1,1,1,3)
,('2020-02-17 08:15:00.000',false,-1,'REQUESTED',0,2,NULL,NULL,1,1,NULL)
,('2020-02-07 00:40:21.100',false,-1,'DONE',2,2,2,1,1,1,3)
,('2020-01-31 00:40:21.100',false,-1,'DONE',2,1,2,1,1,1,3)
,('2020-02-03 08:40:21.100',false,-1,'DONE',2,5,2,4,1,5,6)
;


UPDATE examination_reportpojo SET appointment_id = 13 WHERE id = 1;
UPDATE examination_reportpojo SET appointment_id = 15 WHERE id = 2;
UPDATE examination_reportpojo SET appointment_id = 16 WHERE id = 3;
UPDATE examination_reportpojo SET appointment_id = 17 WHERE id = 4;


INSERT INTO user_requestpojo (address,birthday,city,email,first_name,insurance_number,last_name,password,phone_number,state,version) VALUES 
('3 Novosadskog Sajma','1996-04-08 02:00:00.000','Novi Sad','andrea@maildrop.cc','Andrea',5845,'Perišić','whoppa42','0654085458','Serbia',0)
,('8 Katolička Porta','1986-04-02 02:00:00.000','Novi Sad','uros@maildrop.cc','Uroš',504,'Bošković','whoppa42','0654848488','Serbia',0)
,('4 Dunavska','1989-05-05 02:00:00.000','Novi Sad','desa@maildrop.cc','Desanka',965,'Nikolić','whoppa42','066556695','Serbia',0)
,('2 Bulevar Oslobođenja','1968-04-08 01:00:00.000','Novi Sad','irena@maildrop.cc','Irena',84,'Filipović','whoppa42','0684844844','Serbia',0)
,('24 Maksima Gorkog','1985-12-19 01:00:00.000','Novi Sad','djole@maildrop.cc','Đorđe',448,'Lazarević','whoppa42','0654848449','Serbbia',0)
;



INSERT INTO clinic_rewiew (rating,clinic_id,patient_id) VALUES 
(3,1,1)
;


INSERT INTO doctor_reviewpojo (rating,doctor_id,patient_id) VALUES 
(5,2,1)
;


insert into perscriptionpojo_medication_list (perscription_id, medication_list_id) values 
(1, 5), 
(1, 6),
(2, 1),
(2, 2), 
(2, 3);


INSERT INTO leave_requests (first_day,last_day,leave_status,leave_type,request_note,staff_role,version,doctor_id,nurse_id) VALUES 
('2020-04-13 02:00:00.000','2020-04-17 02:00:00.000','REQUESTED','PERSONAL','','DOCTOR',0,2,NULL)
,('2020-04-27 02:00:00.000','2020-04-28 02:00:00.000','REQUESTED','ANNUAL','','DOCTOR',0,2,NULL)
;


INSERT INTO operations (date,deleted,status,first_doctor_id,operation_type_id,patient_id,requested_doctor_id,room_id,second_doctor_id,third_doctor_id, version) VALUES 
('2020-02-14 09:00:00.000',false,'REQUESTED',NULL,8,1,2,NULL,NULL,NULL, 0)
;