package com.pichon.moduloarboldecision;

import Util.ArbolDecision;
import Util.NodoArbolDecision;
import Util.UtilArbolDecision;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ArbolDController implements Initializable {
    
    Group layout;
    ArbolDecision arbolDecision;
    @FXML private ChoiceBox<Integer> cantClases;
    @FXML private TextArea entradasClasificar;
    @FXML private StackPane canvas;
    @FXML private TextArea resultado;
    @FXML private TextArea valoresEntrada;
    @FXML private ChoiceBox<Integer> cantAtributos;
    @FXML private TextField atributos;
    @FXML private ScrollPane layoutPane;
    @FXML private Label errorEntradas;
    @FXML private Label errorClasificar;
    
    @FXML void generarArbol(ActionEvent event) {
        ArrayList<ArrayList<String>> matrizDatos;
        ArrayList<String> nombreAtributos;
        try{
            matrizDatos = UtilArbolDecision.getDatosEntrada(valoresEntrada.getText(), cantAtributos.getValue());
            nombreAtributos = UtilArbolDecision.getNombreAtributos(atributos.getText(), cantAtributos.getValue());

            //Creo el objeto arbol de decision
            arbolDecision = new ArbolDecision();

            //Seteo valores iniciales
            arbolDecision.setMatrizDatos(matrizDatos);
            arbolDecision.setCantAtributos(cantAtributos.getValue());
            arbolDecision.setCantClases(cantClases.getValue());
            arbolDecision.setNombreAtributos(nombreAtributos);

            //Corro el algoritmo con el problema cargado
            arbolDecision.runArbolDecision();
            resultado.setText(arbolDecision.getProceso());

            //Dibujo el arbol
            canvas.getChildren().clear();
            canvas.getChildren().add(dibujarArbol(arbolDecision.getEstructuraArbol()));
            StackPane.setAlignment(canvas,Pos.CENTER);
            errorEntradas.setVisible(false);
            
        } catch (Exception e) {
            errorEntradas.setVisible(true);
        }
    }

    @FXML void clasificarEntradas(ActionEvent event) {
        try{
            String entradaClass = entradasClasificar.getText();
            ArrayList<ArrayList<String>> clasificar = UtilArbolDecision.getDatosEntrada(entradaClass, arbolDecision.getCantClases());
            String salida = "\nClasificacion entradas:\n";
            for (int i = 0; i < clasificar.size(); i++) {
                salida += "\nEntrada: "+ clasificar.get(i)+"\n"+arbolDecision.clasificarEntradas(clasificar.get(i), arbolDecision.getEstructuraArbol())+"\n";
            }
            resultado.setText(resultado.getText()+salida);
            errorClasificar.setVisible(false);
        
        } catch (Exception e) {
            errorClasificar.setVisible(true);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cantClases.getItems().addAll(
            2, 3, 4, 5
        );
        cantAtributos.getItems().addAll(
            2, 3, 4, 5, 6, 7
        );
        cantAtributos.setValue(4);
        cantClases.setValue(2);
        errorEntradas.setVisible(false);
        errorClasificar.setVisible(false);
    }    

    private Group dibujarArbol(NodoArbolDecision estructuraArbol) {
        Group root = new Group();
        root.getChildren().add(drawNodo(estructuraArbol, 300, 30));
        return root;
    }
    
    private Group drawNodo(NodoArbolDecision estructuraArbol, int x, int y){
        Group root = new Group();
        int valorX, valorY;
        int distanciaY = 120;
        int aperturaRamas = 600;
        
        //Creamos el nodo
        Rectangle r = drawRectangle(estructuraArbol.getNombeAtributo(), x, y, estructuraArbol.getNombeAtributo().length()*10+30, 30);
        root.getChildren().add(r);
        
        //Creamos el texto de nombre
        valorX = (int) (r.getX()+(r.getWidth()/2)-(estructuraArbol.getNombeAtributo().length()*4));
        valorY = (int) r.getY()+20;
        Text t = drawText(estructuraArbol.getNombeAtributo(), valorX, valorY);
        root.getChildren().add(t);
        
        //Dibujamos las lineas
        int subDiv = aperturaRamas/(estructuraArbol.getHijos().size()+1);
        int posX = subDiv + x-(aperturaRamas/2);
        for (int i = 0; i < estructuraArbol.getHijos().size(); i++) {
            //Dibujo las lineas
            Line line1 = new Line(r.getX()+(r.getWidth()/2), r.getY()+(r.getHeight()), posX, r.getY()+distanciaY);
            root.getChildren().add(line1);
            //Agrego la etiqueta
            if (r.getX()+(r.getWidth()/2) >= posX) {
                valorX = (int) ((r.getX()+(r.getWidth()/2)+ posX)/2)-20;
            } else{
                valorX = (int) ((r.getX()+(r.getWidth()/2)+ posX)/2)+16;
            }
            valorY = (int) (r.getY()+(r.getHeight())+r.getY()+distanciaY)/2;
            Text etiqueta = drawText(estructuraArbol.getVariablesAtributo().get(i), valorX, valorY);
            root.getChildren().add(etiqueta);
            
            if (estructuraArbol.getHijos().get(i)==null) {
                //Dibujo la clase
                valorX = posX-20;
                valorY = (int) r.getY()+distanciaY+20;
                Text tc = drawText(estructuraArbol.getClase().get(i), valorX, valorY);
                root.getChildren().add(tc);
            } else {
                //Dibujo el sub arbol -(r.getWidth()/2)
                valorX = (int) (posX);
                valorY = (int) r.getY()+distanciaY;
                root.getChildren().addAll(drawNodo(estructuraArbol.getHijos().get(i), valorX, valorY));
            }
            posX = posX + subDiv;
        }
        return root;
    }
    
    private Text drawText(String nombre, int x, int y){
        Text t = new Text();
        t.setFill(Color.BLACK);
        t.setText(nombre);
        t.setX(x);
        t.setY(y);
        t.setFont(Font.font ("Verdana", 14));
        return t;
    }
    
    private Rectangle drawRectangle(String nombre, int x, int y, int ancho, int alto){
        Rectangle r = new Rectangle();
        r.setWidth(ancho);    //ancho
        r.setHeight(alto);   //alto
        r.setX((x)-(r.getWidth()/2));
        r.setY(y);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setStroke(Color.BLACK);
        r.setFill(Color.WHITE);
        return r;
    }
}
