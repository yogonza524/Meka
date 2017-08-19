package com.pichon.modulokmeans;

import Util.Axes;
import Util.Cluster;
import Util.KMeans;
import Util.KMeansResultado;
import Util.Plot;
import Util.Punto;
import Util.UtilKmeans;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class KmeansController implements Initializable {
    
    StackPane layout;
    Axes axes;
    KMeansResultado resul;
    @FXML private TextArea entradasClasificar;
    @FXML private AnchorPane canvas;
    @FXML private TextArea resultado;
    @FXML private ChoiceBox<Integer> cantCluster;
    @FXML private ChoiceBox<String> tipoInicio;
    @FXML private TextArea puntosEntrada;
    @FXML private ChoiceBox<Integer> dimension;
    @FXML private ScrollPane scrollPane;
    @FXML private Label errorEntradas;
    @FXML private Label errorClasificar;
    
    @FXML void clasificarEntradas(ActionEvent event) {
        try{
            //Clasificar puntos
            String entradaClasificar = entradasClasificar.getText();
            ArrayList<ArrayList<Double>> puntosClasificar = UtilKmeans.cargarPuntos(entradaClasificar, dimension.getValue());
            //Agregamos los puntos
            List<Punto> puntos = new ArrayList<>();
            for (int i = 0; i < puntosClasificar.size(); i++) {
                Punto p = new Punto(puntosClasificar.get(i));
                puntos.add(p);
            }
            String clasificacion = resul.ClasificarPuntos(puntos);
            resultado.setText(resultado.getText() + "\n" +  clasificacion);
            errorClasificar.setVisible(false);
        } catch (Exception e) {
            errorClasificar.setVisible(true);
        }

    }

    @FXML void generarCluster(ActionEvent event) {
        int dim = dimension.getValue();
        resultado.setText("");
        try{
            int cantClus = cantCluster.getValue();
            String datos = puntosEntrada.getText();
            ArrayList<ArrayList<Double>> datosPuntos = UtilKmeans.cargarPuntos(datos, dim);
            //Creo el kmeans y lo entreno
            KMeans kmeans = new KMeans();
            kmeans.addPuntos(datosPuntos);
            resul = kmeans.calcular(cantClus, tipoInicio.getValue());
        
            //Clasificacion final
            resultado.setText( resultado.getText() + UtilKmeans.resultadoFinal(resul));
            
            //Mostrar colores de cada cluster
            String[] colores = UtilKmeans.getColorName();
            String colorCluster = "";
            for (int i = 0; i < resul.getCantCluster(); i++) {
                colorCluster = colorCluster + "Cluster" + i + " (" + colores[i] + ")\n";
            }
            resultado.setText(resultado.getText() + colorCluster + "\n");

            //Grafico los clusters
            layout = generarGrafico(datosPuntos); //Genero grafico de puntos
            ArrayList<ArrayList<Plot>> puntosPlot = addCluster(layout, resul.getHistorialCluster());
            for (int i = 0; i < puntosPlot.size(); i++) {
                for (int j = 0; j < puntosPlot.get(i).size(); j++) {
                    layout.getChildren().add(puntosPlot.get(i).get(j));                 
                }
            }
            //Agrego las lineas del cluster a los puntos
            ArrayList<Plot> lineasCluster = generarLineasUnion(resul);
            for (int i = 0; i < lineasCluster.size(); i++) {
                layout.getChildren().add(lineasCluster.get(i));                 
            }

            canvas.getChildren().clear();
            canvas.getChildren().add(layout);

            //Agregar las etiquetas
            ArrayList<Punto> clusters = kmeans.getCluster();
            int centroX = (int) canvas.getWidth()/2;
            int centroY = (int) canvas.getHeight()/2;
            for (int i = 0; i < cantClus; i++) {
                Float[] coordenadas = clusters.get(i).getData();
                int valorX = Math.round(centroX+(coordenadas[0]*20)-40);
                int valorY = Math.round(centroY-(coordenadas[1]*20));
                Text t = drawText("CLUSTER "+i, valorX, valorY);
                canvas.getChildren().add(t);
                StackPane.setAlignment(t, Pos.TOP_LEFT);
            }
            scrollPane.setHvalue(scrollPane.getHmax()/2);
            scrollPane.setVvalue(scrollPane.getVmax()/2);
            
            errorEntradas.setVisible(false);
        } catch (Exception e) {
            errorEntradas.setVisible(true);
        }
    }

    @FXML void resetear(ActionEvent event) {
        canvas.getChildren().clear();
        resultado.setText("");
        puntosEntrada.setText("");
        dimension.setValue(2);
        cantCluster.setValue(2);
        tipoInicio.setValue("Aleatoria");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dimension.getItems().addAll(
            2, 3, 4, 5
        );
        tipoInicio.getItems().addAll(
            "Aleatoria", 
            "Forgy"
        );
        cantCluster.getItems().addAll(
            2, 3, 4, 5, 6
        );
        dimension.setValue(2);
        cantCluster.setValue(2);
        tipoInicio.setValue("Aleatoria");
        
        errorEntradas.setVisible(false);
        errorClasificar.setVisible(false);
    }
    
    private StackPane generarGrafico(ArrayList<ArrayList<Double>> datosPuntos){
        //Generar ejes cartesianos
        axes = new Axes(
                800, 800,
                -20, 20, 2,
                -20, 20, 2
        );
        //generamos el layoutAux
        StackPane layoutAux = new StackPane();
        layoutAux.setPadding(new Insets(20));
        layoutAux.setStyle("-fx-background-color: rgb(0, 0, 0);"); //(35, 39, 50)
        layoutAux.setPrefSize(canvas.getWidth() -1, canvas.getHeight() -1);
        canvas.getChildren().add(layoutAux);
        
        //Agregamos los puntos
        ArrayList<Plot> puntosGraficar = UtilKmeans.addPuntosGrafic(datosPuntos, axes);
        for (int i = 0; i < puntosGraficar.size(); i++) {
            layoutAux.getChildren().add(puntosGraficar.get(i));
        }
        return layoutAux;
    }
    
    private ArrayList<ArrayList<Plot>> addCluster(StackPane layout, ArrayList<ArrayList<Cluster>> historiaCluster){
        ArrayList<ArrayList<Plot>> salida = new ArrayList<>();
        for (int i = 0; i < historiaCluster.size(); i++) {
            ArrayList<ArrayList<Double>> cluster = UtilKmeans.getCoordenadasCluster(historiaCluster.get(i));
            ArrayList<Plot> puntosGraficar = UtilKmeans.addClustGrafic(cluster, axes);
            salida.add(puntosGraficar);
        }
        return salida;
    }
    
    private ArrayList<Plot> generarLineasUnion(KMeansResultado resultado){
        ArrayList<Plot> salida = new ArrayList<>();
        for (int i = 0; i < resultado.getClusters().size(); i++) {
            List<Punto> puntos = resultado.getClusters().get(i).getPuntos();
            for (int j = 0; j < puntos.size(); j++) {
                salida.add(UtilKmeans.graficarLineaACluster(puntos.get(j), resultado.getClusters().get(i).getCentroide(), axes, i));
            }
        }
        return salida;
    }
    
    private Text drawText(String nombre, int x, int y){
        Text t = new Text();
        t.setFill(Color.WHITE);
        t.setText(nombre);
        t.setX(x);
        t.setY(y);
        t.setId("textNegrita");
        return t;
    }
}
