package Esencial;


import Conexiones.ConexionMYSQL;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



/**
 * Clase que permite el manejo de archivos .xml ya sea creándolos, leyéndolos, escribiendo en ellos etc.
 * @author Stalin
 */

public class XML {
    private Document documento;
    private Element raiz;
    //private Element elemento;
    private Text texto;
    private String hostname;
    private List listaDeElementos;
    
    
    /**
     * Constructor de archivo xml
     * @param hostname 
     */
    public XML(String hostname){
        this.documento = new Document();
        this.raiz = new Element("Basededatos");
        this.documento.setRootElement(raiz);
        this.texto = new Text("");
        this.hostname = hostname;
        this.listaDeElementos=null;
    }
        
    /**
     * Permite agregar un usuario al archivo xml
     * @param usuario 
     */
    public void AgregarUsuario(Usuario usuario){
        
        Element elemento = new Element("usuario");
        Element nombre = new Element("nombre");
        texto = new Text(usuario.getNombre());
        nombre.addContent(texto);
        elemento.addContent(nombre);
        
        
        Element apellido = new Element("apellido");
        texto = new Text(usuario.getApellido());
        apellido.addContent(texto);
        elemento.addContent(apellido);
        
        Element nickname = new Element("nickname");
        texto = new Text(usuario.getNickname());
        nickname.addContent(texto);
        elemento.addContent(nickname);
        
        Element clave = new Element("clave");
        texto = new Text(usuario.getClave());
        clave.addContent(texto);
        elemento.addContent(clave);
        
        Element imagen = new Element("imagen");
        texto = new Text(usuario.getImagen());
        imagen.addContent(texto);
        elemento.addContent(imagen);
        
        Element email = new Element("email");
        texto = new Text(usuario.getEmail());
        email.addContent(texto);
        elemento.addContent(email);
        
        Element estado = new Element("estado");
        texto = new Text(usuario.getEstado());
        estado.addContent(texto);
        elemento.addContent(estado);
        
        Element partidasJugadas = new Element("partidasJugadas");
        texto = new Text(Integer.toString(usuario.getPartidasJugadas()));
        partidasJugadas.addContent(texto);
        elemento.addContent(partidasJugadas);
        
        Element partidasGanadas = new Element("partidasGanadas");
        texto = new Text(Integer.toString(usuario.getPartidasGanadas()));
        partidasGanadas.addContent(texto);
        elemento.addContent(partidasGanadas);
        
        Element partidasPerdidas = new Element("partidasPerdidas");
        texto = new Text(Integer.toString(usuario.getPartidasPerdidas()));
        partidasPerdidas.addContent(texto);
        elemento.addContent(partidasPerdidas);
        
        Element partidasAbandonadas = new Element("partidasAbandonadas");
        texto = new Text(Integer.toString(usuario.getPartidasAbandonadas()));
        partidasAbandonadas.addContent(texto);
        elemento.addContent(partidasAbandonadas);
        
        
        raiz.addContent(elemento);
        listaDeElementos = raiz.getChildren();
    
    }
	
	
    /**
     * Permite escribir el archivo xml creado
     * @return 
     */
    public boolean EcribirDocumento() {
            
        try{
            XMLOutputter outputter = new XMLOutputter( Format.getPrettyFormat() );
            outputter.output ( documento, new FileOutputStream ( "usuarios.xml" ) );
            return true;
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
	
    }
	
    /**
     * Se encarga de descargar los datos de los usuarios de la base de datos y agregarlos al archivo xml
     * @return true si la descarga y la escritura finalizaron con exito, false en caso contrario.
     */
    public boolean cargarUsuarios(){
        
        ConexionMYSQL mysql = new ConexionMYSQL(hostname,"bduno","UsuarioUNO");
        Connection conexion = mysql.conectar();
        String sentenciaSQL = "";
        
        Usuario usuarioTemporal;
        
        ImageIcon imagenTemporal = null;
        
        if(conexion!=null)
            try {
                
                sentenciaSQL = "SELECT nombre, apellido, nickname, clave, imagen, email, estado,"
                             + "partidasJugadas, partidasGanadas, partidasPerdidas, partidasAbandonadas "
                             + "FROM usuarios";
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);
                

                while(RS.next()){
                    
                    usuarioTemporal = new Usuario(RS.getString("nombre"), RS.getString("apellido"), RS.getString("nickname"), 
                                                     RS.getString("clave"), RS.getString("imagen"), RS.getString("email"),
                                                     RS.getString("estado"), null);
                    
                    usuarioTemporal.setPartidasJugadas(Integer.parseInt(RS.getString("partidasJugadas")));
                    usuarioTemporal.setPartidasGanadas(Integer.parseInt(RS.getString("partidasGanadas")));
                    usuarioTemporal.setPartidasPerdidas(Integer.parseInt(RS.getString("partidasPerdidas")));
                    usuarioTemporal.setPartidasAbandonadas(Integer.parseInt(RS.getString("partidasAbandonadas")));
                    
                    AgregarUsuario(usuarioTemporal);
                    
                }
                
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        else
            return false;
    }

    /**
     * Se encarga de leer desde el archivo xml los datos de los usuarios y almacenarlos en un ArrayList
     * @return ArrayList de usuarios almacenados
     */
    public ArrayList<Usuario> obtenerListaDeUsuarios(){
        List listaDeUsuarios = new ArrayList<Usuario>();
        Element elemento;
        
        String nombre,apellido,nickname,clave,imagen,email,estado;
        int partidasJugadas,partidasGanadas,partidasPerdidas,partidasAbandonadas;
        
        
        for(int i=0;i<listaDeElementos.size();i++){
            elemento = (Element) listaDeElementos.get(i);
            
            nombre = elemento.getChild("nombre").getText();
            apellido = elemento.getChild("apellido").getText();
            nickname = elemento.getChild("nickname").getText();
            clave = elemento.getChild("clave").getText();
            imagen = elemento.getChild("imagen").getText();
            email = elemento.getChild("email").getText();
            estado = elemento.getChild("estado").getText();
            partidasJugadas = Integer.parseInt(elemento.getChild("partidasJugadas").getText());
            partidasGanadas = Integer.parseInt(elemento.getChild("partidasGanadas").getText());
            partidasPerdidas = Integer.parseInt(elemento.getChild("partidasPerdidas").getText());
            partidasAbandonadas = Integer.parseInt(elemento.getChild("partidasAbandonadas").getText());
            
            listaDeUsuarios.add(new Usuario(nombre, apellido, nickname, clave, imagen, email, estado, null, 
                                            partidasJugadas, partidasGanadas, partidasPerdidas, partidasAbandonadas));
            
        }
        
        return (ArrayList<Usuario>) listaDeUsuarios;
    }
    
    
    /**
     * Permite modificar el campo indicado con el nuevo valor del usuario que corresponda con el nickname recibido
     * @param nickname
     * @param campo
     * @param nuevoValor
     * @return true si el usuario fue modificado, false en caso contrario;
     */
    public boolean ModificarUsuario(String nickname, String campo, String nuevoValor) {
        
        Element elemento;
        
        for(int i=0;i<listaDeElementos.size();i++){
            elemento = (Element) listaDeElementos.get(i);
            
            if(elemento.getChild("nickname").getText().equalsIgnoreCase(nickname)){
                elemento.getChild(campo).setText(nuevoValor);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Se encarga se buscar al usuario que corresponda el nickname recibido
     * @param nickname
     * @return usuario encontrado, null en caso contrario
     */
    public Usuario buscarUsuario(String nickname){
        Usuario usuario = null;
        Element elemento;
        String nombre,apellido,clave,imagen,email,estado;
        int partidasJugadas,partidasGanadas,partidasPerdidas,partidasAbandonadas;
        
        for(int i=0;i<listaDeElementos.size();i++){
            elemento = (Element) listaDeElementos.get(i);
            
            if(elemento.getChild("nickname").getText().equalsIgnoreCase(nickname)){
                
                nombre = elemento.getChild("nombre").getText();
                apellido = elemento.getChild("apellido").getText();
                clave = elemento.getChild("clave").getText();
                imagen = elemento.getChild("imagen").getText();
                email = elemento.getChild("email").getText();
                estado = elemento.getChild("estado").getText();
                partidasJugadas = Integer.parseInt(elemento.getChild("partidasJugadas").getText());
                partidasGanadas = Integer.parseInt(elemento.getChild("partidasGanadas").getText());
                partidasPerdidas = Integer.parseInt(elemento.getChild("partidasPerdidas").getText());
                partidasAbandonadas = Integer.parseInt(elemento.getChild("partidasAbandonadas").getText());

                usuario = new Usuario(nombre, apellido, nickname, clave, imagen, email, estado, null, 
                                      partidasJugadas, partidasGanadas, partidasPerdidas, partidasAbandonadas);
                
                return usuario;
            }
        }
        return usuario;
    }
    
}
