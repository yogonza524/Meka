/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.kmeans;

/**
 *
 * @author pichon
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeansResultado {
    private List<Cluster> clusters = new ArrayList<Cluster>();
    private Double ofv;
    private int cantCluster;
    private ArrayList<ArrayList<Cluster>> historialCluster;

    public KMeansResultado(List<Cluster> clusters, Double ofv, int cantCluster) {
	super();
	this.ofv = ofv;
	this.clusters = clusters;
        this.cantCluster = cantCluster;
    }

    public List<Cluster> getClusters() {
	return clusters;
    }

    public Double getOfv() {
	return ofv;
    }

    public int getCantCluster() {
        return cantCluster;
    }

    public ArrayList<ArrayList<Cluster>> getHistorialCluster() {
        return historialCluster;
    }

    public void setHistorialCluster(ArrayList<ArrayList<Cluster>> historialCluster) {
        this.historialCluster = historialCluster;
    }

    public String ClasificarPuntos(List<Punto> puntosClasificar) {
        String salida = "";
        double distancia;
        int numCluster = -1;
        for (int i = 0; i < puntosClasificar.size(); i++) {
            distancia = Double.POSITIVE_INFINITY;
            for (int j = 0; j < cantCluster; j++) {
                if (distancia > puntosClasificar.get(i).distanciaEuclideana(clusters.get(j).getCentroide())) {
                    distancia = puntosClasificar.get(i).distanciaEuclideana(clusters.get(j).getCentroide());
                    numCluster = j;
                }
            }
            salida = salida + "Punto: " + Arrays.toString(puntosClasificar.get(i).getData()) + " corresponde al cluster: " + numCluster + "(" + UtilKmeans.getColorName()[numCluster] + ")" + "\n";
        }
        return salida;
    }

}
