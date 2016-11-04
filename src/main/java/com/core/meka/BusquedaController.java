/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import com.core.controller.GrafoController;
import com.core.entities.Delta;
import com.core.entities.NodoCircular;
import com.core.entity.Arista;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    
    private Grafo generado;
    
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
    }

    private void initBUttons() {
        
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

            private void mostrarGrafo(Grafo grafo) {
                
                nodo_inicial_combo.getItems().clear();
                nodo_final_combo.getItems().clear();
                
                Map<String, Nodo> treeNodos = Grafo.ordenarNodos(grafo.getNodos());
                for(Map.Entry<String,Nodo> entry : treeNodos.entrySet()){
                    nodo_inicial_combo.getItems().add(entry.getKey());
                    nodo_final_combo.getItems().add(entry.getKey());
                }
                
                dibujarGrafo(canvas, grafo);
            }
        });
    }

    private void initComboBoxes() {
        
        algoritmo_combo.getItems().clear();
        
        for(Algoritmos a : Algoritmos.values()){
            algoritmo_combo.getItems().add(a.getValue());
        }
    }
    
    private void dibujarGrafo(Pane panel, Grafo grafo){
        if (grafo != null) {
            List<NodoCircular> circulos = new ArrayList<>();
            List<Text> rotulos = new ArrayList<>();
            List<Line> lines = new ArrayList<>();
//            List<StackPane> stacks = new ArrayList<>();
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
                        
                        lines.add(l);
                        
                        Delta.enableDrag(c,t,l);
                    }
            }
            }
            
            root = new Group();
            
            for(Line l : lines){
                root.getChildren().add(l);
            }
            
            for(Circle c : circulos){
                root.getChildren().add(c);
            }
            
            for(Text t : rotulos){
                root.getChildren().add(t);
            }

            panel.getChildren().remove(0);
            
            panel.getChildren().add(root);
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
