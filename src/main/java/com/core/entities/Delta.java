/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.entities;

import com.core.entity.Grafo;
import com.core.meka.BusquedaController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Delta { 
    double x, y;
    
    public static void enableDrag(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
          }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.getScene().setCursor(Cursor.HAND);
          }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.setCenterX(mouseEvent.getX() + dragDelta.x);
            circle.setCenterY(mouseEvent.getY() + dragDelta.y);
          }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDrag(final Group root) {
        final Delta dragDelta = new Delta();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = root.getLayoutX() - mouseEvent.getX();
            dragDelta.y = root.getLayoutY() - mouseEvent.getY();
            root.getScene().setCursor(Cursor.MOVE);
          }
        });
        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            root.getScene().setCursor(Cursor.HAND);
          }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            root.setLayoutX(mouseEvent.getX() + dragDelta.x);
            root.setLayoutY(mouseEvent.getY() + dragDelta.y);
          }
        });
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              root.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        root.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              root.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDrag(final Pane root) {
        final Delta dragDelta = new Delta();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = root.getLayoutX() - mouseEvent.getX();
            dragDelta.y = root.getLayoutY() - mouseEvent.getY();
            root.getScene().setCursor(Cursor.MOVE);
          }
        });
        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            root.getScene().setCursor(Cursor.HAND);
          }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            root.setLayoutX(mouseEvent.getX() + dragDelta.x);
            root.setLayoutY(mouseEvent.getY() + dragDelta.y);
          }
        });
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              root.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        root.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              root.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDrag(final Circle circle, final Text rotulo) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
          }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.getScene().setCursor(Cursor.HAND);
          }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.setCenterX(mouseEvent.getX() + dragDelta.x);
            circle.setCenterY(mouseEvent.getY() + dragDelta.y);
            rotulo.setLayoutX(mouseEvent.getX() + dragDelta.x - (circle.getRadius() / 2));
            rotulo.setLayoutY(mouseEvent.getY() + dragDelta.y);
          }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDrag(final Circle circle, final Text rotulo, final Line line) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
          }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.getScene().setCursor(Cursor.HAND);
          }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.setCenterX(mouseEvent.getX() + dragDelta.x);
            circle.setCenterY(mouseEvent.getY() + dragDelta.y);
            rotulo.setLayoutX(mouseEvent.getX() + dragDelta.x - (circle.getRadius() / 2));
            rotulo.setLayoutY(mouseEvent.getY() + dragDelta.y);
            line.setStartX(mouseEvent.getX() + dragDelta.x);
            line.setStartY(mouseEvent.getY() + dragDelta.y);
          }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDrag(final Circle circle, final Text rotulo, final Line line, Grafo grafo, Group root, Pane canvas) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
          }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            circle.getScene().setCursor(Cursor.HAND);
            BusquedaController.dibujarGrafo(canvas, root, grafo);
          }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
//            circle.setCenterX(mouseEvent.getX() + dragDelta.x);
//            circle.setCenterY(mouseEvent.getY() + dragDelta.y);
//            rotulo.setLayoutX(mouseEvent.getX() + dragDelta.x - (circle.getRadius() / 2));
//            rotulo.setLayoutY(mouseEvent.getY() + dragDelta.y);
//            line.setStartX(mouseEvent.getX() + dragDelta.x);
//            line.setStartY(mouseEvent.getY() + dragDelta.y);
            
          }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              circle.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDragOrigen(final Circle origen, final Text rotulo, final Line line) {
        final Delta dragDelta = new Delta();
        origen.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = origen.getCenterX() - mouseEvent.getX();
            dragDelta.y = origen.getCenterY() - mouseEvent.getY();
            origen.getScene().setCursor(Cursor.MOVE);
          }
        });
        origen.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            origen.getScene().setCursor(Cursor.HAND);
          }
        });
        origen.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            origen.setCenterX(mouseEvent.getX() + dragDelta.x);
            origen.setCenterY(mouseEvent.getY() + dragDelta.y);
            rotulo.setLayoutX(mouseEvent.getX() + dragDelta.x - (origen.getRadius() / 2));
            rotulo.setLayoutY(mouseEvent.getY() + dragDelta.y);
            line.setStartX(mouseEvent.getX() + dragDelta.x);
            line.setStartY(mouseEvent.getY() + dragDelta.y);
          }
        });
        origen.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              origen.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        origen.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              origen.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
    
    public static void enableDragDestino(final Circle origen, final Text rotulo, final Line line) {
        final Delta dragDelta = new Delta();
        origen.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = origen.getCenterX() - mouseEvent.getX();
            dragDelta.y = origen.getCenterY() - mouseEvent.getY();
            origen.getScene().setCursor(Cursor.MOVE);
          }
        });
        origen.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            origen.getScene().setCursor(Cursor.HAND);
          }
        });
        origen.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            origen.setCenterX(mouseEvent.getX() + dragDelta.x);
            origen.setCenterY(mouseEvent.getY() + dragDelta.y);
            rotulo.setLayoutX(mouseEvent.getX() + dragDelta.x - (origen.getRadius() / 2));
            rotulo.setLayoutY(mouseEvent.getY() + dragDelta.y);
            line.setEndX(mouseEvent.getX() + dragDelta.x);
            line.setEndY(mouseEvent.getY() + dragDelta.y);
          }
        });
        origen.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              origen.getScene().setCursor(Cursor.HAND);
              
            }
          }
        });
        origen.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              origen.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        });
        }
}

