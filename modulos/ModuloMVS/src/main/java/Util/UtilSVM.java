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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import ru.lanwen.verbalregex.*;

/**
 *
 * @author pichon
 */
public class UtilSVM {
    
    private static BufferedReader bf;

//    private static VerbalExpression.Builder patronPunto = VerbalExpression.regex().capt()
//                                        .maybe("-")
//                                        .digit().atLeast(1)
//                                        .then(",")
//                                        .maybe("-")
//                                        .digit().atLeast(1)
//                                        .then(",")
//                                        .maybe("-")
//                                        .digit().atLeast(1)
//                                        .then(";")
//                                        .maybe("\n");
//        
//    private static VerbalExpression.Builder patronEntradaAlfa = VerbalExpression.regex().capt()
//                                        .digit().atLeast(1)
//                                        .maybe("/")
//                                        .digit().atLeast(0)
//                                        .then(";");
//         
//    private static VerbalExpression.Builder patronPuntoClasific = VerbalExpression.regex().capt()
//                                        .maybe("-")
//                                        .digit().atLeast(1)
//                                        .then(",")
//                                        .maybe("-")
//                                        .digit().atLeast(1)
//                                        .then(";")
//                                        .maybe("\n");
//         
//    private static VerbalExpression patronPE = VerbalExpression.regex()
//                            .startOfLine()
//                            .add(patronPunto).atLeast(1)
//                            .endOfLine()
//                            .build();
//         
//    private static VerbalExpression patronAlfas = VerbalExpression.regex()
//                            .startOfLine()
//                            .add(patronEntradaAlfa).atLeast(2)
//                            .endOfLine()
//                            .build();
//         
//    private static VerbalExpression patronPC = VerbalExpression.regex()
//                            .startOfLine()
//                            .add(patronPuntoClasific).atLeast(1)
//                            .endOfLine()
//                            .build();
    
    
    public static ArrayList<ArrayList<Double>> cargarPuntosArchivo(String dir) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        bf = leerFichero(dir);
        bf.readLine(); // avanzo la cabecera
        while(!(linea = bf.readLine()).equals("}")){
            salida.add(getPunto2Dim(linea));
        }
        return salida;
    }
    
    public static ArrayList<ArrayList<Double>> cargarPuntosTexto(String texto){
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        texto = texto + "\n#";
        //obtengo los puntos
        while(!texto.equals("#")){
            linea = texto.substring(0, texto.indexOf("\n"));
            salida.add(getPunto2Dim(linea));
            texto = texto.substring(texto.indexOf("\n")+1);
        }
        return salida;
    }
    
    private static BufferedReader leerFichero(String dir) throws FileNotFoundException{
        FileReader fr = new FileReader(dir);
        BufferedReader bf = new BufferedReader(fr);
        return bf;
    }
    
    private static int getParametros(String linea){
        int salida = 0;
        for (int i = 1; i < linea.length(); i++) {
            if (linea.charAt(i) == ':') {
//                System.out.println(linea.substring(i+1,linea.length()-1));
                salida = Integer.parseUnsignedInt(linea.substring(i+1,linea.length()-1));
            }
        }
        return salida;
    }
    
    // formato: 0,-1,1; 2 dimensiones unicamente
    private static ArrayList<Double> getPunto2Dim(String entrada){
        ArrayList<Double> salida = new ArrayList<>();
        String aux;
        String linea = entrada;
        // leo primer parametro
        aux = linea.substring(0, linea.indexOf(','));
        if (aux.contains("-")) {
            aux = aux.substring(1);
            salida.add(Double.parseDouble(aux)*-1);
        } else {
            salida.add(Double.parseDouble(aux));
        }
        //leo segundo parametro
        linea = linea.substring(linea.indexOf(',')+1);
        aux = linea.substring(0, linea.indexOf(','));
        if (aux.contains("-")) {
            aux = aux.substring(1);
            salida.add(Double.parseDouble(aux)*-1);
        } else {
            salida.add(Double.parseDouble(aux));
        }
        //leo el tipo de elemento (1 o -1)
        linea = linea.substring(linea.indexOf(',')+1);
        aux = linea.substring(0, linea.indexOf(';'));
        if (aux.contains("-")) {
            salida.add(Double.parseDouble("-1"));
        } else {
            salida.add(Double.parseDouble("1"));
        }
        
//        System.out.println(salida);
        return salida;
    }
    
    public static double[] getAlfas(String entrada, int cantPuntos){
        String aux; double a, b;
        double[] alfas = new double[cantPuntos];
        entrada =  entrada.replaceAll(" ", "");
        for (int i = 0; i < cantPuntos-1; i++) {
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
            alfas[cantPuntos-1] = a/b;
        } else {
            alfas[cantPuntos-1] = Double.parseDouble(aux);
        }
        return alfas;
    }
    
    public static ArrayList<ArrayList<Double>> cargarPuntosClasif(String texto){
        ArrayList<ArrayList<Double>> salida = new ArrayList<>();
        String linea;
        texto = texto.replace("\n", "");
        //obtengo los puntos
        linea = texto.substring(0, texto.indexOf(";")+1);
        while(!linea.equals("")){
            salida.add(getPunto2DimClasif(linea));
            texto = texto.substring(texto.indexOf(";")+1);
            if (texto.contains(";")) {
                linea = texto.substring(0, texto.indexOf(";")+1);
            } else{
                break;
            }
        }
        return salida;
    }
    
    private static ArrayList<Double> getPunto2DimClasif(String linea){
        ArrayList<Double> salida = new ArrayList<>();
        String aux;
        System.out.println(linea);
        // leo primer parametro
        aux = linea.substring(0, linea.indexOf(","));
        if (aux.contains("-")) {
            aux = aux.substring(1);
            salida.add((Double.parseDouble(aux)*-1));
        } else {
            salida.add((Double.parseDouble(aux)));
        }
        //leo segundo parametro
        linea = linea.substring(linea.indexOf(",")+1);
        aux = linea.substring(0, linea.indexOf(";"));
        if (aux.contains("-")) {
            aux = aux.substring(1);
            salida.add((Double.parseDouble(aux)*-1));
        } else {
            salida.add((Double.parseDouble(aux)));
        }        
//        System.out.println(salida);
        return salida;
    }

    public static boolean verificarAlfas(String text) {
//        return patronAlfas.testExact(text);
        return true;
    }

    public static boolean verificarPuntosEntradas(String text) {
//        return patronPE.testExact(text);
        return true;
    }
    
    public static boolean verificarPuntosClasificar(String text) {
//        return patronPC.testExact(text);
        return true;
    }
    
    public static void generarGrafico(){
    
    }
    
    public static ArrayList<Plot> addPuntos(ArrayList<ArrayList<Double>> puntos, Axes axes){
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
                    axes, 7.0, Color.BLUE
                );
            }else{
                puntoG = new Plot(
                    x -> y ,
                    puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                    axes, 7.0, Color.RED
                );
            }
            puntosGraf.add(puntoG);
        }
        return puntosGraf;
    }
    
    public static Plot graficarLinea(double[] w, double b, Axes axes, int l, Color color){
        Plot line = new Plot(
                    x -> (l-b-w[0]*x)/w[1],
                    -8, 8, 1,
                    axes, 1.0, color
            );
        return line;
    }
    
    public static ArrayList<Plot> addPuntosClass(ArrayList<ArrayList<Double>> puntos, ArrayList<Integer> clase, Axes axes){
        //Graficar puntos de la lista
        System.out.println(puntos);
        double ancho = 0.001, diferencia = 0.00051;
        ArrayList<Plot> puntosGraf = new ArrayList<>();
        Plot puntoG;
        for (int i = 0; i < puntos.size(); i++) {
            double y = (double) puntos.get(i).get(1);
            if (clase.get(i) > 0) {
                puntoG = new Plot(
                    x -> y ,
                    puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                    axes, 7.0, Color.BLUE
                );
            } else {
                puntoG = new Plot(
                    x -> y ,
                    puntos.get(i).get(0)-diferencia, puntos.get(i).get(0)+diferencia, ancho,
                    axes, 7.0, Color.YELLOW
                );
            }
            puntosGraf.add(puntoG);
        }
        return puntosGraf;
    }
}
