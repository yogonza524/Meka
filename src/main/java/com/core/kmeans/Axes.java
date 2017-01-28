/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.kmeans;


import java.util.function.Function;
import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author pichon
 */
public class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public Axes(
                int width, int height,
                double xLow, double xHi, double xTickUnit,
                double yLow, double yHi, double yTickUnit
        ) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(false);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(false);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(
                Bindings.subtract(
                    (width / 2) + 1,
                    yAxis.widthProperty()
                )
            );

            getChildren().setAll(xAxis, yAxis);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }

        public NumberAxis getYAxis() {
            return yAxis;
        }
    }

    
