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
public class SalidaAprendizajePercep {
    String procesoAprendizaje;
    ArrayList<double[]> pesosProceso;

    public String getProcesoAprendizaje() {
        return procesoAprendizaje;
    }

    public void setProcesoAprendizaje(String procesoAprendizaje) {
        this.procesoAprendizaje = procesoAprendizaje;
    }

    public ArrayList<double[]> getPesosProceso() {
        return pesosProceso;
    }

    public void setPesosProceso(ArrayList<double[]> pesosProceso) {
        this.pesosProceso = pesosProceso;
    }
    
}
