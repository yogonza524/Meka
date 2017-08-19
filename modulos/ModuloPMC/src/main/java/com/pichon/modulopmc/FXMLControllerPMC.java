package com.pichon.modulopmc;

import Util.Conexion;
import Util.PMC;
import Util.PercepFun;
import Util.Perceptron;
import Util.Pesos;
import Util.ProcesoAprendizaje;
import Util.UtilPMC;
import Util.ConexPeso;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sun.awt.AWTAccessor;

public class FXMLControllerPMC implements Initializable {
    
    private PMC redNeuronal;
    private ObservableList<PercepFun> dataPerceptron;
    private ObservableList<ConexPeso> dataConexiones;
    private SingleSelectionModel<Tab> selectionModel;
    @FXML private TextArea resultado;
    @FXML private Tab topologiaGraf;
    @FXML private TextField capasOcultas;
    @FXML private TextArea patronesEntrada;
    @FXML private TextArea entradasClasificar;
    @FXML private TextField entradas;
    @FXML private TextField perceptronXCapa;
    @FXML private TableView tablaFuncion;
    @FXML private TextArea patronesSalida;
    @FXML private ChoiceBox<String> funcion;
    @FXML private Tab aprendizajeGraf;
    @FXML private TextField salidas;
    @FXML private TableView tablaPesos;
    @FXML private TextField errorAceptable;
    @FXML private TextField valorAlfa;
    @FXML private Tab pestanaAprendizaje;
    @FXML private Tab pestanasFuncion;
    @FXML private Tab pestanaPeso;
    @FXML private StackPane canvas;
    @FXML private StackPane canvasGrafico;
    @FXML private TabPane tabPanelGraficos;
    @FXML private Label errorEntradas;
    @FXML private Label errorClasificar;

    @FXML void clasificarEntradas(ActionEvent event) {
        try {
            resultado.setText(resultado.getText() + "\n - - - - - - - - - - - - - \n Clasificacion de entradas:\n");
            ArrayList<ArrayList<Double>> entradaClasificar = UtilPMC.cargarPuntos(entradasClasificar.getText(), redNeuronal.getCantEntradas());
            for (int i = 0; i < entradaClasificar.size(); i++) {
                String salidaProceso = redNeuronal.runRedNeuronal(entradaClasificar.get(i));
                resultado.setText(resultado.getText()+salidaProceso+"\n");
            }
            errorClasificar.setVisible(false);
        } catch (Exception e) {
            errorClasificar.setVisible(true);
        }
    }

    //Generar la topologia de la red
    @FXML void generarRed(ActionEvent event) {
        redNeuronal = new PMC();
        //Ingreso datos topologia de la red
        redNeuronal.setCantEntradas(Integer.parseInt(entradas.getText()));
        redNeuronal.setCantCapasOculta(Integer.parseInt(capasOcultas.getText()));
        redNeuronal.setCantNeuronasCapasOcultaI(Integer.parseInt(perceptronXCapa.getText()));
        redNeuronal.setCantSalida(Integer.parseInt(salidas.getText()));
        redNeuronal.setErrorAceptable(Double.valueOf(errorAceptable.getText()));
        redNeuronal.setAlfa(Double.valueOf(valorAlfa.getText()));
        redNeuronal.setFuncion(funcion.getValue());
        resultado.setText(redNeuronal.informeRed() + "\n \n");
        redNeuronal.inicializarRedNeuronal();
        
        //habilitar las otras pestañas
        pestanaAprendizaje.setDisable(false);
        pestanasFuncion.setDisable(false);
        pestanaPeso.setDisable(false);
        
        //Cargar tabla de funciones
        tablaFuncion = generarTablaFuncion(tablaFuncion);
        
        //Cargar tabla de pesos
        tablaPesos = generarTablaPesos(tablaPesos);
        
        //Graficar topologia de reg
        canvas.getChildren().clear();
        canvas.getChildren().add(dibujarTopologiaRed(redNeuronal));
    }

    @FXML void ensenarPatrones(ActionEvent event) {
        try{
            ArrayList<ArrayList<Double>> valoresEntrada = UtilPMC.cargarPuntos(patronesEntrada.getText(), redNeuronal.getCantEntradas());
            ArrayList<ArrayList<Double>> valoresSalida = UtilPMC.cargarPuntos(patronesSalida.getText(), redNeuronal.getCantSalida());
            //Ingresar patrones de entrada
            redNeuronal.setValorEntradas(valoresEntrada);
            //Ingresar patrones de salida
            redNeuronal.setValorSalida(valoresSalida);
            errorEntradas.setVisible(false);
        } catch (Exception e) {
            errorEntradas.setVisible(true);
            return;
        }
        
        //Comenzar proceso de aprendizaje
        ProcesoAprendizaje procesoAprend;
        procesoAprend = redNeuronal.entrenarRedNeuronal();
        
        //Aprendizaje terminado
        //mostrar las primeras n iteraciones del proceso. Sino son muchas y al pedo
        int iteraciones = 10;
        for (int i = 0; i < 1+(4*iteraciones); i++) {
            resultado.setText(resultado.getText()+ procesoAprend.getProceso().get(i));
        }
        //Muestro los pesos estabilizados que cumplen con el error aceptable
        resultado.setText(resultado.getText()+ "- - - - - - - - - -\n" + "Pesos estabilizados\n");
        int ultimosCinco = procesoAprend.getProceso().size();
        for (int i = ultimosCinco-5; i < ultimosCinco; i++) {
            resultado.setText(resultado.getText() + procesoAprend.getProceso().get(i) + "\n");
        }
        
        //Graficar error durante aprendizaje
        canvasGrafico.getChildren().clear();
        canvasGrafico.getChildren().add(generarGraficoError(procesoAprend.getErrorEpoca()));
        
    }
    
    @FXML void actualizarFuncion(ActionEvent event) {
        ArrayList<Perceptron> perceptrones = redNeuronal.getPerceptrones();
        int cont = 0;
        for (int i = 0; i < perceptrones.size(); i++) {
                String funcion = dataPerceptron.get(cont).getFuncion();
                perceptrones.get(i).setFuncion(funcion);
        }
    }

    @FXML
    void actualizarPeso(ActionEvent event) {
        ArrayList<ArrayList<Conexion>> conexiones = redNeuronal.getConexiones();
        int cont = 0;
        for (int i = 0; i < conexiones.size(); i++) {
            for (int j = 0; j < conexiones.get(i).size(); j++) {
                double nuevoPeso = Double.parseDouble(dataConexiones.get(cont).getPeso());
                conexiones.get(i).get(j).setPeso(nuevoPeso);
                cont++;
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcion.getItems().addAll(
            "Lineal", 
            "Sigmoidal"
        );
        funcion.setValue("Sigmoidal");
        selectionModel = tabPanelGraficos.getSelectionModel();
        errorEntradas.setVisible(false);
        errorClasificar.setVisible(false);
    }

    private LineChart<Number, Number> generarGraficoError(ArrayList<Double> procesoAprend){
        selectionModel.select(1); //Selecciono la pestana del grafico
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Interaciones");
        yAxis.setLabel("Error");
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Grafica del error cometido");
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Error cometido");
        
        //Agregar cada punto al grafico
        for (int i = 0; i < procesoAprend.size(); i=i+5) {
            series.getData().add(new XYChart.Data<Number, Number>(i, procesoAprend.get(i)));
        }
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        return lineChart;
    }

    private Group dibujarTopologiaRed(PMC redNeuronal){
        Group root = new Group();
        ArrayList<Integer> centros = new ArrayList<>();
        ArrayList<ArrayList<int[]>> coordenadas = new ArrayList<>();
        int inicioX = 100;
        int inicioY = 50;
        int separacionX = 80;
        int separacionY = 70;

        
        //Agrego las coordenadas de las entradas
        ArrayList<int[]> xyNEntrada = new ArrayList<>();
        int valorY = inicioY;
        int valorX = inicioX;
        for (int i = 0; i < redNeuronal.getCantEntradas(); i++) {
            int[] xy = new int[]{valorX, valorY};
            xyNEntrada.add(xy);
            valorY += separacionY;
        }
        centros.add((int) valorY/2);
        coordenadas.add(xyNEntrada);
        
        //Agrego las coordenadas de las capa ocultas
        valorX = separacionX*2 + inicioX;
        for (int i = 0; i < redNeuronal.getCantCapasOculta(); i++) {
            ArrayList<int[]> xyCapa = new ArrayList<>();
            valorY = inicioY;
            for (int j = 0; j < redNeuronal.getCantNeuronasCapasOcultaI(); j++) {
                int[] xy = new int[]{valorX, valorY};
                xyCapa.add(xy);
                valorY += separacionY;
            }
            centros.add((int) valorY/2);
            coordenadas.add(xyCapa);
            valorX += separacionX+(10*redNeuronal.getCantCapasOculta());
        }
        
        //Agrego las coordenadas de las salidas
        ArrayList<int[]> xyNSalida = new ArrayList<>();
        valorY = inicioY;
        valorX += separacionX;
        for (int i = 0; i < redNeuronal.getCantSalida(); i++) {
            int[] xy = new int[]{valorX, valorY};
            xyNSalida.add(xy);
            valorY += separacionY;
        }
        centros.add((int) valorY/2);
        coordenadas.add(xyNSalida);
        
        //centro las neuronas
        boolean centrado = true;
        while(true){
            for (int i = 0; i < centros.size()-1; i++) {
                int diferencia = Math.abs(centros.get(i)-centros.get(i+1));
                if (centros.get(i) > centros.get(i+1)) {
                    for (int j = 0; j < coordenadas.get(i+1).size(); j++) {
                        coordenadas.get(i+1).get(j)[1]+= diferencia;
                        centros.set(i+1, centros.get(i));
                        centrado = false;
                    }
                }
                if (centros.get(i) < centros.get(i+1)) {
                    for (int j = 0; j < coordenadas.get(i).size(); j++) {
                        coordenadas.get(i).get(j)[1]+= diferencia;
                        centros.set(i, centros.get(i+1));
                        centrado = false;
                    }
                }
            }
            if (centrado) {
                break;
            }
            centrado = true;
        }
        
        //Dibujo las lineas
        Line lineR = new Line(0,0,1,1);
        root.getChildren().add(lineR);

        //lineas internas
        for (int i = 0; i < coordenadas.size()-1; i++) {
            for (int j = 0; j < coordenadas.get(i).size(); j++) {
                for (int k = 0; k < coordenadas.get(i+1).size(); k++) {
                    Line line = new Line(coordenadas.get(i).get(j)[0],coordenadas.get(i).get(j)[1],coordenadas.get(i+1).get(k)[0],coordenadas.get(i+1).get(k)[1]);
                    root.getChildren().add(line);
                }
            }
        }
        //Lineas de salida
        int ultimo = coordenadas.size()-1;
        for (int i = 0; i < coordenadas.get(ultimo).size(); i++) {
            Line line = new Line(coordenadas.get(ultimo).get(i)[0],coordenadas.get(ultimo).get(i)[1],coordenadas.get(ultimo).get(i)[0]+60,coordenadas.get(ultimo).get(i)[1]);
            root.getChildren().add(line);
        }
        
        //Dibujo las neuronas
        for (int i = 0; i < coordenadas.get(0).size(); i++) {
            Circle circulo = new Circle();
                circulo.setCenterX(coordenadas.get(0).get(i)[0]);
                circulo.setCenterY(coordenadas.get(0).get(i)[1]);
                circulo.setRadius(8);
                
                root.getChildren().add(circulo);
        }
        for (int i = 1; i < coordenadas.size(); i++) {
            for (int j = 0; j < coordenadas.get(i).size(); j++) {
                Circle circulo = new Circle();
                circulo.setCenterX(coordenadas.get(i).get(j)[0]);
                circulo.setCenterY(coordenadas.get(i).get(j)[1]);
                circulo.setRadius(15);
                circulo.setFill(Color.WHITE);
                circulo.setStroke(Color.BLACK);
                
                root.getChildren().add(circulo);
            }
        }
        for (int i = 0; i < coordenadas.get(ultimo).size(); i++) {
            Circle circulo = new Circle();
                circulo.setCenterX(coordenadas.get(ultimo).get(i)[0]+60);
                circulo.setCenterY(coordenadas.get(ultimo).get(i)[1]);
                circulo.setRadius(8);
                
                root.getChildren().add(circulo);
        }
        
        //Nombro las neuronas
        //Entradas
        for (int i = 0; i < coordenadas.get(0).size(); i++) {
            Text t = new Text();
            t.setText("E"+i);
            t.setX(coordenadas.get(0).get(i)[0]-30);
            t.setY(coordenadas.get(0).get(i)[1]+5);
            t.setFont(Font.font ("Verdana", 14));
            root.getChildren().add(t);
        }
        //Neuronas
        int cont = 0;
        for (int i = 1; i < coordenadas.size(); i++) {
            for (int j = 0; j < coordenadas.get(i).size(); j++) {
                Text t = new Text();
                t.setText(String.valueOf(cont));
                t.setX(coordenadas.get(i).get(j)[0]-8);
                t.setY(coordenadas.get(i).get(j)[1]+5);
                t.setFont(Font.font ("Verdana", 14));
                root.getChildren().add(t);
                cont++;
            }
        }
        //Salida
        for (int i = 0; i < coordenadas.get(ultimo).size(); i++) {
            Text t = new Text();
            t.setText("S"+i);
            t.setX(coordenadas.get(ultimo).get(i)[0]+70);
            t.setY(coordenadas.get(ultimo).get(i)[1]+5);
            t.setFont(Font.font ("Verdana", 14));
            root.getChildren().add(t);
        }
        
        return root;
    }
    
    private TableView generarTablaFuncion(TableView t){
        TableView table = t;
        String[] tituloColumna = {"Perceptron", "Funcion"};
        String[] nombreAtributo = {"perceptron", "funcion"};
        double[] tamano = {0.29, 0.7};
        table.getColumns().clear();
        
        //Columna nombre de la conexion
        TableColumn col1 = new TableColumn(tituloColumna[0]);
        col1.prefWidthProperty().bind(table.widthProperty().multiply(tamano[0]));
        col1.setCellValueFactory(new PropertyValueFactory<>(nombreAtributo[0]));
        
        //Columna peso de la conexion
        TableColumn<PercepFun, String> col2 = new TableColumn(tituloColumna[1]);
        col2.prefWidthProperty().bind(table.widthProperty().multiply(tamano[1]));
        col2.setCellValueFactory(new PropertyValueFactory<>(nombreAtributo[1]));
        col2.setCellFactory(new Callback<TableColumn<PercepFun, String>, TableCell<PercepFun, String>>() {
             @Override
             public TableCell<PercepFun, String> call(TableColumn<PercepFun, String> param) {
                  ObservableList<String> testlist = FXCollections.observableArrayList("Sigmoidal", "Lineal");
                  return new ChoiceBoxTableCell(testlist);
             }
        });
        
//        col2.setCellValueFactory(new PropertyValueFactory<>(nombreAtributo[1]));
//        col2.setCellFactory(TextFieldTableCell.<PercepFun>forTableColumn());
//        col2.setOnEditCommit(
//            (CellEditEvent<PercepFun, String> tab) -> {
//                ((PercepFun) tab.getTableView().getItems().get(
//                    tab.getTablePosition().getRow())
//                    ).setFuncion(tab.getNewValue());
//        });
        table.getColumns().addAll(col1, col2);
        
        dataPerceptron = FXCollections.observableArrayList();
        ArrayList<Perceptron> listaPercep = redNeuronal.getPerceptrones();
        for (int i = 0; i < listaPercep.size(); i++) {
            dataPerceptron.add(new PercepFun(String.valueOf(i), listaPercep.get(i).getFuncion()));
        }
        table.setItems(dataPerceptron);
        return table;
    }
    
    private TableView generarTablaPesos(TableView t){
        TableView table = t;
        String[] tituloColumna = {"Conexion", "Peso"};
        String[] nombreAtributo = {"conexion", "peso"};
        double[] tamano = {0.29, 0.7};
        table.getColumns().clear();
        
        //Columna nombre de la conexion
        TableColumn col1 = new TableColumn(tituloColumna[0]);
        col1.prefWidthProperty().bind(table.widthProperty().multiply(tamano[0]));
        col1.setCellValueFactory(new PropertyValueFactory<>(nombreAtributo[0]));
        
        //Columna peso de la conexion
        TableColumn<ConexPeso, String> col2 = new TableColumn(tituloColumna[1]);
        col2.prefWidthProperty().bind(table.widthProperty().multiply(tamano[1]));
        col2.setCellValueFactory(new PropertyValueFactory<>(nombreAtributo[1]));
        col2.setCellFactory(TextFieldTableCell.<ConexPeso>forTableColumn());
        col2.setOnEditCommit(
            (CellEditEvent<ConexPeso, String> tab) -> {
                ((ConexPeso) tab.getTableView().getItems().get(
                    tab.getTablePosition().getRow())
                    ).setPeso(tab.getNewValue());
        });
        table.getColumns().addAll(col1, col2);
        
        dataConexiones = FXCollections.observableArrayList();
        ArrayList<ArrayList<Conexion>> listaConex = redNeuronal.getConexiones();
        
        for (int i = 0; i < listaConex.size(); i++) {
            for (int j = 0; j < listaConex.get(i).size(); j++) {
                dataConexiones.add(new ConexPeso(listaConex.get(i).get(j).getNombre(), String.valueOf(listaConex.get(i).get(j).getPeso())));
            }
        }
        table.setItems(dataConexiones);
        return table;
    }
    
}
