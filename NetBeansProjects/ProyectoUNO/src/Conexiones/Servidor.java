package Conexiones;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 * Clase que representa el servidor que alojara a los clientes e intercambiará datos con ellos.
 * @author Stalin
 */

public class Servidor extends Thread{
    
    private ServerSocket server;
    private ArrayList<Socket> cliente;
    private int puerto;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private int cantidadClientes;
    private String mensajeRecibido;
    private int numClientesConectados;
    
    //private Thread hilo;
    
    /**
     * Constructor de servidor que crea un server por el puerto dado que admite un máximo de la cantidadClientes dada.
     * @param puerto
     * @param cantidadClientes 
     */
    public Servidor (int puerto,int cantidadClientes){
        try {
            server = new ServerSocket(puerto);
            this.cantidadClientes = cantidadClientes;
            cliente = new ArrayList<Socket>();
            salida=null;
            entrada=null;
            numClientesConectados = 0;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        //hilo = new Thread(this);
        //hilo.start();
    }
    
    
    /**
     * Envia datos al socket recibido como parámetro.
     * @param socket
     * @param dato 
     */
    public void enviar(Socket socket, String dato) {
        
        
        try {
            setSalida(new DataOutputStream(socket.getOutputStream()));
            getSalida().flush();
            getSalida().writeUTF(dato);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
	
    }
    

    /**
     * Cierra los flujos de entrada y salida, asi como los sockets conectados y tambien el server.
     */
    public void terminar(){
            try {

                for(int i =0 ; i<cantidadClientes;i++)
                    cliente.get(i).close();

                if(entrada!=null)
                    entrada.close();
                
                if(salida!=null)
                    salida.close();
                
                if(!server.isClosed())
                    server.close();
 
                //hilo.stop();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
    }
    
    /**
     * Devuelve el mensaje enviado por el socket dado como parametro.
     * @param cliente
     * @return mensajeRecibido 
     */
    public String recibir(Socket cliente){
        String mensajeRecibido = "";
        try {
            setEntrada(new DataInputStream(cliente.getInputStream()));
            
            mensajeRecibido = entrada.readUTF();
            return mensajeRecibido;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return null;
    }
    
    /**
     * Devuelve true si el socket dado como parametro existe en la lista de socket del servidor, false en caso contrario.
     * @param cliente
     * @return 
     */
    public boolean existeCliente(Socket cliente){
        boolean existencia = false;
        for(int i =0;i<cantidadClientes;i++){
            if(this.cliente.get(i).equals(cliente)){
                existencia = true;
                return existencia;
            }
        }
        
        return existencia;
    }

    /**
     * @return the server
     */
    public ServerSocket getServer() {
        return server;
    }
    
    

    /**
     * @param server the server to set
     */
    public void setServer(ServerSocket server) {
        this.server = server;
    }

    /**
     * @return the cliente
     */
    public ArrayList<Socket> getCliente() {
        return cliente;
    }
    
    public Socket getCliente(int i){
        return cliente.get(i);
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ArrayList cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the puerto
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    /**
     * @return the entrada
     */
    public DataInputStream getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salida
     */
    public DataOutputStream getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    /**
     * @return the cantidadClientes
     */
    public int getCantidadClientes() {
        return cantidadClientes;
    }

    /**
     * @param cantidadClientes the cantidadClientes to set
     */
    public void setCantidadClientes(int cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }
    
    public String getMensajeRecibido() {
        return mensajeRecibido;
    }

    /**
     * @param mensajeRecibido the mensajeRecibido to set
     */
    public void setMensajeRecibido(String mensajeRecibido) {
        this.mensajeRecibido = mensajeRecibido;
    }
    
    @Override
    public void run(){
        
    }

    /**
     * @return the numClientesConectados
     */
    public int getNumClientesConectados() {
        return numClientesConectados;
    }

    /**
     * @param numClientesConectados the numClientesConectados to set
     */
    public void setNumClientesConectados(int numClientesConectados) {
        this.numClientesConectados = numClientesConectados;
    }
    
//    public Thread getHilo() {
//        return hilo;
//    }

    /**
     * @param hilo the hilo to set
     */
//    public void setHilo(Thread hilo) {
//        this.hilo = hilo;
//    }
    
}
