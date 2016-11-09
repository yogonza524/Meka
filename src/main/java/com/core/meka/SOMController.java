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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class SOMController implements Initializable {

    @FXML private AnchorPane base;
    
    @FXML private TitledPane t1;
    @FXML private Accordion ac;
    @FXML private CheckBox pesos_check;
    @FXML private ComboBox funcion_vecindad_combo;
    @FXML private TextField entradas_text;
    @FXML private TextField epocas_text;
    @FXML private TextField neuronas_text;
    @FXML private TextArea patrones_train_text;
    @FXML private TextArea pesos_text;
    @FXML private Button entrenar_btn;
    @FXML private Label pesos_label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initButtons();
        initListeners();
        initComboBoxes();
    }    

    private void initConfig() {
        this.ac.setExpandedPane(t1);
        pesos_label.setVisible(false);
        pesos_text.setVisible(false);
        pesos_check.setSelected(true);
    }

    private void initButtons() {
        entrenar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (entradas_text.getText().isEmpty()) {
                    return;
                }
                if(epocas_text.getText().isEmpty()){
                    return;
                }
                if (neuronas_text.getText().isEmpty()) {
                    return;
                }
                if (patrones_train_text.getText().isEmpty()) {
                    return;
                }
                if (funcion_vecindad_combo.getSelectionModel().getSelectedIndex() < 0) {
                    return;
                }
                //Valores correctos
                String[] patrones = patrones_train_text.getText().replaceAll("\\s+","").replaceAll("\\n", "").split(";");
                if (patrones.length == 0) {
                    return;
                }
                
                
            }
        });
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
                    pesos_label.setVisible(true);
                    pesos_text.setVisible(true);
                }
                else{
                    pesos_label.setVisible(false);
                    pesos_text.setVisible(false);
                }
            }
        });
        base.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    Util.closeWindow(ac);
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
