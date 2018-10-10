/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Esencial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Clase que permite reproducir sonidos de fondo.
 * @author Stalin
 */
public class Sonido implements Runnable{
    
    private Player sonidoMP3;
    private Thread hilo;
    private JFrame framePadre;
    private String nombre;
    
    /**
     * Constructor que crea un sonido específico por el numero recibido, el cual pertenece al  frame recibido.
     * @param framePadre
     * @param numero 
     */
    public Sonido(JFrame framePadre, int numero){
        this.nombre = "Sonidos/fondo"+numero+".mp3";
        this.framePadre = framePadre;
    }
    
    
    /**
     * Constructor que crea un sonido aleatorio, el cual pertenece al  frame recibido.
     * @param framePadre
     */
    public Sonido(JFrame framePadre){
        
        
        this.framePadre = framePadre;
        
        int numero;
        Random aleatorio = new Random();
        
        numero = aleatorio.nextInt(9);
        
        this.nombre = "Sonidos/fondo"+numero+".mp3";
    }
    

    /**
     * Reproduce el sonido.
     */
     public void play(){
         
        try {
            File archivoSonido = new File(nombre);
            FileInputStream FIS;
            FIS = new FileInputStream(archivoSonido);
            
             try {
                 sonidoMP3 = new Player(FIS);
                 
                 hilo = new Thread(this);
                 hilo.start();
                 
             } catch (JavaLayerException ex) {
                 JOptionPane.showMessageDialog(framePadre, ex.getMessage());
             }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(framePadre, ex.getMessage());
        }
     }
     
     /**
      * Detiene el sonido.
      */
     public void Stop(){
         hilo.stop();
     }
     
     
     /**
      * Hilo que se encarga de mantener la reproduccion del sonido en un segundo plano
      */
    @Override
    public void run() {
        try {

            sonidoMP3.play();
        } 
        catch (JavaLayerException ex) {

            JOptionPane.showMessageDialog(framePadre, ex.getMessage());
        }
   }
}
