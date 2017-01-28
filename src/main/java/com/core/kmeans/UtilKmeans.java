/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.kmeans;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author pichon
 */
public class UtilKmeans {

    static Color[] colores = {Color.RED, Color.BLUE, Color.GREENYELLOW, Color.AQUAMARINE, Color.ORANGE, Color.DARKMAGENTA};
    static String[] colorName = {"Rojo", "Azul", "Verde", "Celeste", "Naranja", "Violeta"};
    
    public static String[] getColorName() {
        return colorName;
    }
    
    public static ArrayList<ArrayList<Double>> cargarPuntos(String entradaString, int dim){
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        entradaString = entradaString + "\n" + "#";
        entradaString = entradaString.replaceAll(" ", "");
        //obtengo cada punto
        while(!entradaString.equals("#")){
            linea = entradaString.substring(0, entradaString.indexOf("\n"));
            salida.add(getPunto(linea, dim));
            entradaString = entradaString.substring(entradaString.indexOf("\n")+1);
        }
        return salida;
    }
    
    private static ArrayList<Double> getPunto(String linea, int dim){
        ArrayList<Double> salida = new ArrayList<>();
        String aux;
        for (int i = 0; i < dim-1; i++) {
            aux = linea.substring(0, linea.indexOf(','));
            salida.add(Double.valueOf(aux));
            linea = linea.substring(linea.indexOf(',')+1);
        }
        aux = linea.substring(0, linea.indexOf(';'));
        salida.add(Double.valueOf(aux));
        return salida;
    }
    
    public static String resultadoFinal(KMeansResultado resultado){
        String result = "";
        result = result + "------- Con k=" + resultado.getCantCluster() + /*" ofv=" + resultado.getOfv() +*/ "-------\n";
        int i = 0;
        for (Cluster cluster : resultado.getClusters()) {
            result = result + "--Elementos del Cluster " + i + " --\n";
            for (Punto punto : cluster.getPuntos()) {
                result = result + punto.toString() + "\n";
            }
            result = result + "Posicion del centroide: \n";
            result = result + cluster.getCentroide().toString();
            result = result + "\n\n";
            i++;
        }
        return result;
    }
    
    //Graficar los puntos
    public static ArrayList<Plot> addPuntosGrafic(ArrayList<ArrayList<Double>> puntos, Axes axes){
        double ancho = 0.001, diferencia = 0.00051;
        ArrayList<Plot> puntosGraf = new ArrayList<>();
        Plot puntoG;
        for (int i = 0; i < puntos.size(); i++) {
            double y = (double) puntos.get(i).get(1);
            puntoG = new Plot(
                x -> y ,
                puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                axes, 7.0, Color.WHITE
            );
            puntosGraf.add(puntoG);
        }
        return puntosGraf;
    }
    
    //Graficar los clusters
    public static ArrayList<Plot> addClustGrafic(ArrayList<ArrayList<Double>> puntos, Axes axes){
        double ancho = 0.001, diferencia = 0.00051;
        ArrayList<Plot> puntosGraf = new ArrayList<>();
        Plot puntoG;
        
        for (int i = 0; i < puntos.size(); i++) {
            double y = (double) puntos.get(i).get(1);
            puntoG = new Plot(
                x -> y ,
                puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                axes, 7.0, colores[i]
            );
            puntosGraf.add(puntoG);
        }
        return puntosGraf;
    }
    
    public static ArrayList<ArrayList<Double>> getCoordenadasCluster(ArrayList<Cluster> historiaCluster) {
        ArrayList<ArrayList<Double>> puntos = new ArrayList<>();
        for (int i = 0; i < historiaCluster.size(); i++) {
            Punto puntoCluster = historiaCluster.get(i).getCentroide();
            ArrayList<Double> coordenadas = new ArrayList<>();
            Float[] data = puntoCluster.getData();
            for (int j = 0; j < 2; j++) {
                coordenadas.add(data[j].doubleValue());
            }
            puntos.add(coordenadas);
        }
        return puntos;
    }

    public static Plot graficarLineaACluster(Punto punto, Punto centroide, Axes axes, int numClust) {
        Plot puntoG;
        double largo = 0.01;
        double ancho = 1.5;
        float aux = punto.get(0);
        if (Math.abs(centroide.get(0)-punto.get(0))<largo) {
            aux = (float) (aux + largo);
        }
        final float x0 = aux;
        if (centroide.get(0)< punto.get(0)) {
            puntoG = new Plot(
            x -> ((punto.get(1)-centroide.get(1))/(x0-centroide.get(0))*(x-centroide.get(0)))+centroide.get(1),
            centroide.get(0), x0, largo,
            axes, ancho, colores[numClust]
        );
        }else{
            puntoG = new Plot(
                x -> ((centroide.get(1)-punto.get(1))/(centroide.get(0)-x0)*(x-x0))+punto.get(1),
                x0, centroide.get(0), largo,
                axes, ancho, colores[numClust]
            );
        }
        return puntoG;
    }
}
