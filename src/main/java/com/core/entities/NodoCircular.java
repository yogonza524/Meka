/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.entities;

import com.core.entity.Nodo;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author gonza
 */
public class NodoCircular extends Circle{
    
    private Nodo nodo;

    public NodoCircular() {
    }

    public NodoCircular(double radius) {
        super(radius);
    }

    public NodoCircular(double radius, Paint fill) {
        super(radius, fill);
    }

    public NodoCircular(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
    }

    public NodoCircular(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
    }

    public NodoCircular(Nodo nodo, double radius) {
        super(radius);
        this.nodo = nodo;
    }

    public NodoCircular(Nodo nodo, double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        this.nodo = nodo;
    }

    public NodoCircular(Nodo nodo, double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
        this.nodo = nodo;
    }

    public NodoCircular(Nodo nodo, double radius, Paint fill) {
        super(radius, fill);
        this.nodo = nodo;
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }
    
    
}
