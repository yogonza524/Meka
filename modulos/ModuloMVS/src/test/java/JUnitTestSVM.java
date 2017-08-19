/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pichon
 */
public class JUnitTestSVM {
    
    public JUnitTestSVM() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
  
    @Test
    // verificado que funciona bien
    public void test() throws IOException{
        
        SVM svm = new SVM();
        UtilSVM utilSVM = new UtilSVM();
        SalidaAprendizaje procesoAprendizaje;
        ArrayList<ArrayList<Double>> puntos;

        //Cargar datos de caja de texto
        String texto =  "1,1,1;\n" +
                        "1,2,1;\n" +
                        "-1,0,-1;\n" +
                        "0,-1,-1;";
        if(utilSVM.verificarPuntosEntradas(texto)){
            System.out.println("Formato de puntosEntrada correcto");
        } else {
            System.out.println("Formato incorrecto");
        }
        puntos = UtilSVM.cargarPuntosTexto(texto);
        System.out.println(puntos);
        
        //Generar string de maximixacion
        System.out.println("Generacion de string de maximizacion");
        System.out.println(svm.generarStringMaximizar(puntos));
        
        //obtener los alfas
        String entradaAlfa = "5/8; 0; 3/8; 1/4;";
        entradaAlfa = entradaAlfa.replace(" ", "");
        if(utilSVM.verificarAlfas(entradaAlfa)){
            System.out.println("Formato de alfas correcto");
        } else {
            System.out.println("Formato incorrecto");
        }
        double[] alfa = UtilSVM.getAlfas(entradaAlfa, puntos.size());
        System.out.println(Arrays.toString(alfa));
        
        //Proceso de aprendizaje
        procesoAprendizaje = svm.aprendizaje(alfa, puntos);
        System.out.println(procesoAprendizaje.getProceso());
        double[] w = procesoAprendizaje.getW();
        double b = procesoAprendizaje.getB();
        
        //Clasificar puntos
        ArrayList<double[]> puntosClas = new ArrayList<>();
        puntosClas.add(new double[]{1f,0.5f});
        puntosClas.add(new double[]{-0.5f,-0.5f});
        puntosClas.add(new double[]{-2f,0.5f});
        puntosClas.add(new double[]{0.5f,-0.2f});
        puntosClas.add(new double[]{2f,-1f});
        System.out.println("Clasificacion de puntos");
        
        //System.out.println(svm.clasificarPuntos(puntosClas, w, b));
    }
    

}