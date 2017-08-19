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
public class ConexPeso {
    
    private SimpleStringProperty conexion;
    private SimpleStringProperty peso;

    public ConexPeso(String conexion, String peso) {
        this.conexion = new SimpleStringProperty(conexion);
        this.peso = new SimpleStringProperty(peso);
    }
    
    public String getConexion(){
        return conexion.get();
    }
    
    public String getPeso(){
        return peso.get();
    }
    
    public void setConexion(String conexion) {
        this.conexion.set(conexion);
    }
    
    public void setPeso(String peso) {
        this.peso.set(peso);
    }
    
}
