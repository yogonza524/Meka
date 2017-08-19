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
public class SalidaAprendizaje {
    private double[] w = new double[2];
    private double b = 0;
    private String proceso;
    private String stringMaximizacion;

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getStringMaximizacion() {
        return stringMaximizacion;
    }

    public void setStringMaximizacion(String stringMaximizacion) {
        this.stringMaximizacion = stringMaximizacion;
    }

    public SalidaAprendizaje(int wSize) {
        w = new double[wSize];
    }
    
}
