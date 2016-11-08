/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class SOMController implements Initializable {

    @FXML private TitledPane t1;
    @FXML private Accordion ac;
    @FXML private Button cargar_pesos_btn;
    @FXML private CheckBox pesos_check;
    @FXML private ComboBox funcion_vecindad_combo;
    @FXML private TextField entradas_text;
    @FXML private TextField epocas_text;
    @FXML private TextField neuronas_text;
    @FXML private Button entrenar_btn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initButtons();
        initListeners();
        initComboBoxes();
    }    

    private void initConfig() {
        this.ac.setExpandedPane(t1);
//        this.cargar_pesos_btn.setVisible(false);
    }

    private void initButtons() {
        
    }

    private void initListeners() {
        entradas_text.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    entradas_text.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        epocas_text.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    epocas_text.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        neuronas_text.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    neuronas_text.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        pesos_check.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    cargar_pesos_btn.setVisible(true);
                }
                else{
                    cargar_pesos_btn.setVisible(false);
                }
            }
        });
    }

    private void initComboBoxes() {
        funcion_vecindad_combo.getItems().clear();
        
        funcion_vecindad_combo.getItems().add("Si");
        funcion_vecindad_combo.getItems().add("No");
    }
    
}
