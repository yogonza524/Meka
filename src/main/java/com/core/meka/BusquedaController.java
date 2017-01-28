/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import com.core.controller.AlgoritmoController;
import com.core.controller.GrafoController;
import com.core.entities.Delta;
import com.core.entities.NodoCircular;
import com.core.entity.Arista;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
import com.core.entity.Solucion;
import com.core.util.Algoritmos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class BusquedaController implements Initializable {

    @FXML private AnchorPane base;
    @FXML private Pane canvas;
    private Group root;
    
    @FXML private Accordion ac1;
    @FXML private Accordion ac2;
    
    @FXML private TitledPane tab1;
    @FXML private TitledPane tab2;
    
    @FXML private Accordion ac3;
    @FXML private Accordion ac4;
    
    @FXML private TitledPane tab3;
    @FXML private TitledPane tab4;
    
    @FXML private MenuItem cargar_menu_item;
    @FXML private TextArea grafo_txt;
    @FXML private TextArea result_txt;
    @FXML private Button generar_btn;
    @FXML private Button borrar_btn;
    @FXML private Button ejecutar_btn;
    
    private Grafo generado;
    private static List<NodoCircular> circulos;
    private static List<Line> lines;
    
    @FXML private ComboBox nodo_inicial_combo;
    @FXML private ComboBox nodo_final_combo;
    @FXML private ComboBox algoritmo_combo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initBUttons();
        initKeyBoard();
        initMenuItems();
        initComboBoxes();
    }    

    private void initConfig() {
        ac1.setExpandedPane(tab1);
        ac2.setExpandedPane(tab2);
        ac3.setExpandedPane(tab3);
        ac4.setExpandedPane(tab4);
        
        root = new Group();
        root.setAutoSizeChildren(true);
        canvas.getChildren().add(root);
        
        circulos = new ArrayList<>();
        lines = new ArrayList<>();
    }

    private void initBUttons() {
        borrar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grafo_txt.setText("");
                generado = null;
            }
        });
        generar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!grafo_txt.getText().isEmpty()) {
                    generado = GrafoController.desdeString(grafo_txt.getText().toUpperCase());
                    if (generado != null) {
                        mostrarGrafo(generado);
                    }
                }
            }
        });
        
        ejecutar_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (generado == null) {
                    return;
                }
                if (algoritmo_combo.getSelectionModel().getSelectedIndex() < 0) {
                    return;
                }
                if (nodo_inicial_combo.getSelectionModel().getSelectedIndex() < 0) {
                    return;
                }
                if (nodo_final_combo.getSelectionModel().getSelectedIndex() < 0) {
                    return;
                }
                if (nodo_final_combo.getSelectionModel().getSelectedItem().equals(nodo_inicial_combo.getSelectionModel().getSelectedItem())) {
                    return;
                }
                //Limpiar lineas
                for(Line l : lines){
                    l.setStrokeWidth(1);
                }
                //Ejecutar busqueda
                String result = "";
                String inicio = nodo_inicial_combo.getSelectionModel().getSelectedItem().toString();
                String fin = nodo_final_combo.getSelectionModel().getSelectedItem().toString();
                
                Solucion sol = null;
                switch(algoritmo_combo.getSelectionModel().getSelectedIndex()){
                    case 0: 
                        sol = AlgoritmoController.busquedaProfundidadConSolucion(generado, inicio, fin);
                        result = ((sol != null) ? sol.getPasos() : "Sin solucion");
                        break; //Primero en profundidad
                    case 1: 
                        sol = AlgoritmoController.busquedaAmplitud(generado, 
                            inicio, 
                            fin);
                        result = ((sol!= null) ? sol.getPasos() : "Sin solucion");
                        break; //Escalada Simple
                    case 2: 
                        sol = AlgoritmoController.busquedaEscaladaSimpleConSolucion(generado, inicio, fin);
                        result = ((sol != null) ? sol.getPasos() : "Sin solucion");
                        break; //Primero en amplitud
                    case 3: 
                        sol = AlgoritmoController.busquedaEscaladaMaximaConSolucion(generado, inicio, fin);
                        result = ((sol != null )? sol.getPasos() : "Sin solucion");
                        break; //Escalada Maxima
                    case 4: 
                        sol = AlgoritmoController.aEstrellaConNodos(generado, inicio, fin);
                        result = ((sol != null )? sol.getPasos() : "Sin solucion");
                        break; //A*
                }
                result_txt.setText(result);
                if (sol != null && sol.getNodos() != null && !sol.getNodos().isEmpty() && generado != null) {
                    //Nodos a dibujar
                    List<String> nodosConectados = conectar(sol.getNodos());
                    for(String conexion : nodosConectados){
                        Line l = buscarLinea(conexion);
                        if (l != null) {
                            l.setStrokeWidth(5);
                        }
                    }
                }
            }

            private Line buscarLinea(String key) {
                Line l = null;
                if (key != null && !key.isEmpty()) {
                    for(Line line : lines){
                        String id = line.getId();
                        String idInverted = id.split("-")[1] + "-" + id.split("-")[0];
                        if (id.equals(key) || idInverted.equals(key)) {
                            l = line;
                            break;
                        }
                    }
                }
                return l;
            }
            
            private List<String> aristasNombre(Map<String,Arista> aristas){
                List<String> result = null;
                if (aristas != null && !aristas.isEmpty()) {
                    result = new ArrayList<>();
                    for(Map.Entry<String,Arista> entry : aristas.entrySet()){
                        result.add(entry.getKey());
                    }
                }
                return result;
            }

            private List<String> conectar(List<Nodo> nodos) {
                List<String> result = null;
                if (nodos != null && !nodos.isEmpty()) {
                    result = new ArrayList<>();
                    String arista = "";
                    for(Nodo nodo : nodos){
                        if (arista.contains("-")) {
                            arista += nodo.getNombre();
                            result.add(arista);
                            arista = nodo.getNombre() + "-";
                        }
                        else{
                            arista += nodo.getNombre() + "-";
                        }
                    }
                    System.out.println("Nodos conectados: " + Arrays.toString(result.toArray()));
                }
                return result;
            }
        });
    }

    private void initKeyBoard() {
        base.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                
                if ( event.getCode().equals( KeyCode.ESCAPE ) )
                {
                    Util.closeWindow(ac1);
                }
            }
        });
    }

    private void initMenuItems() {
        cargar_menu_item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
//                chooser.setInitialFileName("*.txt");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
                File file = chooser.showOpenDialog(base.getScene().getWindow());
                if (file != null) {
                  if (file.getName().endsWith(".txt")) {
                      try {
                          Grafo grafo = GrafoController.desdeArchivo(file.getAbsolutePath());
                          if (grafo != null) {
                              grafo_txt.setText(grafo.getExpresion());
                              generado = grafo;
                              mostrarGrafo(grafo);
                          }
                          else{
                              Util.error("Grafo con formato incorrecto", "Verifique su sintaxis", "Generalmente esto se debe por errores de sintaxis", ac1);
                          }
                      } catch (Exception ex) {
                          Logger.getLogger(BusquedaController.class.getName()).log(Level.SEVERE, null, ex);
                          Util.exception(ex, "Excepcion capturada", "Verifique la pila", ex.getMessage(), ac1);
                      }
                  } else {
                    Util.error("Extensión incorrecta", "Verifique la extension", file.getName() + " no tiene la extension correcta", ac1);
                  }
                }
            }
        });
    }
    
    private void mostrarGrafo(Grafo grafo) {
                
        nodo_inicial_combo.getItems().clear();
        nodo_final_combo.getItems().clear();

        Map<String, Nodo> treeNodos = Grafo.ordenarNodos(grafo.getNodos());
        for(Map.Entry<String,Nodo> entry : treeNodos.entrySet()){
            nodo_inicial_combo.getItems().add(entry.getKey());
            nodo_final_combo.getItems().add(entry.getKey());
        }

        BusquedaController.dibujarGrafo(canvas, root, grafo);
    }

    private void initComboBoxes() {
        
        algoritmo_combo.getItems().clear();
        
        for(Algoritmos a : Algoritmos.values()){
            algoritmo_combo.getItems().add(a.getValue());
        }
    }
    
    public static void dibujarGrafo(Pane canvas, Group root, Grafo grafo){
        //Verifico que el grafo no sea nulo
        if (grafo != null) {
            circulos = new ArrayList<>();//Inicializo el panel de circulos dibujados
            List<Text> rotulos = new ArrayList<>();
            lines = new ArrayList<>();
            List<Text> textoDeAristas = new ArrayList<>();
            
            for(Map.Entry<String, Nodo> entry : grafo.getNodos().entrySet()){
                Random r = new Random();
                
                double radius = 30;
                double centerX = r.nextDouble() * canvas.getWidth();
                double centerY = r.nextDouble() * canvas.getHeight();
                
                while((centerX + radius) > canvas.getWidth() && (centerY + radius) > canvas.getHeight()){
                    centerX = r.nextDouble() * canvas.getWidth();
                    centerY = r.nextDouble() * canvas.getHeight();
                }
                
                NodoCircular c;
                
                if (entry.getValue().getValor() == 0) {
                    c = new NodoCircular(centerX, centerY, 20,Color.GREENYELLOW.brighter());
                }else{
                    c = new NodoCircular(centerX, centerY, 20,Color.AQUA.brighter());
                }
//                c.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.8)));
                c.setStroke(Color.STEELBLUE.brighter());
                c.setStrokeWidth(3);
                c.setNodo(entry.getValue());
                
                Text t = new Text(entry.getKey());
                t.setLayoutX(centerX - (radius / 2) + t.getText().length() + 5);
                t.setLayoutY(centerY + (radius / 5));
                t.setBoundsType(TextBoundsType.VISUAL);
                t.setFontSmoothingType(FontSmoothingType.LCD);
                
                rotulos.add(t);
                
                circulos.add(c);

                for(Map.Entry<String,Arista> en : grafo.getAristas().entrySet()){
                    Nodo origen = en.getValue().getOrigen();
                    Nodo destino = en.getValue().getDestino();
                    
                    NodoCircular origenCircular = null;
                    NodoCircular destinoCircular = null;
                    
                    for(NodoCircular n : circulos){
                        if (n.getNodo().equals(origen)) {
                            origenCircular = n;
                            break;
                        }
                    }
                    
                    for(NodoCircular n : circulos){
                        if (n.getNodo().equals(destino)) {
                            destinoCircular = n;
                            break;
                        }
                    }
                    if (origenCircular != null && destinoCircular != null) {
                        Line l = new Line();
                        l.setStartX(origenCircular.getCenterX());
                        l.setStartY(origenCircular.getCenterY());
                        
                        l.setEndX(destinoCircular.getCenterX());
                        l.setEndY(destinoCircular.getCenterY());
                        l.setId(en.getKey().split("\\(")[0]);
                        
                        l.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                l.setStrokeWidth(5);
                            }
                        });
                        
                        l.setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                l.setStrokeWidth(1);
                            }
                        });
                        
                        lines.add(l);
                        
                        //Agregar los rotulos a las artistas
                        Text aristaText = new Text(en.getValue().getValor() + "");
                        aristaText.setLayoutX((l.getLayoutX() + l.getEndX() + l.getStartX()) / 2 );
                        aristaText.setLayoutY((l.getLayoutY() + l.getEndY() + l.getStartY()) / 2 );
                        aristaText.setBoundsType(TextBoundsType.VISUAL);
                        aristaText.setFontSmoothingType(FontSmoothingType.LCD);
                        aristaText.setFont(Font.font("Verdana", FontPosture.ITALIC, 18));
                        
                        textoDeAristas.add(aristaText);
                        
                        Delta.enableDrag(c,t,l, grafo, root, canvas);
                    }
            }
            }
            
            root = new Group();
            
            for(Line l : lines){
                root.getChildren().add(l);
            }
            
            for(Text t : textoDeAristas){
                root.getChildren().add(t);
            }
            
            for(Circle c : circulos){
                root.getChildren().add(c);
            }
            
            for(Text t : rotulos){
                root.getChildren().add(t);
            }

            canvas.getChildren().remove(0);
            
            canvas.getChildren().add(root);
        }
    }
    
    private boolean empty(double[] array){
        boolean result = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}
