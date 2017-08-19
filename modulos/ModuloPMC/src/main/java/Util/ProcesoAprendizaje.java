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
public class ProcesoAprendizaje {
    
    ArrayList<String> proceso;
    ArrayList<Double> errorEpoca;

    public ProcesoAprendizaje() {
        proceso = new ArrayList<>();
        errorEpoca = new ArrayList<>();
    }
    
    

    public ArrayList<String> getProceso() {
        return proceso;
    }
    
    public void addProceso(String entrada){
        proceso.add(entrada);
    }

    public ArrayList<Double> getErrorEpoca() {
        return errorEpoca;
    }
    
    public void addError(double entrada){
        errorEpoca.add(entrada);
    }
    
    
}
