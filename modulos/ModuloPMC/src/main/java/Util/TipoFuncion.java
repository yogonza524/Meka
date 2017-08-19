/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author pichon
 */
public class TipoFuncion {
    private static final double C = 4.0;
    
    public static double getTipoFuncion(String tipoFuncion, double net){
        double salida = 0;
        if (tipoFuncion.equals("Lineal")) {
            salida = lineal(net);
        }
        if (tipoFuncion.equals("Sigmoidal")) {
            salida = sigmoid(net);
        }
        return salida;
    }
    
    public static double getDerivadaTipoFuncion(String tipoFuncion, double net){
        double salida = 0;
        if (tipoFuncion.equals("Lineal")) {
            salida = 1;
        }
        if (tipoFuncion.equals("Sigmoidal")) {
            salida = sigmoid(net)*(1-sigmoid(net));
        }
        return salida;
    }
    
    private static double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-x))));
    }
    
    private static double lineal(double x) {
        double salida;
        if (x != Double.NaN) {
            if (x < (-C)) {
            salida = 0;
        } else {
            if (x > C) {
                salida = 1;
            } else {
                salida = (x/(C*2))+1/2;
            }
        }
         System.out.println(salida);
        }
        else{
            System.out.println("Sin valor cargado");
        }
        return x;
    }
}
