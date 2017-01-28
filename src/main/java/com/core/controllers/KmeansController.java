package com.core.controllers;

import com.core.kmeans.Axes;
import com.core.kmeans.Cluster;
import com.core.kmeans.KMeans;
import com.core.kmeans.KMeansResultado;
import com.core.kmeans.Plot;
import com.core.kmeans.Punto;
import com.core.kmeans.UtilKmeans;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

public class KmeansController implements Initializable {
    
    private StackPane layout;
    private Axes axes;
    private KMeansResultado resul;
    @FXML private TextArea entradasClasificar;
    @FXML private StackPane canvas;
    @FXML private TextArea resultado;
    @FXML private ChoiceBox<Integer> cantCluster;
    @FXML private ChoiceBox<String> tipoInicio;
    @FXML private TextArea puntosEntrada;
    @FXML private ChoiceBox<Integer> dimension;
    @FXML private ScrollPane scrollPane;
    
    @FXML private ComboBox<Integer> xCombo;
    @FXML private ComboBox<Integer> yCombo;
    
    @FXML void clasificarEntradas(ActionEvent event) {
        
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
        resultado.setScrollTop(Double.MAX_VALUE);
    }

    @FXML void generarCluster(ActionEvent event) {
        
        if (xCombo.getSelectionModel().getSelectedIndex() < 0) {
            resultado.appendText("Seleccione un intervalo para X");
            resultado.setScrollTop(Double.MAX_VALUE);
            return;
        }
        if (yCombo.getSelectionModel().getSelectedIndex() < 0) {
            resultado.appendText("Seleccione un intervalo para XY");
            resultado.setScrollTop(Double.MAX_VALUE);
            return;
        }
        
        int x = xCombo.getSelectionModel().getSelectedItem();
        int y = xCombo.getSelectionModel().getSelectedItem();
        
        dibujarCluster(x,y);
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
        initCombo();
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
        layoutAux.setStyle("-fx-background-color: rgb(35, 39, 50);");
        layoutAux.setPrefSize(canvas.getWidth() -1, canvas.getHeight() -1);
        canvas.getChildren().add(layoutAux);
        //Agregamos los puntos
        ArrayList<Plot> puntosGraficar = UtilKmeans.addPuntosGrafic(datosPuntos, axes);
        for (int i = 0; i < puntosGraficar.size(); i++) {
            layoutAux.getChildren().add(puntosGraficar.get(i));
        }
        return layoutAux;
    }
    
    private StackPane generarGrafico(ArrayList<ArrayList<Double>> datosPuntos, int x, int y){
        //Generar ejes cartesianos
        axes = new Axes(
                800, 800,
                -x, x, 2,
                -y, y, 2
        );
        //generamos el layoutAux
        StackPane layoutAux = new StackPane();
        layoutAux.setPadding(new Insets(20));
        layoutAux.setStyle("-fx-background-color: rgb(35, 39, 50);");
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
    
    @FXML private void mostrarAyudaDimensiones(ActionEvent e){
        mostrarPopConImagen(new PopOver(), new ImageView(new Image("/img/ayuda2.png")), dimension);
    }
    
    @FXML private void mostrarAyudaClusters(ActionEvent e){
        mostrarPopConImagen(new PopOver(), new ImageView(new Image("/img/ayuda11.png")), cantCluster);
    }
    
    @FXML private void mostrarPatronesDeEntrenamiento(ActionEvent e){
        mostrarPopConImagen(new PopOver(), new ImageView(new Image("/img/ayuda9.png")), puntosEntrada);
    }
    
    @FXML private void mostrarAyudaClasificacion(ActionEvent e){
        mostrarPopConImagen(new PopOver(), new ImageView(new Image("/img/ayuda3.png")), entradasClasificar);
    }
    
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
    
    @FXML
    public void cerrarVentana(ActionEvent e){
        closeWindow(scrollPane);
    }
    
    public static void closeWindow(Node c){
        Stage stage = (Stage) c.getScene().getWindow();
        stage.close();
    }

    @FXML private void dibujarRed(ActionEvent e){
        if (xCombo.getSelectionModel().getSelectedIndex() < 0) {
            resultado.appendText("Seleccione un intervalo para X");
            resultado.setScrollTop(Double.MAX_VALUE);
            return;
        }
        if (yCombo.getSelectionModel().getSelectedIndex() < 0) {
            resultado.appendText("Seleccione un intervalo para XY");
            resultado.setScrollTop(Double.MAX_VALUE);
            return;
        }
        
        int x = xCombo.getSelectionModel().getSelectedItem();
        int y = xCombo.getSelectionModel().getSelectedItem();
        
        dibujarCluster(x,y);
    }
    
    private void dibujarCluster(int x, int y) {
        int dim = dimension.getValue();
        resultado.setText("");
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
            colorCluster = colorCluster + "Cluster " + i + " (" + colores[i] + ")\n";
        }
        resultado.setText(resultado.getText() + colorCluster + "\n");
        
        //Grafico los clusters
        layout = generarGrafico(datosPuntos, x ,y); //Genero grafico de puntos
        ArrayList<ArrayList<Plot>> puntosPlot= addCluster(layout, resul.getHistorialCluster());
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
        scrollPane.setHvalue(scrollPane.getHmax()/2);
        scrollPane.setVvalue(scrollPane.getVmax()/2);
        resultado.setScrollTop(Double.MAX_VALUE);
    }

    private void initCombo() {
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
        
        xCombo.getItems().addAll(20,40,60,80,100,200,500,1000);
        yCombo.getItems().addAll(20,40,60,80,100,200,500,1000);
        
        xCombo.getSelectionModel().selectFirst();
        yCombo.getSelectionModel().selectFirst();
    }
}
