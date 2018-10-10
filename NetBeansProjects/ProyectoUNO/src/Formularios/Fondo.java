package Formularios;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
 
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Fondo extends javax.swing.JPanel {
    
    // Atributo que guardara la imagen de Background que le pasemos.
    private String rutaImagen;
    private URL urlImagen;
    private ImageIcon imagenFondo;
    private int x;
    private int y;
    
    public Fondo(String rutaImagen) {
        this.rutaImagen = rutaImagen;
        imagenFondo = new ImageIcon(getClass().getResource(rutaImagen));
        this.x = imagenFondo.getIconWidth();
        this.y = imagenFondo.getIconHeight();
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
    public Fondo(URL urlImagen) {
        this.urlImagen= urlImagen;
        imagenFondo = new ImageIcon(urlImagen);
        this.x = imagenFondo.getIconWidth();
        this.y = imagenFondo.getIconHeight();
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
    public Fondo(ImageIcon imagen) {
        
        imagenFondo = imagen;
        this.x = imagenFondo.getIconWidth();
        this.y = imagenFondo.getIconHeight();
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
   
    
    
    public Fondo(int x, int y, String rutaImagen) {
        this.rutaImagen = rutaImagen;
        imagenFondo = new ImageIcon(getClass().getResource(rutaImagen));
        this.x = x;
        this.y = y;
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
    public Fondo(int x, int y, URL urlImagen) {
        this.urlImagen= urlImagen;
        imagenFondo = new ImageIcon(urlImagen);
        this.x = x;
        this.y = y;
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
    public Fondo(int x, int y, ImageIcon imagen) {
        
        this.imagenFondo = imagen;
        this.x = x;
        this.y = y;
        initComponents();
        this.setSize(x,y); //se selecciona el tamaño del panel
        
    }
    
    
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 89, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    // Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
    public void paint(Graphics grafico) {
    
    Dimension height = getSize();

    //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa

   

    //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel

    grafico.drawImage(getImagenFondo().getImage(), 0, 0, x, y, null);

    setOpaque(false);
    super.paintComponent(grafico);
    }

    /**
     * @return the rutaImagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * @param rutaImagen the rutaImagen to set
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * @return the imagenFondo
     */
    public ImageIcon getImagenFondo() {
        return imagenFondo;
    }

    /**
     * @param imagenFondo the imagenFondo to set
     */
    public void setImagenFondo(ImageIcon imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

}
