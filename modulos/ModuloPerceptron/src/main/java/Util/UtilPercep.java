/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author pichon
 */
public class UtilPercep {
    
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
    
    public static ArrayList<ArrayList<Double>> cargarPuntosArchivo(String dir) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        BufferedReader bf;
        bf = leerFichero(dir);
        double dim = getParametros(bf.readLine());
        double alfa = getParametros(bf.readLine());
        bf.readLine(); //Avanzo cabecera

        while(!(linea =  bf.readLine()).equals("}")){
            salida.add(getPunto(linea, (int) dim));
        }
        return salida;
    }
    
    private static BufferedReader leerFichero(String dir) throws FileNotFoundException{
        FileReader fr = new FileReader(dir);
        BufferedReader bf = new BufferedReader(fr);
        return bf;
    }
    
    private static double getParametros(String linea){
        double salida = 0;
        for (int i = 1; i < linea.length(); i++) {
            if (linea.charAt(i) == ':') {
                salida = Double.parseDouble(linea.substring(i+1,linea.length()-1));
            }
        }
        return salida;
    }
    
    private static ArrayList<Double> getPunto(String linea, int dim){
        ArrayList<Double> salida = new ArrayList<>();
        String aux;
        for (int i = 0; i < dim; i++) {
            aux = linea.substring(0, linea.indexOf(','));
            salida.add(Double.valueOf(aux));
            linea = linea.substring(linea.indexOf(',')+1);
        }
        aux = linea.substring(0, linea.indexOf(';'));
        salida.add(Double.valueOf(aux));
        return salida;
    }
    
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double resultado = valorInicial;
        int num = (int) Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado * num);
        resultado = resultado/num;
        return resultado;        
    }
    
    public static double[] cargarWs(String entrada, int dim){
    String aux; double a, b;
        double[] alfas = new double[dim+1];
        entrada =  entrada.replaceAll(" ", "");
        for (int i = 0; i < dim; i++) {
            aux = entrada.substring(0, entrada.indexOf(";"));
            if (aux.contains("/")) {
                a = Double.parseDouble(aux.substring(0, entrada.indexOf("/")));
                b = Double.parseDouble(aux.substring(entrada.indexOf("/")+1));
                alfas[i] = a/b;
            } else {
                alfas[i] = Double.parseDouble(aux);
            }
            entrada = entrada.substring(entrada.indexOf(";")+1);
        }
        aux = entrada.substring(0, entrada.indexOf(";"));
        if (aux.contains("/")) {
            a = Double.parseDouble(aux.substring(0, entrada.indexOf("/")));
            b = Double.parseDouble(aux.substring(entrada.indexOf("/")+1));
            alfas[dim] = a/b;
        } else {
            alfas[dim] = Double.parseDouble(aux);
        }
        return alfas;
    }
    
    public static ArrayList<Plot> addPuntosGrafic(ArrayList<ArrayList<Double>> puntos, Axes axes){
        //Graficar puntos de la lista
        double ancho = 0.001, diferencia = 0.00051;
        ArrayList<Plot> puntosGraf = new ArrayList<>();
        Plot puntoG = null;
        for (int i = 0; i < puntos.size(); i++) {
            double y = (double) puntos.get(i).get(1);
            if (puntos.get(i).get(2) > 0) {
                puntoG = new Plot(
                    x -> y ,
                    puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                    axes, 7.0, Color.RED
                );
            }else{
                puntoG = new Plot(
                    x -> y ,
                    puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                    axes, 7.0, Color.BLUE
                );
            }
            puntosGraf.add(puntoG);
        }
        return puntosGraf;
    }
    
    public static String armarEncabezado(int dim, String espacio) {
        String salida = "Entrada |";
        espacio = espacio.substring(3);
        for (int i = 0; i < dim; i++) {
            salida = salida + " x" + i + espacio + "|";
        }
        salida = salida + " salida" + espacio + "|";
        for (int i = 0; i < dim+1; i++) {
            salida = salida + " w" + i + espacio + "|";
        }
        salida = salida + " net" + espacio + "| fNet" + espacio + "| error" + espacio;
        return salida;
    }
    
    public static ArrayList<ArrayList<Double>> cargarPuntosClasificar(String entradaString, int dim){
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        entradaString = entradaString + "\n" + "#";
        entradaString = entradaString.replaceAll(" ", "");
        //obtengo cada punto
        while(!entradaString.equals("#")){
            linea = entradaString.substring(0, entradaString.indexOf("\n"));
            salida.add(getPunto(linea, dim-1));
            entradaString = entradaString.substring(entradaString.indexOf("\n")+1);
        }
        return salida;
    }
 
    public static Plot graficarLinea(double[] pesos, Axes axes, Color color, double grosor){
        Plot puntoG = new Plot(
                        x -> (pesos[0]-(x*pesos[1]))/pesos[2] ,
                        -8, 8, 1,
                        axes, grosor, color
                    );
        return puntoG;
    }
    
    public static ArrayList<Plot> addPuntosGraficClass(ArrayList<ArrayList<Double>> puntos, Axes axes){
        //Graficar puntos de la lista
        double ancho = 0.001, diferencia = 0.00051;
        ArrayList<Plot> puntosGraf = new ArrayList<>();
        Plot puntoG = null;
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
}
