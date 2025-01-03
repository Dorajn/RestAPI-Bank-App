# RestAPI-Bank-App

Aplikacja REST API do obsługi banku, umożliwiająca:

- Rejestrowanie użytkowników
- Realizowanie przelewów
- Wpłacanie i pobieranie pieniędzy

## Technologie

- **Spring Boot** – Framework do tworzenia aplikacji backendowych
- **PostgreSQL** – Baza danych użytkowników i transakcji

## Endpoints

- `GET /api/users` – Pobierz wszystkich użytkowników
- `POST /api/users` – Zarejestruj nowego użytkownika
- `GET /api/users/{uid}/account` – Pobierz saldo użytkownika
- `POST /api/users/{uid}/account/withdraw` – Wypłać środki
- `POST /api/users/{uid}/account/deposit` – Wpłać środki
- `POST /api/users/{uid}/account/transfer` – Przelew między użytkownikami
- `GET /api/users/{uid}/account/history` – Pobierz historię przelewów
- `GET /api/users/{uid}/account/history/raport` – Pobierz plik PDF z wykazem przelewów użytkownika
