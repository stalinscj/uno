package Esencial;

import Conexiones.Cliente;
import Conexiones.ConexionMYSQL;
import Conexiones.Servidor;
import Formularios.CartaEspecial;
import Formularios.CartaNormal;
import static Formularios.Consulta.usuarioActual;
import Formularios.Fondo;
import Formularios.PilaDescarte;
import Formularios.PilaRobo;
import Formularios.Sala;
import Formularios.Tablero;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Clase que representa la máxima autoridad  dentro del juego, la cual se encarga de aplicar las reglas y castigos.  
 * @author Stalin
 */
public class Juego implements Runnable{
    
    private int cantidadJugadores;
    private String tipo;
    private int puntos;
    private int puntosAOtorgar;
    private String ipGanador;
    private ListaJugadores listaJugadores;
    private String desafiante;
    private Servidor servidor;
    private PilaRobo pilaRobo;
    private PilaDescarte pilaDescarte;
    private String sentido;
    private Thread hilo;
    private Socket contactoOrganizador;
    
    
    /**
     * Constructuor de juego con los parametros dados
     * @param servidor
     * @param cantidadJugadores
     * @param modalidad
     * @param puntos 
     */
    public Juego(Servidor servidor,int cantidadJugadores, String modalidad, int puntos){
        listaJugadores = new ListaJugadores();
        this.servidor = servidor;
        pilaDescarte = null;
        pilaRobo = null;
        hilo = new Thread(this);
        contactoOrganizador = null;
        puntosAOtorgar=0;
        this.cantidadJugadores = cantidadJugadores;
        this.tipo = modalidad;
        this.puntos = puntos;
        sentido = "";
        desafiante = "";
        ipGanador = "";
        hilo.start();
        
    }
    
    /**
     * Le da el numero de cartas a repartir cartas a al jugador que se comunique a través del socket recibido
     * @param numeroDeCartasARepartir
     * @param socketDestino 
     */
    public void darCarta(int numeroDeCartasARepartir,Socket socketDestino){
        String carta;
        int n = pilaRobo.getListaCartas().size();
        
        for(int k=0;k<numeroDeCartasARepartir;k++){
            if(pilaRobo.getListaCartas().size()==0){
                pilaDescarteAPilaRobo();
                n = pilaRobo.getListaCartas().size();
            }
            
            if(pilaRobo.getListaCartas().size()==0 && pilaDescarte.getListaCartas().size()==0){
                JOptionPane.showMessageDialog(pilaRobo, "Se acabaron las cartas");
                System.exit(0);
                break;    
            }else
                try {
                    if (pilaRobo.getListaCartas().get(n-1) instanceof CartaNormal){

                        CartaNormal cartaNormal = (CartaNormal) (pilaRobo.getListaCartas().get(n-1));
                        carta = cartaNormal.getValor()+","+cartaNormal.getColor()+","+cartaNormal.getValor(); 

                        servidor.enviar(socketDestino,"add,carta,CartaNormal,"+ carta);


                    }else{

                        CartaEspecial cartaEspecial = (CartaEspecial) (pilaRobo.getListaCartas().get(n-1));
                        carta = cartaEspecial.getNombre()+","+cartaEspecial.getColor()+","+cartaEspecial.getValor();

                        servidor.enviar(socketDestino,"add,carta,CartaEspecial,"+ carta);                        

                    }

                    Thread.sleep(10);
                    pilaRobo.quitarUltima();
                    n--; 

                    for(int j=0;j<servidor.getCantidadClientes();j++){
                        servidor.enviar(servidor.getCliente(j), "set,jugador,"+socketDestino.getInetAddress().toString()+",cantidadcartas,1");
                    }   
                    listaJugadores.getJugador(socketDestino.getInetAddress().toString()).setCantidadCartas(1);
                    servidor.enviar(socketDestino, "set,proteccion,off");
                    listaJugadores.getJugador(socketDestino.getInetAddress().toString()).setProteccion("off");

                }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                }
        }
    }
    
    /**
     * Verifica si una carta jugada es incorrecta y dependiendo del resultado de dicha verificacion ejecuta las reglas pertinentes.
     * @param ipDelAcusante 
     */
    public void verificarCartaEquivocada(String ipDelAcusante){
        Socket acusante=null;
        Socket acusado=null;
        
        for(int i=0;i<listaJugadores.getCantidad();i++){
            if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorAnterior())){
                acusado = servidor.getCliente(i);
                break;
            }
        }
        
        for(int i=0;i<listaJugadores.getCantidad();i++){
            if(servidor.getCliente(i).getInetAddress().toString().contains(ipDelAcusante)){
                acusante = servidor.getCliente(i);
                break;
            }
        }
        
        boolean resultado = true;
        
        if(pilaDescarte.getListaCartas().size()<2)
            resultado = false;
        else
            if((pilaDescarte.getColorCartaActual().equalsIgnoreCase(pilaDescarte.getColorCartaAnterior())) || 
               (pilaDescarte.getNumeroONombreCartaActual().equalsIgnoreCase(pilaDescarte.getNumeroONombreCartaAnterior())) || 
               (pilaDescarte.getNumeroONombreCartaActual().equalsIgnoreCase("CC")) || 
               (pilaDescarte.getNumeroONombreCartaActual().equalsIgnoreCase("+4")))
                resultado = false;
        
        
        
        
        if(resultado && listaJugadores.getJugadorActual().contains(ipDelAcusante)){
            
            //devolver carta equivocada
            
            int n=pilaDescarte.getListaCartas().size();
            String carta;
            try {
                if (pilaDescarte.getListaCartas().get(n-1) instanceof CartaNormal){
                        
                    CartaNormal cartaNormal = (CartaNormal) (pilaDescarte.getListaCartas().get(n-1));
                    carta = cartaNormal.getValor()+","+cartaNormal.getColor()+","+cartaNormal.getValor();
                        
                    servidor.enviar(acusado,"add,carta,CartaNormal,"+ carta);
                }
                else{
                        
                    CartaEspecial cartaEspecial = (CartaEspecial) (pilaDescarte.getListaCartas().get(n-1));
                    carta = cartaEspecial.getNombre()+","+cartaEspecial.getColor()+","+cartaEspecial.getValor();
                        
                    servidor.enviar(acusado,"add,carta,CartaEspecial,"+ carta);
                        
                } 
                //Thread.sleep(10);
                pilaDescarte.removeCartaActual();
                
                
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
            for(int j=0;j<servidor.getCantidadClientes();j++){
                servidor.enviar(servidor.getCliente(j), "set,jugador,"+acusado.getInetAddress().toString()+",cantidadcartas,1");
            }
            
            listaJugadores.getJugador(acusado.getInetAddress().toString()).setCantidadCartas(1);
            
            
            //dar 2 cartas de castigo
            
            darCarta(2, acusado);
            
            for(int k=0;k<servidor.getCantidadClientes();k++){
                servidor.enviar(servidor.getCliente(k), "set,cartaactual,"+pilaDescarte.getCartaActual());
            }
            
            servidor.enviar(acusante, "activacartas");
        }
        
        if(resultado && !listaJugadores.getJugadorActual().contains(ipDelAcusante)){
            
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorActual())){
                    servidor.enviar(servidor.getCliente(i), "show,imagen,"+"La carta que\nse jugo es\nincorrecta\nACUSALOOO!!!,"+listaJugadores.getJugador(ipDelAcusante).getNickname()+": CartaAnterior,"+pilaDescarte.getNumeroONombreCartaAnterior()+pilaDescarte.getColorCartaAnterior());
                    break;
                }       
            }
        }
        
        if(!resultado){
            
            servidor.enviar(acusante, "show,mensaje,La carta jugada es correcta");
            verificarGanador(ipGanador);
        }
        
        
    }
    
    
    /**
     * Verifica un desafio y dependiendo del resultado de dicha verificacion ejecuta las reglas pertinentes.
     * @param ipDesafiado
     * @param resultado 
     */
    public void verificarDesafio(String ipDesafiado,String resultado) {
        
        int n = pilaRobo.getListaCartas().size();
        int numeroDecartasARepartir=0;
        String carta;
        Socket perdedorDesafio =null;
        
        if(resultado.equals("true")){
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(ipDesafiado)){
                    perdedorDesafio = servidor.getCliente(i);
                    numeroDecartasARepartir =4;
                    break;
                }
            }
        }else{
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(desafiante)){
                    perdedorDesafio = servidor.getCliente(i);
                    numeroDecartasARepartir = 6;
                    servidor.enviar(perdedorDesafio, "activacartas");
                    break;
                }
            }
            
        }

        darCarta(numeroDecartasARepartir,perdedorDesafio);
        
    }
    
    /**
     * Proetege al jugador que corresponda la ip recibida.
     * @param ipAProteger 
     */
    public void proteger(String ipAProteger){
    
        if(listaJugadores.getJugador(ipAProteger).getCantidadCartas()<3){
            
            listaJugadores.getJugador(ipAProteger).setProteccion("on");
                    
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(ipAProteger)){
                    servidor.enviar(servidor.getCliente(i), "set,proteccion,on");
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,Tranquilo ya estas protegido");
                    break;
                }
            }
            
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(!servidor.getCliente(i).getInetAddress().toString().contains(ipAProteger)){
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,"+listaJugadores.getJugador(ipAProteger).getNickname()+" ha dicho UNO!!!");
                    break;
                }
            }
                   
                    
                    
        }else{    
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(ipAProteger)){
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,No puedes decir UNO porque todavia tienes mas de 2 cartas");
                    break;
                }
            }
        
        }
        
        
    }
    
    
    /**
     * Procesa la accion de sorprender a un jugador que no ha dicho uno y dependiendo del resultado de dicho proceso ejecuta las reglas pertinentes.
     * @param ipAcusante
     * @param ipASorprender 
     */
    public void sorprender(String ipAcusante, String ipASorprender){
        
        if(listaJugadores.getJugador(ipASorprender).getCantidadCartas()==1){
                    
                    if(listaJugadores.getJugador(ipASorprender).getProteccion() == "off"){
                        
                        Socket socketASorprender = null;
                        
                        for(int i=0;i<servidor.getCantidadClientes();i++){
                            if(servidor.getCliente(i).getInetAddress().toString().contains(ipASorprender)){
                                socketASorprender = servidor.getCliente(i);
                                break;
                            }
                        }
                    
                        darCarta(2, socketASorprender);
                        
                    }else{
                        for(int i=0;i<servidor.getCantidadClientes();i++){
                            if(servidor.getCliente(i).getInetAddress().toString().contains(ipAcusante)){
                                servidor.enviar(servidor.getCliente(i), "show,mensaje,No puedes sorprender a "+listaJugadores.getJugador(ipASorprender).getNickname()+" porque el ya dijo UNO");
                                break;
                            }
                        }
                    }
                    
                }else{
                    for(int i=0;i<servidor.getCantidadClientes();i++){
                        if(servidor.getCliente(i).getInetAddress().toString().contains(ipAcusante)){
                            servidor.enviar(servidor.getCliente(i), "show,mensaje,No puedes sorprender a "+listaJugadores.getJugador(ipASorprender).getNickname()+" porque todavia tiene mas de una carta");
                            break;
                        }
                    }
                    
                }
    }

    
    /**
     * Salta al jugador actual debido a que este no posee una carta válida para jugar.
     * @param ipAPasar 
     */
    public void pasar(String ipAPasar){
        if(listaJugadores.getJugadorActual().contains(ipAPasar)){
            actualizarSecuencia();
        }
        else{
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(ipAPasar)){
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,No es tu turno para jugar");
                    break;
                }
            }
        }
    }
    
    /**
     * Ejecuta la accion de la carta especial en la pila de descarta tomando como víctima al jugador actual que está de acuerdo con dicha carta.
     * @param ipQuepulsoOK 
     */
    public void ejecutarAccionDeCarta(String ipQuepulsoOK){
        
        Socket socketDestino=null;
        
        if(listaJugadores.getJugadorActual().contains(ipQuepulsoOK)){
            if(pilaDescarte.getTipoCartaActual().equalsIgnoreCase("CartaEspecial")){
                String nombreCarta=pilaDescarte.getNumeroONombreCartaActual();

                switch (nombreCarta){
                    case "+2":{
                        for(int i=0;i<servidor.getCantidadClientes();i++){
                            if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorActual())){
                                socketDestino = servidor.getCliente(i);
                                break;
                            }
                        }
                        darCarta(2, socketDestino);
                        verificarGanador(listaJugadores.getJugadorAnterior());
                        actualizarSecuencia();

                    }break;

                    case "+4":{
                        for(int i=0;i<servidor.getCantidadClientes();i++){
                            if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorActual())){
                                socketDestino = servidor.getCliente(i);
                                break;
                            }
                        }
                        darCarta(4, socketDestino);
                        verificarGanador(listaJugadores.getJugadorAnterior());
                        actualizarSecuencia();

                    }break;

                    case "R":{
                        if(sentido.equalsIgnoreCase("Horario"))
                            sentido = "AntiHorario";
                        else
                            sentido = "Horario";

                        for(int i=0;i<servidor.getCantidadClientes();i++){
                            servidor.enviar(servidor.getCliente(i),"set,sentido,"+sentido);
                        }
                        
                        verificarGanador(listaJugadores.getJugadorAnterior());
                        actualizarSecuencia();
                        
                        if(servidor.getCantidadClientes() > 2)
                            actualizarSecuencia();

                    }break;

                    case "S":{
                        verificarGanador(listaJugadores.getJugadorAnterior());
                        actualizarSecuencia();
                    }break;

                    case "CC":{
                        
                        //El color es cambiado en el momento que el jugador elige la carta y selecciona el color
                    }break;

                    default:{
                    }break;
                }
                
            }
        }else{
            for(int i=0;i<servidor.getCantidadClientes();i++){
                if(servidor.getCliente(i).getInetAddress().toString().contains(ipQuepulsoOK)){
                    socketDestino = servidor.getCliente(i);
                    break;
                }
            }
            
            servidor.enviar(socketDestino, "show,mensaje,No es tu turno para jugar");
        }
    }
    
    /**
     * Verifica ya hay un ganador para detener el juego y realizar las acciones pertinentes.
     * @param ipGanador 
     */
    public void verificarGanador(String ipGanador) {
        Usuario ganador = listaJugadores.getJugador(ipGanador);
        
        if(ganador!=null)
            if(ganador.getCantidadCartas()==0){
                this.ipGanador = ipGanador;
                listaJugadores.setJugadoresSumados(0);
                       
                for(int i=0;i<servidor.getCantidadClientes();i++){
                    servidor.enviar(servidor.getCliente(i), "get,sumavalores");
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,El ganador es: "+ganador.getNickname());
                } 
            }  
    }
	
    /**
     * Determina cual jugador será el primero en jugar.
     */
    public void elegirPrimerJugador() {
        int cantidadJugadores = servidor.getCantidadClientes();
        
        
        int valoresDeCartas[] = new int[cantidadJugadores];
        
        int a=0;
        
        
        
        
        
        Random aleatorio = new Random();
        int contador=0;
        List<Integer> valoresGenerados = new ArrayList<Integer>();
        String cartasGeneradas[] = {"","","",""};
        boolean cartaRepetida=true;
        
        for(int i = 0; i<cantidadJugadores;i++){
            
            while(cartaRepetida){
                a = aleatorio.nextInt(pilaRobo.getListaCartas().size()-1);
                if(!valoresGenerados.contains(a)){
                    valoresGenerados.add(a);
                    cartaRepetida = false;
                }
            }
        
            cartaRepetida = true;
            
            if(pilaRobo.getListaCartas().get(a) instanceof CartaNormal){
                CartaNormal carta;
                carta = (CartaNormal) pilaRobo.getListaCartas().get(a);
                
                
                valoresDeCartas[i] = carta.getNumero();
                cartasGeneradas[i] = Integer.toString(carta.getNumero()).concat(carta.getColor());
                servidor.enviar(servidor.getCliente(i), "show,imagen,Esta es tu carta\npara ver quien\nempieza,CartaAleatoria,"+carta.getNumero()+carta.getColor());
            }
            else{
                CartaEspecial carta;
                carta = (CartaEspecial) pilaRobo.getListaCartas().get(a);
                servidor.enviar(servidor.getCliente(i), "show,imagen,Esta es tu carta\npara ver quien\nempieza,CartaAleatoria,"+carta.getNombre()+carta.getColor());
                valoresDeCartas[i] = 0;
                cartasGeneradas[i] = carta.getNombre().concat(carta.getColor());
            }
            
            
        }
        
        int posicionMayor = 0;
        int mayor = valoresDeCartas[posicionMayor];
        String strCarta=cartasGeneradas[posicionMayor];
        
        
        for(int i = 1;i<cantidadJugadores;i++){
            if(valoresDeCartas[i]>mayor){
                mayor = valoresDeCartas[i];
                posicionMayor = i;
                strCarta = cartasGeneradas[i];
            }
        }
        
        
        
        listaJugadores.setJugadorActual(servidor.getCliente(posicionMayor).getInetAddress().toString());
        
        
        int sig = (posicionMayor+1)%cantidadJugadores;
        
        listaJugadores.setJugadorSiguiente(servidor.getCliente(sig).getInetAddress().toString());
        
        int anterior;
        
        if (posicionMayor==0)
            anterior=cantidadJugadores-1;
        else
            anterior=posicionMayor-1;
        
        listaJugadores.setJugadorAnterior(servidor.getCliente(anterior).getInetAddress().toString());
        
        String nickname="";
        
        
        
        for(int i=0;i<listaJugadores.getJugadores().size();i++){
            
            if(listaJugadores.getJugadorActual().contains(listaJugadores.getJugadores().get(i).getCliente().getMiIp()))
                nickname = listaJugadores.getJugadores().get(i).getNickname();
        }
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            servidor.enviar(servidor.getCliente(i), "set,jugadoractual,"+listaJugadores.getJugadorActual());
            servidor.enviar(servidor.getCliente(i), "show,imagen,Comienza "+nickname+"\nporque fue el \nprimero en sacar \nesta carta,"+"CartaDe: "+nickname+","+strCarta);                                         
            
        }
    }
    
    /**
     * Elige una carta al azar para colocarla como primera carta de la pila de descarte para que inicie el juego.
     */
    public void elegirPrimeraCarta(){
        String strCarta;
        Random aleatorio = new Random();
        boolean cartaValida = false;
        int n = pilaRobo.getListaCartas().size();

        while(!cartaValida){
            n = aleatorio.nextInt(pilaRobo.getListaCartas().size()-1);
            try {
                if (pilaRobo.getListaCartas().get(n) instanceof CartaNormal){

                    CartaNormal cartaNormal = (CartaNormal) (pilaRobo.getListaCartas().get(n));
                    pilaDescarte.add("CartaNormal",Integer.toString(cartaNormal.getValor()),cartaNormal.getColor(),cartaNormal.getValor());
                    cartaValida = true;
                }else{

                    CartaEspecial cartaEspecial = (CartaEspecial) (pilaRobo.getListaCartas().get(n));
                    if(!cartaEspecial.getNombre().equalsIgnoreCase("+4")){
                        pilaDescarte.add("CartaEspecial",cartaEspecial.getNombre(),cartaEspecial.getColor(),cartaEspecial.getValor());
                        cartaValida = true;
                        
                        
                        
                        if(cartaEspecial.getNombre().equalsIgnoreCase("CC")){
                            for(int i=0;i<servidor.getCantidadClientes();i++){
                                if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorActual())){
                                    servidor.enviar(servidor.getCliente(i), "elijecolor");
                                    break;
                                }
                            }
                        }
                    }

                }
            }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
        pilaRobo.getListaCartas().remove(n);
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            servidor.enviar(servidor.getCliente(i), "set,cartaactual,"+pilaDescarte.getCartaActual());
        }
        
        ejecutarAccionDeCarta(listaJugadores.getJugadorActual());
    }
	
	
    /**
     * Actualiza las estadisticas de los jugadores involucrados en el juego, como sus partidas jugadas, ganadas o perdidas.
     */
    public void actualizarEstadisticas(){
        
        ConexionMYSQL mysql = null;
        Connection conexion = null;
        String sentenciaSQL = "";
        
        String strPartidasJugadas = "";
        String strPartidasGanadas = "";
        String strPartidasPerdidas = "";
        
        int intPartidasJugadas = 0;
        int intPartidasGanadas = 0;
        int intPartidasPerdidas = 0;
        
        
        Usuario usuarioActualizar=null;
        
        for(int i=0; i<listaJugadores.getJugadoresSumados();i++){
            
            usuarioActualizar = listaJugadores.getJugadores().get(i);
            
            mysql = new ConexionMYSQL(usuarioActualizar.getHostname(),"bduno","UsuarioUNO");
            conexion = mysql.conectar();
            
            if(ipGanador.contains(usuarioActualizar.getCliente().getMiIp())){
                if(conexion!=null)
                    try {
                        sentenciaSQL = "SELECT partidasJugadas, partidasGanadas "
                                + "FROM usuarios WHERE nickname='"+usuarioActualizar.getNickname()+"'";
                        
                        Statement ST = conexion.createStatement();
                        ResultSet RS = ST.executeQuery(sentenciaSQL);

                        if(RS.next()){

                            strPartidasJugadas  = RS.getString("partidasJugadas");
                            strPartidasGanadas  = RS.getString("partidasGanadas");
                            
                            intPartidasJugadas  = Integer.parseInt(strPartidasJugadas);
                            intPartidasGanadas  = Integer.parseInt(strPartidasGanadas);
                            
                            intPartidasJugadas++;
                            intPartidasGanadas++;
                            
                            try{
                                sentenciaSQL = " UPDATE usuarios SET partidasJugadas = "+intPartidasJugadas+","
                                        + " partidasGanadas = "+intPartidasGanadas+" "
                                        + " WHERE nickname = '"+usuarioActualizar.getNickname()+"'";
                                
                                PreparedStatement PST = conexion.prepareStatement(sentenciaSQL);
            
                                PST.executeUpdate();
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        
                    }
            }else{
                if(conexion!=null)
                    try {
                        sentenciaSQL = "SELECT partidasJugadas, partidasPerdidas "
                                + "FROM usuarios WHERE nickname='"+usuarioActualizar.getNickname()+"'";
                        
                        Statement ST = conexion.createStatement();
                        ResultSet RS = ST.executeQuery(sentenciaSQL);

                        if(RS.next()){

                            strPartidasJugadas  = RS.getString("partidasJugadas");
                            strPartidasPerdidas = RS.getString("partidasPerdidas");
                            
                            intPartidasJugadas  = Integer.parseInt(strPartidasJugadas);
                            intPartidasPerdidas = Integer.parseInt(strPartidasPerdidas);
                            
                            intPartidasJugadas++;
                            intPartidasPerdidas++;
                            
                            try{
                                sentenciaSQL = " UPDATE usuarios SET partidasJugadas = "+intPartidasJugadas+","
                                        + " partidasPerdidas = "+intPartidasPerdidas+" "
                                        + " WHERE nickname = '"+usuarioActualizar.getNickname()+"'";
                                
                                PreparedStatement PST = conexion.prepareStatement(sentenciaSQL);
            
                                PST.executeUpdate();
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        
                    }
                
                
            }
            
        }
	
    }
	
    /**
     * Actualiza la secuencia del juego, asignando un nuevo jugador anterior, un nuevo jugador actual y un nuevo jugador siguiente.
     */
    public void actualizarSecuencia(){
        int cantidadJugadores = listaJugadores.getJugadores().size();
        int posicionJugadorActual=-1;
        int posicionJugadorSiguiente=-1;
        int posicionJugadorAnterior=-1;
        
        for(int i=0;i<cantidadJugadores;i++){
            if(listaJugadores.getJugadorActual().contains(servidor.getCliente(i).getInetAddress().toString())){
                //viejo jugador actual
                posicionJugadorActual =i;
                break;
            }
        }
        
        
        if(sentido=="Horario"){
            //viejo jugador siguiente
            posicionJugadorSiguiente = (posicionJugadorActual+1)%cantidadJugadores;
            
            //viejo jugador anterior
            if (posicionJugadorActual==0)
                posicionJugadorAnterior=cantidadJugadores-1;
            else
                posicionJugadorAnterior=posicionJugadorActual-1;
            
            //nuevo jugador anterior
            posicionJugadorAnterior = posicionJugadorActual;
            
            //nuevo jugador actual
            posicionJugadorActual = posicionJugadorSiguiente;
            
            //nuevo jugador siguiente
            posicionJugadorSiguiente = (posicionJugadorActual+1)%cantidadJugadores;
        
        
        }else{
            //viejo jugador siguiente
            if (posicionJugadorActual==0)
                posicionJugadorSiguiente=cantidadJugadores-1;
            else
                posicionJugadorSiguiente=posicionJugadorActual-1;
            
            // viejo jugador anterior
            posicionJugadorAnterior = (posicionJugadorActual+1)%cantidadJugadores;
            
            //nuevo jugador anterior
            posicionJugadorAnterior = posicionJugadorActual;
            
            //nuevo jugador actual
            posicionJugadorActual = posicionJugadorSiguiente;
            
            //nuevo jugador siguiente
            if (posicionJugadorActual==0)
                posicionJugadorSiguiente=cantidadJugadores-1;
            else
                posicionJugadorSiguiente=posicionJugadorActual-1;
        }
        
        listaJugadores.setJugadorActual(servidor.getCliente(posicionJugadorActual).getInetAddress().toString());
        listaJugadores.setJugadorAnterior(servidor.getCliente(posicionJugadorAnterior).getInetAddress().toString());
        listaJugadores.setJugadorSiguiente(servidor.getCliente(posicionJugadorSiguiente).getInetAddress().toString());
	
        for(int i=0;i<servidor.getCantidadClientes();i++){
            servidor.enviar(servidor.getCliente(i), "set,jugadoractual,"+servidor.getCliente(posicionJugadorActual).getInetAddress().toString());
        }
    }
    
    /**
     * Reparte 7 cartas a cada jugador.
     */
    public void repartir(){
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            darCarta(7, servidor.getCliente(i));
        }
    }
    
    /**
     * Coloca las cartas de la pila de descarte en el pila de robo
     */
    public void pilaDescarteAPilaRobo(){
        
        CartaNormal cartaNormal = new CartaNormal(-1,"");
        CartaEspecial cartaEspecial = new CartaEspecial("","",-1);
        
        int numDeCartasEnPilaDescarte = pilaDescarte.getListaCartas().size();
        for(int i=0;i<numDeCartasEnPilaDescarte;i++){
            if(pilaDescarte.getListaCartas().get(0) instanceof CartaNormal){
                cartaNormal = (CartaNormal) pilaDescarte.getListaCartas().get(0);
                pilaRobo.getListaCartas().add(cartaNormal);
            }
            else{
                cartaEspecial = (CartaEspecial) pilaDescarte.getListaCartas().get(0);
                if(cartaEspecial.getNombre().equalsIgnoreCase("CC") || cartaEspecial.getNombre().equalsIgnoreCase("+4"))
                    cartaEspecial.setColor("Negro");
                pilaRobo.getListaCartas().add(cartaEspecial);  
            }
            pilaDescarte.getListaCartas().remove(0);
            
        } 
        pilaRobo.Barajar();
    }
    
    
    
    /**
     * Coloca la carta con los atributos recibidos en la pila de robo
     * @param tipoCarta
     * @param numeroONombre
     * @param color
     * @param valor 
     */
    public void ponerCartaEnPilaRobo(String tipoCarta, String numeroONombre, String color,int valor){
        if(tipoCarta.equalsIgnoreCase("CartaNormal")){
            pilaRobo.getListaCartas().add(new CartaNormal(valor, color));
        }
        else{
             pilaRobo.getListaCartas().add(new CartaEspecial(numeroONombre, color, valor));
        }
    }
    
   
    /**
     * Cambia el color de la carta Cambio de color que ha salido como primera carta del juego.
     * @param color 
     */
    public void cambioColor(String color){
        pilaDescarte.getListaCartas().remove(0);
        pilaDescarte.add("CartaEspecial","CC",color,50);
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            servidor.enviar(servidor.getCliente(i), "set,cartaactual,"+pilaDescarte.getCartaActual());
        }
    }
    
    /**
     * Suma los valores de las cartas de los jugadores perdedores al jugador ganador
     * @param puntos 
     */
    public void sumarValores(String puntos) {
        puntosAOtorgar = Integer.parseInt(puntos);
        
        listaJugadores.setJugadoresSumados(listaJugadores.getJugadoresSumados()+1);
        
        listaJugadores.getJugador(ipGanador).setPuntos(listaJugadores.getJugador(ipGanador).getPuntos()+puntosAOtorgar);
        
        if(listaJugadores.getJugadoresSumados() >= listaJugadores.getJugadores().size())
            if(listaJugadores.getJugador(ipGanador).getPuntos() >= this.puntos){
                actualizarEstadisticas();
                finPartida();
            }
            else{
                
                for(int i=0;i<servidor.getCantidadClientes();i++)
                    servidor.enviar(servidor.getCliente(i), "nuevaPartida");
                
                for(int i=0;i<servidor.getCantidadClientes();i++)
                    servidor.enviar(servidor.getCliente(i), "set,puntos,"+listaJugadores.getJugador(ipGanador).getPuntos()
                                                          + "," +listaJugadores.getJugador(ipGanador).getNickname());
                
                for(int i=0;i<servidor.getCantidadClientes();i++)
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,A "+listaJugadores.getJugador(ipGanador).getNickname()
                                                          + " sólo le faltan "+(this.puntos - listaJugadores.getJugador(ipGanador).getPuntos())
                                                          + " para ganar la partida");
                
                
                for (int i=0;i<listaJugadores.getJugadores().size();i++){
                    listaJugadores.getJugadores().get(i).setProteccion("off");
                    listaJugadores.getJugadores().get(i).setCantidadCartas(0);       
                }
                
                iniciarJuego();
                    
            }
    }
    
    /**
     * Termina la partida actual despidiendo a todos los jugadores.
     */
    public void finPartida(){
        
        for(int i=0;i<servidor.getCantidadClientes();i++)
            servidor.enviar(servidor.getCliente(i), "finPartida");
        
        if(contactoOrganizador!=null){
            try {
                servidor.enviar(contactoOrganizador, "finPartida");
                contactoOrganizador.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
        
        terminar();
    }
    
    /**
     * Saca del juego al un jugador que ha decido abandonar la partida.
     * @param ipASacar 
     */
    public void sacarJugador(String ipASacar){
    
                
        ConexionMYSQL mysql = null;
        Connection conexion = null;
        String sentenciaSQL = "";
        
        String strPartidasJugadas = "";
        String strPartidasAbandonadas = "";
        
        int intPartidasJugadas = 0;
        int intPartidasAbandonadas = 0;
        
        
        Usuario usuarioQueAbandona = listaJugadores.getJugador(ipASacar);
        String nicknameASacar = usuarioQueAbandona.getNickname();
            
        mysql = new ConexionMYSQL(usuarioQueAbandona.getHostname(),"bduno","UsuarioUNO");
        conexion = mysql.conectar();
            
        
        if(conexion!=null)
            try {
                sentenciaSQL = "SELECT partidasJugadas, partidasAbandonadas "
                             + "FROM usuarios WHERE nickname='"+usuarioQueAbandona.getNickname()+"'";
                        
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);

                if(RS.next()){

                    strPartidasJugadas  = RS.getString("partidasJugadas");
                    strPartidasAbandonadas = RS.getString("partidasAbandonadas");
                            
                    intPartidasJugadas  = Integer.parseInt(strPartidasJugadas);
                    intPartidasAbandonadas = Integer.parseInt(strPartidasAbandonadas);
                            
                    intPartidasJugadas++;
                    intPartidasAbandonadas++;
                            
                    try{
                        sentenciaSQL = " UPDATE usuarios SET partidasJugadas = "+intPartidasJugadas+","
                                     + " partidasAbandonadas = "+intPartidasAbandonadas+" "
                                     + " WHERE nickname = '"+usuarioQueAbandona.getNickname()+"'";
                                
                        PreparedStatement PST = conexion.prepareStatement(sentenciaSQL);
            
                        PST.executeUpdate();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        
        
        cantidadJugadores--;
        
        if(cantidadJugadores==1){
            
            
            for(int i=0; i<2;i++){
                if(!servidor.getCliente(i).getInetAddress().toString().contains(ipASacar)){
     
                    servidor.enviar(servidor.getCliente(i), "show,mensaje,"+usuarioQueAbandona.getNickname()+" ha abandonado la partida\n"
                                                          + " asi que has ganado.");
                    ipGanador = servidor.getCliente(i).getInetAddress().toString();
                }
            }
            
            Usuario usuarioGanador = listaJugadores.getJugador(ipGanador);
            listaJugadores.setJugadoresSumados(1);
            
            String strPartidasGanadas = "";
            int intPartidasGandas = 0;
            
            try {
                sentenciaSQL = "SELECT partidasJugadas, partidasGanadas "
                             + "FROM usuarios WHERE nickname='"+usuarioGanador.getNickname()+"'";
                        
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);

                if(RS.next()){

                    strPartidasJugadas  = RS.getString("partidasJugadas");
                    strPartidasGanadas = RS.getString("partidasGanadas");
                            
                    intPartidasJugadas  = Integer.parseInt(strPartidasJugadas);
                    intPartidasGandas = Integer.parseInt(strPartidasGanadas);
                            
                    intPartidasJugadas++;
                    intPartidasGandas++;
                            
                    try{
                        sentenciaSQL = " UPDATE usuarios SET partidasJugadas = "+intPartidasJugadas+","
                                     + " partidasGanadas = "+intPartidasGandas+" "
                                     + " WHERE nickname = '"+usuarioGanador.getNickname()+"'";
                                
                        PreparedStatement PST = conexion.prepareStatement(sentenciaSQL);
            
                        PST.executeUpdate();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
            
            for(int i=0;i<2;i++)
                servidor.enviar(servidor.getCliente(i), "finPartida");
            
        }
        
        
        
        
        
        
        for(int i=0;i<listaJugadores.getCantidad();i++){
            if(listaJugadores.getJugadores().get(i).getNickname().equalsIgnoreCase(nicknameASacar)){
                listaJugadores.getJugadores().remove(i);
                if(listaJugadores.getJugadorActual().contains(ipASacar)){
                    listaJugadores.setJugadorActual(listaJugadores.getJugadorAnterior());
                    actualizarSecuencia();
                }else{
                    if(listaJugadores.getJugadorSiguiente().contains(ipASacar)){
                        for(int j=0;j<servidor.getCantidadClientes();j++)
                            if(servidor.getCliente(i).getInetAddress().toString().contains(ipASacar)){
                                listaJugadores.setJugadorSiguiente(servidor.getCliente((i+1)%servidor.getCantidadClientes()).getInetAddress().toString());
                                    
                            }
                    }else{
                        if(listaJugadores.getJugadorAnterior().contains(ipASacar)){
                            listaJugadores.setJugadorAnterior(listaJugadores.getJugadorActual());
                        }
                    }
                        
                }
                break;
            }
            
        }
        
        
        
        
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            servidor.enviar(servidor.getCliente(i), "abandono,"+ipASacar+","+nicknameASacar);
        }
        
        for(int i=0;i<servidor.getCantidadClientes();i++){
            if(servidor.getCliente(i).getInetAddress().toString().contains(ipASacar)){
                if(ipASacar.contains(Cliente.saberMiIp())){
                    contactoOrganizador = servidor.getCliente(i);
                    servidor.enviar(contactoOrganizador, "show,mensaje,Por ser el organizador del juego\ndebes esperar que"
                                                       + "termine la partida para poder salir.");
                }
                else{
                    try {
                        servidor.enviar(servidor.getCliente(i), "finpartida");
                        servidor.getCliente(i).close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
                
                servidor.getCliente().remove(i);
                servidor.setCantidadClientes(servidor.getCantidadClientes()-1);
                servidor.setNumClientesConectados(servidor.getNumClientesConectados()-1);
                break;
            }
        }
        
        
 
    }
    
    /**
     * Analiza, interpreta y procesa un mensaje enviado por un jugador.
     * @param msj 
     */
    public void procesa(String msj){
        String[] instruccion = msj.split(",");
        //posicion 0 Accion: add get set sorprende desafio cartaEquivocada protegeme paso
        
        switch(instruccion[0]){
            
            case "add":{
                //posicion 1 informacion u objeto a añadir: Jugador Carta
                switch(instruccion[1]){
                    case "jugador":{
                        //posicion 2 nickname
                        //posicion 3 ip
                        //posicion 4 imagen
                        //posticion 5 hostname
                        
                        Usuario nuevoJugador = new Usuario(instruccion[2],instruccion[4],new Cliente(instruccion[3]),null);
                        
                        nuevoJugador.setHostname(instruccion[5]);
                        
                        listaJugadores.getJugadores().add(nuevoJugador);
                        
                        nuevoJugador = null;
                        
                        for(int i=0; i<servidor.getCantidadClientes();i++){
                            servidor.enviar(servidor.getCliente(i),"add,jugador,"+instruccion[2]+","+instruccion[3]+","+instruccion[4]);     
                        }
                        
                    }break;
                        
                    //poner carta en pila de descarte
                    case "carta":{
                        //posicion 2 ip del jugador que envia la carta
                        //posicion 3 TipoDeCarta: CartaNormal CartaEspecial
                        //posicion 4 NumeroONombreDeCarta: 0 2 +4 R S 1
                        //posicion 5 ColorDeCarta: Rojo Azul Amarillo Verde 
                        //posicion 6 ValorDecarta: 0 1 2 4 3 5 20 50
                        ponerCartaEnPilaDescarte(instruccion[2],instruccion[3],instruccion[4],instruccion[5],instruccion[6]);
                            
                        
                        
                        
                    }break;
                        
                    default:{
                        JOptionPane.showMessageDialog(null, "Juego dice: Instruccion[1] invalida");
                    }break;
                
                }
                
            
            }break;
                
            case "get":{
                //posicion 1 informacion u objeto a obtener
                switch(instruccion[1]){
                    
                    case "carta":{
                        //posicion 2 ip del jugador que pide la carta                     
                        if(listaJugadores.getJugadorActual().contains(instruccion[2])){
                            
                            Socket socketDestino = null;
                            
                            for(int i=0;i<servidor.getCantidadClientes();i++){
                                if(servidor.getCliente(i).getInetAddress().toString().contains(instruccion[2])){
                                    socketDestino = servidor.getCliente(i);
                                    break;
                                }
                            }
                            
                            darCarta(1, socketDestino);
                            
                        }else{
                            
                            for(int i=0;i<servidor.getCantidadClientes();i++){
                                if(servidor.getCliente(i).getInetAddress().toString().contains(instruccion[2])){
                                    servidor.enviar(servidor.getCliente(i), "show,mensaje,No es tu turno para jugar");
                                    break;
                                }
                            }
                        }
                        
                        
                    }break;
                    
                    
                    default:{
                        JOptionPane.showMessageDialog(null, "Juego dice: Instruccion[1] invalida");
                    }break;
                }
            
            }break;
                
            case "cambiocolor":{
                //posicion 1 nuevo color
                cambioColor(instruccion[1]);
            }break;
                
            case "sorprende":{
                //posicion 1 ip del jugador que quiere sorprender a posicion2
                //posicion 2 ip del jugador a sorprender
                sorprender(instruccion[1], instruccion[2]);
            
            }break;
                
            case "desafio":{
                //posicion 1 ip del jugador desafiante
               
                
                desafiante = instruccion[1];
                
                
                String numeroONombre,color;
                
                numeroONombre = pilaDescarte.getNumeroONombreCartaAnterior();
                color = pilaDescarte.getColorCartaAnterior();
                
                for(int i=0;i<servidor.getCantidadClientes();i++){
                    if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorAnterior())){
                        servidor.enviar(servidor.getCliente(i), "desafio,"+numeroONombre+","+color);
                        break;
                    }
                }
                
            
            }break;
                
            case "medesafiaron":{
                //posicion 2 ip del jugador desafiado
                //posicion 3 resultado de busqueda de numeroOnombre y color en sus cartas
                
                verificarDesafio(instruccion[1],instruccion[2]);
                
            }break;
            
            case "protegeme":{
                //posicion 1 ip del jugador que quiere protegerse (dijo "UNO")
                proteger(instruccion[1]);    
                
            }break;
                
            case "cartaequivocada":{
                //posicion 1 ip del jugador que dijo que la carta era incorrecta
                verificarCartaEquivocada(instruccion[1]);
            
            }break;
                
            case "paso":{
                //posicion 1 ip del jugador que quiere pasar
                
                pasar(instruccion[1]);
            }break;
            
            case "ejecutacarta":{
                //posicion 1 ip del jugador que quiere que se ejecute la accion de la carta jugada
      
                ejecutarAccionDeCarta(instruccion[1]);
                
            }break;
                
            case "numero":{
                JOptionPane.showMessageDialog(pilaRobo, "PR= "+pilaRobo.getListaCartas().size()+" PD= "+pilaDescarte.getListaCartas().size());
            }break;
                
            case "sumavalores":{
                //posicion 1 suma de los valores de las cartas de un oponente
                sumarValores(instruccion[1]);
            }break;
                
            case "verificaganador":{
                verificarGanador(ipGanador);
            }break;
             
            
            case "abandono":{
                //posicion 1 ip del jugador que va a abandonar la partida
                //posicion 2 numero de cartas restantes del jugador que va a abandonar
                //desde la posicion 3 vienen las cartas del jugador asi tipo,numeroONombre,color,valor,tipo,numeroONombre,color,valor.....
                
                int numCartaRestantes = Integer.parseInt(instruccion[2]);
                int i=3;
                while(numCartaRestantes>0){
                    ponerCartaEnPilaRobo(instruccion[i], instruccion[i+1], instruccion[i+2], Integer.parseInt(instruccion[i+3]));
                    i+=4;
                    numCartaRestantes--;
                }
                
                sacarJugador(instruccion[1]);
            }break;
            
            default:{
                JOptionPane.showMessageDialog(null, "Juego dice: Instruccion[0] invalida");
            }break;
        
        }
        
        
    }
    
    /**
     * Carga a los jugadores que se han unido a la partida.
     */
    public void cargarJugadores(){
        String jugador;
        for(int i=0;i<servidor.getCantidadClientes();i++){
            try {
                servidor.enviar(servidor.getCliente(i),"get,jugador");
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
    }
    
    /**
     * Espera a que todos lo jugadores se conecten y va guardando los socket de cada uno para comunicarse con ellos cuando sea necesario.
     */
    public void esperarJugadores(){
        int i =0;
        while(i<servidor.getCantidadClientes()){
            try {
                
                System.out.println("Esperando clientes");
                
                servidor.getCliente().add(servidor.getServer().accept());
                servidor.setNumClientesConectados(servidor.getNumClientesConectados()+1);
                    
                System.out.println("Se conecto: "+ servidor.getCliente().get(i).getInetAddress().toString());
 
                i++;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    
    }
    
    /**
     * Hilo que se encarga de escuchar todos los mensajes que envian los clientes y envía dichos mensajes para que sean procesado.
     */
    @Override
    public void run(){
        String mensajeRecibido=""; 
        esperarJugadores();
        
        //Atencion de llamadas de clientes y mensajes de Jugadores
        while(true){
            
            
            int n =-1;
            try {
                Socket temporal = servidor.getServer().accept();
                
                System.out.println("Servidor: llego un mensaje");
                
                String ipTemp = temporal.getInetAddress().toString();
                String ipCliente ="";
                
                for(int j=0; j<servidor.getCantidadClientes();j++){
                    ipCliente = servidor.getCliente().get(j).getInetAddress().toString();
                    
                    if(ipTemp.equalsIgnoreCase(ipCliente)){
                        
                        n =j;
                        System.out.println("lo encontre: "+n);
                        
                    }
                }
                
                if(n!=-1){
                    
                    mensajeRecibido = servidor.recibir(servidor.getCliente().get(n));
                    System.out.println("Juego voy a procesar: "+mensajeRecibido);
                    procesa(mensajeRecibido);
                    
                    //JOptionPane.showMessageDialog(null, "Servidor esto es lo q llego: "+mensajeRecibido);
                     
                }
               
                temporal.close();
                mensajeRecibido="";
                
                
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                
                
            }
            
            
            
            //mensajeRecibido=servidor.getMensajeRecibido();
            //if(mensajeRecibido!=null && mensajeRecibido!=""){
                //System.out.println("Juego voy a procesar: "+mensajeRecibido);
                //procesa(mensajeRecibido);
                //servidor.setMensajeRecibido("");
            //}
        }
    }
    
    /**
     * Coloca la carta enviada por un jugador en la pila de descarte siempre y cuendo este jugador sea el jugador actual
     * @param ipJugadorQueEnviaCarta
     * @param tipoCarta
     * @param numeroONombreCarta
     * @param colorCarta
     * @param valorCarta 
     */
    public void ponerCartaEnPilaDescarte(String ipJugadorQueEnviaCarta, String tipoCarta, String numeroONombreCarta, String colorCarta, String valorCarta){
        if( listaJugadores.getJugadorActual().contains(ipJugadorQueEnviaCarta)){
                            
            pilaDescarte.add(tipoCarta,numeroONombreCarta,colorCarta,Integer.parseInt(valorCarta));
                            
                            
            for(int i=0;i<servidor.getCantidadClientes();i++){
                servidor.enviar(servidor.getCliente(i), "set,jugador,"+ipJugadorQueEnviaCarta+",cantidadcartas,-1");
            }              
                            
            listaJugadores.getJugador(ipJugadorQueEnviaCarta).setCantidadCartas(-1);  
                            
            actualizarSecuencia();
                           
            for(int k=0;k<servidor.getCantidadClientes();k++){
                servidor.enviar(servidor.getCliente(k), "set,cartaactual,"+pilaDescarte.getCartaActual());
            }
                            
            this.ipGanador = ipJugadorQueEnviaCarta;
            if(listaJugadores.getJugador(ipJugadorQueEnviaCarta).getCantidadCartas()==0){
                if(tipoCarta.equalsIgnoreCase("CartaNormal")){
                    for(int i=0;i<servidor.getCantidadClientes();i++){
                        if(servidor.getCliente(i).getInetAddress().toString().contains(listaJugadores.getJugadorActual())){
                            servidor.enviar(servidor.getCliente(i), "verificaganador");
                            break;
                        }
                    }
                }else{
                    if (numeroONombreCarta.equalsIgnoreCase("CC"))
                        verificarGanador(ipGanador);
                }
            }
            }else{
                if(numeroONombreCarta.equalsIgnoreCase("CC") || numeroONombreCarta.equalsIgnoreCase("+4"))
                    colorCarta="Negro";
                            
                for(int i=0;i<servidor.getCantidadClientes();i++){
                    if(servidor.getCliente(i).getInetAddress().toString().contains(ipJugadorQueEnviaCarta)){
                        servidor.enviar(servidor.getCliente(i), "add,carta,"+tipoCarta+","+numeroONombreCarta+","+colorCarta+","+valorCarta);
                        servidor.enviar(servidor.getCliente(i), "show,mensaje,No es tu turno para jugar");
                        break;
                    }
                }
            }
    }
    
    /**
     * Establece las condiciones necesarias para iniciar la partida.
     */
    public void iniciarJuego(){
        
        pilaDescarte = new PilaDescarte();
        pilaRobo = new PilaRobo();
        sentido = "Horario";
        
        if(listaJugadores.getJugadores().size()==0)
            cargarJugadores();
        
        if(tipo.equalsIgnoreCase("Rapido"))
            for(int i=0;i<servidor.getCantidadClientes();i++)
                servidor.enviar(servidor.getCliente(i), "show,mensaje,Tipo de Juego: "+this.tipo);
                                                      
        else
            for(int i=0;i<servidor.getCantidadClientes();i++)
                servidor.enviar(servidor.getCliente(i), "show,mensaje,Tipo de Juego: "+this.tipo
                                                      + "\nPuntos para ganar: "+puntos);
            
                           
        
        elegirPrimerJugador();
        repartir();
        elegirPrimeraCarta();
    }
    
    
    /**
     * Ejecuta el método del servidor y detiene el hilo para terminar asi con el juego.
     */
    public void terminar(){
        
        servidor.terminar();
        hilo.stop();
    }
    
    public void setPuntos(int puntos){
        this.puntos = puntos;
    }
    
    public int getPuntos(){
        return puntos;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
        
    }
    
    public String getTipo(){
        return tipo;
    }
}
