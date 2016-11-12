/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import com.core.controllers.SOMManager;
import com.core.somcluster.HyperbolicFunction;
import com.core.somcluster.SOMCluster;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
    @FXML private Tab test_tab;
    @FXML private Accordion ac;
    @FXML private Accordion act1;
    @FXML private Accordion act2;
    @FXML private Accordion act3;
    @FXML private TitledPane tact1;
    @FXML private TitledPane tact2;
    @FXML private TitledPane tact3;
    @FXML private CheckBox pesos_check;
    @FXML private ComboBox funcion_vecindad_combo;
    @FXML private TextField entradas_text;
    @FXML private TextField epocas_text;
    @FXML private TextField neuronas_text;
    @FXML private TextArea patrones_train_text;
    @FXML private TextArea pesos_text;
    @FXML private Button entrenar_btn;
    @FXML private Button borrar_btn;
    @FXML private Label pesos_label;
    @FXML private TextArea result_text;
    
    private SOMCluster som;
    
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
        this.act1.setExpandedPane(tact1);
        this.act2.setExpandedPane(tact2);
        this.act3.setExpandedPane(tact3);
    }

    private void initButtons() {
        entrenar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (neuronas_text.getText().isEmpty()) {
                    String result = "Debe ingresar una cantidad de neuronas";
                    result += "\nPor ejemplo: cantidad = 4, entonces la red contendra 4 neuronas en total";
                    result_text.setText(result);
                    return;
                }
                if(epocas_text.getText().isEmpty()){
                    String result = "Debe ingresar una cantidad de epocas";
                    result += "\nPor ejemplo: epocas = 5, entonces la red se estabilizara luego de 5 ciclos de entrenamiento";
                    result_text.setText(result);
                    return;
                }
                if (entradas_text.getText().isEmpty()) {
                    String result = "Debe ingresar una cantidad de entradas para el entrenamiento";
                    result += "\nPor ejemplo: entradas = 3, entonces [4,2,5] es un patron valido de entrenamiento";
                    result_text.setText(result);
                    return;
                }
                if (patrones_train_text.getText().isEmpty()) {
                    String result = "Debe ingresar algun patron de entrenamiento";
                    result += "\nPor ejemplo: patrones = [1,2,4],[6,1,3], entonces la red tendra 2 patrones de entrenamiento validos";
                    result_text.setText(result);
                    return;
                }
                if (funcion_vecindad_combo.getSelectionModel().getSelectedIndex() < 0) {
                    String result = "Debe elegir si desea utilizar una funcion de vecindad";
                    result_text.setText(result);
                    return;
                }
                //Valores correctos
                Integer dimension = Integer.valueOf(entradas_text.getText());
                boolean formatoPatronesEntrenamiento = SOMManager.validarDimensionArreglo(dimension, patrones_train_text.getText());
                if (!formatoPatronesEntrenamiento) {
                    String result = "Los patrones ingresados no tienen una dimension correcta";
                    result += "\nPor ejemplo: 1.3,2.6 ; 5.6,22,55,66 (2 patrones) es incorrecto Dimension(p1) = 2 y Dimension(p2) = 4";
                    result_text.setText(result);
                    return;
                }
                
                if (!pesos_check.isSelected()) {
                    boolean formatoPesos = SOMManager.validarDimensionArreglo(dimension, pesos_text.getText());
                    if (!formatoPesos) {
                        String result = "Los pesos ingresados no tienen una dimension correcta";
                        result += "\nPor ejemplo: 1.8,3.6 ; 7.6,12,5,1 (2 patrones) es incorrecto Dimension(p1) = 2 y Dimension(p2) = 4";
                        result_text.setText(result);
                        return;
                    }
                }
                
                //Correcto, realizar operaciones solicitadas
                Integer epocas = Integer.valueOf(epocas_text.getText());
                Boolean pesosAleatorios = pesos_check.isSelected();
                Boolean utilizarFuncionVecindad = funcion_vecindad_combo.getSelectionModel().getSelectedIndex() == 0;
                Integer clusters = Integer.valueOf(neuronas_text.getText());
                double[][] patrones = crearArreglo(patrones_train_text.getText().replaceAll("\\s+","").replaceAll("\\n+","").split(";"));
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        crearCluster(dimension, 
                            epocas, 
                            pesosAleatorios, 
                            utilizarFuncionVecindad, 
                            clusters,
                            patrones,
                            (pesosAleatorios ? null : crearArreglo(pesos_text.getText().replaceAll("\\s+","").replaceAll("\\n+","").split(";"))));
                        test_tab.setDisable(false);
                    }
                });
            }

            private double[][] crearArreglo(String[] patrones) {
                int totalPatrones = patrones.length;
                int dimension = patrones[0].split(",").length;
                double[][] result = new double[totalPatrones][dimension];
                
                for (int i = 0; i < totalPatrones; i++) {
                    String[] patron = patrones[i].split(",");
                    double[] patronDouble = new double[dimension];
                    for (int j = 0; j < patron.length; j++) {
                        patronDouble[j] = Double.valueOf(patron[j]);
                    }
                    result[i] = patronDouble;
                }
                
                return result;
            }

            private void crearCluster(Integer dimension, Integer epocas, Boolean pesosAleatorios, Boolean utilizarFuncionVecindad, Integer clusters, double[][] patrones, double[][] pesos) {
                SOMCluster.SOMClusterBuilder somBuilder = new SOMCluster.SOMClusterBuilder()
                        .dimension(dimension)
                        .epochs(epocas)
                        .clusters(clusters)
                        ;
                if (utilizarFuncionVecindad) {
                    somBuilder.neighborhoodFunction(new HyperbolicFunction())
                            .updateNeighborhood(true)
                            ;
                }
                if (!pesosAleatorios) {
                    somBuilder.weights(pesos);
                }
                
                som = somBuilder.build();
                
                //Entrenamiento
                som.train(patrones);
                result_text.setText(som.getTrainingLOG()); //Muestro el entrenamiento
            }
        });
        borrar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                result_text.setText("");
                pesos_text.setText("");
                patrones_train_text.setText("");
                entradas_text.setText("");
                epocas_text.setText("");
                neuronas_text.setText("");
                test_tab.setDisable(true);
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
