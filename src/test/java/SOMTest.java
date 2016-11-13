/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.core.meka.Util;
import java.io.IOException;
import org.apache.commons.lang.ArrayUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author gonza
 */
public class SOMTest {
    
    public SOMTest() {
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
     public void ingresarPatronesTest() {
         String patrones = "0.1,3.6,36.0;"
                 + "\n     5. 7, 5,2,25.87;";
         String[] result = patrones.replaceAll(" ", "").replaceAll("\n", "").split(";");
         System.out.println(validarFormatoPatrones(3, patrones));
    }
     
     @Test
     @Ignore
     public void leerFicheroTest() throws IOException{
         String dir = "/home/gonza/Descargas/grafo.txt";
         System.out.println(Util.leerFichero(dir));
     }
     
     private boolean validarFormatoPatrones(int dimension, String patrones){
        boolean result = true;
        String[] validar = patrones.replaceAll(" ", "").replaceAll("\n", "").split(";");
        for(String v : validar){
            String[] patron = v.split(",");
            if (patron.length != dimension) {
                result = false;
                break;
            }
        }
        return result;
    }
}
