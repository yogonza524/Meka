/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author pichon
 */
public class Pesos {
    private SimpleStringProperty conexion;
    private SimpleDoubleProperty peso;

    private void Person(String fConex, double fPeso) {
        this.conexion = new SimpleStringProperty(fConex);
        this.peso = new SimpleDoubleProperty(fPeso);
    }

    public SimpleStringProperty getConexion() {
        return conexion;
    }

    public void setConexion(SimpleStringProperty conexion) {
        this.conexion = conexion;
    }

    public SimpleDoubleProperty getPeso() {
        return peso;
    }

    public void setPeso(SimpleDoubleProperty peso) {
        this.peso = peso;
    }
    
    
}
