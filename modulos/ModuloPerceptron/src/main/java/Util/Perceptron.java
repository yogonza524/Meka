/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author pichon
 */
public class Perceptron {
    
    private ArrayList<ArrayList<Double>> entradas;
    private double alfa;
    private String funcion;
    private int omega, dim;
    private double[] ws;
    private int iteracionMax = 100;
    private boolean estable = false;

    //Geters y Seters
    public ArrayList<ArrayList<Double>> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<ArrayList<Double>> entradas) {
        this.entradas = entradas;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public int getOmega() {
        return omega;
    }

    public void setOmega(int omega) {
        this.omega = omega;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public double[] getWs() {
        return ws;
    }

    public void setWs(double[] ws) {
        this.ws = ws;
    }

    public boolean isEstable() {
        return estable;
    }

    public void setEstable(boolean estable) {
        this.estable = estable;
    }
    
    //Comenzar aprendizaje del perceptron
    public SalidaAprendizajePercep comenzarAprendizaje(){
        //inicializaciones
        SalidaAprendizajePercep salidaProceso = new SalidaAprendizajePercep();
        String salida = "", espacio = "      ";
        ArrayList<double[]> pesosProceso = new ArrayList<>();
        boolean bandera = false;
        double net, fNet = 0;
        int index = 0, epoca = 0, n = 0;
        salida = salida +(UtilPercep.armarEncabezado(dim, espacio)) + "\n";
        pesosProceso.add(ws.clone());
        
//comienza proceso
        while(n < iteracionMax){
            salida = salida + (index+1 + espacio);
            
            //Mostrar valores de entrada
            for (int i = 0; i < dim+1; i++) {
                double entrada = (double) entradas.get(index).get(i);
                salida = salida + UtilPercep.redondearDecimales(entrada, dim) + espacio;
            }
            //Mostrar valores de pesos
            for (int i = 0; i < dim+1; i++) {
                salida = salida + UtilPercep.redondearDecimales(ws[i], dim) + espacio;
            }
            //Obtener net
            net = -ws[0];
            for (int i = 0; i < dim; i++) {
                net = net + (ws[i+1]*entradas.get(index).get(i));
            }
            //Obtener f(net)
            fNet = TipoFuncion.getTipoFuncion(funcion, net);
            
            //Calcular el error cometido
            Double error = entradas.get(index).get(dim) - fNet;
            
            //Actualizar pesos en caso de error
            if (!funcion.equals("Escalera")) {
//                System.out.println("entro");
                if (error < 0.1) {
                    epoca++;
                }else{
                    epoca = 0;
                    ws[0] = ws[0] + (alfa*error);
                    for (int i = 0; i < dim; i++) {
                        ws[i+1] = ws[i+1] + (alfa*error*entradas.get(index).get(i));
                    }
                    pesosProceso.add(ws.clone());
                }
            }else{
                if (error.equals(new Double("0.0"))) {
                    epoca++;
                }else{
                    epoca = 0;
                    ws[0] = ws[0] + (alfa*error);
                    for (int i = 0; i < dim; i++) {
                        ws[i+1] = ws[i+1] + (alfa*error*entradas.get(index).get(i));
                    }
                    pesosProceso.add(ws.clone());
                }
            }
            
            //Incrementamos indice
            index++;
            if (index == entradas.size()) {
                index = 0;
            }
            //mostrar tabla de resultados
            salida = salida + UtilPercep.redondearDecimales(net, 2) + espacio + fNet + espacio + error + "\n";
            
            //Pregunto si se completo una epoca
            if (epoca == entradas.size()) {
                //sistema estabilizado
                bandera = true;
                estable = true;
                break;
            }
            n++;
        }
        if (bandera) {
            salida = salida + "Sistema estabilizado";
        } else {
            salida = salida + "El sistema no se ha estabilizado en las primeras " + n + " iteraciones";
        }
        salidaProceso.setPesosProceso(pesosProceso);
        salidaProceso.setProcesoAprendizaje(salida);
        return salidaProceso;
    }
    
    public String ClasificarPuntos(ArrayList<ArrayList<Double>> puntos, int dim){
        String salida = "";
        double net;
        double fNet;
        salida = salida + "Clasificacion de puntos ingresados" + "\n";
        for (int i = 0; i < puntos.size(); i++) {
            //Obtener net
            net = -ws[0];
            for (int j = 0; j < dim; j++) {
                net = net + (ws[j+1]*puntos.get(i).get(j));
            }
            //Obtener f(net)
            fNet = TipoFuncion.getTipoFuncion(funcion, net);
            System.out.println(Arrays.toString(ws));
            System.out.println(puntos.get(i));
            System.out.println("net= " + net + " fNet= " + fNet);
            salida = salida + "Entrada " + puntos.get(i) + " salida igual a: " + fNet + "\n";
        }
        return salida;
    }
    
}
