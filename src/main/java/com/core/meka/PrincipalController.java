/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class PrincipalController implements Initializable {

    @FXML private VBox busqueda_vbox;
    @FXML private VBox SBR_vbox;
    @FXML private VBox mlp_vbox;
    @FXML private VBox perceptron_vbox;
    @FXML private VBox bayes_vbox;
    @FXML private VBox geneticos_vbox;
    @FXML private VBox agentes_vbox;
    @FXML private VBox reglas_asoc_vbox;
    @FXML private VBox cluster_vbox;
    @FXML private VBox som_vbox;
    @FXML private VBox hopfield_vbox;
    @FXML private VBox svm_vbox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initButtons();
        initListeners();
        initComboBoxes();
    }    

    private void initConfig() {
        busqueda_vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    Util.showMainWindowMaximized("Busqueda en grandes espacios", "/fxml/Busqueda.fxml", BusquedaController.class, true, false);
                }
            }
        });
        
    }

    private void initButtons() {
        
    }

    private void initListeners() {
        
    }

    private void initComboBoxes() {
        
    }
    
}
