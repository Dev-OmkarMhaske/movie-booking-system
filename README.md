# Movie Ticket Booking System

Run:
- `./mvnw spring-boot:run` (or `mvn spring-boot:run`)
- H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:moviedb)

Demo users (basic auth, defined in SecurityConfig):
- admin(username) / adminpass(password)  (ROLE_ADMIN)
- user(username) / userpass(password)    (ROLE_CUSTOMER)

APIs:
- GET /api/movies
- GET /api/shows
- GET /api/shows/{id}/seats
- POST /api/book  (body: { "showId":1, "seatCodes":["A1","A2"], "promoCode":"FREE1" } )

Notes:
- This is a minimal demo focusing on booking flow with pessimistic locking.
- Security is BasicAuth for simplicity. For production, integrate JWT and password encoding.
