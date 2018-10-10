
package Formularios;

import Conexiones.Cliente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Clase que representa una carta normal es decir, además  posee un número.
 * @author Stalin
 */
public class CartaNormal extends Carta {
    
    private int numero;
    
    
    public CartaNormal(int valor, String color) {
        super(valor,color);
        numero=valor;
        initComponents();
        this.setSize(134, 205); //se selecciona el tamaño del panel
        this.setBackground(Color.black);
    }
    
    public CartaNormal(int valor, String color, Cliente poseedor, Tablero ubicacion) {
        super(valor,color,poseedor,ubicacion);
        numero=valor;
        initComponents();
        this.setSize(134, 205); //se selecciona el tamaño del panel
        this.setBackground(Color.black);
    }
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //JOptionPane.showMessageDialog(this, Integer.toString(this.numero)+" "+this.color+": me pulsaron");
        ubicacion.removeDeMisCartas(Integer.toString(numero),color);
        poseedor.enviar("add,carta,"+poseedor.getMiIp()+",CartaNormal,"+numero+","+color+","+valor);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    //Se crea un método cuyo parámetro debe ser un objeto Graphics
 
    public void paint(Graphics grafico) {
    
    Dimension height = getSize();

    //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa

    ImageIcon Img = new ImageIcon(getClass().getResource("/Imagenes/"+valor+color+".jpg")); 

    //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel

    grafico.drawImage(Img.getImage(), 0, 0, 132, 204, null);

    setOpaque(false);
    super.paintComponent(grafico);
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }


}
