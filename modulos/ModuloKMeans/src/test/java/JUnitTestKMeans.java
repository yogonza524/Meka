/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Util.Cluster;
import Util.KMeans;
import Util.KMeansResultado;
import Util.Punto;
import Util.UtilKmeans;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pichon
 */
public class JUnitTestKMeans {
    
    public JUnitTestKMeans() {
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
    public void hello() {
        String datos =    "6,8,2;\n"
                        + "12,60,8;\n"
                        + "9,81,2;\n"
                        + "11,62,8;\n"
                        + "9,63,8;\n"
                        + "9,94,4;\n"
                        + "6,54,8;\n"
                        + "1,16,0;\n"
                        + "19,96,2;\n"
                        + "18,57,6;\n"
                        + "17,47,8;\n"
                        + "12,26,6;\n"
                        + "7,78,2;\n"
                        + "4,58,4;\n"
                        + "6,99,0;\n"
                        + "3,49,6;\n"
                        + "11,1,5;";
        
//	List<String[]> myEntries = reader.readAll();
        int dim = 3;
        int cantCluster = 3;
        ArrayList<ArrayList<Double>> datosPuntos = UtilKmeans.cargarPuntos(datos, dim);

	KMeans kmeans = new KMeans();
        kmeans.addPuntos(datosPuntos);
        KMeansResultado resultado = kmeans.calcular(cantCluster, "Forgy");
        System.out.println("------- Con k=" + cantCluster + " ofv=" + resultado.getOfv()
                + "-------\n");
        int i = 0;
        for (Cluster cluster : resultado.getClusters()) {
            i++;
            System.out.println("-- Cluster " + i + " --\n");
            for (Punto punto : cluster.getPuntos()) {
                System.out.println(punto.toString() + "\n");
            }
            System.out.println("\n");
            System.out.println(cluster.getCentroide().toString());
            System.out.println("\n\n");
        }
    }
}
