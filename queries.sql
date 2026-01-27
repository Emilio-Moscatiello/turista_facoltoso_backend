
-- 1. Ottenere le abitazioni dato un codice host
SELECT
    a.*
FROM abitazione a
JOIN host h ON a.host_id = h.id
WHERE h.codice_host = 'HOST001';


-- 2. Ottenere l'ultima prenotazione dato un id utente
SELECT *
FROM prenotazione
WHERE utente_id = '53d2e464-fd89-4db5-8cb3-1916fa434f18'
ORDER BY data_fine DESC
LIMIT 1;


-- 3. Ottenere l'abitazione più gettonata nell'ultimo mese
SELECT
    a.nome,
    COUNT(p.id) AS numero_prenotazioni
FROM prenotazione p
JOIN abitazione a ON p.abitazione_id = a.id
WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
GROUP BY a.nome
ORDER BY numero_prenotazioni DESC
LIMIT 1;


-- 4. Ottenere gli host con più prenotazioni nell'ultimo mese
SELECT
    h.codice_host,
    COUNT(p.id) AS totale_prenotazioni
FROM prenotazione p
JOIN abitazione a ON p.abitazione_id = a.id
JOIN host h ON a.host_id = h.id
WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
GROUP BY h.codice_host
ORDER BY totale_prenotazioni DESC;


-- 5. Ottenere tutti i super-host (>= 100 prenotazioni totali)
SELECT
    h.codice_host,
    COUNT(p.id) AS totale_prenotazioni
FROM prenotazione p
JOIN abitazione a ON p.abitazione_id = a.id
JOIN host h ON a.host_id = h.id
GROUP BY h.codice_host
HAVING COUNT(p.id) >= 100;


-- 6. Ottenere i 5 utenti con più giorni prenotati nell'ultimo mese
SELECT
    u.nome,
    u.cognome,
    SUM(p.data_fine - p.data_inizio) AS giorni_prenotati
FROM prenotazione p
JOIN utente u ON p.utente_id = u.id
WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
GROUP BY u.id
ORDER BY giorni_prenotati DESC
LIMIT 5;


-- 7. Ottenere il numero medio di posti letto
SELECT
    AVG(numero_posti_letto) AS media_posti_letto
FROM abitazione;
