package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.dto.StatisticheUtenteDTO;
import com.turistafacoltoso.model.Utente;
import com.turistafacoltoso.util.DatabaseConnection;

public class UtenteRepository {

    private static final String TOP_5_UTENTI_GIORNI_MESE = """
                SELECT
                    u.nome,
                    u.cognome,
                    u.email,
                    SUM(p.data_fine - p.data_inizio) AS giorni_prenotati
                FROM prenotazione p
                JOIN utente u ON p.utente_id = u.id
                WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
                GROUP BY u.nome, u.cognome, u.email
                ORDER BY giorni_prenotati DESC
                LIMIT 5
            """;

    private static final String FIND_ALL = """
                SELECT * FROM utente
            """;

    private static final String FIND_BY_ID = """
                SELECT * FROM utente WHERE id = ?
            """;

    private static final String INSERT = """
                INSERT INTO utente (id, nome, cognome, email, indirizzo)
                VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE = """
                UPDATE utente
                SET nome = ?, cognome = ?, email = ?, indirizzo = ?
                WHERE id = ?
            """;

    private static final String DELETE = """
                DELETE FROM utente WHERE id = ?
            """;

    public List<Utente> findAll() {
        List<Utente> utenti = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utenti.add(mapRow(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero utenti", e);
        }

        return utenti;
    }

    public Optional<Utente> findById(UUID id) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero utente", e);
        }

        return Optional.empty();
    }

    public void save(Utente utente) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setObject(1, utente.getId());
            ps.setString(2, utente.getNome());
            ps.setString(3, utente.getCognome());
            ps.setString(4, utente.getEmail());
            ps.setString(5, utente.getIndirizzo());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Errore nella creazione utente", e);
        }
    }

    public boolean update(Utente utente) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getIndirizzo());
            ps.setObject(5, utente.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Errore nell'aggiornamento utente", e);
        }
    }

    public boolean delete(UUID id) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setObject(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Errore nella cancellazione utente", e);
        }
    }

    public List<StatisticheUtenteDTO> findTop5UtentiPerGiorniPrenotatiUltimoMese() {
        List<StatisticheUtenteDTO> risultato = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(TOP_5_UTENTI_GIORNI_MESE);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                risultato.add(
                        new StatisticheUtenteDTO(
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getString("email"),
                                rs.getInt("giorni_prenotati")));
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dei top 5 utenti per giorni prenotati",
                    e);
        }

        return risultato;
    }

    private Utente mapRow(ResultSet rs) throws Exception {
        return new Utente(
                UUID.fromString(rs.getString("id")),
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getString("email"),
                rs.getString("indirizzo"));
    }
}
