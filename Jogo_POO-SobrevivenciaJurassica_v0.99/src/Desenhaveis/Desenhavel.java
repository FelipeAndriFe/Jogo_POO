package Desenhaveis;

import java.awt.image.BufferedImage;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
 */
public abstract class Desenhavel {
    private int x;
    private int y;
    private final char simbolo;
    private BufferedImage image;
    
    public abstract BufferedImage pegarImage();
    
    public Desenhavel(int x, int y, char simbolo) {
        this.x = x;
        this.y = y;
        this.simbolo = simbolo;
        this.image = null;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public char getSimbolo() {
        return simbolo;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(BufferedImage i) {
        this.image = i;
    }
    
    public void setX(int novoX) {
        this.x = novoX;
    }
    
    public void setY(int novoY) {
        this.y = novoY;
    }
}
