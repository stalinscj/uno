
package Formularios;

import Conexiones.Cliente;
import javax.swing.JFrame;

/**
 * Clase abstracta que servirá de base para la creación de los dos tipos de cartas necesarios para el desarrollo del juego(Normales y Especiales)
 * @author Stalin
 */
public class Carta extends javax.swing.JPanel {

    protected int valor;
    protected String color;
    protected Cliente poseedor;
    protected Tablero ubicacion;
    
    public Carta() {
        initComponents();
    }
    
    
    
    public Carta(int valor, String color) {
        initComponents();
        this.valor=valor;
        this.color=color;
    }
    
    public Carta(int valor, String color, Cliente poseedor, Tablero ubicacion) {
        initComponents();
        this.valor=valor;
        this.color=color;
        this.poseedor=poseedor;
        this.ubicacion = ubicacion;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public int getValor() {
        return valor;
    }

    
    public void setValor(int valor) {
        this.valor = valor;
    }

    
    public String getColor() {
        return color;
    }

    
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the poseedor
     */
    public Cliente getPoseedor() {
        return poseedor;
    }

    /**
     * @param poseedor the poseedor to set
     */
    public void setPoseedor(Cliente poseedor) {
        this.poseedor = poseedor;
    }

    /**
     * @return the ubicacion
     */
    public Tablero getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Tablero ubicacion) {
        this.ubicacion = ubicacion;
    }

    
    
    
}
