/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.sun.org.apache.xpath.internal.operations.Variable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author pichon
 */
public class ArbolDecision {
    
    ArrayList<ArrayList<String>> matrizDatos = new ArrayList<>();
    ArrayList<String> nombreAtributos = new ArrayList<>();
    int cantAtributos = 4;
    int cantClases = 2;
    String proceso;
    NodoArbolDecision estructuraArbol;

    //Geter y Seter
    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
    
    public ArrayList<ArrayList<String>> getMatrizDatos() {
        return matrizDatos;
    }

    public void setMatrizDatos(ArrayList<ArrayList<String>> matrizDatos) {
        this.matrizDatos = matrizDatos;
    }

    public ArrayList<String> getNombreAtributos() {
        return nombreAtributos;
    }

    public void setNombreAtributos(ArrayList<String> nombreAtributos) {
        this.nombreAtributos = nombreAtributos;
    }

    public int getCantAtributos() {
        return cantAtributos;
    }

    public void setCantAtributos(int cantAtributos) {
        this.cantAtributos = cantAtributos;
    }

    public int getCantClases() {
        return cantClases;
    }

    public void setCantClases(int cantClases) {
        this.cantClases = cantClases;
    }

    public NodoArbolDecision getEstructuraArbol() {
        return estructuraArbol;
    }

    public void setEstructuraArbol(NodoArbolDecision estructuraArbol) {
        this.estructuraArbol = estructuraArbol;
    }
    
    //Metodos clase
    public void runArbolDecision() {
        //Inicializo el sistema
        int posClase = matrizDatos.get(0).size()-1;
        ArrayList<String> tiposClases = getClases(matrizDatos, posClase);
        
        ClaseAuxiliar salida = correrAlgoritmo(matrizDatos, cantClases, new ArrayList<>(nombreAtributos), tiposClases);    
        proceso = "Comienzo algoritmo: \n\n" + salida.getProceso();
        estructuraArbol = salida.getEstructuraArbol();
    }

    private double getEntropia(ArrayList<ArrayList<String>> matrizAux, ArrayList<String> tiposClases) {
        double salida = 0;
        double total = matrizAux.size();
        double[] observaciones = getObservaciones(matrizAux, tiposClases);
        for (int i = 0; i < tiposClases.size(); i++) {
            salida = salida + ((observaciones[i]/total)*logBase(tiposClases.size(), (observaciones[i]/total)));
        }
        return salida;
    }
    
    private double logBase(double base, double numero){
        if (numero == 0) {
            return 0;
        }
        return -(Math.log(numero) / Math.log(base));
    }

    private ArrayList<String> getClases(ArrayList<ArrayList<String>> matrizDatos, int columna) {
        ArrayList<String> salida = new ArrayList<>();
        salida.add(matrizDatos.get(0).get(columna));
        for (int j = 1; j < matrizDatos.size(); j++) {
            if (!salida.contains(matrizDatos.get(j).get(columna))) {
                salida.add(matrizDatos.get(j).get(columna));
            }
        }
        return salida;
    }

    //Obtengo la cantidad de veces q aparece cada clase
    private double[] getObservaciones(ArrayList<ArrayList<String>> matrizAux, ArrayList<String> tiposClases) {
        double[] observaciones = new double[tiposClases.size()];
        int posClase = matrizAux.get(0).size()-1;
        for (int i = 0; i < tiposClases.size(); i++) {
            observaciones[i] = 0;
            for (int j = 0; j < matrizAux.size(); j++) {
                if (matrizAux.get(j).get(posClase).equals(tiposClases.get(i))) {
                    observaciones[i] = observaciones[i] + 1;
                }
            }
        }
        return observaciones;
    }

    private ClaseAuxiliar getEntropiaVariables(int columna, ArrayList<ArrayList<String>> matrizAux, ArrayList<String> tiposClases, String nombreAtributo, ArrayList<String> variablesAtributo) {
        ClaseAuxiliar salida = new ClaseAuxiliar();
        ArrayList<Double> entropiaAtributos = new ArrayList<>();
        String proceso = "";
        double entropiaAux, total, aux;
        
        //Obtengo las observaciones de cada variable del atributo
        double[][] cantObserv = getObservAtributos(matrizAux, tiposClases, columna, variablesAtributo);

        for (int i = 0; i < variablesAtributo.size(); i++) {
            entropiaAux = 0;
            total = cantObserv[i][tiposClases.size()];
            String stringAux = "";
            for (int j = 0; j < tiposClases.size(); j++) {
                aux = cantObserv[i][j]/total;
                stringAux += "+("+cantObserv[i][j]+"/"+total+")log"+tiposClases.size()+"("+cantObserv[i][j]+"/"+total+")";
                if (aux == 0 || aux == 1) {
                    entropiaAux = 0;
                }
                entropiaAux = entropiaAux + (aux*logBase(tiposClases.size(), aux));
            }
            stringAux = stringAux.substring(1);
            entropiaAtributos.add(entropiaAux);
            proceso += "Entropia[" + nombreAtributo + "-" + variablesAtributo.get(i)+"]: "+ stringAux +"= " + entropiaAux + "\n";
        }
        salida.setProceso(proceso);
        salida.setValores(entropiaAtributos);
        return salida;
    }
    
    private double[][] getObservAtributos(ArrayList<ArrayList<String>> matrizAux, ArrayList<String> tiposClases, int columna, ArrayList<String> claseAtributo) {
        double[][] observaciones = new double[claseAtributo.size()][tiposClases.size()+1];
        int posClase = matrizAux.get(0).size()-1;
        int contador;
        for (int k = 0; k < claseAtributo.size(); k++) {
            for (int i = 0; i < tiposClases.size(); i++) {
                observaciones[k][i] = 0;
                contador = 0;
                for (int j = 0; j < matrizAux.size(); j++) {
                    if (matrizAux.get(j).get(columna).equals(claseAtributo.get(k))) {
                        contador = contador +1;
                        if (matrizAux.get(j).get(posClase).equals(tiposClases.get(i))) {
                            observaciones[k][i] = observaciones[k][i] + 1;
                        }
                    }
                }
                observaciones[k][tiposClases.size()] = contador;
            }
            
        }
        return observaciones;
    }

    private ClaseAuxiliar getEntropiaAtributo(ArrayList<Double> entropiaVariables, ArrayList<ArrayList<String>> matrizAux, int columna, String atributo) {
        ClaseAuxiliar salida = new ClaseAuxiliar();
        double total = matrizAux.size();
        double entropia = 0;
        double observaciones;
        String stringAux = "";
        ArrayList<String> variablesAtributo = getClases(matrizAux, columna);
        
        for (int i = 0; i < variablesAtributo.size(); i++) {
            observaciones = 0;
            for (int j = 0; j < matrizAux.size(); j++) {
                if (matrizAux.get(j).get(columna).equals(variablesAtributo.get(i))) {
                    observaciones++;
                }
            }
            entropia += (observaciones/total)*entropiaVariables.get(i);
            stringAux += "+("+observaciones+"/"+total+")*"+entropiaVariables.get(i);
        }
        stringAux = "Entropia "+atributo+": "+stringAux.substring(1)+"= "+entropia+"\n";
        stringAux += "Ganancia: 1-"+entropia+"= "+(1-entropia)+"\n\n";
        salida.setProceso(stringAux);
        salida.setEntropia(entropia);
        return salida;
    }

    private int getMejor(ArrayList<Double> entropiaAtributos) {
        int index = 0;
        double valor = 999;
        for (int i = 0; i < entropiaAtributos.size(); i++) {
            if (valor > entropiaAtributos.get(i)) {
                valor = entropiaAtributos.get(i);
                index = i;
            }
        }
        return index;
    }

    private ArrayList<ArrayList<ArrayList<String>>> subdividirMariz(ArrayList<ArrayList<String>> matriz, int mejorAtributo, ArrayList<String> variables) {
        ArrayList<ArrayList<ArrayList<String>>> salida = new ArrayList<>();
        for (int i = 0; i < variables.size(); i++) {
            ArrayList<ArrayList<String>> matrizAux = new ArrayList<>();
            
            for (int j = 0; j < matriz.size(); j++) {
                if (matriz.get(j).get(mejorAtributo).equals(variables.get(i))) {
                    matriz.get(j).remove(mejorAtributo);
                    matrizAux.add(matriz.get(j));
                }
            }
            salida.add(matrizAux);
        }
        return salida;
    }

    private ClaseAuxiliar correrAlgoritmo(ArrayList<ArrayList<String>> matrizAux, int cantClases, ArrayList<String> atributos, ArrayList<String> tiposClases) {
        ClaseAuxiliar salida = new ClaseAuxiliar();
        ArrayList<ArrayList<Double>> calculos = new ArrayList<>();
        ArrayList<Double> entropiaAtributos = new ArrayList<>();
        String proceso = "";
        double entropia;
        int columnaClase = matrizAux.get(0).size()-1;
        
        //Calculo la entropia general del sistema
        entropia = getEntropia(matrizAux, tiposClases);
        proceso += "Entropia general: " + entropia + "\n";
        if (entropia == 0) {
            salida.setProceso(proceso);
            salida.setNodo(false);
            salida.setClase("Clase: "+ matrizAux.get(0).get(columnaClase));
            return salida;
        }
        
//        if (atributos.isEmpty()){
//            proceso += "Error: ya no hay atributos a clasificar";
//            salida.setProceso(proceso);
//            salida.setNodo(false);
//            salida.setClase("Error");
//            return salida;
//        }
        
        //Para cada atributo caluculo la entropia
        for (int i = 0; i < matrizAux.get(0).size()-1; i++) {
            //Obtengo las variables del atributo
            ArrayList<String> variablesAtributo = getClases(matrizAux, i);
            proceso += "Variables del atributo "+ atributos.get(i) +": " + variablesAtributo + "\n";
            
            //Si la entropia es distinta de cero y solo queda una variable para el atributo, 
            //y no quedan mas atributos por los cuales clasificar salgo.
            if ((variablesAtributo.size() == 1)) { //Consultar esta parte -------------------------
                proceso += "Solo queda una variable para el atributo";
                salida.setProceso(proceso);
                salida.setNodo(false);
                //Debo obtener el porcentaje en el q aparece cada clase
                ArrayList<String> clasePorcentaje = new ArrayList<>();
                ArrayList<Double> porcentaje = new ArrayList<>();
                int total = matrizAux.size();
                String claseIndefinida = "";
                for (int j = 0; j < cantClases; j++) {
                    int cont = 0;
                    for (int k = 0; k < matrizAux.size(); k++) {
                        if (matrizAux.get(k).get(columnaClase).equals(tiposClases.get(j))) {
                            cont++;
                        }
                    }
                    clasePorcentaje.add(tiposClases.get(j));
                    double porcent = ((double)cont)/((double)total);
                    porcentaje.add(porcent);
                    claseIndefinida += "Clase: "+ tiposClases.get(j)+" : "+new DecimalFormat("#.##").format(porcent)+"%\n";
                }
                salida.setClase(claseIndefinida);
                salida.setClasePorcentaje(clasePorcentaje);
                salida.setPorcentaje(porcentaje);
                salida.setIndefinida(true);
                return salida;
            }
            
            //Calculo la entropia de cada variable del atributo
            ClaseAuxiliar datosEntropiaVariable = getEntropiaVariables(i, matrizAux, tiposClases, atributos.get(i), variablesAtributo);
            calculos.add(datosEntropiaVariable.getValores());
            proceso += datosEntropiaVariable.getProceso();

            //Calcular la entropia total del atributo y su ratio
            ClaseAuxiliar datosEntropiaAtributo = getEntropiaAtributo(datosEntropiaVariable.getValores(), matrizAux, i, atributos.get(i));
            entropiaAtributos.add(datosEntropiaAtributo.getEntropia());
            proceso += datosEntropiaAtributo.getProceso();
                
        }
        
        //Selecciono el atributo q mejor represente al conjunto
        if (entropiaAtributos.isEmpty()) {
            proceso += "Ya no es posible subdividir\n";
            salida.setProceso(proceso);
            salida.setNodo(false);
            salida.setClase("Error");
            return salida;
        }
        
        int indexMejorAtributo = getMejor(entropiaAtributos);
        String nombreMejorAtriburo = atributos.get(indexMejorAtributo);
        ArrayList<String> subAtributos = atributos;
        proceso += "Atributo seleccionado como mejor: " + atributos.get(indexMejorAtributo)+"\n";
        subAtributos.remove(indexMejorAtributo);
        
        //Creo un nuevo nodo del arbol
        NodoArbolDecision nodoArbol = new NodoArbolDecision();
        nodoArbol.setNombeAtributo(nombreMejorAtriburo);
        //Busco el indice del atributo en la matriz de entrada
        for (int i = 0; i < nombreAtributos.size(); i++) {
            if (nombreMejorAtriburo.equals(nombreAtributos.get(i))) {
                nodoArbol.setIndice(i);
                break;
            }
        }
        
        //Obtengo las variables del atributo seleccionado
        ArrayList<String> variablesAtri = getClases(matrizAux, indexMejorAtributo);
        nodoArbol.setVariablesAtributo(variablesAtri);
        
        //Genero las sub tablas
        ArrayList<ArrayList<ArrayList<String>>> subMatriz = subdividirMariz(matrizAux, indexMejorAtributo, variablesAtri);
        proceso += "Submatrices: \n";
        for (int i = 0; i < subMatriz.size(); i++) {
            proceso += "Variable: "+variablesAtri.get(i)+"  "+subMatriz.get(i)+"\n";
        }
        
        //Llamo recursivamente a la funcion
        ArrayList<NodoArbolDecision> estructuraA = new ArrayList<>();
        ArrayList<String> clasificacion = new ArrayList<>();
        boolean tieneHijos = false;
        for (int i = 0; i < subMatriz.size(); i++) {
            proceso += "\nSub arbol de decision atributo: "+ nombreMejorAtriburo +", variable: "+ variablesAtri.get(i) +"\n";
            proceso += "Atributos: "+subAtributos+"\n";
            ClaseAuxiliar salidaAux = correrAlgoritmo(subMatriz.get(i), cantClases, subAtributos, tiposClases);
            proceso += salidaAux.getProceso()+"\n";
            if (salidaAux.isNodo()) {
                estructuraA.add(salidaAux.getEstructuraArbol());
                clasificacion.add(null);
                tieneHijos = true;
            }else{
                estructuraA.add(null);
                clasificacion.add(salidaAux.getClase());
            }
        }
        nodoArbol.setHijos(estructuraA);
        nodoArbol.setClase(clasificacion);
        if (tieneHijos) {
            salida.setNodo(true);
        }
        salida.setProceso(proceso);
        salida.setEstructuraArbol(nodoArbol);
        return salida;
    }

    public String clasificarEntradas(ArrayList<String> clasificar, NodoArbolDecision estructuraArbol) {
        String salida = "";
        
        for (int j = 0; j < estructuraArbol.getVariablesAtributo().size(); j++) {
            if (clasificar.get(estructuraArbol.getIndice()).equals(estructuraArbol.getVariablesAtributo().get(j))) {
                if (estructuraArbol.getHijos().get(j) != null) {
                    //Llamo recursivamente a clasificar entrada
                    salida = clasificarEntradas(clasificar, estructuraArbol.getHijos().get(j));
                }else{
                    //Si es null es porque el nodo es una hoja por lo tanto devuelvo la clase
                    salida = estructuraArbol.getClase().get(j);
                }
            }
        }
        return salida;
    }

    public String mostrarArbol(NodoArbolDecision estructuraArbol) {
        String salida = "Arbol de decision:\n";
        salida += "Atributo: "+estructuraArbol.getNombeAtributo()+"\n";
        for (int i = 0; i < estructuraArbol.getVariablesAtributo().size(); i++) {
            salida += "\tVariable: "+estructuraArbol.getVariablesAtributo().get(i)+"\n";
            if (estructuraArbol.getHijos().get(i)!=null) {
                mostrarArbol(estructuraArbol.getHijos().get(i));
            }else{
                salida += "Clase: "+estructuraArbol.getClase().get(i)+"\n";
            }
        }
        return salida;
    }

    
}
