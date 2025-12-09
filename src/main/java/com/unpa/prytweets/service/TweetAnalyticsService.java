/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpa.prytweets.service;

import com.unpa.prytweets.model.Tweet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TweetAnalyticsService {

    // Promedio de longitud de texto por sentimiento
    public double calcularPromedioLongitud(List<Tweet> tweets, String sentimiento) {
        return tweets.stream()
                .filter(t -> t.getSentimiento().equalsIgnoreCase(sentimiento))
                .mapToInt(t -> t.getTexto().length())
                .average()
                .orElse(0.0);
    }

    // Mapa con cantidad de tweets por sentimiento
    public Map<String, Long> contarTweetsPorSentimiento(List<Tweet> tweets) {
        return tweets.stream()
                .collect(Collectors.groupingBy(
                        Tweet::getSentimiento,
                        Collectors.counting()
                ));
    }
}
