package com.pichon.moduloperceptron;

import Util.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PerceptronController implements Initializable {
    
    StackPane layout;
    Axes axes;
    Perceptron perceptron;
    @FXML private TextArea entradasClasificar;
    @FXML private TextField pesosW;
    @FXML private TextArea resultado;
    @FXML private TextField valorAlfa;
    @FXML private TextArea patronesEntradas;
    @FXML private ChoiceBox<Integer> dimension;
    @FXML private ChoiceBox<String> tipoFuncion;
    @FXML private StackPane canvas;
    @FXML private Label errorEntradas;
    @FXML private Label errorPesos;
    @FXML private Label errorClasificar;

    @FXML
    void generarPerceptron(ActionEvent event) {
        //Cargo entradas al perceptron
        perceptron = new Perceptron();
        perceptron.setDim(dimension.getValue());
        perceptron.setAlfa(Double.parseDouble(valorAlfa.getText()));
        perceptron.setFuncion(tipoFuncion.getValue());
        try {   //Verifico que las entradas estan bien ingresadas
            perceptron.setEntradas(UtilPercep.cargarPuntos(patronesEntradas.getText(), perceptron.getDim()));
            errorEntradas.setVisible(false);
        } catch (Exception e) {
            errorEntradas.setVisible(true);
            return;
        }

        try {   //Verifico que los pesos estan bien ingresados
            perceptron.setWs(UtilPercep.cargarWs(pesosW.getText(), perceptron.getDim()));
            errorPesos.setVisible(false);
        } catch (Exception e) {
            errorPesos.setVisible(true);
            return;
        }
        
        //Comienza el proceso de aprendizaje
        SalidaAprendizajePercep procesoAprendizaje = perceptron.comenzarAprendizaje();
        resultado.setText( resultado.getText() + procesoAprendizaje.getProcesoAprendizaje());
        
        //redondear valores para q se vean bien
        double[] pesosEstables = perceptron.getWs();
        for (int i = 0; i < pesosEstables.length; i++) {
            pesosEstables[i] = UtilPercep.redondearDecimales(pesosEstables[i], 2);
        }
        perceptron.setWs(pesosEstables);
        resultado.setText( resultado.getText() + "\nPesos estables:" + Arrays.toString(perceptron.getWs()));
        
        //Generar ejes cartesianos
        axes = new Axes(
                800, 800,
                -8, 8, 1,
                -8, 8, 1
        );
        
        //generamos el layout
        layout = new StackPane();
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(0, 0, 0);");
        layout.setPrefSize(canvas.getWidth() -1, canvas.getHeight() -1);
        canvas.getChildren().add(layout);
        
        //Graficar lineas
        ArrayList<double[]> pesosProceso = procesoAprendizaje.getPesosProceso();
        layout.getChildren().add(UtilPercep.graficarLinea(pesosProceso.get(0), axes, Color.YELLOW, 1.5));
        if (pesosProceso.size() > 1) {
            for (int i = 1; i < pesosProceso.size()-1; i++) {
                layout.getChildren().add(UtilPercep.graficarLinea(pesosProceso.get(i), axes, Color.GRAY, 0.5));
            }
        }
        if(perceptron.isEstable()){
            layout.getChildren().add(UtilPercep.graficarLinea(perceptron.getWs(), axes, Color.GREENYELLOW, 1.5));
        }else{
            layout.getChildren().add(UtilPercep.graficarLinea(perceptron.getWs(), axes, Color.RED, 1.5));
        }
        
        //Agregamos los puntos
        ArrayList<Plot> puntosGraficar = UtilPercep.addPuntosGrafic(perceptron.getEntradas(), axes);
        for (int i = 0; i < puntosGraficar.size(); i++) {
            layout.getChildren().add(puntosGraficar.get(i));
        }

    }
    
    @FXML
    void clasificarEntradas(ActionEvent event) {
        try {
            //Clasificar puntos
            String entradaClasificar = entradasClasificar.getText();
            ArrayList<ArrayList<Double>> puntosClasificar = UtilPercep.cargarPuntos(entradaClasificar, perceptron.getDim()-1);
            String clasificacion = perceptron.ClasificarPuntos(puntosClasificar, perceptron.getDim());
            resultado.setText(resultado.getText() + "\n\n**********************\n" +  clasificacion);

            //Agregamos los puntos
            ArrayList<Plot> puntosGraficar = UtilPercep.addPuntosGraficClass(puntosClasificar, axes);
            for (int i = 0; i < puntosGraficar.size(); i++) {
                layout.getChildren().add(puntosGraficar.get(i));
            }
            errorClasificar.setVisible(false);
        } catch (Exception e) {
            errorClasificar.setVisible(true);
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dimension.getItems().addAll(
            2, 3, 4, 5
        );
        tipoFuncion.getItems().addAll(
            "Escalera", 
            "Lineal",
            "Sigmoidal"
        );
        dimension.setValue(2);
        tipoFuncion.setValue("Escalera");
        
        errorEntradas.setVisible(false);
        errorPesos.setVisible(false);
        errorClasificar.setVisible(false);
    }
}
