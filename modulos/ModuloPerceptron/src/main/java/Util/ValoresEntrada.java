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
public class ValoresEntrada {
    
    private double alfa;
    private int dimensiones;
    private ArrayList<ArrayList<Integer>> puntosEntrada;
    private ArrayList<Double> pesosWs;

    public ArrayList<Double> getPesosWs() {
        return pesosWs;
    }

    public void setPesosWs(ArrayList<Double> pesosWs) {
        this.pesosWs = pesosWs;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public int getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(int dimensiones) {
        this.dimensiones = dimensiones;
    }

    public ArrayList<ArrayList<Integer>> getPuntos() {
        return puntosEntrada;
    }

    public void setPuntos(ArrayList<ArrayList<Integer>> salida) {
        this.puntosEntrada = salida;
    }

    public ValoresEntrada() {
        puntosEntrada = new ArrayList<>();
    }
    
    
    
}
