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
public class NodoArbolDecision {
    
    private String nombeAtributo;
    private int indice;
    private ArrayList<NodoArbolDecision> hijos;
    private ArrayList<String> variablesAtributo;
    private ArrayList<String> clase;

    public String getNombeAtributo() {
        return nombeAtributo;
    }

    public void setNombeAtributo(String nombeAtributo) {
        this.nombeAtributo = nombeAtributo;
    }

    public ArrayList<NodoArbolDecision> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<NodoArbolDecision> hijos) {
        this.hijos = hijos;
    }

    public ArrayList<String> getVariablesAtributo() {
        return variablesAtributo;
    }

    public void setVariablesAtributo(ArrayList<String> variablesAtributo) {
        this.variablesAtributo = variablesAtributo;
    }

    public ArrayList<String> getClase() {
        return clase;
    }

    public void setClase(ArrayList<String> clase) {
        this.clase = clase;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
    
    
}
