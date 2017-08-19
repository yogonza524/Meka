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
public class UtilArbolDecision {
    
    public static ArrayList<ArrayList<String>> getDatosEntrada(String entradaString, int dim) {
        ArrayList<ArrayList<String>> salida = new ArrayList<>();
        String linea;
        entradaString = entradaString + "\n" + "#";
        entradaString = entradaString.replaceAll(" ", "");
        //obtengo cada punto
        while(!entradaString.equals("#")){
            linea = entradaString.substring(0, entradaString.indexOf("\n"));
            salida.add(getFila(linea, dim));
            entradaString = entradaString.substring(entradaString.indexOf("\n")+1);
        }
        return salida;
    }
    
    private static ArrayList<String> getFila(String linea, int dim){
        ArrayList<String> salida = new ArrayList<>();
        String aux;
        for (int i = 0; i < dim; i++) {
            aux = linea.substring(0, linea.indexOf(','));
            salida.add(aux);
            linea = linea.substring(linea.indexOf(',')+1);
        }
        aux = linea.substring(0, linea.indexOf(';'));
        salida.add(aux);
        return salida;
    }
    
    public static ArrayList<String> getNombreAtributos(String atributos, int cantAtributos) {
        ArrayList<String> salida = new ArrayList<>();
        String aux;
        atributos = atributos.replaceAll(" ", "");
        for (int i = 0; i < cantAtributos-1; i++) {
            aux = atributos.substring(0, atributos.indexOf(','));
            salida.add(aux);
            atributos = atributos.substring(atributos.indexOf(',')+1);
        }
        aux = atributos.substring(0, atributos.indexOf(';'));
        salida.add(aux);
        return salida;
    }
}
