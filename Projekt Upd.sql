drop table if exists klienci
go
drop table if exists admini
go
drop table if exists pracownicy
go
drop table if exists adresy
go
drop table if exists dostawcy
go
drop table if exists dostawy
go
drop table if exists produkty
go
drop table if exists kategorie
go
drop table if exists zamowienia
go
drop table if exists mails

create table klienci
(
id_klient int primary key identity,
imie varchar(20) ,
nazwisko varchar(20) ,
data_ur date 

)
go
create table admini
(
id_admin int primary key identity,
imie varchar(20) ,
nazwisko varchar(20) ,
data_ur date,
logiin varchar(20) not null,
haslo varchar(20) not null
)
go
create table pracownicy
(
id_pracownik int primary key identity,
imie varchar(20) ,
nazwisko varchar(20) ,
data_ur date ,
wynagrodzenie_brutto decimal(20,2),
logiin varchar(20) not null,
haslo varchar(20) not null,
id_adresu int,
poziom_uprawnien int

)
go
create table adresy
(
id_adres int primary key identity,
miasto varchar(30),
ulica varchar(30),
nr_budynku varchar(20),
kod_pocztowy varchar(10)
)
go
create table dostawcy
(
id_dostawca int primary key identity,
nazwa varchar(20) ,
stawka_za_km decimal(20,2),
id_siedziby varchar(20)
)
go
create table dostawy
(
id_dostawa int primary key identity,
id_dostawcy int,
id_produktu int,
ilosc int,
data_dostawy date
)
go
create table produkty
(
id_produkt int primary key identity,
nazwa varchar(40) ,
opis varchar(200),
id_kategorii int,
cena decimal(20,2),
stan_na_magazynie int
)
go
create table kategorie
(
id_kategoria int primary key identity,
nazwa_kategorii varchar(35),
magazyn varchar(20),
rabat_stalego_klienta decimal(3,2)
)
go
create table zamowienia
(
id_zamowienie int primary key identity,
id_klienta int,
id_pracownika int,
id_produktu int,
ilosc int,
data_zamowienia date
)
go
create table mails
(
id_mail int primary key identity,
id_nadawcy int,
id_odbiorcy int,
title varchar(50),
body varchar(5000),
send_date date,
stan bit
)
insert into klienci values
('Jan','Kowalski','1994-11-25'),
('Anna','Malinowska','1998-08-24'),
('Grzegorz','Nowak','1996-10-05'),
('Joanna','Kuczkowska','1988-02-04')
go
insert into admini values
('Wojciech','Brzozowski','1979-02-01','a','a'),
('Joanna','Kwiecieñ','1993-04-22','ad2','admin2')

go
insert into pracownicy values
('Marcin','Brzozowski','1975-09-11',3005.50,'p','p', 1,2),
('Marta','Cielecka','1997-04-29',4500.20,'pr2','prac2', 3,2),
('Wojciech','Malinowski','1994-03-25',3603.90,'pr3','prac3', 2,1),
('Krzysztof','Jankowski','1988-12-03',6220.40,'pr4','prac4', 4,1)
go
insert into adresy values
('Warszawa','Mickiewicza','21','74-240'),
('Szczecin','Brzozowa','5B','23-550'),
('Gdynia','Makowa','4','55-210'),
('Warszawa','Kasztanowa','48C/23','11-550'),
('£ódŸ','Piwna','7','45-440'),
('Wolin','Wiosenna','49F','21-550'),
('Szczecin','Bary³kowa','48D/33','85-260'),
('Elbl¹g','Zimowa','11A/3','21-770')

go
insert into dostawcy values
('SPEEDTRANS',7.40,7),
('Kurier',8.20,6),
('Speedy',8.50,8),
('Mebex',6.80,5)
go
insert into dostawy values
(1,1,20,'2017-09-22'),
(4,5,50,'2017-07-17'),
(3,2,30,'2017-01-28'),
(4,3,25,'2017-08-04')
go
insert into produkty values
('Sunflare Medium', 'Ma³ej wielkoœci lampa preznaczona na biórka',2,80.80,205),
('Dêbiarz C7', 'Stó³ dêbowy rozmair 1m x 3m',1, 406.40, 55),           
('Komfort Extra', 'Fotel skórzany ekologiczny',1, 789.99, 124),
('Chumrka M', 'Wymiary 200cm x 155cm',1, 1799.99, 49),
('Sosner L', 'Stó³ sosnowy rozmair 1m x 2m',1, 649.99, 43),
('VIR Ziemia uniwersalna 50kg', 'Worki',4, 11.99, 211),
('POWER SS28M', 'Wiertarka',3, 149.99,21)
go
insert into kategorie values
('Meble','A',0.95),
('Elektronika','A', 0.95),           
('Narzêdzia','C',0.92),
('Ogrodnictwo','D',0.84)
go
insert into zamowienia values
(2,1,1,2,'2019-11-23'),
(4,3,2,5,'2019-10-15'),
(1,4,5,1,'2019-11-25'),
(2,1,3,20,'2020-01-13'),
(3,1,7,10,'2020-03-27'),
(4,2,2,6,'2020-03-02'),
(4,1,5,15,'2020-04-11'),
(4,2,5,11,'2020-05-14'),
(3,3,6,33,'2020-05-23'),
(2,1,7,11,'2020-05-07'),
(4,3,1,22,'2020-06-04'),
(3,4,2,44,'2020-06-12'),
(2,2,6,1,'2020-06-19'),
(2,3,3,3,'2020-06-21')
go
insert into mails values
(1,2,'Tytu³ testowy','Test tu jest tekst. One two, three.','2018-02-04',0),
(3,2,'Tytu³ testowy 2','Test tu jest tekst2. Awaria w sekcji czwartej.','2018-07-05',1),
(3,2,'Tytu³ testowy 3','Test tu jest tekst3. Wait for delivery.','2018-07-05',1),
(2,4,'Uwaga!','Test tu jest tekst3. Wait for delivery. Nowa wiadomoœæ. Set the delivery for tommorow.','2018-07-05',1)
go
SELECT SCOPE_IDENTITY()
go
SELECT COUNT(*) FROM zamowienia
WHERE id_pracownika = 1
go
select * from pracownicy 
where logiin = 'pr1' AND haslo = 'prac1'