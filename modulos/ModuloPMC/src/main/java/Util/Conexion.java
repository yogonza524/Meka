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
public class Conexion {
    
    private String nombre;
    private double valor;
    private double peso;
    private Perceptron perceptronEntrada;
    private Perceptron perceptronSalida;

    public Perceptron getPerceptronEntrada() {
        return perceptronEntrada;
    }

    public void setPerceptronEntrada(Perceptron perceptronEntrada) {
        this.perceptronEntrada = perceptronEntrada;
    }

    public Perceptron getPerceptronSalida() {
        return perceptronSalida;
    }

    public void setPerceptronSalida(Perceptron perceptronSalida) {
        this.perceptronSalida = perceptronSalida;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
