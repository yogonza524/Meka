/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author pichon
 */
public class Perceptron {
    
    private double net = 0;
    private double error = 0;
    private String funcion;

    private static double valorAlfa = 0;
    private static double errorGeneral = 0;
    
    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public static double getErrorGeneral() {
        return errorGeneral;
    }

    public static void setErrorGeneral(double errorGen) {
        errorGeneral = errorGen;
    }
    
    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public static double getValorAlfa() {
        return valorAlfa;
    }

    public static void setValorAlfa(double valorAlfa) {
        Perceptron.valorAlfa = valorAlfa;
    }
    
    private Conexion[] conexionesSalida;
    private Conexion[] conexionesEntrada;
    
    //Get y Set Conexiones
    public Conexion[] getConexionesSalida() {
        return conexionesSalida;
    }

    public void setConexionesSalida(Conexion[] conexionesSalida) {
        this.conexionesSalida = conexionesSalida;
    }

    public Conexion[] getConexionesEntrada() {
        return conexionesEntrada;
    }

    public void setConexionesEntrada(Conexion[] conexionesEntrada) {
        this.conexionesEntrada = conexionesEntrada;
    }
    
    //Get set cantidadCOnexiones
    public void cantConexionesSalidas(int cantEntradas){
        conexionesSalida = new Conexion[cantEntradas];
    }
    
    public void cantConexionesEntrada(int cantEntradas){
        conexionesEntrada = new Conexion[cantEntradas];

    }
    
    //Metodo verificado
    public void calcularNet(){
        double net = 0;
        for (int i = 0; i < conexionesEntrada.length; i++) {
            net = net + conexionesEntrada[i].getValor() * conexionesEntrada[i].getPeso();
        }
        setNet(net);
    }
    
    //Metodo verificado
    public double getSalida(){
        return TipoFuncion.getTipoFuncion(funcion, net);
    }
    
    //Ver de nuevo
    public void actualizarPeso(){ //Las salidas deben ya haberse actualizado
        double nuevoPeso, pesoActual, error, valorConexion;
        for (int i = 0; i < conexionesEntrada.length; i++) {
            pesoActual = conexionesEntrada[i].getPeso();
            error = getError();
            valorConexion = conexionesEntrada[i].getValor();
            nuevoPeso = pesoActual + (getValorAlfa() * error * valorConexion);
            conexionesEntrada[i].setPeso(nuevoPeso);
        }
    }
    
    //Metodo verificado
    public double getErrorPerceptron(int cantSalidas){
        double error = 0;
        for (int i = 0; i < conexionesSalida.length; i++) {
            error = error + (conexionesSalida[i].getPerceptronSalida().getError() * conexionesSalida[i].getPeso());
        }
        error = error * TipoFuncion.getDerivadaTipoFuncion(funcion, net);
        setError(error);
        return error;
    }
    
    //Metodo verificado
    public double getErrorPerceptronSalida(double salidaDeseada) {
        double error;
        error =  (salidaDeseada - getSalida())* TipoFuncion.getDerivadaTipoFuncion(funcion, net);
        setError(error);
        return error;
    }
    
    //Metodo verificado
    public void actualizarValorSalidaConexion() {
        for (int i = 0; i < conexionesSalida.length; i++) {
                conexionesSalida[i].setValor(getSalida());
        }
    }
    
}
