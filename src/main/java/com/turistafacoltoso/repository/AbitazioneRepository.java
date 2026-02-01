package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.AbitazioneDTO;
import com.turistafacoltoso.dto.AbitazioneGettonataDTO;
import com.turistafacoltoso.model.Abitazione;
import com.turistafacoltoso.util.DatabaseConnection;

public class AbitazioneRepository {

    private static final String FIND_BY_CODICE_HOST = """
                SELECT
                    a.id,
                    a.nome,
                    a.indirizzo,
                    a.numero_posti_letto,
                    a.prezzo
                FROM abitazione a
                JOIN host h ON a.host_id = h.id
                WHERE h.codice_host = ?
            """;

    public List<AbitazioneDTO> findByCodiceHost(String codiceHost) {

        List<AbitazioneDTO> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CODICE_HOST)) {

            ps.setString(1, codiceHost);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRowDTO(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero delle abitazioni per codice host", e);
        }

        return result;
    }

    private AbitazioneDTO mapRowDTO(ResultSet rs) throws Exception {
        return new AbitazioneDTO(
                rs.getObject("id", UUID.class),
                rs.getString("nome"),
                rs.getString("indirizzo"),
                rs.getInt("numero_posti_letto"),
                rs.getBigDecimal("prezzo"));
    }

    private static final String ABITAZIONE_PIU_GETTONATA = """
                SELECT
                    a.nome,
                    a.indirizzo,
                    COUNT(p.id) AS numero_prenotazioni
                FROM prenotazione p
                JOIN abitazione a ON p.abitazione_id = a.id
                WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
                GROUP BY a.id, a.nome, a.indirizzo
                ORDER BY numero_prenotazioni DESC
                LIMIT 1
            """;

    public AbitazioneGettonataDTO findAbitazionePiuGettonataUltimoMese() {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(ABITAZIONE_PIU_GETTONATA);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new AbitazioneGettonataDTO(
                        rs.getString("nome"),
                        rs.getString("indirizzo"),
                        rs.getInt("numero_prenotazioni"));
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dell'abitazione più gettonata", e);
        }
    }

    private static final String MEDIA_POSTI_LETTO = """
                SELECT AVG(numero_posti_letto) AS media_posti_letto
                FROM abitazione
            """;

    public double findMediaPostiLetto() {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(MEDIA_POSTI_LETTO);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("media_posti_letto");
            }

            return 0.0;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel calcolo della media dei posti letto", e);
        }
    }

    private static final String FIND_ALL = """
                SELECT * FROM abitazione
            """;

    public List<Abitazione> findAll() {

        List<Abitazione> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowEntity(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore recupero abitazioni", e);
        }

        return list;
    }

    private Abitazione mapRowEntity(ResultSet rs) throws Exception {
        return new Abitazione(
                rs.getObject("id", UUID.class),
                rs.getObject("host_id", UUID.class),
                rs.getString("nome"),
                rs.getString("indirizzo"),
                rs.getInt("numero_locali"),
                rs.getInt("numero_posti_letto"),
                rs.getObject("piano", Integer.class),
                rs.getBigDecimal("prezzo"),
                rs.getDate("disponibilita_inizio").toLocalDate(),
                rs.getDate("disponibilita_fine").toLocalDate());
    }

    private static final String INSERT_FOR_HOST = """
                INSERT INTO abitazione (
                    id, host_id, nome, indirizzo,
                    numero_locali, numero_posti_letto,
                    piano, prezzo,
                    disponibilita_inizio, disponibilita_fine
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public void saveForHost(UUID hostId, Abitazione abitazione) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_FOR_HOST)) {

            ps.setObject(1, abitazione.getId());
            ps.setObject(2, hostId);
            ps.setString(3, abitazione.getNome());
            ps.setString(4, abitazione.getIndirizzo());
            ps.setInt(5, abitazione.getNumeroLocali());
            ps.setInt(6, abitazione.getNumeroPostiLetto());
            ps.setObject(7, abitazione.getPiano());
            ps.setBigDecimal(8, abitazione.getPrezzo());
            ps.setDate(9, java.sql.Date.valueOf(abitazione.getDisponibilitaInizio()));
            ps.setDate(10, java.sql.Date.valueOf(abitazione.getDisponibilitaFine()));

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Errore creazione abitazione per host", e);
        }
    }

    private static final String UPDATE_FOR_HOST = """
                UPDATE abitazione
                SET nome = ?, indirizzo = ?, numero_locali = ?, numero_posti_letto = ?,
                    piano = ?, prezzo = ?, disponibilita_inizio = ?, disponibilita_fine = ?
                WHERE id = ? AND host_id = ?
            """;

    public void updateForHost(UUID hostId, UUID abitazioneId, Abitazione abitazione) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_FOR_HOST)) {

            ps.setString(1, abitazione.getNome());
            ps.setString(2, abitazione.getIndirizzo());
            ps.setInt(3, abitazione.getNumeroLocali());
            ps.setInt(4, abitazione.getNumeroPostiLetto());
            ps.setObject(5, abitazione.getPiano());
            ps.setBigDecimal(6, abitazione.getPrezzo());
            ps.setDate(7, java.sql.Date.valueOf(abitazione.getDisponibilitaInizio()));
            ps.setDate(8, java.sql.Date.valueOf(abitazione.getDisponibilitaFine()));
            ps.setObject(9, abitazioneId);
            ps.setObject(10, hostId);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Abitazione non trovata per questo host");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore aggiornamento abitazione", e);
        }
    }

    private static final String DELETE_FOR_HOST = """
                DELETE FROM abitazione
                WHERE id = ? AND host_id = ?
            """;

    public void deleteForHost(UUID hostId, UUID abitazioneId) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_FOR_HOST)) {

            ps.setObject(1, abitazioneId);
            ps.setObject(2, hostId);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Abitazione non trovata per questo host");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore eliminazione abitazione", e);
        }
    }

    private static final String FIND_BY_HOST_ID = """
                SELECT
                    id,
                    nome,
                    indirizzo,
                    numero_posti_letto,
                    prezzo
                FROM abitazione
                WHERE host_id = ?
            """;

    public List<AbitazioneDTO> findByHostId(UUID hostId) {
        List<AbitazioneDTO> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_HOST_ID)) {

            ps.setObject(1, hostId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new AbitazioneDTO(
                            rs.getObject("id", UUID.class),
                            rs.getString("nome"),
                            rs.getString("indirizzo"),
                            rs.getInt("numero_posti_letto"),
                            rs.getBigDecimal("prezzo")));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore recupero abitazioni host", e);
        }

        return result;
    }

}
