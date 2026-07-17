/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desenhaveis;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author felip
 */
public class Blank extends Desenhavel {
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Blank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public Blank(int x, int y) {
        super(x, y, '0');
        this.setImage(this.pegarImage());
    }
}
