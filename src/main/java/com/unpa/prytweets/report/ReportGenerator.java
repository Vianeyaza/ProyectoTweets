/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpa.prytweets.report;

import com.unpa.prytweets.model.Tweet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    // Guarda una lista de tweets ya transformados/limpios
    public void guardarTweetsLimpios(List<Tweet> tweets, String rutaSalida) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {

            for (Tweet t : tweets) {
                bw.write(t.toString());
                bw.newLine(); 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guarda un resumen de estadísticas
    public void guardarResumenEstadisticas(String resumen, String rutaSalida) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {

            bw.write(resumen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

