package Esencial;

import Formularios.CartaEspecial;
import Formularios.CartaNormal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase que representa una lista de cartas bien sea de un jugador, de la pila de robo o de la pila de descarte.
 * @author Stalin
 * @param <E> 
 */

public class ListaCartas <E>{
    List<E> listaCartas;
       
    /**
     * Constructor de una lista de cartas inicialmente vacia.
     */
    public ListaCartas() {    
        this.listaCartas = new ArrayList<>();
    }
    
    /**
     * Agrega la carta recibida a la lista de cartas.
     * @param carta 
     */
    public void add(E carta){
        listaCartas.add(carta);
        
    }
    
    /**
     * Agrega la carta recibida en la posicion i de la lista de cartas
     * @param i
     * @param carta 
     */
    public void add(int i, E carta){
        listaCartas.add(i, carta);
    }
    
    /**
     * Elimina la carta recibida de la lista de cartas
     * @param carta 
     */
    public void remove(E carta){
        listaCartas.remove(carta);
    }
    
    /**
     * Elimina la carta que se encuentre en la posicion i de la lista de cartas
     * @param i 
     */
    public void remove(int i){
        listaCartas.remove(i);
    }
    
    /**
     * Devuelve la carta recibida que se encuentra en la lista de cartas, devuelve null en caso contrario
     * @param carta
     * @return 
     */
    public E get(E carta){
        int i = listaCartas.indexOf(carta);
        
        if(i>=0){
            return listaCartas.get(i);
        }
        
        return null;
    }
    
    /**
     * Devulve la carta que se encuentre en la posicion i de la lista de cartas.
     * @param i
     * @return 
     */
    public E get(int i){
        
        try {
            
            return listaCartas.get(i);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        } 
    }
    
    /**
     * Devuelve la cantidad de cartas que se encuentran en la lista de cartas.
     * @return 
     */
    public int size(){
        return listaCartas.size();
    }
    
    /**
     * Reemplaza la carta que se encuentra en la posicion i de la lista de cartas por la carta recibida.
     * @param i
     * @param carta 
     */
    public void set(int i, E carta){
        listaCartas.set(i, carta);
    }
    
    /**
     * Busca una una carta que tenga el numero o nombre recibido como parametro.
     * @param numeroONombre
     * @return resultado de la busqueda true si la encuentra, false en caso contrario.
     */
    public boolean tengoNumeroONombre(String numeroONombre){
        for(int i=0;i<listaCartas.size();i++){
            if(listaCartas.get(i) instanceof CartaNormal){
                CartaNormal cartaNormal = (CartaNormal) (listaCartas.get(i));
                if(Integer.toString(cartaNormal.getNumero()).equals(numeroONombre))                   
                    return true;
            }
            else{
                CartaEspecial cartaEspecial = (CartaEspecial) (listaCartas.get(i));
                if(cartaEspecial.getNombre().equals(numeroONombre))                  
                    return true;
            }
        }
        return false;
    
    }
    
    /**
     * Busca una carta que corresponda con el numero o nombre y el color recibido.
     * @param numeroONombre
     * @param color
     * @return la carta encontrada, null en caso contrario
     */
    public E get(String numeroONombre,String color){
    
        for(int i=0;i<listaCartas.size();i++){
            if(listaCartas.get(i) instanceof CartaNormal){
                CartaNormal cartaNormal = (CartaNormal) (listaCartas.get(i));
                //JOptionPane.showMessageDialog(null, "E get"+Integer.toString(cartaNormal.getNumero()) +" "+ cartaNormal.getColor());
                if((Integer.toString(cartaNormal.getNumero()).equalsIgnoreCase(numeroONombre)) && cartaNormal.getColor().equalsIgnoreCase(color))
                    return listaCartas.get(i);
            }
            else{
                CartaEspecial cartaEspecial = (CartaEspecial) (listaCartas.get(i));
                if((cartaEspecial.getNombre() == numeroONombre) && cartaEspecial.getColor()==color)
                    return listaCartas.get(i);
            }
        }
        return null;
    }
    
    /**
     * Busca al menos una carta que tenga el color recibido
     * @param color
     * @return resultado de la búsqueda, true si encuentra alguna, false en caso contrario.
     */
    public boolean tengoColor(String color){
        for(int i=0;i<listaCartas.size();i++){
            if(listaCartas.get(i) instanceof CartaNormal){
                CartaNormal cartaNormal = (CartaNormal) (listaCartas.get(i));
                if(cartaNormal.getColor().equals(color))
                    return true;
                
            }
            else{
                CartaEspecial cartaEspecial = (CartaEspecial) (listaCartas.get(i));
                if(cartaEspecial.getColor().equals(color))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Se en carga de sumar todos los valores de las cartas que estan en la lista de cartas.
     * @return suma
     */
    public int sumaValores(){
        int sum = 0;
        for(int i=0;i<listaCartas.size();i++){
            if(listaCartas.get(i) instanceof CartaNormal){
                CartaNormal cartaNormal = (CartaNormal) (listaCartas.get(i));
                sum = sum + cartaNormal.getValor();
                    
            }
            else{
                CartaEspecial cartaEspecial = (CartaEspecial) (listaCartas.get(i));
                sum = sum + cartaEspecial.getValor();
            }
        }
        return sum;
    }
    
    /**
     * Intercambia la carta que se encuentra en la posicion a con la carta que se encuentra en la posicion b de la lista de cartas.
     * @param a
     * @param b 
     */
    public void intercambia(int a,int b){
        
        E carta1,carta2;
        
        carta1 = listaCartas.get(a);
        carta2 = listaCartas.get(b);
        
        listaCartas.set(a, carta2);
        listaCartas.set(b, carta1);

    }
    
    
    
}
