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
import Util.*;
import org.junit.Ignore;

/**
 *
 * @author pichon
 */
public class JUnitTestPMC {
    
    public JUnitTestPMC() {
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
//    @Ignore
    public void hello() {
        int cantidadEntradas = 4;
        int cantidadSalidas = 2;
        String patronesEntrada =  "0,0,0,0;\n"
                                + "1,1,1,1;\n"
                                + "1,0,1,0;\n"
                                + "0,1,0,1;";
        
        String patronesSalida =   "0,0;\n"
                                + "0,1;\n"
                                + "1,0;\n"
                                + "1,1;";
        
        ArrayList<ArrayList<Double>> entradas = UtilPMC.cargarPuntos(patronesEntrada, cantidadEntradas);
        System.out.println("Cargo entradas");
        ArrayList<ArrayList<Double>> salidas = UtilPMC.cargarPuntos(patronesSalida, cantidadSalidas);
        System.out.println("Cargo salidas");
        String salidaProceso;
        ArrayList<Double> errorRedEpoca;
         
        PMC redNeuronal = new PMC();
        //Ingreso datos topologia de la red
        redNeuronal.setCantEntradas(cantidadEntradas);
        redNeuronal.setCantCapasOculta(2);
        redNeuronal.setCantNeuronasCapasOcultaI(3);
        redNeuronal.setCantSalida(cantidadSalidas);
        redNeuronal.setErrorAceptable(0.01);
        redNeuronal.setAlfa(0.5);
        redNeuronal.setFuncion("Lineal");
        
        //Ingresar patrones de entrada
        redNeuronal.setValorEntradas(entradas);
        //Ingresar patrones de salida
        redNeuronal.setValorSalida(salidas);
        
        //Datos de la red
        salidaProceso = redNeuronal.informeRed();
        System.out.println(salidaProceso);
         
        //Generar red
        System.out.println("Generando red...");
        redNeuronal.inicializarRedNeuronal();
         
        //Proceso de aprendizaje
        ProcesoAprendizaje procesoAprend;
        procesoAprend = redNeuronal.entrenarRedNeuronal();
        
        //mostrar las primeras n iteraciones del proceso. Sino son muchas y al pedo
        int iteraciones = 10;
        for (int i = 0; i < 1+(4*iteraciones); i++) {
            System.out.println(procesoAprend.getProceso().get(i));
        }
         
        //Muestro los pesos estabilizados que cumplen con el error aceptable
        System.out.println("- - - - - - - - - -");
        System.out.println("Pesos estabilizados");
        int ultimosCinco = procesoAprend.getProceso().size();
        for (int i = ultimosCinco-5; i < ultimosCinco; i++) {
            System.out.println(procesoAprend.getProceso().get(i));
        }
         
        //muestro el error por cada iteracion. Esto hay q meterlo en un grafico
        System.out.println("Error del proceso");
        for (int i = 0; i < procesoAprend.getErrorEpoca().size()-10; i=i+10) {
            System.out.println(procesoAprend.getErrorEpoca().get(i));
        }
         
        //Obtener conexiones de la red.
        ArrayList<ArrayList<Conexion>> conexiones = redNeuronal.getConexiones();
        for (int i = 0; i < conexiones.size(); i++) {
            //Conexiones del perceptron i
            System.out.println("Perceptron: " + i);
            for (int j = 0; j < conexiones.get(i).size(); j++) {
                System.out.println(conexiones.get(i).get(j).getNombre());
            }
        }
        
        //Obtener perceptrones
        ArrayList<Perceptron> perceptrones = redNeuronal.getPerceptrones();
        for (int i = 0; i < perceptrones.size(); i++) {
            System.out.println("Funcion P" + i + ": " + perceptrones.get(i).getFuncion());
        }
         
        //Realizar pruebas
        String clasificar =   "1,1,1,0.8;\n"
                            + "0.1,0.2,0,-0.1;\n"
                            + "1.2,0.1,1,0;";
        ArrayList<ArrayList<Double>> entradaClasificar = UtilPMC.cargarPuntos(clasificar, cantidadEntradas);
        
        for (int i = 0; i < entradaClasificar.size(); i++) {
            salidaProceso = redNeuronal.runRedNeuronal(entradaClasificar.get(i));
            System.out.println(salidaProceso);
        }
         
     }
    
    @Test
    @Ignore
    public void linealPCMTest(){
        /**
         * Test de simulacion del perceptron multicapa con funciones lineales
         */
        int cantidadEntradas = 2;
        int cantidadSalidas = 2;
        String patronesEntrada =  "0,0;\n"
                                + "1,1;\n"
                                + "1,0;\n"
                                + "0,1;";
        
        String patronesSalida =   "0,0;\n"
                                + "0,1;\n"
                                + "1,0;\n"
                                + "1,1;";
        
        ArrayList<ArrayList<Double>> entradas = UtilPMC.cargarPuntos(patronesEntrada, cantidadEntradas);
        System.out.println("Cargo entradas");
        ArrayList<ArrayList<Double>> salidas = UtilPMC.cargarPuntos(patronesSalida, cantidadSalidas);
        System.out.println("Cargo salidas");
        String salidaProceso;
        ArrayList<Double> errorRedEpoca;
         
        PMC redNeuronal = new PMC();
        //Ingreso datos topologia de la red
        redNeuronal.setCantEntradas(cantidadEntradas);
        redNeuronal.setCantCapasOculta(1);
        redNeuronal.setCantNeuronasCapasOcultaI(2);
        redNeuronal.setCantSalida(cantidadSalidas);
        redNeuronal.setErrorAceptable(0.01);
        redNeuronal.setAlfa(0.5);
        redNeuronal.setFuncion("Lineal");
        
        //Ingresar patrones de entrada
        redNeuronal.setValorEntradas(entradas);
        //Ingresar patrones de salida
        redNeuronal.setValorSalida(salidas);
        
        //Datos de la red
        salidaProceso = redNeuronal.informeRed();
        System.out.println(salidaProceso);
         
        //Generar red
        System.out.println("Generando red...");
        redNeuronal.inicializarRedNeuronal();
         
        //Proceso de aprendizaje
        ProcesoAprendizaje procesoAprend;
        procesoAprend = redNeuronal.entrenarRedNeuronal();
        
    }
}
