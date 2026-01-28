package com.turistafacoltoso;

import com.turistafacoltoso.controller.AbitazioneController;
import com.turistafacoltoso.controller.HostController;
import com.turistafacoltoso.controller.PrenotazioneController;
import com.turistafacoltoso.controller.UtenteController;
import com.turistafacoltoso.util.DatabaseConnection;

import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        try {
            DatabaseConnection.getConnection();
            System.out.println("Connessione al database riuscita");
        } catch (Exception e) {
            System.err.println("Errore di connessione al database");
            e.printStackTrace();
            return;
        }

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
        }).start(7000);

        new AbitazioneController(app);
        new PrenotazioneController(app);
        new HostController(app);
        new UtenteController(app);
    }
}

// mvn clean package
// java -jar target/turista_facoltoso_backend-1.0-SNAPSHOT.jar