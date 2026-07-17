/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import java.awt.image.BufferedImage;

/**
 *
 * @author felip
 */
public abstract class Desenhavel {
    private int x;
    private int y;
    private final char simbolo;
    private BufferedImage image;
    
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
