package Esencial;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



/**
 * Clase que representa la lista de jugadores que participan en el juego.
 * @author Stalin
 */
public class ListaJugadores /*extends ArrayList*/ {
     
    private String jugadorAnterior;
    private String jugadorActual;
    private String jugadorSiguiente;
    private List<Usuario> jugadores;
    private int cantidad;
    private int jugadoresSumados;
	
    /**
     * Constructor que crea una lista de jugadores inicialmente vacia.
     */
    public ListaJugadores(){
        //super();
        jugadores=new ArrayList<Usuario>();
        jugadorAnterior = null;
        jugadorActual = null;
        jugadorSiguiente = null;
        cantidad = 0;

    }

    /**
     * @return the jugadorAnterior
     */
    public String getJugadorAnterior() {
        return jugadorAnterior;
    }

    /**
     * @param jugadorAnterior the jugadorAnterior to set
     */
    public void setJugadorAnterior(String jugadorAnterior) {
        this.jugadorAnterior = jugadorAnterior;
    }

    /**
     * @return the jugadorActual
     */
    public String getJugadorActual() {
        return jugadorActual;
    }

    /**
     * @param jugadorActual the jugadorActual to set
     */
    public void setJugadorActual(String jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    /**
     * @return the jugadorSiguiente
     */
    public String getJugadorSiguiente() {
        return jugadorSiguiente;
    }

    /**
     * @param jugadorSiguiente the jugadorSiguiente to set
     */
    public void setJugadorSiguiente(String jugadorSiguiente) {
        this.jugadorSiguiente = jugadorSiguiente;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return jugadores.size();
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the jugadores
     */
    public List<Usuario> getJugadores() {
        return jugadores;
    }

    /**
     * @param jugadores the jugadores to set
     */
    public void setJugadores(List<Usuario> jugadores) {
        this.jugadores = jugadores;
    }
    
    /**
     * Busca al jugador que corresponda la ip o nick recibido
     * @param ipONick
     * @return el jugador encontrado, null si no lo encuentra.
     */
    public Usuario getJugador(String ipONick){
        
        for(int i=0;i<jugadores.size();i++){
            if((jugadores.get(i).getNickname() == ipONick) ||(ipONick.contains(jugadores.get(i).getCliente().getMiIp()))){
                return jugadores.get(i);
            }
        }
        return null;
    }

    /**
     * @return the jugadoresSumados
     */
    public int getJugadoresSumados() {
        return jugadoresSumados;
    }

    /**
     * @param jugadoresSumados the jugadoresSumados to set
     */
    public void setJugadoresSumados(int jugadoresSumados) {
        this.jugadoresSumados = jugadoresSumados;
    }
    
   
        
}
