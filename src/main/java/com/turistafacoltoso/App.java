package com.turistafacoltoso;

import java.sql.Connection;

import com.turistafacoltoso.util.DatabaseConnection;

import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connessione al database riuscita");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;
        });

        app.get("/", ctx -> {
            ctx.result("Turista Facoltoso backend attivo");
        });

        app.start(7000);
    }
}

// mvn clean package
// java -jar target/turista_facoltoso_backend-1.0-SNAPSHOT.jar