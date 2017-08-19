/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author pichon
 */
public class PercepFun {
    
    private SimpleStringProperty perceptron;
    private SimpleStringProperty funcion;

    public PercepFun(String perceptron, String funcion) {
        this.perceptron = new SimpleStringProperty(perceptron);
        this.funcion = new SimpleStringProperty(funcion);
    }
    
    public String getPerceptron(){
        return perceptron.get();
    }
    
    public String getFuncion(){
        return funcion.get();
    }
    
    public void setPerceptron(String p) {
        perceptron.set(p);
    }
    
    public void setFuncion(String f) {
        funcion.set(f);
    }
}
