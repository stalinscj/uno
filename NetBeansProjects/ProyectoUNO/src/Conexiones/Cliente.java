package Conexiones;


import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 * Clase que representa un cliente el cual intercambiará datos con un servidor.
 * @author Stalin
 */

public class Cliente {
    
    private Socket socket;
    private String ip;
    private int puerto;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private String mensajeRecibido;
    private String miIp;
    
    /**
     * Constructor de cliente recibe el ip al cual desea conectarse y el puerto por el cual quiere hacerlo.
     * @param ip
     * @param puerto
     * @throws Exception 
     */
    public Cliente (String ip, int puerto) throws Exception{
        
        try {
            this.ip = ip;
            this.puerto = puerto;
            this.socket = new Socket(ip,puerto);
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
            
            InetAddress miIp = InetAddress.getLocalHost();
            this.miIp =  miIp.getHostAddress();
            
            mensajeRecibido = "";
   
        } catch (Exception ex) {
            throw ex;
            //JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    /**
     * Constructor de cliente el cual crea un cliente asignandole la ip recibida y sin conexion
     * @param miIp 
     */
    public Cliente(String miIp){
        this.miIp=miIp;
    }
    
    /**
     * Envia el dato recibido al servidor
     * @param dato 
     */
    public void enviar(String dato){
        
        try{
            Cliente cliente = new Cliente(ip,9090);
            
            salida = new DataOutputStream(socket.getOutputStream());
            salida.flush();
            salida.writeUTF(dato);
            cliente.terminar();
            Thread.sleep(100);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    
    
    /**
     * Devuelve el mensaje que le ha enviado el servidor
     * @return mensajeRecibido
     * @throws IOException 
     */
    public String recibir () throws IOException{
        String mensajeRecibido="";
        try {
            entrada = new DataInputStream(socket.getInputStream());
            
            mensajeRecibido =  entrada.readUTF();
            return mensajeRecibido;
        } catch (IOException ex) {
            
            throw ex;
            
        }
    }
    
    
    /**
     * Cierra los flujos de entrada y salida del socket y tambien cierra el socket.
     */
    public void terminar(){
        try {
            entrada.close();
            salida.close();
            socket.close();
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
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
    public DataOutput getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    /**
     * @return the mensajeRecibido
     */
    public String getMensajeRecibido() {
        return mensajeRecibido;
    }

    /**
     * @param mensajeRecibido the mensajeRecibido to set
     */
    public void setMensajeRecibido(String mensajeRecibido) {
        this.mensajeRecibido = mensajeRecibido;
    }

    /**
     * @return the miIp
     */
    public String getMiIp() {
        return miIp;
    }

    /**
     * @param miIp the miIp to set
     */
    public void setMiIp(String miIp) {
        
        this.miIp = miIp;
        
    }
    
    /**
     * Devuelve la ip que tiene la pc.
     * @return ip
     */
    public static String saberMiIp(){
        
        String ip="";
        
        try {

            InetAddress miIp = InetAddress.getLocalHost();
            ip =  miIp.getHostAddress();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return ip;
    }
	
}
