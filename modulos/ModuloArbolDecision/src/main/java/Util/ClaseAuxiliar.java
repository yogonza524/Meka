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
public class ClaseAuxiliar {
    private String proceso;
    private ArrayList<Double> valores;
    private double entropia;
    private NodoArbolDecision estructuraArbol;
    private boolean nodo = true;
    private String clase;
    //En caso de que un patronos no se pueda clasificar
    private boolean indefinida = false;
    private ArrayList<String> clasePorcentaje;
    private ArrayList<Double> porcentaje;

    public double getEntropia() {
        return entropia;
    }

    public void setEntropia(double entropia) {
        this.entropia = entropia;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public ArrayList<Double> getValores() {
        return valores;
    }

    public void setValores(ArrayList<Double> valores) {
        this.valores = valores;
    }

    public NodoArbolDecision getEstructuraArbol() {
        return estructuraArbol;
    }

    public void setEstructuraArbol(NodoArbolDecision estructuraArbol) {
        this.estructuraArbol = estructuraArbol;
    }

    public boolean isNodo() {
        return nodo;
    }

    public void setNodo(boolean nodo) {
        this.nodo = nodo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public boolean isIndefinida() {
        return indefinida;
    }

    public void setIndefinida(boolean indefinida) {
        this.indefinida = indefinida;
    }

    public ArrayList<String> getClasePorcentaje() {
        return clasePorcentaje;
    }

    public void setClasePorcentaje(ArrayList<String> clasePorcentaje) {
        this.clasePorcentaje = clasePorcentaje;
    }

    public ArrayList<Double> getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(ArrayList<Double> porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
}
