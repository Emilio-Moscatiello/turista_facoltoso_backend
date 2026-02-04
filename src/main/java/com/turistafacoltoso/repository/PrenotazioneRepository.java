package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.dto.PrenotazioneDettaglioDTO;
import com.turistafacoltoso.dto.PrenotazioneHostDTO;
import com.turistafacoltoso.model.Prenotazione;
import com.turistafacoltoso.util.DatabaseConnection;

public class PrenotazioneRepository {

    private static final String FIND_ULTIMA_BY_UTENTE = """
                SELECT *
                FROM prenotazione
                WHERE utente_id = ?
                ORDER BY data_fine DESC
                LIMIT 1
            """;

    private static final String FIND_ULTIMA_DETTAGLIO_BY_UTENTE = """
                SELECT
                    p.data_inizio,
                    p.data_fine,

                    u.id      AS utente_id,
                    u.nome    AS utente_nome,
                    u.cognome AS utente_cognome,

                    a.id   AS abitazione_id,
                    a.nome AS abitazione_nome
                FROM prenotazione p
                JOIN utente u ON p.utente_id = u.id
                JOIN abitazione a ON p.abitazione_id = a.id
                WHERE p.utente_id = ?
                ORDER BY p.data_fine DESC
                LIMIT 1
            """;

    private static final String INSERT = """
                INSERT INTO prenotazione (id, utente_id, abitazione_id, data_inizio, data_fine)
                VALUES (?, ?, ?, ?, ?)
            """;

    private static final String CHECK_OVERLAP = """
                SELECT 1
                FROM prenotazione
                WHERE abitazione_id = ?
                  AND data_inizio <= ?
                  AND data_fine >= ?
            """;

    public Optional<Prenotazione> findUltimaByUtenteId(UUID utenteId) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ULTIMA_BY_UTENTE)) {

            ps.setObject(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dell'ultima prenotazione",
                    e);
        }

        return Optional.empty();
    }

    public Optional<PrenotazioneDettaglioDTO> findUltimaPrenotazioneDettaglioByUtenteId(UUID utenteId) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ULTIMA_DETTAGLIO_BY_UTENTE)) {

            ps.setObject(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new PrenotazioneDettaglioDTO(
                                    rs.getDate("data_inizio").toLocalDate().toString(),
                                    rs.getDate("data_fine").toLocalDate().toString(),
                                    rs.getString("utente_id"),
                                    rs.getString("utente_nome"),
                                    rs.getString("utente_cognome"),
                                    rs.getString("abitazione_id"),
                                    rs.getString("abitazione_nome")));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dell'ultima prenotazione dettagliata",
                    e);
        }

        return Optional.empty();
    }

    private Prenotazione mapRow(ResultSet rs) throws Exception {
        return new Prenotazione(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("utente_id")),
                UUID.fromString(rs.getString("abitazione_id")),
                rs.getDate("data_inizio").toLocalDate(),
                rs.getDate("data_fine").toLocalDate());
    }

    public boolean existsOverlappingPrenotazione(
            UUID abitazioneId,
            LocalDate dataInizio,
            LocalDate dataFine) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(CHECK_OVERLAP)) {

            ps.setObject(1, abitazioneId);
            ps.setDate(2, java.sql.Date.valueOf(dataFine));
            ps.setDate(3, java.sql.Date.valueOf(dataInizio));

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore controllo sovrapposizione prenotazioni",
                    e);
        }
    }

    public void save(Prenotazione prenotazione) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setObject(1, prenotazione.getId());
            ps.setObject(2, prenotazione.getUtenteId());
            ps.setObject(3, prenotazione.getAbitazioneId());
            ps.setDate(4, java.sql.Date.valueOf(prenotazione.getDataInizio()));
            ps.setDate(5, java.sql.Date.valueOf(prenotazione.getDataFine()));

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore durante il salvataggio della prenotazione",
                    e);
        }
    }

    private static final String FIND_ALL = """
                SELECT
                    p.id,
                    p.utente_id,
                    p.abitazione_id,
                    p.data_inizio,
                    p.data_fine
                FROM prenotazione p
                ORDER BY p.data_fine DESC
            """;

    public List<Prenotazione> findAll() {

        List<Prenotazione> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Prenotazione(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("utente_id")),
                        UUID.fromString(rs.getString("abitazione_id")),
                        rs.getDate("data_inizio").toLocalDate(),
                        rs.getDate("data_fine").toLocalDate()));
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore recupero prenotazioni",
                    e);
        }

        return list;
    }

    private static final String FIND_BY_HOST_ID = """
                SELECT
                    p.id        AS prenotazione_id,
                    p.utente_id AS utente_id,
                    u.nome      AS utente_nome,
                    u.cognome   AS utente_cognome,
                    a.nome      AS abitazione_nome,
                    p.data_inizio,
                    p.data_fine
                FROM prenotazione p
                JOIN abitazione a ON p.abitazione_id = a.id
                JOIN utente u ON p.utente_id = u.id
                WHERE a.host_id = ?
                ORDER BY p.data_fine DESC
            """;

    public List<PrenotazioneHostDTO> findByHostId(UUID hostId) {

        List<PrenotazioneHostDTO> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_HOST_ID)) {

            ps.setObject(1, hostId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(
                            new PrenotazioneHostDTO(
                                    rs.getString("prenotazione_id"),
                                    rs.getString("utente_id"),
                                    rs.getString("utente_nome"),
                                    rs.getString("utente_cognome"),
                                    rs.getString("abitazione_nome"),
                                    rs.getDate("data_inizio").toString(),
                                    rs.getDate("data_fine").toString()));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore recupero prenotazioni per host",
                    e);
        }

        return list;
    }
}
