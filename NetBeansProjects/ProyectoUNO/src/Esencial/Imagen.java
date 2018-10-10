package Esencial;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Clase que permite codificar una imagen en una cadena de caracteres y decodificar una cadena de caracteres en una imagen.
 * @author Stalin
 */

public class Imagen {

    /**
     * Decodifica una cadena de caracteres en una imagen
     * @param imageString The string to decode
     * @return imagen decodificada
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Codifica una imagen en una cadena de caracteres
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return cadena codificada
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    
    
    
    /*
    
    public static void main (String args[]) throws IOException {
        
        
        //Imagen.crearArchivoAgregar("centrum", "ururyryryrhrhr");
        
        
        
        
        
        BufferedImage img = ImageIO.read(new File("../scr/Imagenes/imagenAuxiliar.jpg"));
        BufferedImage newImg;
        String imgstr;
        imgstr = encodeToString(img, "png");
        //System.out.println(imgstr);
        newImg = decodeToImage(imgstr);
        ImageIO.write(newImg, "png", new File("C:/Users/Rodolfo/Pictures/imagenAuxiliar.jpg"));
        System.out.println(imgstr);
        // Test image to string and string to image finish 
    }
    */
    
}