/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class PrincipalController implements Initializable {

    @FXML private GridPane grid;
    
    @FXML private VBox busqueda_vbox;
    @FXML private VBox SBR_vbox;
    @FXML private VBox mlp_vbox;
    @FXML private VBox perceptron_vbox;
    @FXML private VBox bayes_vbox;
    @FXML private VBox geneticos_vbox;
    @FXML private VBox agentes_vbox;
    @FXML private VBox reglas_asoc_vbox;
    @FXML private VBox cluster_vbox;
    @FXML private VBox som_vbox;
    @FXML private VBox hopfield_vbox;
    @FXML private VBox svm_vbox;
    
    //Imagenes
    @FXML private ImageView imgBusqueda;
    @FXML private ImageView sbr;
    @FXML private ImageView ra;
    @FXML private ImageView agentes;
    @FXML private ImageView geneticos;
    @FXML private ImageView bayes;
    @FXML private ImageView perceptron;
    @FXML private ImageView mlp; //10
    @FXML private ImageView svm; //8
    @FXML private ImageView hopfield; //6
    @FXML private ImageView som; //7
    @FXML private ImageView clustering; //4
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initButtons();
        initListeners();
        initComboBoxes();
    }    

    private void initConfig() {
        busqueda_vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    Util.showMainWindowMaximized("Busqueda en grandes espacios", "/fxml/Busqueda.fxml", BusquedaController.class, true, false);
                }
            }
        });
        mlp_vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    Util.showMainWindowMaximized("Perceptron Multi-Capa", "/fxml/MLP.fxml", MLPController.class, true, false);
                }
            }
        });
        som_vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    Util.showMainWindowMaximized("Mapas Auto-Organizados", "/fxml/SOM.fxml", SOMController.class, true, false);
                }
            }
        });
    }

    private void initButtons() {
        
    }

    private void initListeners() {
        
    }

    private void initComboBoxes() {
        imgBusqueda.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imgBusqueda.setImage(new Image("/img/cambiarColor/Busquedas.png"));
                imgBusqueda.getScene().setCursor(Cursor.HAND);
            }
        });
        imgBusqueda.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imgBusqueda.setImage(new Image("/img/negro/2.png"));
                imgBusqueda.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        sbr.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sbr.setImage(new Image("/img/cambiarColor/SBR.png"));
                sbr.getScene().setCursor(Cursor.HAND);
            }
        });
        sbr.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sbr.setImage(new Image("/img/negro/14.png"));
                sbr.getScene().setCursor(Cursor.DEFAULT);
            }
        });        
        ra.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ra.setImage(new Image("/img/cambiarColor/ReglasAso.png"));
                ra.getScene().setCursor(Cursor.HAND);
            }
        });
        ra.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ra.setImage(new Image("/img/negro/13.png"));
                ra.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        agentes.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                agentes.setImage(new Image("/img/cambiarColor/Agentes.png"));
                agentes.getScene().setCursor(Cursor.HAND);
            }
        });
        agentes.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                agentes.setImage(new Image("/img/negro/1.png"));
                agentes.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        geneticos.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                geneticos.setImage(new Image("/img/cambiarColor/Geneticos.png"));
                geneticos.getScene().setCursor(Cursor.HAND);
            }
        });
        geneticos.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                geneticos.setImage(new Image("/img/negro/5.png"));
                geneticos.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        bayes.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bayes.setImage(new Image("/img/cambiarColor/Bayes.png"));
                bayes.getScene().setCursor(Cursor.HAND);
            }
        });
        bayes.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bayes.setImage(new Image("/img/negro/12.png"));
                bayes.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        perceptron.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                perceptron.setImage(new Image("/img/cambiarColor/Perceptron.png"));
                perceptron.getScene().setCursor(Cursor.HAND);
            }
        });
        perceptron.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                perceptron.setImage(new Image("/img/negro/9.png"));
                perceptron.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        mlp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mlp.setImage(new Image("/img/cambiarColor/PMC.png"));
                mlp.getScene().setCursor(Cursor.HAND);
            }
        });
        mlp.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mlp.setImage(new Image("/img/negro/10.png"));
                mlp.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        svm.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                svm.setImage(new Image("/img/cambiarColor/MVS.png"));
                svm.getScene().setCursor(Cursor.HAND);
            }
        });
        svm.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                svm.setImage(new Image("/img/negro/8.png"));
                svm.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        hopfield.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hopfield.setImage(new Image("/img/cambiarColor/Hopfiel.png"));
                hopfield.getScene().setCursor(Cursor.HAND);
            }
        });
        hopfield.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hopfield.setImage(new Image("/img/negro/6.png"));
                hopfield.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        som.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                som.setImage(new Image("/img/cambiarColor/MAO.png"));
                som.getScene().setCursor(Cursor.HAND);
            }
        });
        som.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                som.setImage(new Image("/img/negro/7.png"));
                som.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        clustering.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clustering.setImage(new Image("/img/cambiarColor/Cluster.png"));
                clustering.getScene().setCursor(Cursor.HAND);
            }
        });
        clustering.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clustering.setImage(new Image("/img/negro/4.png"));
                clustering.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
    
}
