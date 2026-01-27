package com.turistafacoltoso;

import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;
        });

        app.get("/", ctx -> {
            ctx.result("Turista Facoltoso backend attivo");
        });

        app.start(7000);
    }
}
