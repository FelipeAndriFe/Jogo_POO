package Desenhaveis;

import Sistemas.Tabuleiro;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Eduardo Ramos
 * @author Felipe Ferreira
 * @author Rafael Bermudes
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
    
    @Override
    public Desenhavel mover(Tabuleiro tabuleiro) {
        return null;
    }
}
