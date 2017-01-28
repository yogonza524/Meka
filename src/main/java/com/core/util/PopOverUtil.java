/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.core.util;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * Utilizadad para mostrar leyendas tipo Pop, utilizado para ayuda
 * @author gonza
 * Github: github.com/yogonza524
 * Web: http://idsoft.com.ar
 * mail: yogonza524@gmail.com
 */
public class PopOverUtil {

    /**
     * Muestra un mensaje Pop sobre un nodo objetivo
     * @param p PopOver a mostrar
     * @param imagen Imagen a mostrar
     * @param objetivo Nodo objetivo a mostrar el PopOver
     */
    public static void mostrarPopConImagen(PopOver p, ImageView imagen, Node objetivo){
        
        BorderPane b = new BorderPane();
        b.setPadding(new Insets(10, 20, 10, 20));
        VBox vbox = new VBox();
        b.setCenter(vbox);
        vbox.getChildren().add(imagen);
        
        p.setContentNode(b);
        
        p.show(objetivo);
    }
}
