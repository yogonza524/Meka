/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.kmeans;

/**
 * Autor del codigo:
    Jonathan Leibiusky (xetorthio)
    https://github.com/xetorthio
 * Modificado por pichon
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {
    
    List<Punto> puntos;
    
    public KMeansResultado calcular(Integer k, String tipoInicio) {
        ArrayList<ArrayList<Cluster>> historialCluster = new ArrayList<>();
        ArrayList<Cluster> clusters = inicializarCentroides(puntos, k, tipoInicio);
        
        historialCluster.add((ArrayList<Cluster>) clusters.clone());
	while (!finalizo(clusters)) {
	    prepararClusters(clusters);
	    asignarPuntos(puntos, clusters);
	    recalcularCentroides(clusters);
            historialCluster.add((ArrayList<Cluster>) clusters.clone());
	}

	Double ofv = calcularFuncionObjetivo(clusters);
        KMeansResultado resultKMeans = new KMeansResultado(clusters, ofv, k);
        resultKMeans.setHistorialCluster(historialCluster);
        return resultKMeans;
    }

    private void recalcularCentroides(List<Cluster> clusters) {
	for (Cluster c : clusters) {
	    if (c.getPuntos().isEmpty()) {
		c.setTermino(true);
		continue;
	    }

	    Float[] d = new Float[c.getPuntos().get(0).getGrado()];
	    Arrays.fill(d, 0f);
	    for (Punto p : c.getPuntos()) {
		for (int i = 0; i < p.getGrado(); i++) {
		    d[i] += (p.get(i) / c.getPuntos().size());
		}
	    }

	    Punto nuevoCentroide = new Punto(d);

	    if (nuevoCentroide.equals(c.getCentroide())) {
		c.setTermino(true);
	    } else {
		c.setCentroide(nuevoCentroide);
	    }
	}
    }

    private void asignarPuntos(List<Punto> puntos, List<Cluster> clusters) {
	for (Punto punto : puntos) {
	    Cluster masCercano = clusters.get(0);
	    Double distanciaMinima = Double.MAX_VALUE;
	    for (Cluster cluster : clusters) {
		Double distancia = punto.distanciaEuclideana(cluster
			.getCentroide());
		if (distanciaMinima > distancia) {
		    distanciaMinima = distancia;
		    masCercano = cluster;
		}
	    }
	    masCercano.getPuntos().add(punto);
	}
    }

    private void prepararClusters(List<Cluster> clusters) {
	for (Cluster c : clusters) {
	    c.limpiarPuntos();
	}
    }

    private Double calcularFuncionObjetivo(List<Cluster> clusters) {
	Double ofv = 0d;

	for (Cluster cluster : clusters) {
	    for (Punto punto : cluster.getPuntos()) {
		ofv += punto.distanciaEuclideana(cluster.getCentroide());
	    }
	}

	return ofv;
    }

    private boolean finalizo(List<Cluster> clusters) {
	for (Cluster cluster : clusters) {
	    if (!cluster.isTermino()) {
		return false;
	    }
	}
	return true;
    }

    private ArrayList<Cluster> elegirCentroidesAleatoria(List<Punto> puntos, Integer k) {
	ArrayList<Cluster> centroides = new ArrayList<Cluster>();

	List<Float> maximos = new ArrayList<Float>();
	List<Float> minimos = new ArrayList<Float>();
	// me fijo máximo y mínimo de cada dimensión

	for (int i = 0; i < puntos.get(0).getGrado(); i++) {
	    Float min = Float.POSITIVE_INFINITY, max = Float.NEGATIVE_INFINITY;

	    for (Punto punto : puntos) {
		min = min > punto.get(i) ? punto.get(0) : min;
		max = max < punto.get(i) ? punto.get(i) : max;
	    }

	    maximos.add(max);
	    minimos.add(min);
	}

	Random random = new Random();

	for (int i = 0; i < k; i++) {
	    Float[] data = new Float[puntos.get(0).getGrado()];
	    Arrays.fill(data, 0f);
	    for (int d = 0; d < puntos.get(0).getGrado(); d++) {
		data[d] = random.nextFloat()
			* (maximos.get(d) - minimos.get(d)) + minimos.get(d);
	    }

	    Cluster c = new Cluster();
	    Punto centroide = new Punto(data);
	    c.setCentroide(centroide);
	    centroides.add(c);
	}

	return centroides;
    }

    private ArrayList<Cluster> inicializarCentroides(List<Punto> puntos, Integer k, String tipoInicio) {
        if (tipoInicio.equals("Aleatoria")) {
            return elegirCentroidesAleatoria(puntos, k);
        } else {
            return elegirCentroidesForgy(puntos, k);
        }
    }

    private ArrayList<Cluster> elegirCentroidesForgy(List<Punto> puntos, Integer cantClus) {
        ArrayList<Cluster> cluster =  new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < cantClus; i++) {
            int index = random.nextInt(puntos.size());
	    Float[] data = puntos.get(index).getData();
            
	    Cluster c = new Cluster();
	    Punto centroide = new Punto(data);
	    c.setCentroide(centroide);
	    cluster.add(c);
	}
//        System.out.println("Calculo con forgy");
        return cluster;
    }

    public void addPuntos(ArrayList<ArrayList<Double>> datosPuntos) {
        puntos = new ArrayList<>();
        //Crear los puntos para el algoritmo Kmeans
	for (int i = 0; i < datosPuntos.size(); i++) {
	    Punto p = new Punto(datosPuntos.get(i));
	    puntos.add(p);
	}
    }
}

