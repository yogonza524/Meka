/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.ArrayList;

/**
 *
 * @author pichon
 */
public class UtilPMC {
    public static ArrayList<ArrayList<Double>> cargarPuntosClasificar(String entradaString, int dim){
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
}
