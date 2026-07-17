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
public class Rex extends Dinossauro {
    @Override
    public BufferedImage pegarImage() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/Imagens/Rex.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public Rex(int x, int y, char simbolo, int hp, int dano, int velocidade, boolean tomaDanoDeSoco, boolean tomaDanoDeArma) {
        super(x, y, simbolo, hp, dano, velocidade, tomaDanoDeSoco, tomaDanoDeArma);
    }
}
