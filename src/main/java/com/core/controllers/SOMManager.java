/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.core.controllers;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 *
 * @author gonza
 * Github: github.com/yogonza524
 * Web: http://idsoft.com.ar
 * mail: yogonza524@gmail.com
 */
public class SOMManager {
    
    public static boolean validarDimensionArreglo(int dimension, String arreglo){
        String formateado = arreglo.replaceAll("\\s+","").replaceAll("\\n+","");
        String[] validar = formateado.split(";");
        boolean result = validar.length > 0;
        System.out.println("Formateado: " + formateado + "\nValido: " + result);
        for(String v : validar){
            String[] patron = v.split(",");
            if (patron.length != dimension) {
                result = false;
                break;
            }
        }
        return result;
    }
}
