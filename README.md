# Dokumentacija

- [Opis projekta](#opis-projekta)
- [Dizajn baze podataka](#dizajn-baze-podataka)
- [Arhitektura projekta](#arhitektura-projekta)

  - [Domain](#domain)
  - [Dao](#dao)
  - [Service](#service)
  - [Dto](#dto)
  - [Controller](#controller)
  - [Util](#util)

- [Sigurnost](#sigurnost)

- [Obrada grešaka](#obrada-grešaka)

- [Praćenje aktivnosti korisnika](#praćenje-aktivnosti-korisnika)

- [Swaggeer API dokumentacija]("#swagger-api-dokumentacija")

## Opis projekta

Projekat realizuje osnovne funkcionalnosti aplikacije za veb prodavnicu. Neke od mogućnosti su logovanje korisnika, pretraga, dodavanje, brisanje, izmena, komentarisanje, ocenjivanje postova. Takodje postovi su rasporedjeni po kategorijama, jedan post moze biti u vise kategorija.

Tehnologije korišćene u izradi ove REST API aplikacije:

- Java 11
- Spring Boot
- MySQL
- Hibernate JPA ORM
- Querydsl
- Lombok
- ModelMapper
- JSON Web Token (JWT)
- Swagger API Docs

## Dizajn baze podataka

Za čuvanje podataka koršćena je MySQL baza podataka, sa sledećom strukturom:
![db_schema](assets/db_schema.png)

## Arhitektura projekta

Aplikacija je rasporedjena u 6 projekata, od kojih svaki predstavlja jedan sloj u celokupnoj arhitekturi aplikacije.

### Domain

Domenski sloj aplikacije predstavlja skup klasa koje opisuju strukturu, i medjusobne relacije domenskih podataka naše aplikacije.

https://github.com/markorusic/netcore-blog-app/tree/master/src/Domain

### Dao

Dao, odnosno Data Access Object, je sloj koji se bavi komunikacijom naše aplikacije sa bazom podataka. Struktura podataka, i relacije baze opisani su u domenskom sloju, tako da se Dao sloj oslanja na njega. Upotrebom domenskog sloja i Entity Framework Core biblioteke za SQLServer dobijamo Dao sloj.

https://github.com/markorusic/netcore-blog-app/tree/master/src/Dao

https://github.com/markorusic/netcore-blog-app/tree/master/src/Dto

### Service

Servisni sloj predstavlja skup interfejsa koji opisuju biznis logiku naše aplikacije. Za svaki definisani interfejst pravimo klasu koja predstavlja stvarnu implementaciju, i u kojoj živi celokupna lokiga. Servisni sloj, odnosno njegova implementacija se oslanja na sve prehodno pomenute slojeve, kao i na druge servise, i njihovim kombinovanjem izvšava specifični zahtev. Implementacija servisnog interfejsa se ubacuje u IoC kontejner putem Dependency Injection mehanizna.

[Primer sevisnog interfejsa](https://github.com/markorusic/netcore-blog-app/tree/master/src/Service/IPostService.cs)
[Primer implementacije servisnog interfejsa](https://github.com/markorusic/netcore-blog-app/tree/master/src/Service/Impl/PostServiceImpl.cs)

https://github.com/markorusic/netcore-blog-app/tree/master/src/Service

### Dto

Dto, odnostno Data Transfer Object, prestsavlja skup klasa koje opisuju strukturu podatka koje želimo da prikažemo korisniku (klijentu) naše aplikacije. Svrha Dto klase je da strukturu odgovarajuće domenske klase prilagodi potrebama klijenta. Za mapiranje domenskih klasa u dto, korišćena je biblioteka Automapper.

https://github.com/markorusic/netcore-blog-app/tree/master/src/Common

### Controller

Controller je glavna ulazna tačka naše aplikacije. Za razliku od ostalih slojeva, koji prestavljaju biblioteke klasa, Controller sloj se izvršava samostalno. Glavna namena Controller sloja je da pruži REST API pristup našoj aplikaciji. Pri pokretanju inicijalizuje se ceo projekat, konfiguracija, u DI kontejner se ubacuju neophodni dependecy-ji...

Centralni deo Controller sloja predstavljau kontroleri. Njihov posao je da izlože definisane API endpointe preko kojih će klijenti moći da komuniciraju sa našom aplikaicjiom. Kontroleri se oslanjaju na servisni sloj za izvršavanje neophodne biznis lokige.

https://github.com/markorusic/netcore-blog-app/tree/master/src/Api

### Util

Predstavlja skup klasa sa opštom namenom koje mogu biti korišćene na svim slojevima aplikacije.

## Sigurnost

Sigurnost, odnosno mogućnost zaštite određenih delova aplikacije, realizovana je JWT (JSON Web Token) standardom za autentifikaciju i autorizaciju korisnika. Zaštita se vrši na nivou jednog API endpointa kontrolera.

Autentifikacija korisnika vrši se preko [AuthController-a](https://github.com/markorusic/netcore-blog-app/blob/master/src/Api/Controllers/AuthController.cs). Biznis logika neophodna za autentifikaciju i dohvatanje trenutnog korisnika apstraktovana je u [AuthSerivce](https://github.com/markorusic/netcore-blog-app/blob/master/src/Service/Impl/AuthServiceImpl.cs).

Autorizacija korisnika dešava se na nivou role. Postoje dva tipa korisničkih rola, korisnik i admin. Api-jevi za pretrage podataka su javni, medjutim oni koji se bave izmenom podataka zaštićeni su. Korisnik sa rolom korisnik može pristupiti Api-jevima za dodavanje, izmenu i brisanje postova, komentara i ocena. Korisnik sa rolom admin može pristupiti delu aplikaciej koji se bavi administracijom kategorija.

Primer zaštite API endpointa:

![user_autorize](images/user_autorize.png)

## Obrada grešaka

Obrada grešaka dešava se na globalnom nivou putem [ExceptionMiddleware](https://github.com/markorusic/netcore-blog-app/blob/master/src/Api/Middlewares/ExceptionMiddleware.cs) klase, koja se [inicijaluzuje](https://github.com/markorusic/netcore-blog-app/blob/master/src/Api/Startup.cs#L87) pri pokretanju Api projekta u zajedno sa ostalim komponentama.

Time imamo mogućnost da iz npr servisnog sloja izbacujemo izuzetke koje ne moramo pojedinačno, i ručno obrađivati, jer se o tome brine globalni ExceptionMiddleware. Primer izuzetaka u [implementaciji](https://github.com/markorusic/netcore-blog-app/blob/master/src/Service/Impl/PostServiceImpl.cs) `IPostSerivce`-a.

![post_service_exceptions](images/post_service_exceptions.png)

## Praćenje aktivnosti korisnika

Aktivnosti ulogovanog korisnika koje menjaju stanje aplikacije se prate i zapisuju u bazu podataka. Logika praćenja aktivnosti apstraktovana je u [UserActivityService](https://github.com/markorusic/netcore-blog-app/blob/master/src/Service/Impl/UserActivityImpl.cs).

Primer zapisivanja aktivnosti korisnika:

```cs
_userActivityService.Track($"Created rate on post({postId}): {rate.Value}");
```

Aktivnost korisnika mogu se pretraziti putem `api/user-activity` endpointa kreiranog u [UserActivityController-u](https://github.com/markorusic/netcore-blog-app/blob/master/src/Api/Controllers/UserActivityController.cs).

## Swaggeer API dokumentacija

Swaggeer API dokumentacija [inicijaluzuje](https://github.com/markorusic/netcore-blog-app/blob/master/src/Api/Startup.cs#L100) se pri pokretanju Api projekta, dostupna je na `/swagger/index.html` putanji.

![swagger-api-docs](images/swagger-api-docs.png)
