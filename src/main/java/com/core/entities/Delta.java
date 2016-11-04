/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.entities;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
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
    
}

