package Esencial;

import Conexiones.Cliente;
import Conexiones.Servidor;
import Formularios.CartaEspecial;
import Formularios.CartaNormal;
import Formularios.Sala;
import Formularios.Tablero;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * Clase que representa a un usuario el cual podrá interactuar con el sistema a través de su cuenta, ya sea como un jugador durante el juego, el cual puede seleccionar la acción que desee ejecutar durante su turno o simplemente interactuar con las ventanas.
 * @author Stalin
 */
public class Usuario implements Runnable {
    private String nombre;
    private String apellido;
    private String nickname;
    private String clave;
    private String imagen;
    private String email;
    private int partidasJugadas;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasAbandonadas;
    private String estado;
    private Cliente cliente;
    private Thread hilo;
    private Tablero tablero;
    private ListaCartas listaCartas;
    private int cantidadCartas;
    private int puntos;
    private String proteccion;
    private String hostname;
    
    
    /**
     * Constructor que crea un usuario base con los parametros recibidos es decir sin medios de comunicacion, para ser enviados a los demas jugadores 
     * @param nickname
     * @param imagen
     * @param cliente
     * @param listaCartas 
     */
    public Usuario(String nickname, String imagen,Cliente cliente, ListaCartas listaCartas){
        this.nickname = nickname;
        this.imagen = imagen;
        this.cliente = cliente;
        this.cantidadCartas = 0;
        this.proteccion = "off";
        this.listaCartas = listaCartas;
        this.puntos=0;
    }
    
    /**
     * Constructor de usuario a partir de los parametros dados para actuar como jugador es decir posee medios de comunicacion
     * @param nombre
     * @param apellido
     * @param nickname
     * @param clave
     * @param imagen
     * @param email
     * @param estado
     * @param cliente 
     */
    public Usuario(String nombre, String apellido, String nickname, String clave, String imagen, String email, String estado, Cliente cliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.clave = clave;
        this.imagen = imagen;
        this.email = email;
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.partidasAbandonadas = 0;
        this.estado=estado;
        this.cliente = cliente;
        this.puntos=0;
        
        listaCartas = new ListaCartas();
        
        puntos = 0;
        proteccion="off";
        
        tablero = null;
        
        hilo = new Thread(this);
        
        
    }
    
    /**
     * Constructor de usuario con todos sus atributos sin medios de comnicacion ideal para guardar todos sus datos y estadísticas
     * @param nombre
     * @param apellido
     * @param nickname
     * @param clave
     * @param imagen
     * @param email
     * @param estado
     * @param cliente
     * @param partidasJugadas
     * @param partidasGanadas
     * @param partidasPerdidas
     * @param partidasAbandonadas 
     */
    public Usuario(String nombre, String apellido, String nickname, String clave, String imagen, String email, String estado, 
                   Cliente cliente,int partidasJugadas,int partidasGanadas,int partidasPerdidas,int partidasAbandonadas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.clave = clave;
        this.imagen = imagen;
        this.email = email;
        this.partidasJugadas = partidasJugadas;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.partidasAbandonadas = partidasAbandonadas;
        this.estado=estado;
        this.cliente = cliente;
        this.puntos=0;
        
        listaCartas = new ListaCartas();
        
        puntos = 0;
        proteccion="off";
        
        tablero = null;
        
        hilo = new Thread(this);
        
        
    }

    
    /**
     * Incia el hilo
     */
    public void iniciar(){
        hilo.start();
    }
    
    /**
     * Al hacer clik sobre una carta, esta es enviada para ser colocada en la pila de robo
     */
    public void tirarCarta() {
        //la carta se tira al hacer click sobre ella
    }
	
    /**
     * Toma una carta que corresponde con los parametros recibidos de la pila de robo y la agrega a su lista de cartas.
     * @param tipoCarta
     * @param numeroONombre
     * @param color
     * @param valor 
     */
    public void robarCarta(String tipoCarta, String numeroONombre,String color,String valor) {
        // la carta se roba al hacer click sobre la pila de robo
        
        tablero.addAMisCartas(tipoCarta, numeroONombre, color, valor);
	
    }
    
    
    /**
     * Verifica si tiene al menos una carta que tenga el numero o nombre o color dado como parametro en la lista de cartas.
     * @param numeroONombre
     * @param color 
     */
    public void verificarDesafio(String numeroONombre, String color){
        String resultado = "false";
        
        if(listaCartas.tengoNumeroONombre(numeroONombre))
            resultado = "true";
        else{
            if(listaCartas.tengoColor(color))
                resultado = "true";
        }
        
        cliente.enviar("medesafiaron,"+cliente.getMiIp()+","+resultado);
    }
	
    
    /**
     * Al pulsar el boton Decir UNO, se le envia un mensaje al juego indicandole que el usuario quiere protegerse
     */
    public void decirUNO() {
        // se dice UNO al pulsar el boton "Decir UNO!!!"
    }
	
    /**
     * Al pulsar el botón desafiar se le envia al juego un mensaje que indica que estamos desafiando a un jugador
     */
    public void desafiar() {
	//Se desafia al pulsar el botón "Desafiar"
    }
	
    /**
     * Al pulsar el botón sorprender se le envia un mensaje al juego indicandole que se quiere sorprender al usuario seleccionado en la lista de nickname.
     */
    public void sorprender() {
	// Se sorprende al jugador seleccionado en el JComboBox al pulsar el boton "Sorprender"
    }
	
    /**
     * Al pulsar el botón Abandonar se le envia un mensaje al juego indicandole que queremos abandonar la partida
     */
    public void abandonar() {
	// Se abandona la partida al pulsar el boton "Abandonar"
        
    }
	
    /**
     * Al pulsar el botón carta equivocada se le envia un mensaje al juego indicandole que la carta jugada es incorrecta.
     */
    public void cartaEquivocada() {
	// Se indica que la carta jugada no es correcta al pulsar el boton "Carta Equivocada"
    }
    
    
    /**
     * Muestra el tablero
     */
    public void cargarTablero(){
        tablero.setVisible(true);
    }
    
    /**
     * Se encarga de analizar, interpretar y procesar el mensaje recibido
     * @param msj 
     */
    public void procesa(String msj){
        String[] instruccion = msj.split(",");
        
        switch(instruccion[0]){
            //posicion 0 Accion: add get set desafio
            
            case "add":{
                switch(instruccion[1]){
                    //posicion 1 objeto a añadir: Carta Jugador
                    
                    //RobarCarta
                    case "carta":{
                        //posicion 2 TipoDeCarta: CartaNormal CartaEspecial
                        //posicion 3 NumeroONombreDeCarta: 0 2 +4 R S 1
                        //posicion 4 ColorDeCarta: Rojo Azul Amarillo Verde 
                        //posicion 5 ValorDecarta: 0 1 2 4 3 5 20 50
                        
                        
                        robarCarta(instruccion[2],instruccion[3],instruccion[4],instruccion[5]);      
                    
                    }break;
                    
                    //añadir jugador
                    case "jugador":{
                        //posicion 2 nickname
                        //posicion 3 ip
                        //posicion 4 imagen
                        
                        tablero.addJugador(new Usuario(instruccion[2],instruccion[4],cliente,listaCartas),instruccion[3]);
                        
                    }break;
                
                
                
                }

                  
            }break;
            
            case "get":{
                //posicion 1 InformacionAObtener: proteccion numeroDeCartasRestantes nickname apellido
                //posicion 2 NumeroONombre o Color AObtener: 4 6 8 +2 R Azul Rojo
                
                switch(instruccion[1]){
                    
                    case "nombre":{
                        cliente.enviar(nombre);
                    }break;
                    
                    case "apellido":{
                        cliente.enviar(apellido);
                    }break;
                    
                    case "nickname":{
                        cliente.enviar(nickname);
                    }break;
                    
                    case "cliente":{
                        cliente.enviar(cliente.getSocket().getInetAddress().toString());
                    }break;
                    
                    case "puntos":{
                        cliente.enviar(Integer.toString(puntos));
                    }break;
                    
                    case "proteccion":{
                        cliente.enviar(proteccion);
                    }break;
                    
                    case "numeroDeCartas":{
                        cliente.enviar(Integer.toString(listaCartas.size()));
                    }break;
                        
                    case "numeroONombre":{
                        String resultado;
                        if(listaCartas.tengoNumeroONombre(instruccion[2]))
                            resultado = "true";
                        else
                            resultado = "false";
                        cliente.enviar(resultado);
                    }break;
                    
                    case "sumavalores":{
                        cliente.enviar("sumavalores,"+Integer.toString(listaCartas.sumaValores()));
                    }break;
                        
                    case "jugador":{
                        cliente.enviar("add,jugador,"+nickname+","+cliente.getMiIp()+","+imagen+","+hostname);
                    }break;
                        
                    default:{
                        cliente.enviar("instruccion[1] no encontrada");
                    }break;
                
                }
            
            }break;
            
            case "set":{
                //posicion 1 InformacionAModificar: proteccion partidasJugadas sentidoDelJuego cartaActual puntos   
                //posicion 2 NuevoValor: on off Horario AntiHorario NuevaCartaActual nuevos puntos
                switch(instruccion[1]){
                    case "proteccion":{
                        proteccion = instruccion[2];
                    }break;
                    
                    case "sentido":{
                        tablero.cambiarSentido(instruccion[2]);
                    }break; 
                    
                    
                    case "partidasJugadas":{
                        partidasJugadas++;
                    }break;
                        
                    case "partidasGanadas":{
                        partidasGanadas++;
                    }break;
                        
                    case "partidasPerdidas":{
                        partidasPerdidas++;
                    }break;
                    
                    case "partidasAbandonadas":{
                        partidasAbandonadas++;
                    }break;
                        
                    case "puntos":{
                        //posicion 3 nickname del jugador que se le van a modificar los puntos
                        tablero.setPuntos(instruccion[2],instruccion[3]);
                        
                    }break;
                     
                    case "jugador":{
                        //posicion 2 ipDeJugadorAModificar
                        //posicion 3 informacionAModificar
                        switch(instruccion[3]){
                            case "cantidadcartas":{
                                //posicion 4 cantidadDecartasAgregadas(+Num) o cantidadDecartasAgregadas(-Num)
                                tablero.setCantidadCartas(instruccion[2], instruccion[4]);
                                
                            }break;
                            
                            default:{
                                cliente.enviar("instruccion[3] no encontrada");
                            }break;   
                        }
                        
                    }break;
                        
                    case "jugadoractual":{
                        //posicion 2 ip del jugador actual
                        tablero.setJugadorActual(instruccion[2]);
                        
                        
                    }break;
                        
                    case "cartaactual":{
                        //posicion 2 numeroONombre y color de la cartaactual
                        tablero.cambiarCartaActual(instruccion[2]);
                    }break;
                    
                    
                    
                    
                    default:{
                        cliente.enviar("instruccion[1] no encontrada");
                    }break;
                }
            }break;
                
            case "show":{
                
                //posicion 1 objeto a mostrar
                switch(instruccion[1]){
                    
                    case "mensaje":{
                        //posicion 2 mensaje a mostrar
                        JOptionPane.showMessageDialog(tablero, instruccion[2]);
                    }break;
                    
                    case "imagen":{
                        //posicion 2 mensaje
                        //posicion 3 titulo
                        //posicion 4 nombreDeLaImagen
                        
                        JOptionPane.showMessageDialog(tablero,instruccion[2],instruccion[3],2,new ImageIcon(getClass().getResource("/Imagenes/"+instruccion[4]+".jpg")));
                    }break;
                        
                    default:{
                        cliente.enviar("instruccion[1] no encontrada");
                    }break;
                
                }
            }break;
                
            case "desafio":{
                //posicion 1 numeroONombre de carta que se busca
                //posicion 2 color de carta que se busca
                
                verificarDesafio(instruccion[1],instruccion[2]);
                
            }break;
                
            case "elijecolor":{
                String colores[] = new String[] {"Amarillo","Azul","Rojo","Verde"};
                int intColor = JOptionPane.showOptionDialog(tablero, "Selecciona el nuevo color", "Cambio de color",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,new ImageIcon(getClass().getResource("/Imagenes/CCNegro.jpg")), colores, colores[0]);
                String color="";
                switch(intColor){

                    case 0:{
                        color = "Amarillo";
                        cliente.enviar("cambiocolor,"+color);
                    }break;

                    case 1:{
                        color = "Azul";
                        cliente.enviar("cambiocolor,"+color);
                    }break;

                    case 2:{
                        color = "Rojo";
                        cliente.enviar("cambiocolor,"+color);
                    }break;

                    case 3:{
                        color = "Verde";
                        cliente.enviar("cambiocolor,"+color);
                    }break;

                    default:{
                        procesa("elijecolor");
                    }break;
                 }
                
            }break;
                
            case "nuevaPartida":{
                tablero.prepararOtraPartida();
            }break;
                
            case "finPartida":{
                terminar();
                
            }break;
            
                        
            case "activacartas":{
                tablero.activarCartas();
            }break;   
                
                
            case "verificaganador":{
                verificarGanador();
            }break;
                
            case "abandono":{
                //posicion 1 ip del jugador que abandona
                //posicion 2 nickname del jugador que abandona
                tablero.sacarJugador(instruccion[1],instruccion[2]);
            }break;    
                
            default:{
                cliente.enviar("instruccion[0] no encontrada");
            }break;
        
        }
    }
    
    /**
     * Muestra un mensaje indicando que se está espereando a los demas jugadores y permite elegir si se sigue esperando ó si se quiere salir del juego
     * @param servidor
     * @param tablero
     * @return resultado de la eleccion.
     */
    public boolean esperarJugadores(Servidor servidor, Tablero tablero){
        String opciones[] = new String[] {"Actualizar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(null, "Cargando Jugadores: "+servidor.getNumClientesConectados()
                                                        + "/"+servidor.getCantidadClientes()+"\nPulse Actualizar para "
                                                        + "seguir\nesperando y actualiar ó\npulse Cancelar para "
                                                        + "salir", "Esperando Jugadores",
                                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                        new ImageIcon(getClass().getResource("/Imagenes/cargando.gif")), opciones, opciones[0]);
        
        switch(opcion){
            
            case 0:{
                return true;
            }
            
            case 1:{
                return false;   
            }
            
            default:{
                return esperarJugadores(servidor,tablero);
            }
        }
    }
    
    /**
     * Permite al usuario verificar si el supuesto ganador es legítimo ó no
     */
    public void verificarGanador(){
        String opciones[] = new String[] {"Sí","No"};
        int opcion = JOptionPane.showOptionDialog(null, "La carta jugada era la ultima carta que le quedaba"
                                                        + "\n¿Consideras que dicha carta es correcta?", "Ultima carta",
                                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                        new ImageIcon(getClass().getResource("/Imagenes/piensa.gif")), opciones, opciones[0]);
        
        switch(opcion){
            
            case 0:{
               cliente.enviar("verificaganador");
            }break;
            
            case 1:{
                cliente.enviar("cartaequivocada,"+cliente.getMiIp());
            }break;
            
            default:{
                verificarGanador();
            }break;
        }
    }
    
    
    /**
     * Hilo que se encarga de escuchar todos los mensajes que le envia el servidor y envia dichos mensajes para que sean procesados
     */
    @Override
    public void run(){
        String mensajeRecibido="";
        while(true){
            try {
                mensajeRecibido=cliente.recibir();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            if(mensajeRecibido!=null && mensajeRecibido!=""){
                System.out.println("Usuario voy a procesar: "+mensajeRecibido);
                procesa(mensajeRecibido);
                mensajeRecibido="";
            }
        }
    }

    
        
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the eimail
     */
    public String getEimail() {
        return getEmail();
    }

    /**
     * @param eimail the eimail to set
     */
    public void setEimail(String eimail) {
        this.setEmail(eimail);
    }

    /**
     * @return the partidasJugadas
     */
    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    /**
     * @param partidasJugadas the partidasJugadas to set
     */
    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * @return the partidasGanadas
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * @param partidasGanadas the partidasGanadas to set
     */
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * @return the partidasPerdidas
     */
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    /**
     * @param partidasPerdidas the partidasPerdidas to set
     */
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    /**
     * @return the partidasAbandonadas
     */
    public int getPartidasAbandonadas() {
        return partidasAbandonadas;
    }

    /**
     * @param partidasAbandonadas the partidasAbandonadas to set
     */
    public void setPartidasAbandonadas(int partidasAbandonadas) {
        this.partidasAbandonadas = partidasAbandonadas;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the hilo
     */
    public Thread getHilo() {
        return hilo;
    }

    /**
     * @param hilo the hilo to set
     */
    public void setHilo(Thread hilo) {
        this.hilo = hilo;
    }

    /**
     * @return the listaCartas
     */
    public ListaCartas getListaCartas() {
        return listaCartas;
    }

    /**
     * @param listaCartas the listaCartas to set
     */
    public void setListaCartas(ListaCartas listaCartas) {
        this.listaCartas = listaCartas;
    }

    /**
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * @return the proteccion
     */
    public String getProteccion() {
        return proteccion;
    }

    /**
     * @param proteccion the proteccion to set
     */
    public void setProteccion(String proteccion) {
        this.proteccion = proteccion;
    }
    
    /**
     * Permite al usuario salir del juego
     */
    public void terminar(){

        JOptionPane.showMessageDialog(null, "Gracias por jugar");
        System.exit(0);
    }

    /**
     * @return the cantidadCartas
     */
    public int getCantidadCartas() {
        return cantidadCartas;
    }

    /**
     * @param cantidadCartas the cantidadCartas to set
     */
    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas += cantidadCartas;
    }

    /**
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @param tablero the tablero to set
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
    
    /**
     * @param hostname the tablero to set
     */
    public void setHostname(String hostname){
        this.hostname = hostname;
    }
    
    /**
     * @return the hostname
     */
    public String getHostname(){
        return hostname;
    }
        
}
