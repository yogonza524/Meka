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
public class AlmacenDatos {
    ArrayList<ArrayList<Punto>> puntosVecinos;
    String datosProceso;

    public ArrayList<ArrayList<Punto>> getPuntosVecinos() {
        return puntosVecinos;
    }

    public void setPuntosVecinos(ArrayList<ArrayList<Punto>> puntosVecinos) {
        this.puntosVecinos = puntosVecinos;
    }

    public String getDatosProceso() {
        return datosProceso;
    }

    public void setDatosProceso(String datosProceso) {
        this.datosProceso = datosProceso;
    }
    
}
