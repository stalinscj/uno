/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Formularios;

import Conexiones.ConexionMYSQL;
import Esencial.Imagen;
import Esencial.Sonido;
import Esencial.Usuario;
import Esencial.XML;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Clase que representa una ventana en la que un usuario puede gestionar su acceso al sistema o consultar ayuda.
 * @author Stalin
 */
public class Login extends javax.swing.JFrame {
    private Fondo  imagenFondo;
    public static String hostname="";
    private Usuario usuarioActual;
    private Sonido musicaFondo;
        
    
    

    /**
     * Creates new form Login
     */
    public Login() {
        
        
        imagenFondo = new Fondo("/Imagenes/fondoLogin.jpg");
        
        usuarioActual = null;
        
        initComponents();
        
        this.setSize(imagenFondo.getWidth(),imagenFondo.getHeight());
        this.setResizable(false);
        fondo.add(imagenFondo);
        fondo.setOpaque(false);
        //titulo.setOpaque(false);
        
        this.setLocationRelativeTo(null);
        
        musicaFondo = new Sonido(this);
        musicaFondo.play();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondo = new javax.swing.JPanel();
        txtClave = new javax.swing.JPasswordField();
        txtNickname = new javax.swing.JTextField();
        lblNickname = new javax.swing.JLabel();
        btnRegistrarse = new javax.swing.JButton();
        btnOlvideMiPass = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        lblClave = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        txtServidor = new javax.swing.JLabel();
        cboServidor = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondo.setBackground(new java.awt.Color(0, 153, 0));

        txtClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveActionPerformed(evt);
            }
        });

        txtNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNicknameActionPerformed(evt);
            }
        });

        lblNickname.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblNickname.setText("Nickname");

        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarseMouseClicked(evt);
            }
        });

        btnOlvideMiPass.setText("Olvide mi contraseña");
        btnOlvideMiPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOlvideMiPassMouseClicked(evt);
            }
        });

        btnAyuda.setText("Ayuda");
        btnAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAyudaMouseClicked(evt);
            }
        });

        lblClave.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblClave.setText("Clave");

        btnIngresar.setText("Ingresar");
        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIngresarMouseClicked(evt);
            }
        });
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        txtServidor.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        txtServidor.setText("Servidor");

        cboServidor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Puerto Ordaz", "El Callao" }));

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(btnAyuda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIngresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegistrarse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOlvideMiPass)
                .addGap(45, 45, 45))
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(txtServidor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(cboServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNickname)
                            .addComponent(lblClave))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNickname, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(txtClave))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServidor)
                    .addComponent(cboServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNickname)
                    .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClave)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAyuda)
                    .addComponent(btnIngresar)
                    .addComponent(btnRegistrarse)
                    .addComponent(btnOlvideMiPass))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarseMouseClicked
        
        if(cboServidor.getSelectedItem().toString().equalsIgnoreCase("Puerto Ordaz"))
            hostname = "puertoordazcj.ddns.net";
        else
            hostname = "elcallaocj.ddns.net";
        
        musicaFondo.Stop();
        String[] args = null;
        Registro.origen = "Login";
        Registro.hostname = this.hostname;
        Registro.main(args);
        this.dispose();
    }//GEN-LAST:event_btnRegistrarseMouseClicked

    private void btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarMouseClicked
        
        if(cboServidor.getSelectedItem().toString().equalsIgnoreCase("Puerto Ordaz"))
            hostname = "puertoordazcj.ddns.net";
        else
            hostname = "elcallaocj.ddns.net";
        
        if(verificarUsuario(hostname, txtNickname.getText(), DigestUtils.md5Hex(txtClave.getText())) && cargarUsuario(txtNickname.getText())){
                        
            
            JOptionPane.showMessageDialog(this," Bienvenido \n" +tab(" Bienvenido ",txtNickname.getText())+ txtNickname.getText() ,"Acceso permitido",1,new ImageIcon(getClass().getResource("/Imagenes/accesoPermitido.gif")));
            musicaFondo.Stop();
            String[] args = null;
            Sala.hostname = this.hostname;
            Sala.usuarioActual = this.usuarioActual;
            Sala.main(args);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Los  datos  ingresados\nson inválidos. Por favor\nverifique  su  nickname,\n"
                                              + "clave y/o servidor.","Acceso Denegado",1,new ImageIcon(getClass().getResource("/Imagenes/accesoDenegado.gif")));
        }
    }//GEN-LAST:event_btnIngresarMouseClicked

    private void txtNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNicknameActionPerformed
        txtClave.requestFocus();
    }//GEN-LAST:event_txtNicknameActionPerformed

    private void txtClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveActionPerformed
        btnIngresarMouseClicked(null);
    }//GEN-LAST:event_txtClaveActionPerformed

    private void btnAyudaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAyudaMouseClicked
        musicaFondo.Stop();
        String[] args = null;
        Ayuda.main(args);
        this.dispose();
    }//GEN-LAST:event_btnAyudaMouseClicked

    private void btnOlvideMiPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOlvideMiPassMouseClicked
        
        musicaFondo.Stop();
        String[] args = null;
        Recuperacion.main(args);
        this.dispose();
    }//GEN-LAST:event_btnOlvideMiPassMouseClicked

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresarActionPerformed

    
    
    
    
    
    public static boolean verificarUsuario(String hostname, String nickname, String clave){
        
        ConexionMYSQL mysql = new ConexionMYSQL(hostname,"bduno","UsuarioUNO");
        Connection conexion = mysql.conectar();
        String sentenciaSQL = "";
        
        if(conexion!=null)
            try {
                sentenciaSQL = "SELECT nickname, clave FROM usuarios WHERE nickname='"+nickname+"'";
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);

                if(RS.next()){
                    if(nickname.equalsIgnoreCase(RS.getString("nickname")) && clave.equals(RS.getString("clave")))
                        return true;
                }

                return false;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        else
            return false;
    }
    
    private String tab(String strLarga, String strCorta){
        int numespacios=0;
        String espacios="";
        
        numespacios = (strLarga.length()/2)-(strCorta.length()/2);
        
        for (int i = 0; i < numespacios; i++)
            espacios = espacios + " ";
        
        return espacios;
    }
    
    public boolean cargarUsuario(String nickname){
        
        
        ConexionMYSQL mysql = new ConexionMYSQL(hostname,"bduno","UsuarioUNO");
        Connection conexion = mysql.conectar();
        String sentenciaSQL = "";
        
        if(conexion!=null)
            try {
                sentenciaSQL = "SELECT nombre, apellido, nickname, clave, imagen, email, estado FROM usuarios WHERE nickname='"+nickname+"'";
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);

                if(RS.next()){
                    
                    this.usuarioActual = new Usuario(RS.getString("nombre"), RS.getString("apellido"), RS.getString("nickname"), 
                                                     RS.getString("clave"), RS.getString("imagen"), RS.getString("email"), 
                                                     RS.getString("estado"), null);
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
        else
            return false;
    }
    
    
  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnOlvideMiPass;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JComboBox cboServidor;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblNickname;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtNickname;
    private javax.swing.JLabel txtServidor;
    // End of variables declaration//GEN-END:variables
}
