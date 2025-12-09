/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpa.prytweets.service;

import com.unpa.prytweets.model.Tweet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TweetLoader {

    public Supplier<List<Tweet>> crearLectorTweets(String rutaArchivo) {
        return () -> {
            List<Tweet> lista = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 4) {
                        int id = Integer.parseInt(partes[0].trim());
                        String entidad = partes[1].trim();
                        String sentimiento = partes[2].trim();
                        String texto = partes[3].trim();

                        lista.add(new Tweet(id, entidad, sentimiento, texto));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return lista;
        };
    }
}

