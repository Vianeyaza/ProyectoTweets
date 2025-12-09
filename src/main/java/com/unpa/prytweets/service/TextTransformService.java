/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpa.prytweets.service;

import com.unpa.prytweets.model.Tweet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextTransformService {

    // Aplica una transformación y regresa una nueva lista
    public List<Tweet> transformarTweets(List<Tweet> tweets, Function<Tweet, Tweet> transformacion) {
        return tweets.stream()
                .map(transformacion)
                .collect(Collectors.toList());
    }

    // Aplica transformación y luego ejecuta un consumer por cada tweet
    public void procesarTweets(List<Tweet> tweets,
                               Function<Tweet, Tweet> transformacion,
                               Consumer<Tweet> accionFinal) {

        tweets.stream()
                .map(transformacion)
                .forEach(accionFinal);
    }
}

