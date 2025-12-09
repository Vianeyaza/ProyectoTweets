package com.unpa.prytweets.app;

import com.unpa.prytweets.model.Tweet;
import com.unpa.prytweets.service.TweetLoader;
import com.unpa.prytweets.service.TextTransformService;
import com.unpa.prytweets.service.TweetAnalyticsService;
import com.unpa.prytweets.report.ReportGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        // -----------------------------
        // 1) INSTANCIAS DE SERVICIOS
        // -----------------------------
        TweetLoader loader = new TweetLoader();
        TextTransformService transformService = new TextTransformService();
        TweetAnalyticsService analyticsService = new TweetAnalyticsService();
        ReportGenerator reportGenerator = new ReportGenerator();

        String rutaEntrada = "data/twitters.csv";    // Puedes ajustarla
        String rutaTweetsLimpios = "output/tweets_limpios.txt";
        String rutaResumen = "output/resumen_estadisticas.txt";

        // ---------------------------------------------------------
        // 2) Supplier PARA CARGAR TWEETS (obligatorio en el proyecto)
        // ---------------------------------------------------------
        Supplier<List<Tweet>> lectorTweets = loader.crearLectorTweets(rutaEntrada);

        // -------------------------------------------------------------------
        // 3) Definir FUNCTION que transforma un tweet (texto a mayúsculas)
        //    Puedes cambiarla por quitar @usuario, #tema, espacios, etc.
        // -------------------------------------------------------------------
        Function<Tweet, Tweet> transformarMayusculas = t ->
                new Tweet(
                        t.getId(),
                        t.getEntidad(),
                        t.getSentimiento(),
                        t.getTexto().toUpperCase()
                );

        // ---------------------------------------------------------
        // 4) Consumer para imprimir tweets procesados
        // ---------------------------------------------------------
        Consumer<Tweet> imprimirTweet = t -> System.out.println("Procesado: " + t);

        // ---------------------------------------------------------
        // 5) Runnable PRINCIPAL con todo el pipeline
        // ---------------------------------------------------------
        Runnable pipelinePrincipal = () -> {

            // A) Cargar datos con Supplier
            System.out.println("Cargando tweets...");
            List<Tweet> tweets = lectorTweets.get();

            // B) Transformar tweets
            System.out.println("Transformando tweets...");
            List<Tweet> tweetsLimpios = transformService.transformarTweets(
                    tweets,
                    transformarMayusculas
            );

            // C) Procesar tweets (imprimir uno por uno)
            System.out.println("Procesando tweets transformados...");
            transformService.procesarTweets(
                    tweetsLimpios,
                    transformarMayusculas,  // se vuelve a aplicar solo para la muestra
                    imprimirTweet
            );

            // D) Calcular estadísticas
            System.out.println("Calculando estadísticas...");
            double promedioPositivos = analyticsService.calcularPromedioLongitud(tweets, "positive");
            Map<String, Long> cuentaSentimientos = analyticsService.contarTweetsPorSentimiento(tweets);

            // Crear resumen
            StringBuilder sb = new StringBuilder();
            sb.append("=== RESUMEN ESTADÍSTICAS ===\n");
            sb.append("Promedio longitud (positive): ").append(promedioPositivos).append("\n");
            sb.append("Tweets por sentimiento:\n");
            cuentaSentimientos.forEach((sent, cant) ->
                    sb.append(" - ").append(sent).append(": ").append(cant).append("\n")
            );

            String resumen = sb.toString();

            // E) Guardar reportes txt
            System.out.println("Guardando archivos de reporte...");
            reportGenerator.guardarTweetsLimpios(tweetsLimpios, rutaTweetsLimpios);
            reportGenerator.guardarResumenEstadisticas(resumen, rutaResumen);

            System.out.println("Pipeline completado. Reportes generados en carpeta output/");
        };

        // ---------------------------------------------------------
        // 6) Ejecutar el Runnable principal (obligatorio)
        // ---------------------------------------------------------
        Thread hiloPrincipal = new Thread(pipelinePrincipal);
        hiloPrincipal.start();
    }
}

