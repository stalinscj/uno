/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Formularios; // 604*567

import Conexiones.ConexionMYSQL;
import Esencial.Imagen;
import Esencial.Sonido;
import Esencial.Usuario;
import static Formularios.Login.hostname;
import static Formularios.Registro.usuarioActual;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author Stalin
 */
public class Consulta extends javax.swing.JFrame {
    
    private Fondo imagenFondo;
    public static String origen="";
    public static String hostname="";
    public static Usuario usuarioActual;
    private final Sonido musicaFondo;
    
    
    /**
     * Creates new form Consulta
     */
    public Consulta() {
        
        imagenFondo = new Fondo("/Imagenes/fondoConsulta.jpg");
        
        initComponents();
        
        this.setSize(imagenFondo.getWidth(),imagenFondo.getHeight());
        
        fondo.add(imagenFondo);
        fondo.setOpaque(false);
        
        

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        musicaFondo = new Sonido(this);
        musicaFondo.play();
        
        
        
        if(cargarUsuario(this.usuarioActual.getNickname())){
            txtNombre.setText(this.usuarioActual.getNombre());
            txtApellido.setText(this.usuarioActual.getApellido());
            txtNickname.setText(this.usuarioActual.getNickname());
            txtEmail.setText(this.usuarioActual.getEmail());
            txtEstado.setText(this.usuarioActual.getEstado());
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtNickname.setEditable(false);
            txtEmail.setEditable(false);
            txtEstado.setEditable(false);
        }else{
            musicaFondo.Stop();
            String[] args=null;
            Sala.hostname = this.hostname;
            Sala.usuarioActual = this.usuarioActual;
            Sala.main(args);
            this.dispose();
        }
        
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
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        panelImagen = new javax.swing.JPanel();
        lblNickname = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtNickname = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        lblpartidasJugadas = new javax.swing.JLabel();
        lblPartidasGanadas = new javax.swing.JLabel();
        lblPartidasPerdidas = new javax.swing.JLabel();
        lblPartidasAbandonadas = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        lblPartidas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondo.setBackground(new java.awt.Color(0, 153, 51));

        lblNombre.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(51, 255, 51));
        lblNombre.setText("Nombre");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        lblApellido.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblApellido.setForeground(new java.awt.Color(51, 255, 51));
        lblApellido.setText("Apellido");

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        lblNickname.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblNickname.setForeground(new java.awt.Color(51, 255, 51));
        lblNickname.setText("Nickname");

        lblEmail.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(51, 255, 51));
        lblEmail.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNicknameActionPerformed(evt);
            }
        });

        lblEstado.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(51, 255, 51));
        lblEstado.setText("Estado");

        btnVolver.setText("Volver");
        btnVolver.setMaximumSize(new java.awt.Dimension(71, 23));
        btnVolver.setMinimumSize(new java.awt.Dimension(71, 23));
        btnVolver.setPreferredSize(new java.awt.Dimension(71, 23));
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVolverMouseClicked(evt);
            }
        });

        lblpartidasJugadas.setFont(new java.awt.Font("Kristen ITC", 1, 16)); // NOI18N
        lblpartidasJugadas.setForeground(new java.awt.Color(51, 255, 51));
        lblpartidasJugadas.setText("Jugadas:");

        lblPartidasGanadas.setFont(new java.awt.Font("Kristen ITC", 1, 16)); // NOI18N
        lblPartidasGanadas.setForeground(new java.awt.Color(51, 255, 51));
        lblPartidasGanadas.setText("Ganadas:");

        lblPartidasPerdidas.setFont(new java.awt.Font("Kristen ITC", 1, 16)); // NOI18N
        lblPartidasPerdidas.setForeground(new java.awt.Color(51, 255, 51));
        lblPartidasPerdidas.setText("Perdidas:");

        lblPartidasAbandonadas.setFont(new java.awt.Font("Kristen ITC", 1, 16)); // NOI18N
        lblPartidasAbandonadas.setForeground(new java.awt.Color(51, 255, 51));
        lblPartidasAbandonadas.setText("Abandonadas:");

        lblPartidas.setFont(new java.awt.Font("Kristen ITC", 1, 16)); // NOI18N
        lblPartidas.setForeground(new java.awt.Color(51, 255, 51));
        lblPartidas.setText("Partidas");

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPartidasPerdidas)
                            .addComponent(lblPartidasGanadas)
                            .addComponent(lblpartidasJugadas)
                            .addComponent(lblPartidasAbandonadas)
                            .addComponent(lblPartidas)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, fondoLayout.createSequentialGroup()
                                    .addComponent(lblNickname)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNickname))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, fondoLayout.createSequentialGroup()
                                    .addComponent(lblApellido)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 249, Short.MAX_VALUE)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellido)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNickname)
                    .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addComponent(lblPartidas)
                                .addGap(21, 21, 21)
                                .addComponent(lblpartidasJugadas)
                                .addGap(21, 21, 21)
                                .addComponent(lblPartidasGanadas)
                                .addGap(21, 21, 21)
                                .addComponent(lblPartidasPerdidas))
                            .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(lblPartidasAbandonadas))
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

        
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed

        
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed

       
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNicknameActionPerformed

        
    }//GEN-LAST:event_txtNicknameActionPerformed

    private void btnVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseClicked

        musicaFondo.Stop();
        String[] args=null;
        Sala.hostname = this.hostname;
        Sala.usuarioActual = this.usuarioActual;
        Sala.main(args);
        this.dispose();
        

    }//GEN-LAST:event_btnVolverMouseClicked

    
    
    
    
    public boolean cargarUsuario(String nickname){
        
        ConexionMYSQL mysql = new ConexionMYSQL(hostname,"bduno","UsuarioUNO");
        Connection conexion = mysql.conectar();
        String sentenciaSQL = "";
        
        ImageIcon imagenTemporal = null;
        
        if(conexion!=null)
            try {
                sentenciaSQL = "SELECT nombre, apellido, nickname, clave, imagen, email, estado,"
                             + "partidasJugadas, partidasGanadas, partidasPerdidas, partidasAbandonadas "
                             + "FROM usuarios WHERE nickname='"+nickname+"'";
                Statement ST = conexion.createStatement();
                ResultSet RS = ST.executeQuery(sentenciaSQL);

                if(RS.next()){
                    
                    this.usuarioActual = new Usuario(RS.getString("nombre"), RS.getString("apellido"), RS.getString("nickname"), 
                                                     RS.getString("clave"), RS.getString("imagen"), RS.getString("email"),
                                                     RS.getString("estado"), null);
                    
                    
                                                                                                
                    lblpartidasJugadas.setText(lblpartidasJugadas.getText()  +" "+ RS.getString("partidasJugadas"));
                    lblPartidasGanadas.setText(lblPartidasGanadas.getText()  +" "+ RS.getString("partidasGanadas"));
                    lblPartidasPerdidas.setText(lblPartidasPerdidas.getText()+" "+ RS.getString("partidasPerdidas"));
                    lblPartidasAbandonadas.setText(lblPartidasAbandonadas.getText()+" "+ RS.getString("partidasAbandonadas") );
                    
                    
                    panelImagen.removeAll();
                    imagenTemporal = new ImageIcon(Imagen.decodeToImage(usuarioActual.getImagen()));
                    panelImagen.add(new Fondo(150,150, imagenTemporal));
                    
                    
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
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNickname;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPartidas;
    private javax.swing.JLabel lblPartidasAbandonadas;
    private javax.swing.JLabel lblPartidasGanadas;
    private javax.swing.JLabel lblPartidasPerdidas;
    private javax.swing.JLabel lblpartidasJugadas;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNickname;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
