/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import Util.*;
import java.util.Arrays;

/**
 *
 * @author pichon
 */
public class PerceptronJUnitTest {
    
    public PerceptronJUnitTest() {
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
    @Ignore
    public void test(){
//        String entrada = "0,0,0;\n"
//                + "0,1,1;\n"
//                + "1,0,1;\n"
//                + "1,1,1;";
        
//        String entrada = "0,0,0;\n"
//                + "0,1,0;\n"
//                + "1,0,0;\n"
//                + "1,1,1;";
        
        String entrada = "1,2,0;\n"
                + "3,2,0;\n"
                + "2,0,0;\n"
                + "0,3,0;\n"
                + "2,-4,0;\n"
                + "-1,-2,1;\n"
                + "-2,-3,1;\n"
                + "-1,0,1;\n"
                + "-1,-1,1;";
        String pesos = "0.4;0.2;0.2;";
        int dim = 2;
        Perceptron perceptron = new Perceptron();
        perceptron.setDim(dim);
        perceptron.setAlfa(Double.parseDouble("0.5"));
        perceptron.setFuncion("Escalera");
        perceptron.setEntradas(UtilPercep.cargarPuntos(entrada, dim));
        perceptron.setWs(UtilPercep.cargarWs(pesos, dim));
        SalidaAprendizajePercep procesoAprendizaje = perceptron.comenzarAprendizaje();
        System.out.println(procesoAprendizaje.getProcesoAprendizaje());
        //redondear valores
        double[] pesosEstables = perceptron.getWs();
        for (int i = 0; i < pesosEstables.length; i++) {
            pesosEstables[i] = UtilPercep.redondearDecimales(pesosEstables[i], 2);
        }
        perceptron.setWs(pesosEstables);
        System.out.println("Pesos estables:" + Arrays.toString(perceptron.getWs()));
        
        //Clasificar puntos
        String entradaClasificar = "2,2;\n"
                                + "-2,2;\n"
                                + "2,-2;\n"
                                + "-2,-2;";
        ArrayList<ArrayList<Double>> puntosClasificar = UtilPercep.cargarPuntos(entradaClasificar, dim-1);
        String clasificacion = perceptron.ClasificarPuntos(puntosClasificar, dim);
        System.out.println(clasificacion);
        
    }
    
}
