/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import com.core.controller.GrafoController;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
import com.core.util.Algoritmos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class BusquedaController implements Initializable {

    @FXML private AnchorPane base;
    
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
            }
        });
    }

    private void initComboBoxes() {
        
        algoritmo_combo.getItems().clear();
        
        for(Algoritmos a : Algoritmos.values()){
            algoritmo_combo.getItems().add(a.getValue());
        }
    }
    
}
