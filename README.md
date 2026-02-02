# 🧳 Turista Facoltoso – Backoffice Management System

**Turista per sempre** è una piattaforma **full-stack** sviluppata per simulare il **backoffice gestionale** di un sistema di prenotazioni turistiche.

Il progetto consente la gestione di **utenti, host, abitazioni, prenotazioni e feedback**, includendo statistiche e funzionalità avanzate lato backend.

---

## Stack Tecnologico

### Backend
- Java 21
- Maven
- Javalin (REST API)
- JDBC
- PostgreSQL
- UUID come chiavi primarie
- DAO Pattern
- DTO Pattern
- Lombok

### Frontend
- React
- TypeScript
- Vite
- TailwindCSS
- DaisyUI
- React Router

### Database
- PostgreSQL
- Gestione tramite pgAdmin
- Relazioni con foreign key
- Integrità referenziale

---

## Funzionalità Principali

### Gestione Utenti
- Creazione, modifica ed eliminazione utenti
- Visualizzazione lista utenti
- Statistiche sugli utenti più attivi

### Gestione Host
- Creazione host a partire da un utente
- Codice host univoco
- Visualizzazione host
- Identificazione Super Host
- Host con più prenotazioni nell’ultimo mese

### Gestione Abitazioni
- Associazione abitazioni a un host
- Visualizzazione abitazioni
- Statistiche:
  - abitazione più gettonata
  - media posti letto

### Prenotazioni
- Creazione prenotazioni
- Controllo sovrapposizione date
- Visualizzazione:
  - ultima prenotazione di un utente
  - prenotazioni per host
- Ordinamento per data

### Feedback
- Inserimento feedback su una prenotazione
- Campi:
  - titolo
  - testo
  - punteggio (1–5)
- Validazione lato backend
- Salvataggio persistente su database

---

## Interfaccia Utente

- Dashboard centrale Backoffice
- Navigazione tramite navbar
- UI responsive
- Componenti modulari
- Azioni CRUD tramite form
- Gestione errori e feedback utente

---

