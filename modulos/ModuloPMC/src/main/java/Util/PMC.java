/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author pichon
 */
public class PMC {

    private ArrayList<ArrayList<Double>> valorEntradas;
    private ArrayList<ArrayList<Double>> valorSalida;
    private int cantEntradas;
    private int cantCapasOculta;
    private int cantNeuronasCapasOcultaI;
    private int cantSalida;
    private double errorAceptable;
    private double alfa;
    private String funcion; //1 funcion sigmoidal, 2 funcion escalon, 3...
    
    private final Random  rnd = new Random();
    private Perceptron[] capaOculta;
    private Perceptron[] capaSalida;
    private double[] salida;

    public Perceptron[] getCapaOculta() {
        return capaOculta;
    }

    public void setCapaOculta(Perceptron[] capaOculta) {
        this.capaOculta = capaOculta;
    }

    public Perceptron[] getCapaSalida() {
        return capaSalida;
    }

    public void setCapaSalida(Perceptron[] capaSalida) {
        this.capaSalida = capaSalida;
    }
    
    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public ArrayList<ArrayList<Double>> getValorEntradas() {
        return valorEntradas;
    }

    public void setValorEntradas(ArrayList<ArrayList<Double>> valorEntradas) {
        this.valorEntradas = valorEntradas;
    }

    public ArrayList<ArrayList<Double>> getValorSalida() {
        return valorSalida;
    }

    public void setValorSalida(ArrayList<ArrayList<Double>> valorSalida) {
        this.valorSalida = valorSalida;
    }

    public int getCantEntradas() {
        return cantEntradas;
    }

    public void setCantEntradas(int cantEntradas) {
        this.cantEntradas = cantEntradas;
    }

    public int getCantCapasOculta() {
        return cantCapasOculta;
    }

    public void setCantCapasOculta(int cantCapasOculta) {
        this.cantCapasOculta = cantCapasOculta;
    }

    public int getCantNeuronasCapasOcultaI() {
        return cantNeuronasCapasOcultaI;
    }

    public void setCantNeuronasCapasOcultaI(int cantNeuronasCapasOcultaI) {
        this.cantNeuronasCapasOcultaI = cantNeuronasCapasOcultaI;
    }

    public int getCantSalida() {
        return cantSalida;
    }

    public void setCantSalida(int cantSalida) {
        this.cantSalida = cantSalida;
    }

    public double getErrorAceptable() {
        return errorAceptable;
    }

    public void setErrorAceptable(double errorAceptable) {
        this.errorAceptable = errorAceptable;
    }

    public double[] getSalida() {
        return salida;
    }

    public void setSalida(double[] salida) {
        this.salida = salida;
    }
    
    public void setAlfa(double valorAlfa){
        alfa = valorAlfa;
    }
    
    public double getAlfa(){
        return alfa;
    }
    
    public void initValues(){
        capaOculta = new Perceptron[cantCapasOculta * cantNeuronasCapasOcultaI];
        capaSalida = new Perceptron[cantSalida];
        salida = new double[cantSalida];
    }
    
    public void inicializarRedNeuronal(){
        
        Perceptron.setValorAlfa(alfa);
        int inicio, inicioE, fin, finE, cont = 0;
        initValues();
        
        //Inicializamos los perceptrones de la primera capa oculta. Funciona bien
//        System.out.println("Capa oculta numero: " + 0);
        for (int i = 0; i < cantNeuronasCapasOcultaI; i++) {
//            System.out.println("Perceptron numero: " + i);
            Perceptron perceptronCapaOculta = new Perceptron();
            perceptronCapaOculta.setFuncion(funcion);
            perceptronCapaOculta.setConexionesEntrada(new Conexion[cantEntradas]);
            if (cantCapasOculta == 1) {
                perceptronCapaOculta.setConexionesSalida(new Conexion[cantSalida]);
            }else{
                perceptronCapaOculta.setConexionesSalida(new Conexion[cantNeuronasCapasOcultaI]);
            }
            for (int j = 0; j < cantEntradas; j++) {
                Conexion conexion = new Conexion();
//                conexion.setNombre("w"+cont);
                conexion.setNombre("wE"+j+"-"+i);
                conexion.setPerceptronEntrada(null);
                conexion.setPerceptronSalida(perceptronCapaOculta);
                conexion.setPeso(rnd.nextDouble());
//                System.out.print(conexion.getPeso());
                perceptronCapaOculta.getConexionesEntrada()[j] = conexion;
                cont++;
            }
//            System.out.println("");
            capaOculta[i] = perceptronCapaOculta;
        }
        
        
        if (cantCapasOculta != 1) {
            
            //Inicializamos los perceptrones las demas capas ocultas. funciona bien
            if (cantCapasOculta > 1) {
                int cantConexionesSalidas;
                for (int w = 1; w < cantCapasOculta; w++) {
    //                System.out.println("Capa oculta numero: " + w);
                    inicio = w*cantNeuronasCapasOcultaI;
                    fin = (w + 1)*cantNeuronasCapasOcultaI;
                    inicioE = (w-1)*cantNeuronasCapasOcultaI;                    
                    finE = w*cantNeuronasCapasOcultaI;
    //                System.out.println("inicio: " + inicio);
    //                System.out.println("fin: " + fin);
                    if (w == cantCapasOculta-1 ) {
                        cantConexionesSalidas = cantSalida;
                    } else {
                        cantConexionesSalidas = cantNeuronasCapasOcultaI;
                    }
                    cont = 0;
                    for (int i = inicio; i < fin; i++) {
//                        System.out.println("Perceptron numero: " + i);
                        Perceptron perceptronCapaOculta = new Perceptron();
                        perceptronCapaOculta.setFuncion(funcion);
                        perceptronCapaOculta.setConexionesEntrada(new Conexion[cantNeuronasCapasOcultaI]);
                        perceptronCapaOculta.setConexionesSalida(new Conexion[cantConexionesSalidas]);
    //                    System.out.println("Establecer conecciones");

                        //se conectan con las neuronas anterirores. Funciona bien
                        for (int j = 0; j < cantNeuronasCapasOcultaI; j++) {
                            Conexion conexion = new Conexion();
//                            conexion.setNombre("w"+cont);
//                            conexion.setNombre("w"+((i)+j)+"-"+((w*cantNeuronasCapasOcultaI)+j));
                            conexion.setNombre("w"+(inicioE+j)+"-"+(finE+cont));
                            conexion.setPerceptronEntrada(capaOculta[(inicio-cantNeuronasCapasOcultaI)+j]);
                            conexion.setPerceptronSalida(perceptronCapaOculta);
                            conexion.setPeso(rnd.nextDouble());
//                            System.out.print(conexion.getPeso());
                            perceptronCapaOculta.getConexionesEntrada()[j] = conexion;
                            capaOculta[(inicio-cantNeuronasCapasOcultaI)+j].getConexionesSalida()[i-inicio] = conexion;
    //                        System.out.println("Conexion Perceptron: " + i + " con el perceptron: " + ((inicio-cantNeuronasCapasOcultaI)+j));
                        }
                        cont++;
                        capaOculta[i] = perceptronCapaOculta;
                    }
                    
                }

            }
        }
        

        //Inicializamos los perceptrones de la capa conexionesSalida. Creo que si funciona
        inicio = (cantCapasOculta-1) * cantNeuronasCapasOcultaI;
//        System.out.println("Capa conexionesSalida");
        for (int i = 0; i < cantSalida; i++) {
//            System.out.println("Perceptron capa conexionesSalida: " + i);
            Perceptron perceptronSalida = new Perceptron();
            perceptronSalida.setFuncion(funcion);
            perceptronSalida.setConexionesEntrada(new Conexion[cantNeuronasCapasOcultaI]);
            for (int j = 0; j < cantNeuronasCapasOcultaI; j++) {
                Conexion conexion = new Conexion();
                conexion.setNombre("w"+(((cantCapasOculta-1)*cantNeuronasCapasOcultaI)+j)+"-"+((cantCapasOculta*cantNeuronasCapasOcultaI)+i));
                conexion.setPerceptronEntrada(capaOculta[inicio + j]);
                conexion.setPerceptronSalida(perceptronSalida);
                conexion.setPeso(rnd.nextDouble());
//                System.out.print(conexion.getPeso());
                capaOculta[inicio + j].getConexionesSalida()[i] = conexion;
                perceptronSalida.getConexionesEntrada()[j] = conexion;
//                System.out.println("Conexion Perceptron conexionesSalida: " + i + " con el perceptron capa oculta: " + (inicio+j));
                cont++;
            }
            capaSalida[i] = perceptronSalida;
//            System.out.print("");
        }

    }
    
    public ProcesoAprendizaje entrenarRedNeuronal(){
        ProcesoAprendizaje salidaProceso = new ProcesoAprendizaje();
        double[] salidaEsperada = new double[cantSalida];
        int numEntradas = valorEntradas.size();
        int epoca = 0;
        int ciclo = 0;
        double error;
        double errorPatron = 0;
                
        salidaProceso.addProceso("Comenzando proceso de aprendizaje");
        while(epoca < numEntradas){
            for (int i = 0; i < cantNeuronasCapasOcultaI; i++) {
                for (int j = 0; j < cantEntradas; j++) {
                    capaOculta[i].getConexionesEntrada()[j].setValor(valorEntradas.get(ciclo).get(j)); //[(ciclo*cantEntradas)+j]
                }
            }
            for (int i = 0; i < cantSalida; i++) {
                salidaEsperada[i] = valorSalida.get(ciclo).get(i); //[(ciclo*cantSalida)+i];
            }
            salidaProceso.addProceso(obtenerNetRed());      //Calcula el net y actualiza el valor de las conexiones
            salida = calcularSalida();                      //Obtenemos la conexionesSalida de la red
            error = calcularErrorRed(salidaEsperada);
            obtenerErroresPerceptrones(salidaEsperada);
            ActualizarPesoRed();
            errorPatron = errorPatron + error;
            
            salidaProceso.addProceso("Salida esperada: " + Arrays.toString(salidaEsperada));
            salidaProceso.addProceso("Salida obtenida: " + Arrays.toString(salida));
            salidaProceso.addProceso("Error cometido: " + error);
            
//            System.out.println();
//            System.out.println("Error: " + error);
//            System.out.println();
            
            if (error < errorAceptable) {
                epoca++;
            } else {
                epoca = 0;
            }
            ciclo++;
            if (ciclo == numEntradas) {
                salidaProceso.addError(errorPatron);
                ciclo = 0;
                errorPatron = 0;
            }
        }
        salidaProceso.addProceso("Aprendizaje completado!");
        return salidaProceso;
    }
    
    public String runRedNeuronal(ArrayList<Double> entrada){
        String salidaProceso = "";
        double[] salidaObtenida;
        salidaProceso = salidaProceso + "valor entrada:" + entrada + "\n";
//        System.out.println("valor entrada:" + Arrays.toString(entrada));
        //Cargamos el valor de prueba a la entrada de la red.
        ArrayList<Double> valorEntradasAux = entrada; 
        for (int i = 0; i < cantNeuronasCapasOcultaI; i++) {
            for (int j = 0; j < cantEntradas; j++) {
                capaOculta[i].getConexionesEntrada()[j].setValor(valorEntradasAux.get(j));
            }
        }
        //Calculamos el net y obtenemos la conexionesSalida de la red.
        obtenerNetRed();
        salidaObtenida = calcularSalida();
        salidaProceso = salidaProceso + "Salida:  ";
        for (int i = 0; i < salida.length; i++) {
            salidaProceso = salidaProceso + salidaObtenida[i] + "  ";
        }
        salidaProceso = salidaProceso + "\nSalida binaria:  ";
        for (int i = 0; i < salida.length; i++) {
            salidaProceso = salidaProceso + (int) Math.rint(salidaObtenida[i]) + " ";
        }
        salidaProceso = salidaProceso + "\n";
        return salidaProceso;
    }
    
    //Este metodo esta verificado
    private String obtenerNetRed(){
        String salidaNet = "Calcular net \n";
        int fin = cantCapasOculta * cantNeuronasCapasOcultaI;
        
        for (int i = 0; i < fin; i++) {
            capaOculta[i].calcularNet();
            capaOculta[i].actualizarValorSalidaConexion();
            salidaNet = salidaNet + "P"+i+": "+capaOculta[i].getNet()+"\n";
        }
        
        for (int i = 0; i < cantSalida; i++) {
            capaSalida[i].calcularNet();
            salidaNet = salidaNet + "S"+i+": "+capaOculta[i].getNet()+"\n";
        }
        return salidaNet;
    }
    
    //Nice
    private double[] calcularSalida(){
        double[] salidaActual = new double[cantSalida];
        for (int i = 0; i < cantSalida; i++) {
            salidaActual[i] = capaSalida[i].getSalida();
        }
        return salidaActual;
    }
    
    //metodo verificado
    private double calcularErrorRed(double[] salidaEsperada){
        double error = 0;
        for (int i = 0; i < cantSalida; i++) {
            error = error + (Math.pow((salidaEsperada[i] - capaSalida[i].getSalida()),2));            
        }
        return error/2;
    }
    
    //Metodo verificado (angaw)
    private void obtenerErroresPerceptrones(double[] salidaEsperada){
        for (int i = 0; i < cantSalida; i++) {
            capaSalida[i].getErrorPerceptronSalida(salidaEsperada[i]);
//            System.out.println("Error perceptron " + i + " de conexionesSalida: " + capaSalida[i].getError());
        }
        for (int i = (capaOculta.length-1) ; i > -1; i--) {
            capaOculta[i].getErrorPerceptron(cantSalida);
        }
    }
    
    //Metodo verificado
    private void ActualizarPesoRed(){
        for (int i = 0; i < cantSalida; i++) {
            capaSalida[i].actualizarPeso();
        }
        for (int i = capaOculta.length-1; i > -1; i--) {
            capaOculta[i].actualizarPeso();
        }
    }
    
    //Metodo para setear la funcion de cada perceptron
    private void setFuncionPerceptron(int perceptron, int funcion){
        System.out.println("No implementado");
    }
    
    //Metodo para seear el peso de las conexiones
    private void setPesoConexion(String nombreConexion, double peso){
        System.out.println("No implementado");
    }

    //Este metodo muestra los datos de la topologia de la red
    public String informeRed(){
        String salidaProceso = "";
        salidaProceso = salidaProceso + "Cantidad entradas: " + this.getCantEntradas() + "\n";
        salidaProceso = salidaProceso + "Cantidad capas ocultas: " + this.getCantCapasOculta() + "\n";
        salidaProceso = salidaProceso + "Cantidad de neuronas por capa: " + this.getCantNeuronasCapasOcultaI() + "\n";
        salidaProceso = salidaProceso + "Cantidad de salidas: " + this.getCantSalida() + "\n";
        salidaProceso = salidaProceso + "Valor de alfa: " + this.getAlfa() + "\n";
        salidaProceso = salidaProceso + "Error aceptable: " + this.getErrorAceptable() + "\n";
        
        return salidaProceso;
    }
    
    //
    public ArrayList<ArrayList<Conexion>> getConexiones(){
        ArrayList<ArrayList<Conexion>> conexionesSalida = new ArrayList<>();
        for (int i = 0; i < capaOculta.length; i++) {
            ArrayList<Conexion> conexPerceptron = new ArrayList<>();
            for (int j = 0; j < capaOculta[i].getConexionesEntrada().length; j++) {
                conexPerceptron.add(capaOculta[i].getConexionesEntrada()[j]);
            }
            conexionesSalida.add(conexPerceptron);
        }
        for (int i = 0; i < capaSalida.length; i++) {
            ArrayList<Conexion> conexPerceptron = new ArrayList<>();
            for (int j = 0; j < capaSalida[i].getConexionesEntrada().length; j++) {
                conexPerceptron.add(capaSalida[i].getConexionesEntrada()[j]);
            }
            conexionesSalida.add(conexPerceptron);
        }
        return conexionesSalida;
    }
    
    public ArrayList<Perceptron> getPerceptrones(){
        ArrayList<Perceptron> salidaPercep = new ArrayList<>();
        for (int i = 0; i < capaOculta.length; i++) {
            salidaPercep.add(capaOculta[i]);
        }
        for (int i = 0; i < capaSalida.length; i++) {
            salidaPercep.add(capaSalida[i]);
        }
        return salidaPercep;
    }
}
