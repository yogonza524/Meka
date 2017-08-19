/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Util.ArbolDecision;
import Util.NodoArbolDecision;
import Util.UtilArbolDecision;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pichon
 */
public class JUnitTestArbolDec {
    
    public JUnitTestArbolDec() {
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
        ArrayList<ArrayList<String>> matrizDatos = new ArrayList<>();
        ArrayList<String> nombreAtributos = new ArrayList<>();
        String atributos = "A, B, C, D;";
        String entrada = "X, S, O, Q, 2;\n"
                    + "Y, T, P, R, 1;\n"
                    + "Z, T, O, R, 2;\n"
                    + "X, T, O, R, 2;\n"
                    + "X, T, P, Q, 2;\n"
                    + "Y, S, P, Q, 1;\n"
                    + "Z, S, P, Q, 1;\n"
                    + "Z, S, P, Q, 1;\n"
                    + "Y, T, P, R ,1;\n"
                    + "Z, S, P, Q, 2;";
        int cantAtributos = 4;
        int cantClases = 2;
        matrizDatos = UtilArbolDecision.getDatosEntrada(entrada, cantAtributos);
        nombreAtributos = UtilArbolDecision.getNombreAtributos(atributos, cantAtributos);
        
        //Creo el objeto arbol de decision
        ArbolDecision arbolDecision = new ArbolDecision();
        
        //Seteo valores iniciales
        arbolDecision.setMatrizDatos(matrizDatos);
        arbolDecision.setCantAtributos(cantAtributos);
        arbolDecision.setCantClases(cantClases);
        arbolDecision.setNombreAtributos(nombreAtributos);
        
        //Corro el algoritmo con el problema cargado
        arbolDecision.runArbolDecision();
        System.out.println(arbolDecision.getProceso());
        NodoArbolDecision estructuraArbol = arbolDecision.getEstructuraArbol();
        
        //Muestro arbol de decision
        System.out.println(arbolDecision.mostrarArbol(estructuraArbol));
        
        //Clasificar entradas
        System.out.println("\nComienza proceso de clasificacion:");
        String entradaClasificar = "X, S, O, Q;\n"
                                + "Y, T, P, R;\n"
                                + "Z, T, O, R;\n"
                                + "X, T, O, R;\n"
                                + "X, T, P, Q;\n"
                                + "Y, S, P, Q;\n"
                                + "Z, S, P, Q;\n"
                                + "Z, S, P, Q;\n"
                                + "Y, T, P, R;\n"
                                + "Z, S, P, Q;";
        
        ArrayList<ArrayList<String>> clasificar = UtilArbolDecision.getDatosEntrada(entradaClasificar, cantClases);
        for (int i = 0; i < clasificar.size(); i++) {
            System.out.println("\nEntrada: "+ clasificar.get(i)+"\n"+arbolDecision.clasificarEntradas(clasificar.get(i), estructuraArbol));
        }
    }

    @Test
    @Ignore
    public void drawTest(){
        
    
    }
     

}
